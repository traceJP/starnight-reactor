package com.tracejp.starnight.reactor.entity.base;

import com.tracejp.starnight.reactor.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.MultiValueMap;

/**
 * <p> 分页查询条件接口 <p/>
 *
 * @author traceJP
 * @since 2023/10/18 10:30
 */
public interface IQueryPageRequest<T> extends IQuery<T> {

    /**
     * 获取页码
     */
    Integer getPageNum();

    /**
     * 获取每页数量
     */
    Integer getPageSize();

    /**
     * 转换为分页对象 - 复杂分页
     */
    PageRequest getPageRequest();

    @AllArgsConstructor
    abstract class QueryPageRequest<T> implements IQueryPageRequest<T> {

        protected final MultiValueMap<String, String> queryParams;

        private final ConversionService conversionService = ApplicationConversionService.getSharedInstance();

        @Override
        public Integer getPageNum() {
            var num = queryParams.getFirst("pageNum");
            if (StringUtils.hasText(num)) {
                return conversionService.convert(num, Integer.class);
            }
            return 0;
        }

        @Override
        public Integer getPageSize() {
            var size = queryParams.getFirst("pageSize");
            if (StringUtils.hasText(size)) {
                return conversionService.convert(size, Integer.class);
            }
            return 0;
        }

        @Override
        public PageRequest getPageRequest() {
            return PageRequest.of(getPageNum(), getPageSize());
        }

    }

}
