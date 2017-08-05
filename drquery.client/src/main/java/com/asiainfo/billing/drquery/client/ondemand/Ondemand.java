package com.asiainfo.billing.drquery.client.ondemand;

import java.util.List;

public interface Ondemand {

    /**
     * @return int（0为正常，-1为异常）
     */
    public int get_billId(
            long llDomainId, //域编号，默认domainId=-1
            long llChannelId, //各个渠道
            String strBillId, //手机号
            String strStartDate, //起始时间 "yyyy-mm-dd"
            String strStopDate, //截止时间 "yyyy-mm-dd"
            String strBillType, //手机业务类型
            List<String>  fieldSeq, //字段列表
            List<String>  billIdSeq, //电话单据列表
            String sErrMsg //错误信息
            );
     /**
     * @return int（0为正常，-1为异常）
     */   
    public int get_odDocDetails
    (
        long    llOperId, //操作员ID默认-1
        long    llOrgId, //组织ID默认-1
        long llDomainId, //域编号，默认domainId=-1
        long            llChannelId,    //各个渠道 
        String strClientIp, //客户端ip地址
        String strFolderName, //根据目录列表中的选中的目录的字符串,默认'Boss'
        String       strBillId, //手机号码
        String strStartDate, //起始时间 "yyyy-mm-dd"
        String strStopDate, //截止时间 "yyyy-mm-dd"
        String strBillType, //手机业务类型
        long  llOperaterType, //0:查询 1:导出 2:过滤
        long llStartLine, //返回记录起始行，默认-1
        long llStopLine, //返回记录结束行，默认-1
        String strFilterField, //过滤字段名，默认为空字符串
        String strFilterValue, //过滤字段值，默认为空字符串
        long llRecordCount, //总记录数
        List<String>   seqCaption, //话单标题行
        List<List<String>>    seqBody, //话单数据主体
        List<String>   seqTail, //话单统计数据
        String    sErrMsg //错误信息
    );    
    
}
