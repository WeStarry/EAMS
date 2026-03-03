package com.example.eams.common.exception;

import com.example.eams.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理中心
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * 它会监听所有 Controller 抛出的异常，并将处理结果转换为 JSON 响应给前端
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1. 拦截我们刚刚自定义的“业务异常” (BusinessException)
     */
    @ExceptionHandler(BusinessException.class)
    public Result<String> handleBusinessException(BusinessException e) {
        // 提取我们在 Service 里写好的错误码和错误信息，包装成统一的 Result 格式返回
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 2. 拦截所有不可预知的“系统异常” (Exception 兜底)
     * 比如：空指针异常 (NullPointerException)、数据库连接断开等
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleSystemException(Exception e) {
        // 在后台控制台打印出完整的红字报错，方便程序员排查 bug
        e.printStackTrace();

        // 绝对不能把包含系统底层逻辑的报错代码返回给前端，统一提示“系统异常”
        return Result.error(500, "系统繁忙，请稍后再试！错误原因：" + e.getMessage());
    }
}