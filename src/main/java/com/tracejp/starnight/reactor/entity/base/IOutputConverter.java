package com.tracejp.starnight.reactor.entity.base;


import com.tracejp.starnight.reactor.utils.BeanUtils;

/**
 * <p> 转换抽象基类 domain => 输出dto <p/>
 *
 * @author traceJP
 * @since 2023/4/30 14:53
 */
public interface IOutputConverter<DtoT extends IOutputConverter<DtoT, D>, D> {

    /**
     * 将 domain 转换为 dto
     */
    @SuppressWarnings("unchecked")
    default <T extends DtoT> T convertFrom(D domain) {

        BeanUtils.updateProperties(domain, this);

        return (T) this;
    }

}
