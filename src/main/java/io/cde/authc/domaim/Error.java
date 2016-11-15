package io.cde.authc.domaim;

/**
* @author lcl
* @createDate 2016年11月14日上午11:06:46
*
*/
public class Error {
  /**
  * 错误码
  */
  private int code;
  /**
  * 错误信息
  */
  private String message;

  public int getCode() {
    return code;
  }
  public void setCode(int code) {
    this.code = code;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
}
