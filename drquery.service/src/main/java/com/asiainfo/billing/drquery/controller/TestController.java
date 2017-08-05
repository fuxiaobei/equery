package com.asiainfo.billing.drquery.controller;

import com.asiainfo.billing.drquery.connection.ConnectionException;
import com.asiainfo.billing.drquery.datasource.db.BaseDAO;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouquan3
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController{
    private static Log log = LogFactory.getLog(TestController.class);
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView TestPost(HttpServletRequest req, HttpServletResponse resp) {
//        resp.setContentType("application/x-msdownload");
//
//        String rowkey = req.getParameter("id");
//        try {
//            ServletOutputStream out = resp.getOutputStream();
//            Configuration conf = HBaseConfiguration.create();
//            HTable table = new HTable(conf, "T".getBytes());
//            //HTable table = ConnectionPool.getConnection(conf).getHTable("T".getBytes());
//            Scan scan = new Scan();
//            scan.setStartRow(rowkey.getBytes());
//            byte[] stopKey = rowkey.getBytes();
//            stopKey[stopKey.length -1] ++;
//            scan.setStopRow(stopKey);
//            //Get get = new Get(rowkey.getBytes());
//            // Use the table as needed, for a single operation and a single thread
//            ResultScanner scanner = table.getScanner(scan);
//            System.out.println("---------------------");
//            for(Result result : scanner) {
//                Cell cell = result.getColumnLatestCell("F".getBytes(), "C".getBytes());
//                Cell c = result.getColumnLatestCell("F".getBytes(), "FILE".getBytes());
//
//                System.out.println("------>" + c);
//                byte[] b = cell.getValue();
//                System.out.println("size: " + b.length);
//                System.out.println("------------------done");
//                out.write(c.getValue());
//                out.close();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
//        return new ModelAndView("/test", new DataResponse("success").toMap());

        try {
            BaseDAO dao = new BaseDAO("CSQRYConnectionFactory");
            List<Map> results = dao.queryBySQL("select * from user where id=?", new Object[] {"0001"});
            int count = dao.update("update user set username='test' where id=?", new Object[] {"0001"});
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView TestGet(HttpServletRequest req, HttpServletResponse resp) {
        return TestPost(req, resp);
    }
    
    
    public static void main(String[] args){
    	String str = "{name: \"te\\\"st\"}";
    	JSONObject json = JSONObject.fromObject(str);
    	System.out.println(str);
    	System.out.println(json.get("name"));
    }
    
    
}
