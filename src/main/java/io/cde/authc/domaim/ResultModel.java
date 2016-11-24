package io.cde.authc.domaim;

/**
* @author lcl
* @version time 2016年11月14日上午11:06:17
*
*/
public class ResultModel {
    /**
    * 返回结果对象.
    */
    private Object result;
    /**
    * 返回错误对象.
    */
    private ErrorModel error;

    public Object getResult() {
        return result;
    }

    public void setResult(final Object result) {
        this.result = result;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(final ErrorModel error) {
        this.error = error;
    }
}
