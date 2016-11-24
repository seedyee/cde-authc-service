package io.cde.authc.domaim;

/**
* @author lcl
* @version time 2016年11月14日上午11:06:46
*
*/
public class ErrorModel {

    /**
    * 错误码.
    */
    private int code;
    /**
    * 错误信息.
    */
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
