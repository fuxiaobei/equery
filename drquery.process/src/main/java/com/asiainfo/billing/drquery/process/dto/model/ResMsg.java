/**
 * 
 */
package com.asiainfo.billing.drquery.process.dto.model;

/**
 * @author yanghui8
 *
 */
public class ResMsg {
	/**返回状态码：0：成功，-1：失败**/
	private String retCode;
	/**返回给用户的提示信息**/
	private String hint;
	/**详细错误信息**/
	private String errorMsg;
	/**错误编码，对应用户的提示信息**/
	private String errorCode;
	
	
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

    public static ResMsg buildSuccess() {
        ResMsg msg = new ResMsg(); //返回给上层的日志信息
        msg.setRetCode("0");
        msg.setErrorMsg("");
        msg.setHint("Request Success!");
        return msg;
    }
	
	
}
