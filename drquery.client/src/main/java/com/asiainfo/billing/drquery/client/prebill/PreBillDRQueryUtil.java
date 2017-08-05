package com.asiainfo.billing.drquery.client.prebill;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author zhouquan3
 */
public class PreBillDRQueryUtil {

    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
    private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");

    public static List jsonArray2ArrayList(JSONArray jsonArray) {
        int size = jsonArray.size();
        List list = new ArrayList();
        for (int i = 0; i < size; i++) {
            Object o = jsonArray.get(i);
            if (o instanceof JSONArray) {
                o = jsonArray2ArrayList((JSONArray) o);
            }
            list.add(o);
        }
        return list;
    }

    public static List formatStats(JSONArray jsonArray) {
        int size = jsonArray.size();
        List list = new ArrayList();
        List title = new ArrayList();
        List stat = new ArrayList();
        for (int i = 0; i < size; i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String key = obj.getString("title");
            title.add(key);
            String value = obj.getString("stat");
            stat.add(value);
        }
        list.add(title);
        list.add(stat);
        return list;
    }

    public static String formatMoney(String input) {
        if ("".equals(input) || "-".equals(input)) {
            return "0.00";
        } else if (input.indexOf(".") < 0) {
            BigDecimal bd = BigDecimal.valueOf(Integer.valueOf(input).longValue());
            bd = bd.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            return bd.toString();
        } else {
            return input;
        }
        //BigDecimal sumCharge = BigDecimal.valueOf(Integer.valueOf(input).longValue());
        //sumCharge = sumCharge.divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP);
        //return sumCharge.toString();
    }

    public static String formatMoneyForSum(String input) {
        if ("".equals(input) || "-".equals(input)) {
            return "0";
        } else {
            double d = new Double(input).doubleValue();
            long l = new Double(d * 100).longValue();
            return String.valueOf(l);
        }
    }

    public static String formatTime2Date(String input) {
        try {
            return sdf2.format(sdf1.parse(input));
        } catch (Exception e) {
            return input;
        }
    }

    public static String formatTime2Time(String input) {
        try {
            return sdf3.format(sdf1.parse(input));
        } catch (Exception e) {
            return input;
        }
    }

    public static String formatTimeLength(String input) {
        if (input.indexOf(":") > 0) {
            return input;
        } else {
            try {
                String result = "";
                int seconds = Integer.valueOf(input).intValue();
                if (seconds > 3600) {
                    int hour = seconds / 3600;
                    int minute = (seconds % 3600) / 60;
                    int second = seconds % 60;
                    result += hour + ":";
                    if (minute > 9) {
                        result += minute + ":";
                    } else {
                        result += "0" + minute + ":";
                    }
                    if (second > 9) {
                        result += second;
                    } else {
                        result += "0" + second;
                    }
                } else if (seconds > 60) {
                    int minute = seconds / 60;
                    int second = seconds % 60;
                    if (minute > 9) {
                        result += "00:" + minute + ":";
                    } else {
                        result += "00:0" + minute + ":";
                    }
                    if (second > 9) {
                        result += second;
                    } else {
                        result += "0" + second;
                    }
                } else {
                    int second = seconds % 60;
                    if (second > 9) {
                        result += "00:00:" + second;
                    } else {
                        result += "00:0" + second;
                    }
                }
                return result;

            } catch (Exception e) {
                return input;
            }
        }
    }

    public static long formatNumber(String input) {
        if ("".equals(input) || "-".equals(input)) {
            return 0L;
        } else if (input.indexOf(":") > 0) {
            String[] inputArr = input.split(":");
            if (inputArr.length == 2) {
                return Long.parseLong(inputArr[0]) * 60 + Long.parseLong(inputArr[1]);
            } else if (inputArr.length == 3) {
                return Long.parseLong(inputArr[0]) * 3600 + Long.parseLong(inputArr[1]) * 60 + Long.parseLong(inputArr[2]);
            } else {
                return 0;
            }
        } else {
            return Long.parseLong(input);
        }
    }
}
