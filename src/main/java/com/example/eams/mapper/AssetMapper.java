package com.example.eams.mapper;

import com.example.eams.entity.Asset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
}