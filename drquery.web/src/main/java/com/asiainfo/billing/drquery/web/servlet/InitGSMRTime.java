/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.billing.drquery.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 *
 * @author Administrator
 */
public class InitGSMRTime extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
       

            String billId = request.getParameter("billId");
            String busiType = request.getParameter("busiType");
            if (null == busiType) {
                busiType = "GSM_R";
            }
            Map paramMap = new HashMap();
            paramMap.put("billId", billId);
            paramMap.put("busiType", busiType);
            try {
               // StringBuffer rest = new StringBuffer(com.asiainfo.billing.drquery.web.config.Config.getRest());
                //   rest.append("query/common");
                // String result=com.asiainfo.billing.drquery.web.rest.RestManager.postWithJson(rest.toString(),paramMap);

                String result = "{\"baseResponse\":{\"result\":\"success\",\"data\":[{'2012-05-01':'2012-05-31'},{'2012-06-01':'2012-06-21'},{'2012-07-01':'2012-07-15'},{'2012-07-16':'2012-07-31'}]}}";
                
                writeResponse(response,result);
            } catch (Exception e) {
                 Logger.getLogger(InitGSMRTime.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();

        } 
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
            Logger.getLogger(InitGSMRTime.class.getName()).log(Level.SEVERE, e.getMessage());

        } finally {
            writer.print(result);
            writer.flush();
            writer.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
