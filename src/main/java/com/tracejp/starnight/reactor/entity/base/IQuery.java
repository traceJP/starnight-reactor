package com.tracejp.starnight.reactor.entity.base;

/**
 * <p> 查询条件接口 <p/>
 *
 * @author traceJP
 * @since 2023/10/18 11:05
 */
public interface IQuery<T> {

    /**
     * 转换为实体
     */
    default T toEntity() {
        throw new UnsupportedOperationException();
    }

}
