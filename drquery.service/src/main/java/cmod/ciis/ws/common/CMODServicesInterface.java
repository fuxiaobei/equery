/**
 * 
 */
package cmod.ciis.ws.common;

/**
 * @author liujs3
 *
 */
public class CMODServicesInterface {
public String  doServices(
		String a1,String a2,String a3,String a4,String a5,String a6,String a7,
		String a8,String a9,String a10,String a11,String a12,String a13,String a14){
	return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
			"<Results>"+
	    	"<ErrorCode>0</ErrorCode>"+
	"<ErrorMessage>接口反馈码内容说明</ErrorMessage>"+
	"<CustomerFlag>号码标识（可以为空） </CustomerFlag>"+
	    	"<Content>a"+
	+System.currentTimeMillis()+"1|2|3|4|5|6|7|20131213172395|15110272143|10|11|12|13|14|15|16|17|18|19|20|21|22\r\na"+
	+System.currentTimeMillis()+"2|4|3|4|5|6|7|20131213172396|15110272143|10|11|12|13|14|15|16|17|18|19|20|21|22\r\na"+
	System.currentTimeMillis()+"3|5|3|4|5|6|7|20131213172397|15110272143|10|11|12|13|14|15|16|17|18|19|20|21|22"+
	"</Content>"+
	"</Results>";
}
}
