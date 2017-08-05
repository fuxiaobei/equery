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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class QueryGSMRList extends HttpServlet {
           static String driver =null;
           static String url = null;
           static String userName = null;
           static String password = null;
           static  DBHelper db = null;
           
    @Override
    public void init() throws ServletException {
        super.init();
             driver = Config.getProperty("oracle.driver");
             url = Config.getProperty("oracle.url");
             userName = Config.getProperty("oracle.userName");
             password = Config.getProperty("oracle.password");
             db = new DBHelper(driver, url, userName, password);
             System.out.println(driver+":"+ url+":"+userName+":"+password);
    }

    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
           String billId = request.getParameter("billId");
           String busiType = request.getParameter("busiType");
           String fromDate=request.getParameter("fromDate").replaceAll("-", "");
           String thruDate=request.getParameter("thruDate").replaceAll("-", "");
           Map map=new HashMap();
           map.put("billId", billId);
           map.put("busiType", busiType);
           map.put("fromDate", fromDate);
           map.put("thruDate", thruDate);
           StringBuilder rest = new StringBuilder(Config.getRest());
            rest.append("query/common");
           String result=com.asiainfo.billing.drquery.web.rest.RestManager.postWithJson(rest.toString(),map);
            result=buildData(request,response,result);
           //{"result":"success","data":{"contents":[],"startIndex":0,"stopIndex":0,"count":0,"totalCount":0,"stats":null,"fields":null},"message":null}
          // "{"result":"success","data":{"contents":[["1100","10017329298","13564326330","13636350375","0","2012-05-26 23:13:21","00:00:53","被叫","国内漫游","0","12","210","北京|北京","北京","0.40","0.00","0.00","","0","0","0","0","0","359836044750630","非超长话单","上海市","国内长途","8613442450","10B4","1461","2012-05-27 00:02:32","0","0","GSM网络","0"]],"startIndex":1,"stopIndex":1,"count":1,"totalCount":1,"stats":[{"stat":"0.00","title":"长途通话费"},{"stat":"0.40","title":"本地通话费"},{"stat":"0.00","title":"信息费"},{"stat":"00:00:53","title":"通话时长"}],"fields":["业务类型","帐户号","用户号码","对方号码","呼转号码","通话起始时间","通话时长","呼叫类型","漫游类型","访问类型","业务名称","一级归属地","一级漫游地","二级漫游地","本地通话费","长途通话费","信息费","免费资源1","免费分钟数1","免费资源2","免费分钟数2","免费资源3","免费分钟数3","国际移动台识别码","是否是合并话单","对端号码归属地","长途类型","交换机代码","定位地点识别","基站代码","批价处理时间","优惠区信息","是否是视频通话","网络类型","延迟标志"]},"message":null}"
            writeResponse(response,result);
        }catch(Exception e){
        }
    }
    
    private String buildData(HttpServletRequest request, HttpServletResponse response,String res){
        
        if(null!=res&&!"".equals(res)){
            JSONObject json=JSONObject.fromString(res);
            if("success".equals(json.getString("result"))){
                JSONObject jObj=json.getJSONObject("data");
                JSONArray jarray=jObj.getJSONArray("contents");
                JSONArray fields=jObj.getJSONArray("fields");
                fields.put("是否边界漫游");
                fields.put("边漫局数据生效日期");
                fields.put("是否疑似边漫小区");
                fields.put("疑似边漫小区来源");
                
                String fromDate=request.getParameter("fromDate").replaceAll("-", "");
                fromDate=fromDate.substring(0, 6);
                for(int i=0;i<jarray.length();i++){
                  JSONArray row= jarray.getJSONArray(i);
                  String sql=createSql(row.getString(29),row.getString(28),row.getString(27));
                  
                  String valid_date= getDbh(sql);
                  if(null!=valid_date&&!"".equals(valid_date)){
                      String validMonth=valid_date.substring(0, 6);
                      int validM=Integer.valueOf(validMonth);
                      int fromM=Integer.valueOf(fromDate);
                      
                      if(fromM==validM){
                        for(int k=0;k<row.length();k++){
                            String s=addColor(row.getString(k),"FABF8F");
                            row.put(k, s);
                        }
                        row.put("<font color='FABF8F'>是</font>");
                        row.put("<font color='FABF8F'>"+valid_date+"</font>");
                        row.put(" ");
                        row.put(" ");
                      }else if(validM>fromM){
                           for(int k=0;k<row.length();k++){
                            String s=addColor(row.getString(k),"FFFF00");
                             row.put(k, s);
                             }
                        row.put("<font color='FFFF00'>是</font>");
                        row.put("<font color='FFFF00'>"+valid_date+"</font>");
                        row.put(" ");
                        row.put(" ");
                      
                      }else{
                        row.put("是");
                        row.put(valid_date);
                        row.put(" ");
                        row.put(" ");
                      
                      }
                  }else{//疑似边界漫游小区
                      
                       // String _sql=createSql(row.getString(29),row.getString(28),row.getString(27));
                        //getDbh(_sql);
                       for(int k=0;k<row.length();k++){
                            String s=addColor(row.getString(k),"blue");
                            row.put(k, s);
                        }
                        row.put(" ");
                        row.put(" ");
                        row.put("<font color='blue'>是</font>");
                        row.put("<font color='blue'>拨测，投诉</font>");
                     
                      
                  }
                }
                
                res=json.toString();
            }
            
        }
        
        return res;
    }
    
    private  String addColor(String s,String color){
    
     return  "<font color='"+color+"'>"+s+"</font>";
       // return "<span style='background-color:FFFF00'>"+s+"</span>";
    }
    
    private String createSql(String cell_id,String lac_id,String msc_id){
        StringBuffer sql=new StringBuffer();
            sql.append(" select min(t.VALID_DATE)as valid_date from  gsm_border_roam t ");
            sql.append(" where 1=1" ).append(" and t.msc_id=").append("UPPER('"+msc_id+"')");
            sql.append(" and  t.cell_id=").append("UPPER('"+cell_id+"')");
            sql.append(" and  t.lac_id=").append("UPPER('"+lac_id+"')");
            
         return sql.toString();
        
        
    }
    
    private String getDbh(String sql){
        String rs="";
        try {
             rs=db.executeQuerySql(sql);
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(QueryGSMRList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QueryGSMRList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(QueryGSMRList.class.getName()).log(Level.SEVERE, null, ex);
        }
            return rs;
        
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
            Logger.getLogger(QueryGSMRList.class.getName()).log(Level.SEVERE, e.getMessage());

        } finally {
            writer.print(result);
            writer.flush();
            writer.close();
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
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}


   