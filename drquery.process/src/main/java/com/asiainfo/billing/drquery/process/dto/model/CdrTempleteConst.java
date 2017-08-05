package com.asiainfo.billing.drquery.process.dto.model;

public class CdrTempleteConst {

	public static final String CUST_COUNT = "CUST_COUNT"; // 客户个数
	public static final String CUST_DESC = "CUST_DESC"; // 客户描述
	public static final String CUST_CDR = "CUST_CDR"; // 客户详单

	public class CUST_DESC_ITEM {

		public static final String CUST_NAME = "CUST_NAME"; // 客户名称
		public static final String CUST_ID = "CUST_ID"; // 客户编号
		public static final String CERTIFICATE_TYPE = "CERTIFICATE_TYPE"; // 证件类型
		public static final String CERTIFICATE_CODE = "CERTIFICATE_CODE"; // 证件号码
		public static final String QUERY_CODE = "QUERY_CODE"; // 查询号码，如手机号、固话号等
		public static final String QUERY_CYCLE = "QUERY_CYCLE"; // 查询周期，即详单的账期
		public static final String QUERY_DATE = "QUERY_DATE"; // 查询日期

	}

	public class CUST_BLOCK {

		public static final String CDR_SUMMARY = "CDR_SUMMARY"; // 一个区域的汇总
		public static final String CDR_DETAILS = "CDR_DETAILS"; // 一个区域的详单集合

		public class SUMMARY_ITEM {
			public static final String SUM_CODE = "SUM_CODE"; //
			public static final String SUM_NAME = "SUM_NAME"; //
			public static final String SUM_VALUE = "SUM_VALUE"; //
			public static final String SUM_TYPE = "SUM_TYPE"; // 通话详单、短信或彩信详单等类型
			public static final String SUM_SUB_TYPE = "SUM_SUB_TYPE"; // 上月 、
																		// 当月到昨日
																		// 、 当日
		}
	}

	public class FTP_PATH_CODE {
		public static final String PBX_HIS_CDR = "PBX_HIS_CDR";
		public static final String GRP_HIS_CDR = "GRP_HIS_CDR";
		public static final String YW400_HIS_CDR = "YW400_HIS_CDR";
		public static final String PBX_CUR_CDR = "PBX_CUR_CDR";
	}

	public class QRY_PARAM_ITEM {
		public static final String CDR_TYPE = "CDR_TYPE"; // 详单类型，后台根据此值过滤话单
		public static final String YEAR_MONTH = "YEAR_MONTH"; // 查询账期
		public static final String QRY_NUM = "QRY_NUM"; // 查询号码
		public static final String EXTRA_FILTER = "EXTRA_FILTER"; // 保密天使、打印等需要额外的过滤标记
		public static final String REGION_CODE = "REGION_CODE";
		public static final String ACTION_ID = "ACTION_ID";
		public static final String WEB_UID = "WEB_UID";
		public static final String WEB_IP = "WEB_IP";
		public static final String WEB_PORT = "WEB_PORT";
		public static final String CLIENT_IP = "CLIENT_IP";
		public static final String OP_ID = "OP_ID";
		public static final String NOTES = "NOTES";

	}

	public static class CDR_SCHEME_CODE {
		public static short DEF_DL_SCHEMEID = 0; // 长显示
		public static short DEF_PL_SCHEMEID = 1; // 长打印
		public static short DEF_DS_SCHEMEID = 2; // 短显示
		public static short DEF_PS_SCHEMEID = 3; // 短打印

	}

	public static class SMS_OPTCODE {
		public static final String SECONDPASSWD = "2010800000001"; // 二次密码下发的短信发送业务码
		public static final String INTERQUERY = "2010800000000"; // 接口查询详单后的短信发送业务码
	}

	public static final long SECONDPASSWD_LIVE_TIME_LENGTH = 30 * 60 * 1000; // 详单二次密码有效时间，30分钟（单位毫秒）
	public static final String SECONDPASSWD_LIVE_TIME_HINT = String
			.valueOf(SECONDPASSWD_LIVE_TIME_LENGTH / (60 * 1000)); // 短信提示中说明的有效时间（单位分钟）

}
