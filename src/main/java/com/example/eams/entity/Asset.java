package com.example.eams.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 资产实体类 (对应数据库 sys_asset 表)
 * 严格遵守企业规范：使用 Lombok 简化代码，使用包装类型避免空指针
 */
@Data
public class Asset {
    /** 主键ID */
    private Long id;

    /** 资产编号 (业务主键) */
    private String assetCode;

    /** 资产名称 */
    private String assetName;

    /** 分类ID */
    private Long categoryId;

    /** 状态: 0-闲置, 1-领用中, 2-借用中, 3-维修中, 4-已报废 */
    private Integer assetStatus;

    /** 规格型号 */
    private String model;

    /** 存放位置 */
    private String position;

    /** 采购原值 (使用 BigDecimal 保证金额精度) */
    private BigDecimal price;
    /** 采购日期 */
    private LocalDate purchaseDate;
    /** 维保到期日 */
    private LocalDate expireDate;

    /** 当前使用人ID */
    private Long useUserId;
    /** 当前使用人姓名 (冗余字段) */
    private String useUserName;
    /** 当前使用部门ID */
    private Long useDepartmentId;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 录入人 */
    private String createBy;
    /** 逻辑删除: 0-未删, 1-已删 */
    private Integer isDeleted;
}