package com.asiainfo.billing.drquery.client.test;

import com.asiainfo.billing.drquery.client.DRQueryClient;
import com.asiainfo.billing.drquery.client.impl.DRQueryClientImpl;
import com.asiainfo.billing.drquery.client.prebill.PreBillDRQueryClient;
import com.asiainfo.billing.drquery.utils.UtilsNestedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author zhouquan3
 */
public class DRClientTest {

    private final static Log log = LogFactory.getLog(DRClientTest.class);
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");

    public static void main(String[] args) {
        if (args[0].equals("types")) {
            if (args.length != 5) {
                System.err.println("illegal argument,args pattern: [types billId billMonth fromDate thruDate]");
                return;
            }
            JSONObject result = null;
            try {
                result = typesQuery(args[1], args[2], args[3], args[4]);
            } catch (UtilsNestedException e) {
                System.out.println("No query result");
                return;
            }
            if (result == null) {
                System.out.println("No query result");
                return;
            }
            System.out.println(result.toString(1));
//            showResult(result);
        } else if (args[0].equals("common")) {
            if (args.length != 6) {
                System.err.println("illegal argument,args pattern: [common busiType billId billMonth fromDate thruDate]");
                return;
            }
            Map<String, List> result = null;
            try {
                result = commonQuery(args[1], args[2], args[3], args[4], args[5]);
            } catch (UtilsNestedException e) {
                System.out.println("No query result");
                return;
            }
            if (result == null) {
                System.out.println("No query result");
                return;
            }
//            System.out.println(result.toString(1));
            showCommonResult(result);
        } else if (args[0].equals("drcheck")) {
            if (args.length != 5) {
                System.err.println("illegal argument,args pattern: [drcheck billId oppNumber1 oppNumber2 oppNumber3]");
            }
            JSONObject result = null;
            try {
                result = drcheckQuery(args[1], args[2], args[3], args[4]);
            } catch (UtilsNestedException e) {
                System.out.println("No query result");
                return;
            }
            if (result == null) {
                System.out.println("No query result");
                return;
            }
//            System.out.println(result.toString(1));
            showTypesResult(result);
        } else {
            System.err.println("Usage:");
            System.err.println("	java com.asiainfo.billing.drquery.client.test.DRClientTest types|common|drcheck agrs[...]");
        }
    }

    private static Map<String, List> commonQuery(String billId, String busiType,
            String billMonth, String fromDate, String thruDate) {
        if (isNumber(billId) && isDateyyyyMM(billMonth) && isDateyyyyMMdd(fromDate) && isDateyyyyMMdd(thruDate)) {
            PreBillDRQueryClient client = new PreBillDRQueryClient();
            return client.query_allCdrInfoDetail(billId, fromDate, thruDate, busiType);
        } else {
            return null;
        }

    }

    private static JSONObject typesQuery(String billId, String billMonth,
            String fromDate, String thruDate) {
        if (isNumber(billId) && isDateyyyyMM(billMonth) && isDateyyyyMMdd(fromDate) && isDateyyyyMMdd(thruDate)) {
            DRQueryClient client = new DRQueryClientImpl();
            JSONObject param = new JSONObject();
            param.put("billId", billId);
            param.put("billMonth", billMonth);
            param.put("fromDate", fromDate);
            param.put("thruDate", thruDate);
            return client.buisTypeQuery(param);
        } else {
            return null;
        }
    }

    private static JSONObject drcheckQuery(String billId, String oppNumber1,
            String oppNumber2, String oppNumber3) {
        if (isNumber(billId) && isNumber(oppNumber1) && isNumber(oppNumber2) && isNumber(oppNumber3)) {
            DRQueryClient client = new DRQueryClientImpl();
            JSONObject param = new JSONObject();
            List destId = new ArrayList();
            destId.add(oppNumber1);
            destId.add(oppNumber2);
            destId.add(oppNumber3);
            param.put("billId", billId);
            param.put("destId", destId);
            return client.checkQuery(param);
        } else {
            return null;
        }
    }

    private static void showCommonResult(Map result) {
        int length = 0;
        System.out.println("title");
        List title = (List) result.get("listCaption");
        if (title == null) {
            System.out.println("title is null");
        } else {
            length = title.size();
            for (int j = 0; j < title.size(); j++) {
                System.out.print("[" + title.get(j) + "]");
                System.out.print("\t");
            }
            System.out.println("");

        }
        System.out.println("Body");
        List body = (List) result.get("listBody");
        if (body == null) {
            System.out.println("Body is null");
        } else {
            for (int i = 0; i < body.size(); i++) {
                List line = (List) body.get(i);
                for (int j = 0; j < line.size(); j++) {

                    System.out.print("[" + line.get(j) + "]");
                    System.out.print("\t");
                }
                System.out.println("");

                if (line.size() != length) {
                    System.out.println("title与body的长度不一致");
                }
            }
        }
        System.out.println("Tail");
        List tail = (List) result.get("listTail");
        if(tail==null){
            System.out.println("Tail is null");
        }else{
            for (int j = 0; j < tail.size(); j++) {
            System.out.print("[" + tail.get(j) + "]");
            System.out.print("\t");
        }
        System.out.println("");
        
        if (tail.size() != length) {
            System.out.println("title与tail的长度不一致");
        }
        }
        //listSpecialSum
        System.out.println("Stat");
        List stat = (List) result.get("listSpecialSum");
        if(stat==null){
            System.out.println("Stat is null");
        }else{
            for (int j = 0; j < stat.size(); j++) {
            System.out.print("[" + stat.get(j) + "]");
            System.out.print("\n");
        }
        }
    }

    private static void showTypesResult(JSONObject object) {
        JSONArray list = object.getJSONArray("data");
        for (int i = 0; i < list.size(); i++) {
            JSONObject dataObject = list.getJSONObject(i);
            System.out.println("destId:" + dataObject.get("destId"));
            System.out.println("month:" + (dataObject.get("month").toString().equals("") ? "No data" : dataObject.get("month").toString()));
        }
    }

    private static boolean isDateyyyyMMdd(String date) {
        try {
            Date d = sdf1.parse(date);
            return true;
        } catch (ParseException ex) {
            System.err.println("Wrong date string,[" + date + "]");
            return false;
        }
    }

    private static boolean isDateyyyyMM(String date) {
        try {
            Date d = sdf2.parse(date);
            return true;
        } catch (ParseException ex) {
            System.err.println("Wrong date string,[" + date + "]");
            return false;
        }
    }

    private static boolean isNumber(String number) {
        int size = number.length();
        boolean flag = true;
        for (int i = 0; i < size; i++) {
            int chr = number.charAt(i);
            if (chr < 48 || chr > 57) {
                flag = false;
                System.err.println("Wrong billId string,[" + number + "]");
                break;
            }
        }
        return flag;
    }
}
