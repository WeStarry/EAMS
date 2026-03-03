package com.example.eams.mapper;

import com.example.eams.entity.Asset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class AssetMapperTest {

    @Autowired
    private AssetMapper assetMapper; // 这里如果有红线报错，不用管，是IDEA误报，能运行

    @Test
    public void testInsert() {
        System.out.println("====== 开始测试资产入库 ======");

        // 1. 准备数据
        Asset asset = new Asset();
        asset.setAssetCode("ZC" + System.currentTimeMillis()); // 随机编号
        asset.setAssetName("联想ThinkBook 16+");
        asset.setCategoryId(101L);
        asset.setAssetStatus(0); // 闲置
        asset.setModel("i9-13900H/32G/1T");
        asset.setPosition("B区-2排");
        asset.setPrice(new BigDecimal("7999.00"));
        asset.setPurchaseDate(LocalDate.now());
        asset.setCreateBy("测试员");
        asset.setRemark("自动化单元测试数据");

        // 2. 执行插入
        int rows = assetMapper.insert(asset);

        // 3. 验证结果
        System.out.println("插入行数：" + rows);
        System.out.println("生成的主键ID：" + asset.getId());

        Assertions.assertEquals(1, rows, "应该成功插入1条数据");
        Assertions.assertNotNull(asset.getId(), "插入后主键ID不应该为空");

        System.out.println("====== 测试通过！数据库连接正常！ ======");
    }
}