package com.example.eams.service;

import com.example.eams.common.PageResult;
import com.example.eams.common.exception.BusinessException;
import com.example.eams.entity.Asset;
import com.example.eams.entity.dto.AssetQueryDTO;
import com.example.eams.mapper.AssetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetMapper assetMapper;

    // 接收 Controller 传过来的 asset 对象
    public Long addAsset(Asset asset) {
        // 前端只传了名称、型号、价格。这里需要通过程序逻辑补全数据库表要求的其他字段
        asset.setAssetCode("ZC" + System.currentTimeMillis());
        asset.setAssetStatus(0); // 默认为闲置状态
        asset.setIsDeleted(0);
        asset.setCreateTime(LocalDateTime.now());
        asset.setCreateBy("Admin");

        // 调用 MyBatis 将对象的所有属性映射为 SQL 执行持久化
        assetMapper.insert(asset);

        // 返回自增主键 ID
        return asset.getId();
    }
    public PageResult<Asset> getAssetPage(AssetQueryDTO queryDTO) {
        // 1. 获取参数，并做简单的防卫性校验（防止前端传 null 或者负数）
        int pageNo = (queryDTO.getPageNo() == null || queryDTO.getPageNo() < 1) ? 1 : queryDTO.getPageNo();
        int pageSize = (queryDTO.getPageSize() == null || queryDTO.getPageSize() < 1) ? 10 : queryDTO.getPageSize();
        String keyword = queryDTO.getKeyword();

        // 2. 核心数学运算：计算数据库需要的 offset (偏移量)
        // 公式：跳过的条数 = (当前页码 - 1) * 每页条数
        // 比如第 1 页：(1-1)*10 = 0，从第0条开始查
        // 比如第 2 页：(2-1)*10 = 10，跳过前10条，从第11条开始查
        int offset = (pageNo - 1) * pageSize;

        // 3. 呼叫 Mapper，执行第一条 SQL：查总数
        Long total = assetMapper.countByCondition(keyword);

        // 4. 【企业级性能优化点】
        // 如果查出来的总数是 0，说明根本没符合条件的数据
        // 我们就直接返回一个空的分页对象，没必要再去执行第二条 SQL 查列表了，节约数据库性能！
        if (total == null || total == 0) {
            return new PageResult<>(0L, new ArrayList<>());
        }

        // 5. 呼叫 Mapper，执行第二条 SQL：查出真实的 10 条数据
        List<Asset> records = assetMapper.selectPageByCondition(keyword, offset, pageSize);

        // 6. 完美组装：把总数和数据列表塞进 PageResult 套娃里，返回给 Controller
        return new PageResult<>(total, records);
    }
    /**
     * 根据 ID 逻辑删除资产
     */
    public void deleteAsset(Long id) {
        // 调用我们刚刚写的精准 update 语句
        int rows = assetMapper.deleteById(id);

        // 如果 rows == 0，说明数据库里根本没有这个 ID 的数据，或者它早就被删了
        if (rows == 0) {
            throw new RuntimeException("删除失败：该资产不存在或已被删除");
        }
    }

    /**
     * 修改资产信息
     */
    public void updateAsset(Asset asset) {
        // 1. 极致的防御性编程：没有 ID 绝对不能执行更新，否则会造成全表更新的灾难！
        if (asset.getId() == null) {
            throw new RuntimeException("修改失败：资产主键 ID 不能为空");
        }

        // 2. 调用 Mapper 执行动态 SQL
        int rows = assetMapper.updateAsset(asset);

        // 之前是 throw new RuntimeException("...");
        // 现在改成：
        if (rows == 0) {
            throw new BusinessException("修改失败：该资产不存在或已被删除");
        }
    }
}