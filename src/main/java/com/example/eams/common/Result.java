package com.example.eams.common;

import lombok.Data;

@Data
public class Result<T> {
//    业务代码，诸如404  not found*/
    private Integer code;

//    弹窗显示正确或者错误信息
    private String msg;

//    实际返回的数据
    private T data;
//    无参构造函数
    private Result() {}
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(); // 1. 在堆内存中实例化对象
        result.setCode(200);               // 2. 赋值状态码
        result.setMsg("操作成功");       // 3. 赋值提示语
        result.setData(data);              // 4. 赋值业务数据
        return result;                     // 5. 返回对象引用
    }
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
