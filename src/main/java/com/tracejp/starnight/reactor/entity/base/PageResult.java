package com.tracejp.starnight.reactor.entity.base;

import lombok.Data;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/14 10:31
 */
@Data
public class PageResult<T> {

    /**
     * 当前页码
     */
    private final int page;

    /**
     * 每页条数
     */
    private final int size;

    /**
     * 总记录数
     */
    private final long total;

    /**
     * 数据列表
     */
    private final List<T> items;

    public PageResult(int page, int size, long total, List<T> items) {
        Assert.isTrue(total >= 0, "Total elements must be greater than or equal to 0");
        if (page < 0) page = 0;
        if (size < 0) size = 0;
        if (items == null) {
            items = Collections.emptyList();
        }
        this.page = page;
        this.size = size;
        this.total = total;
        this.items = items;
    }

}
