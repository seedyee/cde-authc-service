package io.cde.authc.tools;

import io.cde.authc.domaim.Model;
import io.cde.authc.domaim.Error;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liaofangcai on 11/15/16.
 */
public class ResultUtils {

  private static Model model;

  private static Error error;
  /**
   * 返回数据和null错误对象
   * @param object
   * @return
   */
  public static Object result(Object object) {
    model = new Model();
    model.setResult(object);
    model.setError(null);
    return model;
  }
  /**
   * 返回错误对象
   * @param code
   * @param message
   * @return
   */
  public static Object resultError(int code, String message) {
    Map<String, Error> resultError = new HashMap<>();
    error = new Error();
    error.setCode(code);
    error.setMessage(message);
    resultError.put("error", error);
    return resultError;
  }
  /**
   * 操作成功返回一个null错误对象
   * @return
   */
  public static Object resultNullError(){
    Map<String, Error> resultError = new HashMap<>();
    resultError.put("error", null);
    return resultError;
  }
}
