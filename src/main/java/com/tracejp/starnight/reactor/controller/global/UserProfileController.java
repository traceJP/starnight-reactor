package com.tracejp.starnight.reactor.controller.global;

import com.tracejp.starnight.reactor.controller.BaseController;
import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.IInputConverter;
import com.tracejp.starnight.reactor.entity.base.LoginUser;
import com.tracejp.starnight.reactor.entity.dto.UserDto;
import com.tracejp.starnight.reactor.entity.param.UpdatePwdParam;
import com.tracejp.starnight.reactor.entity.param.UserProfileParam;
import com.tracejp.starnight.reactor.exception.ControllerException;
import com.tracejp.starnight.reactor.handler.file.IFileHandler;
import com.tracejp.starnight.reactor.handler.token.TokenHandler;
import com.tracejp.starnight.reactor.service.IUserEventLogService;
import com.tracejp.starnight.reactor.service.IUserService;
import com.tracejp.starnight.reactor.utils.BeanUtils;
import com.tracejp.starnight.reactor.utils.SecurityUtils;
import com.tracejp.starnight.reactor.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.tracejp.starnight.reactor.constants.Constants.AVATAR_FILE_SIZE;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/19 8:47
 */
@RequiredArgsConstructor
@RestController
public class UserProfileController extends BaseController {

    private final IUserService userService;

    private final IUserEventLogService userEventLogService;

    private final TokenHandler tokenHandler;

    private final IFileHandler fileHandler;

    @Override
    public RouterFunction<ServerResponse> endpoint() {
        return RouterFunctions.route()
                .GET("/user/profile", this::profile)
                .PUT("/user/profile", this::updateProfile)
                .PUT("/user/profile/updatePwd", this::updatePwd)
                .POST("/user/profile/avatar", this::avatar)
                .build();
    }

    private Mono<ServerResponse> profile(ServerRequest request) {
        return SecurityUtils.getLoginUser()
                .map(loginUser -> new UserDto().convertFrom(loginUser.getUser()))
                .flatMap(super::success);
    }

    private Mono<ServerResponse> updateProfile(ServerRequest request) {
        Mono<UserEntity> profileMono = request.bodyToMono(UserProfileParam.class)
                .map(IInputConverter::convertTo);
        Mono<LoginUser> loginUserMono = SecurityUtils.getLoginUser();
        return Mono.zip(profileMono, loginUserMono)
                .flatMap(tuple -> {
                    var profile = tuple.getT1();
                    var loginUser = tuple.getT2();
                    var user = loginUser.getUser();
                    profile.setId(user.getId());
                    return userService.editToAll(profile)
                            .then(Mono.defer(() -> {
                                // 记录日志
                                userEventLogService.saveAsync(user, "修改了个人信息").subscribe();
                                // 更新缓存
                                BeanUtils.updateProperties(profile, user);
                                return tokenHandler.setLoginUser(loginUser);
                            }));
                })
                .flatMap(super::success);
    }

    private Mono<ServerResponse> updatePwd(ServerRequest request) {
        Mono<UpdatePwdParam> paramMono = request.bodyToMono(UpdatePwdParam.class);
        Mono<UserEntity> userMono = SecurityUtils.getUsername()
                .flatMap(userService::getByUserName);
        return Mono.zip(paramMono, userMono)
                .flatMap(tuple -> {
                    var param = tuple.getT1();
                    var user = tuple.getT2();
                    var password = user.getPassword();
                    if (!SecurityUtils.matchesPassword(param.oldPassword(), password)) {
                        return Mono.error(new ControllerException("旧密码错误"));
                    }
                    if (!SecurityUtils.matchesPassword(param.newPassword(), password)) {
                        return Mono.error(new ControllerException("新密码不能与旧密码相同"));
                    }

                    // 数据库更新
                    String encodePassword = SecurityUtils.encryptPassword(param.newPassword());
                    user.setPassword(encodePassword);
                    return userService.updateById(user)
                            .flatMap(newUser -> {
                                // 记录日志
                                userEventLogService.saveAsync(newUser, "修改了密码信息").subscribe();
                                // 更新缓存
                                return SecurityUtils.getLoginUser()
                                        .doOnNext(loginUser -> loginUser.setUser(newUser))
                                        .flatMap(tokenHandler::setLoginUser);
                            });
                })
                .flatMap(super::success);
    }

    private Mono<ServerResponse> avatar(ServerRequest request) {
        return request.bodyToMono(MultipartFile.class)
                .flatMap(file -> {
                    if (file.isEmpty()) {
                        return Mono.error(new ControllerException("上传图片异常，请联系管理员"));
                    }
                    if (file.getSize() > AVATAR_FILE_SIZE) {
                        return error("上传图片大小不能超过" + AVATAR_FILE_SIZE / 1024 / 1024 + "M");
                    }
                    // 上传
                    Mono<String> uploadMono = fileHandler.uploadFile(file);
                    Mono<LoginUser> loginUserMono = SecurityUtils.getLoginUser();
                    return Mono.zip(uploadMono, loginUserMono)
                            .flatMap(tuple -> {
                                var url = tuple.getT1();
                                var loginUser = tuple.getT2();
                                if (StringUtils.isEmpty(url)) {
                                    return Mono.error(new ControllerException("上传图片失败"));
                                }
                                // 数据库更新
                                var user = loginUser.getUser();
                                user.setAvatarPath(url);
                                return userService.updateById(user)
                                        .flatMap(newUser -> {
                                            // 更新缓存
                                            loginUser.setUser(newUser);
                                            return tokenHandler.setLoginUser(loginUser);
                                        });
                            })
                            .flatMap(super::success);
                });
    }

}
