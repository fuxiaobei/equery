/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.billing.drquery.web.servlet;

import com.asiainfo.billing.drquery.web.config.Config;
import com.asiainfo.billing.drquery.web.filterUtil.DBHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author wuhe
 */
public class DrrbossBill extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String method = request.getParameter("method");

            if ("businessType".equals(method)) {
                this.getBusinessTypeList(request, response);
            } else if ("switchType".equals(method)) {
                this.getSwitchTypeList(request, response);
            } else if ("attribution".equals(method)) {
                this.getAttributionList(request, response);
            } else if ("queryByItems".equals(method)) {
               // this.getbrrBossList(request, response);
                getData(request, response);
            }else if("export".equals(method)){
                this.exportData(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void getBusinessTypeList(HttpServletRequest request, HttpServletResponse response) {
        Map json1 = new HashMap();
        Map json2 = new HashMap();
        Map json3 = new HashMap();
        Map json4 = new HashMap();
        Map json5 = new HashMap();
        Map json6 = new HashMap();
        Map json7 = new HashMap();
        List jarry = new ArrayList();
        json2.put("localCall", "本地通话");
        json3.put("message", "短信");
        json4.put("mmessage", "彩信");
        json5.put("ggprs", "GPRS");
        json6.put("wlan", "无线局域网");
        json7.put("kjava", "百宝箱");
        jarry.add(json2);
        jarry.add(json3);
        jarry.add(json4);
        jarry.add(json5);
        jarry.add(json6);
        jarry.add(json7);
        json1.put("list", jarry);
        json1.put("result", "success");
        json1.put("message", "");
        Map r = new HashMap();
        r.put("response", json1);
        System.out.println(r);
        
        writeResponse(response, r);


    }

    private void getSwitchTypeList(HttpServletRequest request, HttpServletResponse response) {
        Map json1 = new HashMap();
        Map json2 = new HashMap();
        Map json3 = new HashMap();
        List jarry = new ArrayList();
        json2.put("8613441484", "8613441484");
        json3.put("8613440492", "8613440492");
        jarry.add(json3);
        jarry.add(json2);
        json1.put("list", jarry);
        json1.put("result", "success");
        json1.put("message", "");
        Map r = new HashMap();
        r.put("response", json1);
        System.out.println(r);
        writeResponse(response, r);

    }

    /*
     * 归属局省
     *
     *
     */
    private void getAttributionList(HttpServletRequest request, HttpServletResponse response) {

        if (request.getParameter("provCode") != null) {
            List arr = new ArrayList();
            Map m1 = new HashMap();
            Map m2 = new HashMap();
            Map m3 = new HashMap();
            Map m4 = new HashMap();
            if (request.getParameter("provCode").equals("001")) {
                m1.put("001", "003");
                m2.put("002", "002");
                m3.put("003", "003");
            } else if (request.getParameter("provCode").equals("003")) {
                m1.put("001", "001");
                m2.put("002", "002");
                m3.put("003", "003");

            } else if (request.getParameter("provCode").equals("002")) {
                m1.put("001", "001");
                m2.put("002", "002");
                m3.put("003", "003");

            }
            arr.add(m1);
            arr.add(m3);
            arr.add(m2);

            m4.put("list", arr);
            m4.put("message", "");
            m4.put("result", "success");
            Map r = new HashMap();
            r.put("response", m4);
            System.out.println(r);
            writeResponse(response, r);
        } else {
            Map json1 = new HashMap();
            Map json2 = new HashMap();
            Map json3 = new HashMap();
            Map json4 = new HashMap();
            List jarry = new ArrayList();

            json2.put("001", "001");
            json3.put("002", "002");
            json4.put("003", "003");

            jarry.add(json3);
            jarry.add(json2);
            jarry.add(json4);
            json1.put("list", jarry);
            json1.put("result", "success");
            json1.put("message", "");
            Map r = new HashMap();
            r.put("response", json1);

            writeResponse(response, r);
        }

    }
    /*
     * 融合计费按条件查询
     *
     *
     */

    private void getbrrBossList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
       
        Map paramMap = getParameterMap(request);

        String businessType = (String) paramMap.get("businessType");
        String phoneNo = (String) paramMap.get("phoneNo");
        String switchCode = (String) paramMap.get("switchCode");
        String attributionProv = (String) paramMap.get("attributionProv");
        String attributionCity = (String) paramMap.get("attributionCity");
        String roamProv = (String) paramMap.get("roamProv");
        String roamCity = (String) paramMap.get("roamCity");
        String timeType = (String) paramMap.get("timeType");
        String queryBasis = (String) paramMap.get("queryBasis");
        String fromTime = (String) paramMap.get("fromTime");
        String thruTime = (String) paramMap.get("thruTime");
        String districtNumber = (String) paramMap.get("districtNumber");
        String baseStationNumber = (String) paramMap.get("baseStationNumber");
        String longDistanceType = (String) paramMap.get("longDistanceType");
        String PeerNumber = (String) paramMap.get("PeerNumber");
        String RoamingType = (String) paramMap.get("RoamingType");
        String startIndex = (String) paramMap.get("startIndex");
        String stopIndex = (String) paramMap.get("stopIndex");

        String tablezrr = fromTime.substring(0, 4) + fromTime.substring(5, 7);

        //本地语音通话业务
        if ("localCall".equals(businessType)) {
            StringBuffer sql = new StringBuffer();

            sql.append("select * from DR_GSM_" + tablezrr + " t where 1=1 ");

            if (!"".equals(phoneNo) && null != phoneNo) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                if ("phoneNumber".equals(queryBasis)) {
                    sql.append("  t.user_number='");
                } else if ("MIN".equals(queryBasis)) {
                    sql.append("  t.IMSI='");
                } else if ("peerNumber".equals(queryBasis)) {
                    sql.append("  t.opp_number='");
                } else if ("account".equals(queryBasis)) {
                    sql.append("  t.acc_id='");
                } else if ("IMEI".equals(queryBasis)) {
                    sql.append("  t.ESN='");
                }
                sql.append(phoneNo).append("' ");
            }

            //交换机代码

            if (!"".equals(switchCode) && null != switchCode) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                sql.append(" t.MSC_ID='").append(switchCode).append("' ");
            }

            //小区代码

            if (!"".equals(districtNumber) && null != districtNumber) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                sql.append(" t.CELL_ID='").append(districtNumber).append("' ");
            }

            //基站代码

            if (!"".equals(baseStationNumber) && null != baseStationNumber) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                sql.append(" t.LAC_ID='").append(baseStationNumber).append("' ");
            }

            //时间类别

            if (!timeType.equals("") && timeType != null) {

                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                if ("telTime".equals(timeType)) {
                    sql.append(" t.LATE_LINK >= 0 and t.START_TIME>=to_date('" + fromTime + "','yyyy-MM-dd HH24:mi:ss') and t.START_TIME<=to_date('" + thruTime + "','yyyy-MM-dd HH24:mi:ss') ");
                } else {
                    sql.append(" t.LATE_LINK >= 0 and t.INPUT_TIME>=to_date('" + fromTime + "','yyyy-MM-dd HH24:mi:ss') and t.INPUT_TIME<=to_date('" + thruTime + "','yyyy-MM-dd HH24:mi:ss') ");

                }
            }

            //归属局

            if (null != attributionProv && !"".equals(attributionProv)) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                sql.append(" t.HPLMN1 ='").append(attributionProv).append("' ");
                if (null != attributionCity && !"".equals(attributionCity)) {
                    if (sql.toString().indexOf("where") > 0) {
                        sql.append(" and ");
                    }
                    sql.append(" t.HPLMN2 ='").append(attributionCity).append("' ");
                }
            }

            //漫游局

            if (null != roamProv && !"".equals(roamProv)) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                sql.append(" t.VPLMN1 ='").append(roamProv).append("' ");
                if (null != roamCity && !"".equals(roamCity)) {
                    if (sql.toString().indexOf("where") > 0) {
                        sql.append(" and ");
                    }
                    sql.append(" t.VPLMN2 ='").append(roamCity).append("' ");
                }
            }



            //长话类型
            if (null != longDistanceType && !"".equals(longDistanceType)) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                if (longDistanceType.indexOf(",") > 0) {
                    sql.append(" t.TOLL_TYPE>=0 ");
                } else if ("localCall".equals(longDistanceType)) {
                    sql.append(" t.TOLL_TYPE='0' ");

                } else if ("longdistanceCall".equals(longDistanceType)) {
                    sql.append(" t.TOLL_TYPE>0 ");
                }
            }

            //漫游类型
            if (null != RoamingType && !"".equals(RoamingType)) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                if (RoamingType.indexOf(",") > 0) {
                    String[] types = RoamingType.split(",");
                    String s = "";
                    boolean flag = false;
                    for (int i = 0; i < types.length; i++) {
                        if (flag) {
                            s += ",";
                        }
                        s += "'" + types[i] + "'";
                        flag = true;
                    }
                    sql.append(" t.ROAM_TYPE in ( ").append(s).append(") ");
                } else if ("0".equals(RoamingType)) {
                    sql.append(" t.ROAM_TYPE='0' ");

                } else if ("2".equals(RoamingType)) {
                    sql.append(" t.ROAM_TYPE='2' ");

                } else if ("4".equals(RoamingType)) {
                    sql.append(" t.ROAM_TYPE='4' ");

                } else if ("5".equals(RoamingType)) {
                    sql.append(" t.ROAM_TYPE='5' ");

                }
            }

            //对端类型
            if (null != PeerNumber && !"".equals(PeerNumber)) {
                if (sql.toString().indexOf("where") > 0) {
                    if (PeerNumber.indexOf(",") > 0 && PeerNumber.split(",").length < 3) {
                        sql.append(" and ");
                    }
                }

                if (PeerNumber.indexOf(",") > 0) {
                    if ("IP,WAP".equals(PeerNumber) || "WAP,IP".equals(PeerNumber)) {
                        sql.append(" ((t.OPP_NUMBER_TYPE > 40 and t.OPP_NUMBER_TYPE <60) or (t.OPP_NUMBER_TYPE=61) ");
                    } else if ("IP,OTHER".equals(PeerNumber) || "OTHER,IP".equals(PeerNumber)) {
                        sql.append("  t.OPP_NUMBER_TYPE!=61 ");

                    } else if ("OTHER,WAP".equals(PeerNumber) || "WAP,OTHER".equals(PeerNumber)) {
                        sql.append(" t.OPP_NUMBER_TYPE <= 40 or  t.OPP_NUMBER_TYPE >=60 ");
                    }

                } else if ("IP".equals(PeerNumber)) {
                    sql.append(" t.OPP_NUMBER_TYPE > 40 and t.OPP_NUMBER_TYPE <60 ");
                } else if ("WAP".equals(PeerNumber)) {
                    sql.append(" t.OPP_NUMBER_TYPE='61' ");
                } else if ("OTHER".equals(PeerNumber)) {
                    sql.append(" t.OPP_NUMBER_TYPE!='61' or t.OPP_NUMBER_TYPE <= 40 or  t.OPP_NUMBER_TYPE >=60");
                }

            }

            // String countSql= " select  count(*) totalCount from ("+sql.toString()+")tc ";

//            if(startIndex!=null&&stopIndex!=null){
//            
//                sql.append(" limit ").append(startIndex).append(",").append(stopIndex );
//            }

            String driver = Config.getProperty("gbase.driver");
            String url = Config.getProperty("gbase.url");
            String userName = Config.getProperty("gbase.userName");
            String password = Config.getProperty("gbase.password");
            DBHelper db = new DBHelper(driver, url, userName, password);
   
            try {

                //int totalCount=db.queryCount(countSql);
                Map rows = db.executeQuery(sql.toString());
                //rows.put("startIndex", startIndex);
                //rows.put("stopIndex",stopIndex);
                // rows.put("totalCount", totalCount);

                writeResponse(response, rows);
            } catch (ClassNotFoundException ex) {

                Logger.getLogger(DrrbossBill.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(DrrbossBill.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }

    private void getData(HttpServletRequest request, HttpServletResponse response) {
         Map paramMap = getParameterMap(request);
      
            String sql=createSql(paramMap);
            String driver = Config.getProperty("gbase.driver");
            String url = Config.getProperty("gbase.url");
            String userName = Config.getProperty("gbase.userName");
            String password = Config.getProperty("gbase.password");
            DBHelper db = new DBHelper(driver, url, userName, password);
           
        try {
            
            Map rows = db.executeQuery(sql);
            
             writeResponse(response, rows);
             
        } catch (SQLException ex) {
            Logger.getLogger(DrrbossBill.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DrrbossBill.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DrrbossBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    private String createSql(Map paramMap) {
        String businessType = (String) paramMap.get("businessType");
        String phoneNo = (String) paramMap.get("phoneNo");
        String switchCode = (String) paramMap.get("switchCode");
        String attributionProv = (String) paramMap.get("attributionProv");
        String attributionCity = (String) paramMap.get("attributionCity");
        String roamProv = (String) paramMap.get("roamProv");
        String roamCity = (String) paramMap.get("roamCity");
        String timeType = (String) paramMap.get("timeType");
        String queryBasis = (String) paramMap.get("queryBasis");
        String fromTime = (String) paramMap.get("fromTime");
        String thruTime = (String) paramMap.get("thruTime");
        String districtNumber = (String) paramMap.get("districtNumber");
        String baseStationNumber = (String) paramMap.get("baseStationNumber");
        String longDistanceType = (String) paramMap.get("longDistanceType");
        String PeerNumber = (String) paramMap.get("PeerNumber");
        String RoamingType = (String) paramMap.get("RoamingType");
        String startIndex = (String) paramMap.get("startIndex");
        String stopIndex = (String) paramMap.get("stopIndex");  
        
        StringBuffer sql=new StringBuffer(" select * from (");

        List tables = getTables(paramMap);
        for(int k=0;k<tables.size();k++){
            if(k!=0){
              sql.append(" union all ");
            }
            sql.append(" select * from  ").append(tables.get(k).toString()).append(" as t"+k).append(" where 1=1 ");
             String f="=";
     
            if (!"".equals(phoneNo) && null != phoneNo) {
                
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                 if(phoneNo.contains("%"))f=" like ";
                    
                if ("phoneNumber".equals(queryBasis)) {
                    sql.append("  t"+k+".user_number"+f+"'");
                } else if ("MIN".equals(queryBasis)) {
                    sql.append("  t"+k+".IMSI"+f+"'");
                } else if ("peerNumber".equals(queryBasis)) {
                    sql.append("  t"+k+".opp_number"+f+"'");
                } else if ("account".equals(queryBasis)) {
                    sql.append("  t"+k+".acc_id"+f+"'");
                } else if ("IMEI".equals(queryBasis) && !businessType.equals("message") ) {
                    sql.append("  t"+k+".ESN"+f+"'");
                }
                sql.append(phoneNo).append("' ");
            }

            //交换机代码

            if (!"".equals(switchCode) && null != switchCode) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                sql.append(" t"+k+".MSC_ID='").append(switchCode).append("' ");
            }

            //小区代码

            if (!"".equals(districtNumber) && null != districtNumber) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                sql.append(" t"+k+".CELL_ID='").append(districtNumber).append("' ");
            }

            //基站代码

            if (!"".equals(baseStationNumber) && null != baseStationNumber) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                sql.append(" t"+k+".LAC_ID='").append(baseStationNumber).append("' ");
            }

            //时间类别

            if (!timeType.equals("") && timeType != null) {

                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                if ("telTime".equals(timeType)) {
                    sql.append(" t"+k+".LATE_LINK >= 0 and t"+k+".START_TIME>=to_date('" + fromTime + "','yyyy-MM-dd HH24:mi:ss') and t"+k+".START_TIME<=to_date('" + thruTime + "','yyyy-MM-dd HH24:mi:ss') ");
                } else {
                    sql.append(" t"+k+".LATE_LINK >= 0 and t"+k+".INPUT_TIME>=to_date('" + fromTime + "','yyyy-MM-dd HH24:mi:ss') and t"+k+".INPUT_TIME<=to_date('" + thruTime + "','yyyy-MM-dd HH24:mi:ss') ");

                }
            }

            //归属局

            if (null != attributionProv && !"".equals(attributionProv)) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                sql.append(" t"+k+".HPLMN1 ='").append(attributionProv).append("' ");
                if (null != attributionCity && !"".equals(attributionCity)) {
                    if (sql.toString().indexOf("where") > 0) {
                        sql.append(" and ");
                    }
                    sql.append(" t"+k+".HPLMN2 ='").append(attributionCity).append("' ");
                }
            }

            //漫游局

            if (null != roamProv && !"".equals(roamProv)) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                sql.append(" t"+k+".VPLMN1 ='").append(roamProv).append("' ");
                if (null != roamCity && !"".equals(roamCity)) {
                    if (sql.toString().indexOf("where") > 0) {
                        sql.append(" and ");
                    }
                    sql.append(" t"+k+".VPLMN2 ='").append(roamCity).append("' ");
                }
            }



            //长话类型
            if (null != longDistanceType && !"".equals(longDistanceType)) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                if (longDistanceType.indexOf(",") > 0) {
                    sql.append(" t"+k+".TOLL_TYPE>=0 ");
                } else if ("localCall".equals(longDistanceType)) {
                    sql.append(" t"+k+".TOLL_TYPE='0' ");

                } else if ("longdistanceCall".equals(longDistanceType)) {
                    sql.append(" t"+k+".TOLL_TYPE>0 ");
                }
            }

            //漫游类型
            if (null != RoamingType && !"".equals(RoamingType)) {
                if (sql.toString().indexOf("where") > 0) {
                    sql.append(" and ");
                }
                if (RoamingType.indexOf(",") > 0) {
                    String[] types = RoamingType.split(",");
                    String s = "";
                    boolean flag = false;
                    for (int i = 0; i < types.length; i++) {
                        if (flag) {
                            s += ",";
                        }
                        s += "'" + types[i] + "'";
                        flag = true;
                    }
                    sql.append(" t"+k+".ROAM_TYPE in ( ").append(s).append(") ");
                } else if ("0".equals(RoamingType)) {
                    sql.append(" t"+k+".ROAM_TYPE='0' ");

                } else if ("2".equals(RoamingType)) {
                    sql.append(" t"+k+".ROAM_TYPE='2' ");

                } else if ("4".equals(RoamingType)) {
                    sql.append(" t"+k+".ROAM_TYPE='4' ");

                } else if ("5".equals(RoamingType)) {
                    sql.append(" t"+k+".ROAM_TYPE='5' ");

                }
            }

            //对端类型
            if (null != PeerNumber && !"".equals(PeerNumber)) {
                if (sql.toString().indexOf("where") > 0) {
                    if (PeerNumber.indexOf(",") > 0 && PeerNumber.split(",").length < 3) {
                        sql.append(" and ");
                    }
                    if(PeerNumber.indexOf(",")<0){
                         sql.append(" and ");
                    }
                }

                if (PeerNumber.indexOf(",") > 0) {
                    if ("IP,WAP".equals(PeerNumber) || "WAP,IP".equals(PeerNumber)) {
                        sql.append(" ((t"+k+".OPP_NUMBER_TYPE > 40 and t"+k+".OPP_NUMBER_TYPE <60) or (t"+k+".OPP_NUMBER_TYPE=61) ");
                    } else if ("IP,OTHER".equals(PeerNumber) || "OTHER,IP".equals(PeerNumber)) {
                        sql.append("  t"+k+".OPP_NUMBER_TYPE!=61 ");

                    } else if ("OTHER,WAP".equals(PeerNumber) || "WAP,OTHER".equals(PeerNumber)) {
                        sql.append(" t"+k+".OPP_NUMBER_TYPE <= 40 or  t"+k+".OPP_NUMBER_TYPE >=60 ");
                    }

                } else if ("IP".equals(PeerNumber)) {
                    sql.append(" t"+k+".OPP_NUMBER_TYPE > 40 and t"+k+".OPP_NUMBER_TYPE <60 ");
                } else if ("WAP".equals(PeerNumber)) {
                    sql.append(" t"+k+".OPP_NUMBER_TYPE='61' ");
                } else if ("OTHER".equals(PeerNumber)) {
                    sql.append(" t"+k+".OPP_NUMBER_TYPE!='61' or  t"+k+".OPP_NUMBER_TYPE <= 40 or  t"+k+".OPP_NUMBER_TYPE >=60 ");
                }

            }
            
         }
        
        sql.append(")t");

        return sql.toString();

    }

    private List getTables(Map paramMap) {
        List tables = new ArrayList();

        String businessType = (String) paramMap.get("businessType");
        String fromTime = (String) paramMap.get("fromTime");
        String thruTime = (String) paramMap.get("thruTime");
        String fromM = fromTime.substring(0, 4) + fromTime.substring(5, 7);
        String endM = thruTime.substring(0, 4) + thruTime.substring(5, 7);
        if (businessType.equals("localCall")) {
                List mons=getYearMonth(fromM,endM);
                for(int i=0;i<mons.size();i++){
                   String tName= "DR_GSM_"+mons.get(i);
                  // String tName2="DR_GSM_ROAMIN"+mons.get(i);
                    tables.add(tName);
                   // tables.add(tName2);
                }

        }else if(businessType.equals("message")) {
                List mons=getYearMonth(fromM,endM);
                for(int i=0;i<mons.size();i++){
                   String tName= "DR_SMS_"+mons.get(i);
                    tables.add(tName);
                }

        }else if(businessType.equals("mmessage")) {
                List mons=getYearMonth(fromM,endM);
                for(int i=0;i<mons.size();i++){
                   String tName= "DR_MMS_"+mons.get(i);
                    tables.add(tName);
                }

        }else if(businessType.equals("ggprs")) {
                List mons=getYearMonth(fromM,endM);
                for(int i=0;i<mons.size();i++){
                   String tName= "DR_GGPRS_"+mons.get(i);
                    tables.add(tName);
                }

        }else if(businessType.equals("wlan")) {
                List mons=getYearMonth(fromM,endM);
                for(int i=0;i<mons.size();i++){
                   String tName= "DR_WLAN_"+mons.get(i);
                    tables.add(tName);
                }

        }else if(businessType.equals("kjava")) {
                List mons=getYearMonth(fromM,endM);
                for(int i=0;i<mons.size();i++){
                   String tName= "DR_KJAVA_"+mons.get(i);
                    tables.add(tName);
                }

        }
        return tables;

    }

    private void writeResponse(HttpServletResponse response, Object result) {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            if (result instanceof Map) {
                JSONObject json = JSONObject.fromObject(result);
                result = json.toString();
            } else if (result instanceof List) {
                JSONArray json = JSONArray.fromObject(result);
                result = json.toString();
            } else if (result instanceof String) {
                result = String.valueOf(result);
            }
        } catch (IOException e) {
            Logger.getLogger(DrrbossBill.class.getName()).log(Level.SEVERE, e.getMessage());

        } finally {
            writer.print(result);
            writer.flush();
            writer.close();
        }
    }

    private Map getParameterMap(HttpServletRequest request) {
        Map paramMap = new HashMap();
        Enumeration enum1 = request.getParameterNames();
        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
            if (key.equals("method")) {
                continue;
            }
            String value = request.getParameter(key);
            try {
                value = java.net.URLDecoder.decode(value, "utf-8");
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
            }
            paramMap.put(key, value);
        }
        return paramMap;
    }

    private List getYearMonth(String date1, String date2) {
        List yearMonth = new ArrayList();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            Date startDate = sdf.parse(date1);
            Date endDate = sdf.parse(date2);

            Calendar startTime = Calendar.getInstance();
            startTime.clear();
            startTime.setTime(startDate);
            int startYear = startTime.get(Calendar.YEAR);
            int startMonth = startTime.get(Calendar.MONTH);
            int startDay = startTime.get(Calendar.DAY_OF_MONTH);
            Calendar endTime = Calendar.getInstance();
            endTime.clear();
            endTime.setTime(endDate);
            int endYear = endTime.get(Calendar.YEAR);
            int endMonth = endTime.get(Calendar.MONTH);
            int endDay = endTime.get(Calendar.DAY_OF_MONTH);

            int count = 0;
            for (int x = startYear; x <= endYear; x++) {
                int gregorianCutoverYear = 1582;
               
                boolean isLeapYear = x >= gregorianCutoverYear ? ((x % 4 == 0) && ((x % 100 != 0) || (x % 400 == 0)))
                        : (x % 4 == 0);

                String isBigYear = "是平年";
                if (isLeapYear) {
                    isBigYear = "是闰年";
                }
                int y = 0;
                if (x == startYear) {
                    y = startMonth;
                }
                for (; y < 12; y++) {
                    int ty = y + 1;
                    String ym = x + "" + ((String.valueOf(ty).length() < 2) ? "0" + ty : ty);
                    yearMonth.add(ym);
                    if (x == endYear && y == endMonth) {
                        break;
                    }

                }
            }
        } catch (Exception e) {
        }
        return yearMonth;
    }
    
   private void exportData(HttpServletRequest request, HttpServletResponse response) {
         Map paramMap = getParameterMap(request);
      
            String sql=createSql(paramMap);
            String driver = Config.getProperty("gbase.driver");
            String url = Config.getProperty("gbase.url");
            String userName = Config.getProperty("gbase.userName");
            String password = Config.getProperty("gbase.password");
            DBHelper db = new DBHelper(driver, url, userName, password);
           
        try {
            
            Map rows = db.executeQuery(sql);
            
            Map map=new HashMap();
            map.put("data", rows);
            map.put("result", "success");
            Map res=new HashMap();
            res.put("baseResponse", map);
            String  result="";
            JSONObject json = JSONObject.fromObject(res);
                result = json.toString();
              buildExcel(request,response,result);
             
        } catch (SQLException ex) {
            Logger.getLogger(DrrbossBill.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DrrbossBill.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            String result = "网络连接异常";
            ServletOutputStream op;
            try {
                op = response.getOutputStream();
                op.write(result.getBytes());
                op.close();
            } catch (IOException ex1) {
                Logger.getLogger(ExportExcel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
             ServletOutputStream op;
            try {
                op = response.getOutputStream();
                op.write(ex.getMessage().getBytes());
                op.close();
            } catch (IOException ex1) {
                Logger.getLogger(ExportExcel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
    }
    
    private void buildExcel(HttpServletRequest req, HttpServletResponse res, String dataStr) throws IOException, Exception {
        Workbook wb = new HSSFWorkbook();
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        Sheet sheet = wb.createSheet("清单列表");
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.fromString(dataStr);
        } catch (Exception e) {
            throw new Exception("详单查询接口返回格式有误");
        }
        String result = jsonObject.getJSONObject("baseResponse").getString("result");
        if (!"success".equals(result)) {
            throw new Exception("调用详单查询接口失败，原因是[" + jsonObject.getJSONObject("baseResponse").getString("detail") + "]");
        }

        JSONArray titles = jsonObject.getJSONObject("baseResponse").getJSONObject("data").getJSONArray("fields");
        JSONArray datas = jsonObject.getJSONObject("baseResponse").getJSONObject("data").getJSONArray("contents");
        Row row = sheet.createRow(0);
        for (int i = 0; i < titles.length(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(titles.getString(i));
        }
        for (int j = 0; j < datas.length(); j++) {
            JSONArray data = datas.getJSONArray(j);
            row = sheet.createRow(j + 1);
            for (int i = 0; i < data.length(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(data.getString(i));
            }
        }
        ServletOutputStream op = res.getOutputStream();
        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=detail.xls");
        res.setHeader("Pragma", "No-cache");
        res.setHeader("Cache-Control", "no-store");
        res.setDateHeader("Expires", 0);
        wb.write(op);
        op.close();
    }
}