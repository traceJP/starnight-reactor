package com.tracejp.starnight.reactor.handler.file;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.tracejp.starnight.reactor.frame.properties.FileConfigProperties;
import com.tracejp.starnight.reactor.utils.MimeTypeUtils;
import com.tracejp.starnight.reactor.utils.StringUtils;
import com.tracejp.starnight.reactor.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p> minio 实现类 <p/>
 *
 * @author traceJP
 * @since 2023/5/2 13:21
 */
@RequiredArgsConstructor
@Primary
@Component
public class MinioFileHandler implements IFileHandler {

    private static final int PRE_SIGN_URL_EXPIRE = 60 * 1000;

    private final AmazonS3 fileClient;

    private final FileConfigProperties configProperties;

    @Override
    public Mono<String> uploadFile(MultipartFile file) {
        return Mono.fromCallable(() -> {
            // 时间 + 随机数 + 后缀
            String suffix = FileNameUtil.getSuffix(file.getOriginalFilename());
            if (StringUtils.isEmpty(suffix)) {
                suffix = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
            }
            String fileKey = DateUtil.format(new Date(), "yyyyMM")
                    + "_" + UUIDUtils.fastUUID().toString(true)
                    + "." + suffix;
            InputStream inputStream = file.getInputStream();
            fileClient.putObject(configProperties.getBucketName(), fileKey, inputStream, null);
            inputStream.close();
            return getFileUrl(fileKey, configProperties.getBucketName());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Map<String, String>> uploadPreSign(String fileKey, Map<String, String> params) {
        return Mono.fromCallable(() -> {
            Date currentDate = new Date();
            Date expireDate = DateUtil.offsetMillisecond(currentDate, PRE_SIGN_URL_EXPIRE);
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(configProperties.getBucketName(), fileKey)
                    .withExpiration(expireDate)
                    .withMethod(HttpMethod.PUT);
            if (params != null) {
                params.forEach(request::addRequestParameter);
            }
            URL url = fileClient.generatePresignedUrl(request);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("url", url.toString());
            resultMap.put("preview", getFileUrl(fileKey, configProperties.getBucketName()));
            return resultMap;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private String getFileUrl(String fileKey, String bucketName) {
        return configProperties.getUrl() + "/" + bucketName + "/" + fileKey;
    }

}
