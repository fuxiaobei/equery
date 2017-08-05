package com.asiainfo.billing.drquery.axis2client;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ailk.oci.ocnosql.client.jdbc.phoenix.PhoenixJdbcHelper;
import com.ailk.oci.ocnosql.common.util.PropertiesUtil;
import com.asiainfo.billing.drquery.client.distributed.DistributedClient;
import com.asiainfo.billing.drquery.client.distributed.ZookeeperException;
public class AxisClient {
	
	public static final JSONObject exception=new JSONObject();
	public static final SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  public static DistributedClient client = null;
	private  Log log = LogFactory.getLog(AxisClient.class);
	boolean processing = false;
	
	public AxisClient() { String resourceName = "client-runtime.properties";
    String quorum = "hbase.zookeeper.quorum";
    String clientport = "hbase.zookeeper.property.clientPort";
    String[] urls = PropertiesUtil.getProperty(resourceName, quorum).split(",");
    String port = PropertiesUtil.getProperty(resourceName, clientport);
    String hosts = "";
    for (String url : urls) {
      if (StringUtils.isNotBlank(url))
        hosts = hosts + url + ":" + port + ",";
    }
    hosts = hosts.substring(0, hosts.length() - 1);
    try {
      if (client == null)
        client = new DistributedClient(hosts, "locks", "seq_", 60000);
    } catch (IOException e) {
      this.log.info("连接zookeeper异常:oc1", e);
    }
  }
	static {
		exception.put("0", "成功");
		exception.put("-1000", "系统升级暂停服务");
		exception.put("-1001", "系统来源认证信息验证不通过");
		exception.put("-1002", "系统密码认证不通过");
		exception.put("-1003", "非法IP地址进入");
		exception.put("-1004", "版本号码验证不通过");
		exception.put("-1005", "手机号码格式验证不通过");
		exception.put("-1006", "客服密码格式验证不通过");
		exception.put("-1007", "业务类型验证不通过");
		exception.put("-1008", "业务子类型验证不通过");
		exception.put("-1009", "查询账期验证不通过");
		exception.put("-1010", "渠道验证不通过");
		exception.put("-1011", "操作员工号验证不通过");
		exception.put("-1012", "交易时间验证不通过");
		exception.put("-1013", "其他输入信息验证不通过");
		exception.put("-1100", "无查询结果");
		exception.put("-1101", "每月1、2号不提供当月月账单查询");
		exception.put("-1102", "当月6号后提供上月账单查询");
		exception.put("-1103", "查询账单月份超过查询范围");
		exception.put("-1104", "查询详单月份超过查询范围");
		exception.put("-1105", "用户账详单内容屏蔽");
		exception.put("-1106", "您已定制详单保护功能，不提供详单内容查询");
		exception.put("-1107", "您的话单量过大，建议登录门户网站进行查询");
		exception.put("-1108", "客服密码验证不通过");
		exception.put("-1109", "您已定制详单粉碎功能，不提供详单内容查询");
	}

	public  void excute(String args[]) throws Exception{
		 //RPCServiceClient是RPC方式调用
        RPCServiceClient client=null;
		try {
			client = new RPCServiceClient();
		} catch (AxisFault e1) {
			log.info("axis2 调用异常", e1);
		}
        Options options = client.getOptions();
        //TODO 设置调用WebService的URL
        String address = PropertiesUtil.getProperty("drquery.service/runtime.properties","cmodintohbase.CMODServicesInterface");
        String targetNamespace = PropertiesUtil.getProperty("drquery.service/runtime.properties","cmodintohbase.targetNamespace");
        String method = PropertiesUtil.getProperty("drquery.service/runtime.properties","cmodintohbase.method");
//        String address = "http://10.4.144.190:80/services/CMODServicesInterface";
        EndpointReference epf = new EndpointReference(address);
        options.setTo(epf);
        /**
        * 设置将调用的方法，http://ws.apache.org/axis2是方法
        * 默认（没有package）命名空间，如果有包名
        * 就是http://service.hoo.com 包名倒过来即可
        */
        //TODO 指定调用的方法和传递参数数据
        QName qname = new QName(targetNamespace,method);
         
		//TODO 调用及设置返回值的类型
        Object[] result=new Object[2];
        System.out.println("address="+address);
        System.out.println("targetNamespace="+targetNamespace);
        System.out.println("method="+method);
        System.out.println("qname="+qname);
	    result = client.invokeBlocking(qname, args, new Class[] { String.class });
	    
        System.out.println(result[0]);
//        test rs eg:
//        result[0]="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
//		"<Results>"+
//		    	"<ErrorCode>0</ErrorCode>"+
//		"<ErrorMessage>接口反馈码内容说明</ErrorMessage>"+
//		"<CustomerFlag>号码标识（可以为空） </CustomerFlag>"+
//		    	"<Content>"+
//		"92|2|3|4|5|6|7|20131213172395|15110272143|10|11|12|13|14|15|16|17|18|19|20|21|22\n"+
//		"93|4|3|4|5|6|7|20131213172396|15110272143|10|11|12|13|14|15|16|17|18|19|20|21|22\n"+
//		"94|5|3|4|5|6|7|20131213172397|15110272143|10|11|12|13|14|15|16|17|18|19|20|21|22\n"+
//		"</Content>"+
//		"</Results>";
        //into hbase
        intoHbase(result[0]);
	}
	
	//查询另外一个表
	public  void excute2(String args[]) throws Exception{
		 //RPCServiceClient是RPC方式调用
        RPCServiceClient client=null;
		try {
			client = new RPCServiceClient();
		} catch (AxisFault e1) {
			log.info("axis2 调用异常", e1);
		}
        Options options = client.getOptions();
        //TODO 设置调用WebService的URL
        String address = PropertiesUtil.getProperty("drquery.service/runtime.properties","cmodintohbase.CMODServicesInterface2");
        String targetNamespace = PropertiesUtil.getProperty("drquery.service/runtime.properties","cmodintohbase.targetNamespace2");
        String method = PropertiesUtil.getProperty("drquery.service/runtime.properties","cmodintohbase.method2");
//        String address = "http://10.4.144.190:80/services/CMODServicesInterface";
        EndpointReference epf = new EndpointReference(address);
        options.setTo(epf);
        /**
        * 设置将调用的方法，http://ws.apache.org/axis2是方法
        * 默认（没有package）命名空间，如果有包名
        * 就是http://service.hoo.com 包名倒过来即可
        */
        //TODO 指定调用的方法和传递参数数据
        QName qname = new QName(targetNamespace,method);
         
		//TODO 调用及设置返回值的类型
        Object[] result=new Object[2];
        System.out.println("address="+address);
        System.out.println("targetNamespace="+targetNamespace);
        System.out.println("method="+method);
        System.out.println("qname="+qname);
	    result = client.invokeBlocking(qname, args, new Class[] { String.class });
	    
        System.out.println(result[0]);
//        test rs eg:
//        result[0]="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
//		"<Results>"+
//		    	"<ErrorCode>0</ErrorCode>"+
//		"<ErrorMessage>接口反馈码内容说明</ErrorMessage>"+
//		"<CustomerFlag>号码标识（可以为空） </CustomerFlag>"+
//		    	"<Content>"+
//		"92|2|3|4|5|6|7|20131213172395|15110272143|10|11|12|13|14|15|16|17|18|19|20|21|22\n"+
//		"93|4|3|4|5|6|7|20131213172396|15110272143|10|11|12|13|14|15|16|17|18|19|20|21|22\n"+
//		"94|5|3|4|5|6|7|20131213172397|15110272143|10|11|12|13|14|15|16|17|18|19|20|21|22\n"+
//		"</Content>"+
//		"</Results>";
        //into hbase
        intoHbase2(result[0]);
	}

	private void intoHbase(Object result) throws Exception{
		if(isValidateRs(result.toString())==0){
		//转换成可用结果
        List<Object[]> params=conventRsTosqlPram(result.toString());
        //制造sql
        String[] sqls=getSql(params.size(),"cmodintohbase.tablesql");
        PhoenixJdbcHelper pjh=new PhoenixJdbcHelper();
		pjh.excuteNonQuery(sqls, params, 3000);
		}else if(isValidateRs(result.toString())==-1){
			log.error("xml错误.");
		}else if(isValidateRs(result.toString())==1){
		}
	}
	
	private void intoHbase2(Object result) throws Exception{
		if(isValidateRs(result.toString())==0){
		//转换成可用结果
        List<Object[]> params=conventRsTosqlPram(result.toString());
        //制造sql
        String[] sqls=getSql(params.size(),"cmodintohbase.tablesql2");
        PhoenixJdbcHelper pjh=new PhoenixJdbcHelper();
		pjh.excuteNonQuery(sqls, params, 3000);
		}else if(isValidateRs(result.toString())==-1){
			log.error("xml错误.");
		}else if(isValidateRs(result.toString())==1){
		}
	}
	
	private int isValidateRs(String string) {
		/**
		 * <?xml version="1.0" encoding="UTF-8"?>
		<Results>
		    	<ErrorCode>接口反馈码</ErrorCode>
		<ErrorMessage>接口反馈码内容说明</ErrorMessage>
		<CustomerFlag>号码标识（可以为空） </CustomerFlag>
		    	<Content>
		a字段1|字段2|字段3|字段*|…
		a字段1|字段2|字段3|字段*|…
		a字段1|字段2|字段3|字段*|…
		</Content>
		</Results>

		 */
         SAXReader reader = new SAXReader();
         Document doc=null;
		try {
			doc =reader.read(new ByteArrayInputStream(string.getBytes("UTF-8")));
		} catch (DocumentException e) {
			log.error("返回xml解析错误.oc",e);
		} catch (UnsupportedEncodingException e) {
			log.error("编码异常。oc",e);
		}catch (Exception e) {
			log.error("webservice异常。oc",e);
		}
        Element root = doc.getRootElement();
        String errorCode = root.elementTextTrim("ErrorCode");
        String Content = root.elementTextTrim("Content");
        if(StringUtils.isNotBlank(errorCode)){
        	try {
        		if(errorCode.equals("0")){
        			if(StringUtils.isBlank(Content)){return 1;}else{
        				return 0;
        			}
        			}
        		log.error("xml返回错误，代码为errorCode="+errorCode+" exception:"+exception.get(errorCode));
        		return -1;
			} catch (Exception e) {
				log.error("xml返回错误，代码为errorCode="+errorCode,e);
				return -1;
			}
        }else{return -1;}
	}

  //key=cmodintohbase.tablesql cmodintohbase.tablesql2
	private static String[] getSql(int size,String key) {
		String[] temp=new String[size];
		for(int i=0;i<size;i++){
			temp[i] = PropertiesUtil.getProperty("drquery.service/runtime.properties",key);
		}
		return temp;
	}


	private List<Object[]> conventRsTosqlPram(String string) throws Exception{
		 SAXReader reader = new SAXReader();
         Document doc=null;
		 doc =reader.read(new ByteArrayInputStream(string.getBytes("UTF-8")));
         Element root = doc.getRootElement();
         String content = root.elementTextTrim("Content");
         return coventtoList(content);
	}

	private List<Object[]> coventtoList(String content) {
		if(StringUtils.isNotBlank(content)){
			ArrayList<Object[]> al=new ArrayList<Object[]>();
			String[] contents=content.split(" a");
			for(int i=0;i<contents.length;i++){
				if(i==0){contents[0]=contents[0].substring(1);}
				String[] contentss=contents[i].split("\\|",23);
				String[] contenttemp=new String[contentss.length+1];
				//create rowkey and put back
				contenttemp[0]=contentss[7]+contentss[1]+contentss[0];
				for(int j=1;j<contenttemp.length;j++){
					contenttemp[j]=contentss[j-1];
				}
				al.add((Object[])contenttemp);
			}
			return al;
		}else{
			return null;
		}
	}

	public void scheduler(){
		log.info("schedulering.."+AxisClient.sdf2.format(new Date()));
		if(processing)
			return;
		String zkdata="";
		String initparama = PropertiesUtil.getProperty("drquery.service/runtime.properties","cmodintohbase.initparama");
		String[] a=initparama.split(",", 14);
		a[7]="";// 账期yyyyMMddHHmmss-yyyyMMddHHmmss
		a[9]=AxisClient.sdf2.format(new Date());//19位系统时间?
		String puttime="";
		boolean islock=false;
		try {
			  //get lock
			  islock =client.tryLock();
			  log.info("Does the client get the lock "+islock);
		      if(islock) {
		    	 processing = true;
		    	 //get time
		    	 log.info("client geted the lock..");
		    	 if(!client.exist("/DRQuery/cmod/lastProcessTime")) {
		    		client.ensurePathExists("/DRQuery/cmod/lastProcessTime"); 
		    	 }
		    	 byte[]  b = client.getData("/DRQuery/cmod/lastProcessTime");
		    	 if(b!=null&&b.length != 0){
		    		 zkdata = new String(b);
		    	 }else{
		    		 String initsystime = PropertiesUtil.getProperty("drquery.service/runtime.properties","cmodintohbase.initsystime");
		    		 log.info("/DRQuery/cmod/lastProcessTime 没有值");
		    		 client.setDataForPersistentZNode("/DRQuery/cmod/lastProcessTime",initsystime.getBytes());
		    		 log.info("/DRQuery/cmod/lastProcessTime 设置值="+zkdata);
		    		 zkdata = initsystime;
		    	 }
		    	 java.util.Calendar c=java.util.Calendar.getInstance();
		    	 String delaytime = PropertiesUtil.getProperty("drquery.service/runtime.properties","cmodintohbase.delaytime");
		    	 c.add(java.util.Calendar.SECOND, -Integer.parseInt(delaytime));
		    	 puttime=AxisClient.sdf.format(c.getTime());
		      	 a[7]=zkdata+"-"+puttime;
		      }else{return;}
		      
		//process
        log.info("initparama[0]~a[13]="+a[0]+","+a[1]+","+a[2]+","+a[3]+","+a[4]+","+a[5]+","+a[6]+","+a[7]+","+a[8]+","+a[9]+","+a[10]+","+a[11]+","+a[12]+","+a[13]);
        log.info("excuting webservicecmodintohbase。。。");
		this.excute(a);
		
		//REFUND_LOG
		String initparama2 = PropertiesUtil.getProperty("drquery.service/runtime.properties","cmodintohbase.initparama2");
		String[] a2=initparama.split(",", 14);
		a2[7]="";// 账期yyyyMMddHHmmss-yyyyMMddHHmmss
		a2[9]=AxisClient.sdf2.format(new Date());//19位系统时间?
		a2[7]=zkdata+"-"+puttime;
		this.excute2(a2);
		log.info("cmodintohbase done。。。");
		
		//put time to ZK
		client.setDataForPersistentZNode("/DRQuery/cmod/lastProcessTime",puttime.getBytes());
		
		} catch (ZookeeperException e) {
			log.error("zookeeper连接失败", e);
		} catch (Exception e1) {
			log.error("操作异常被中断。the opr interrupt .e=", e1);
		} finally{
			processing = false;
			if(client != null&&islock) {
				client.unlock();
				log. info("client released the lock.");
			}
		  }
	}
	
	//TODO ocean manual
	public void cmodintohbase(String[] b) throws Exception{
		log.info("excuting cmodintohbase。。。");
		this.excute(b);
		log.info("cmodintohbase done。。。");
	}
	//TEST
	public static void main(String args[]){
		AxisClient ac=new AxisClient();
		try {
			String[] a=new String[14];
			a[0]="BI";
			a[1]="9f69d7ca6da0ecaa1dd88b2b65ac8c78";
			a[2]="V1.1";
			a[3]="";//如果是退费日志：退费日志手机号码,如果是流量消费行为日志：填空
			a[4]="";//客户服务密码-填空
			a[5]="DATA_CB_LOG";//费用核算退费日志接口（REFUND_LOG）
//			流量消费行为日志接口（DATA_CB_LOG）
			a[6]="";//详单类型 填空
//		  	a[7]=zkdata+"-"+puttime;
		  	a[7]="20131001000000"+"-"+"20131002005900";
			a[8]="BI";//渠道
			a[9]=AxisClient.sdf2.format(new Date());//19位系统时间?
			a[10]="BI";//操作员工号
			a[11]="";//投诉工单ID
			a[12]="";//填空
			a[13]="";//填空
			ac.cmodintohbase(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}