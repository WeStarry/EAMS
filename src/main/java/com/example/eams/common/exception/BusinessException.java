package com.example.eams.common.exception;

/**
 * 自定义业务异常类
 * 当我们的业务逻辑不符合预期时（比如：库存不足、资产已删除等），主动抛出此异常
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    // 默认构造，状态码默认给 500
    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    // 允许自定义状态码和错误信息的构造
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}