package com.asiainfo.billing.drquery.process.core.process;

import com.asiainfo.billing.drquery.exception.BusinessException;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.core.DRCommonProcess;
import com.asiainfo.billing.drquery.process.core.request.CommonDRProcessRequest;
import com.asiainfo.billing.drquery.process.dto.BaseDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * Created by wangkai8 on 15/4/20.
 * this process is for update operation
 */
public class GPRSFlowUnpdateProcess extends DRCommonProcess {

    private final static Log log = LogFactory.getLog(GPRSFlowUnpdateProcess.class);


    private String tablePrefix;//表名前缀

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }


    @Override
    public <E extends BaseDTO> E process(CommonDRProcessRequest rq, MetaModel meta, Map extendParams) throws ProcessException, BusinessException {
//        if("appUpdate".equals(rq.getInterfaceType())) {
//            return appUpdate(rq, meta, extendParams);
//        } else if ("detailUpdate".equals(rq.getInterfaceType())) {
//            return detailUpdate(rq, meta, extendParams);
//        } else {
//            throw new ProcessException("interface type invalid: " + rq.getInterfaceType());
//        }
        return null;

    }


//    public <E extends BaseDTO> E appUpdate(T rq, MetaModel meta, Map extendParams) throws ProcessException, BusinessException {
//        HbaseJdbcHelper jdbcHelper = new PhoenixJdbcHelper();
//        String phone = rq.getParams().get("phoneNo");
//        String startTime = rq.getParams().get("startTime");
//        String endTime = rq.getParams().get("endTime");
//        String prefix = (String) new MD5RowKeyGenerator().generatePrefix(phone);
//        String startKey = prefix + phone + startTime;
//        String stopKey = prefix + phone + endTime + "g";
//        List<String> months = DateUtil.getMonthsBetween(startTime, endTime, "yyyyMMddHHmmss");
//        Configuration conf = Connection.getInstance().getConf();
//        byte[] family = "F".getBytes();
//        byte[] column = "APP_EXT_FLAG".getBytes();
//        byte[] value = "1".getBytes();
//        for(String month : months) {
//            String tableName = tablePrefix + month;
//            try {
//                HTable table = new HTable(conf, tableName.getBytes());
//                List<Put> putList = new ArrayList<Put>();
//                StringBuffer select = new StringBuffer("select id, start_time from ").append(tableName).append(" where ")
//                    .append("id>='").append(startKey).append("' and id<='").append(stopKey).append("' and BUSI_ID='").append(rq.getParams().get("appId")).append("'");
//                log.info(select.toString());
//                List<Map<String, Object>> result = jdbcHelper.executeQuery(select.toString());
//                for(Map<String, Object> record : result) {
//                    Put put = new Put(((String)record.get("ID")).getBytes());
//                    put.add(family, column, value);
//                    putList.add(put);
//                }
//                table.put(putList);
//            } catch (Exception e) {
//                throw new ProcessException("execute update exception", e);
//            }
//        }
//        E dto = (E) new DRResultDTO();
//        ResMsg msg = new ResMsg();
//        msg.setRetCode("0");
//        dto.setResMsg(msg);
//        return dto;
//    }
//
//    public <E extends BaseDTO> E detailUpdate(T rq, MetaModel meta, Map extendParams) throws ProcessException, BusinessException {
//        String rowkey = rq.getParams().get("rowkey");
//        String startTime = rq.getParams().get("startTime");
//        String tableName = tablePrefix + startTime.substring(0,6);
//        Configuration conf = Connection.getInstance().getConf();
//        HbaseJdbcHelper jdbcHelper = new PhoenixJdbcHelper();
//        try {
//            List<Map<String, Object>> result = jdbcHelper.executeQuery("select id,start_time from " + tableName + " where id='" + rowkey + "'");
//            if(result.size() > 0) {
//                HTable table = new HTable(conf, tableName.getBytes());
//                Put put = new Put(rowkey.getBytes());
//                put.add("F".getBytes(), "DETAIL_EXT_FLAG".getBytes(), "1".getBytes());
//                table.put(put);
//            } else {
//                log.warn("rowkey: " + rowkey + " is not exist in table: " + tableName);
//            }
//        } catch (Exception e) {
//            throw new ProcessException("execute update exception", e);
//        }
//        E dto = (E) new DRResultDTO();
//        ResMsg msg = new ResMsg();
//        msg.setRetCode("0");
//        dto.setResMsg(msg);
//        return dto;
//    }
}