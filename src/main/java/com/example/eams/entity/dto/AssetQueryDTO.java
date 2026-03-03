package com.example.eams.entity.dto;

import lombok.Data;

/**
 * 资产查询条件接收类 (DTO)
 * 专门用来接收前端发来的分页参数和搜索关键词
 */
@Data
public class AssetQueryDTO {

    /** 当前页码 (默认给第1页) */
    private Integer pageNo = 1;

    /** 每页显示条数 (默认给10条) */
    private Integer pageSize = 10;

    /** 搜索关键词 (比如按资产名称搜索) */
    private String keyword;
}