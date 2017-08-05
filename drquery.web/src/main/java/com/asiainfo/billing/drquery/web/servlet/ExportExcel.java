/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.billing.drquery.web.servlet;

import com.asiainfo.billing.drquery.web.config.Config;
import com.asiainfo.billing.drquery.web.rest.RestManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author tianyi
 */
public class ExportExcel extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String billId = request.getParameter("billId");
        String billMonth = request.getParameter("billMonth");
        String fromDate = request.getParameter("fromDate").replaceAll("-", "");
        String thruDate = request.getParameter("thruDate").replaceAll("-", "");
        String busiType = request.getParameter("busiType");
        String destId = request.getParameter("destId");
        String startIndex = request.getParameter("startIndex");
        String stopIndex = request.getParameter("stopIndex");
        StringBuffer rest = new StringBuffer(Config.getRest());
        rest.append("query/common?billId=").append(billId);
        rest.append("&billMonth=").append(billMonth);
        rest.append("&fromDate=").append(fromDate);
        rest.append("&thruDate=").append(thruDate);
        rest.append("&busiType=").append(busiType);

        if (destId != null) {
            rest.append("&destId=").append(destId);
        }
        rest.append("&startIndex=1");
        rest.append("&stopIndex=10000");
        try {
            String result = RestManager.get(rest.toString());
            buildExcel(request, response, result);
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

    public void buildExcel(HttpServletRequest req, HttpServletResponse res, String dataStr) throws IOException, Exception {
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
