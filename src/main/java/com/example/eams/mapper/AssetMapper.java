package com.example.eams.mapper;

import com.example.eams.entity.Asset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface AssetMapper {

    /**
     * 新增资产
     */
    int insert(Asset asset);


    // ... 之前写的 insert 方法保留 ...

    /**
     * 1. 根据关键词查询总条数
     */
    Long countByCondition(@Param("keyword") String keyword);

    /**
     * 2. 根据关键词分页查询数据
     * @param keyword 搜索词
     * @param offset 从第几条开始查 (偏移量)
     * @param pageSize 查多少条
     */
    List<Asset> selectPageByCondition(@Param("keyword") String keyword,
                                      @Param("offset") int offset,
                                      @Param("pageSize") int pageSize);

    /**
     * 逻辑删除资产
     * @param id 资产主键
     * @return 影响的行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 动态修改资产信息
     * @param asset 包含要修改字段的资产对象（必须包含 id）
     * @return 影响的行数
     */
    int updateAsset(Asset asset);

    /** 1. 查询当前资产的库存数量 */
    @Select("SELECT stock FROM sys_asset WHERE id = #{id} AND is_deleted = 0")
    Integer getStockById(@Param("id") Long id);

    /** 2. 扣减库存 (注意：WHERE 条件里加了 stock > 0，这是数据库层面的最后一道防线！) */
    @Update("UPDATE sys_asset SET stock = stock - 1 WHERE id = #{id} AND stock > 0 AND is_deleted = 0")
    int reduceStock(@Param("id") Long id);

    /**
     * 单独修改资产状态
     */
    @Update("UPDATE sys_asset SET asset_status = #{assetStatus}, update_time = NOW() WHERE id = #{id} AND is_deleted = 0")
    int updateAssetStatus(@Param("id") Long id, @Param("assetStatus") Integer assetStatus);
}