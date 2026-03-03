package com.example.eams.common;

import lombok.Data;
import java.util.List;

/**
 * 分页结果封装类
 * 专门用于包裹列表数据和总记录数，最终作为 Result 的 data 返回给前端
 */
@Data
public class PageResult<T> {

    /**
     * 数据库中符合查询条件的总记录数
     */
    private Long total;

    /**
     * 当前页的实际数据列表
     */
    private List<T> records;

    // 提供一个无参构造
    public PageResult() {}

    // 提供一个全参构造
    public PageResult(Long total, List<T> records) {
        this.total = total;
        this.records = records;
    }
}