/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.billing.drquery.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
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
 * @author wuhe
 */
public class SaveReason extends HttpServlet {
  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html; charset=utf-8");
            try{
            String result="success";
            
            Map resMap=getParameterMap(request);
        
             PrintWriter out=response.getWriter();
             
             out.print(result);
            }catch(Exception e){
                e.printStackTrace();
                Logger.getLogger("SaveReason: "+e.getMessage());
            }
        
        
    }


    private Map getParameterMap(HttpServletRequest request) {
        Map paramMap = new HashMap();
        Enumeration enum1 = request.getParameterNames();
        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
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
}
