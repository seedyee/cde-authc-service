package io.cde.authc.tools;

import java.util.HashMap;
import java.util.Map;

import io.cde.authc.domaim.ResultModel;
import io.cde.authc.domaim.ErrorModel;

/**
 * @author 作者 Fangcai Liao.
 * @version 创建时间：Nov 3, 2016 2:31:44 PM.
 * 类说明.
 */
public class ResultUtils {

    private static ResultModel model;

    private static ErrorModel error;
    /**
    * 返回数据和null错误对象.
    * @param object this object.
    * @return this model.
    */
    public static Object result(final Object object) {
        model = new ResultModel();
        model.setResult(object);
        model.setError(null);
        return model;
    }
    /**
    * 返回错误对象.
    * @param code this code.
    * @param message this message.
    * @return this Object.
    */
    public static Object resultError(final int code, final String message) {
        final Map<String, ErrorModel> resultError = new HashMap<>();
        error = new ErrorModel();
        error.setCode(code);
        error.setMessage(message);
        resultError.put("error", error);
        return resultError;
    }
    /**
    * 操作成功返回一个null错误对象.
    * @return this Object.
    */
    public static Object resultNullError() {
        final Map<String, ErrorModel> resultError = new HashMap<>();
        resultError.put("error", null);
        return resultError;
    }
}
