package com.example.eams.controller;

import com.example.eams.common.PageResult;
import com.example.eams.common.Result;
import com.example.eams.entity.Asset;
import com.example.eams.entity.dto.AssetQueryDTO;
import com.example.eams.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

// 声明这是一个 Controller 类，专门用来处理与资产相关的 HTTP 请求
@RestController
@CrossOrigin // 必须加！允许前端 5173 端口跨域请求当前接口
public class AssetController {

    @Autowired
    private AssetService assetService;

    // 1. 明确声明只接收 POST 请求，对应前端的 method: 'POST'
    @PostMapping("/test/add")
    // 2. @RequestBody 会触发 Jackson 引擎，自动将 HTTP Body 里的 JSON 字符串反序列化并赋值给 Asset 对象
    public Result<Long> testAdd(@RequestBody Asset asset) {
        try {
            // 此时 asset 对象内部的 assetName, model, price 已经被成功赋上了前端传来的值
            // 将这个装载了数据的对象传递给 Service 层处理
            Long newId = assetService.addAsset(asset);
            return Result.success(newId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, "保存失败: " + e.getMessage());
        }
    }
    @PostMapping("/test/list")
    public Result<PageResult<Asset>> listAssets(@RequestBody AssetQueryDTO queryDTO) {
        try {
            // 1. 将前端送来的装有 pageNo, pageSize, keyword 的快递盒，交给 Service 拆解运算
            PageResult<Asset> pageObj = assetService.getAssetPage(queryDTO);

            // 2. 运算完毕，用最外层的 Result 标准绿皮车厢装载，发回给前端
            return Result.success(pageObj);

        } catch (Exception e) {
            // 捕获可能出现的数据库异常或空指针异常，防止后台直接崩溃死机
            e.printStackTrace();
            return Result.error(500, "查询资产列表失败：" + e.getMessage());
        }
    }


    // ... 前面的代码保留 ...

    /**
     * 删除资产接口 (RESTful 风格)
     * 请求路径示例: DELETE http://localhost:1111/test/delete/5
     */
    @DeleteMapping("/test/delete/{id}")
    public Result<String> deleteAsset(@PathVariable("id") Long id) {
        try {
            // 把前端传来的 ID 交给 Service 执行删除逻辑
            assetService.deleteAsset(id);
            return Result.success("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, e.getMessage());
        }
    }

    // ... 前面的代码保留 ...

    /**
     * 修改资产接口 (RESTful 风格使用 PUT)
     */
    @PutMapping("/test/update")
    public Result<String> updateAsset(@RequestBody Asset asset) {
        // 没有任何 try-catch！直接调 Service！
        // 如果这里发生了异常，会自动飞到 GlobalExceptionHandler 里去处理！
        assetService.updateAsset(asset);
        return Result.success("修改成功！");
    }
}