package com.asiainfo.billing.drquery.client.prebill;
import com.asiainfo.billing.drquery.client.prebill.instance.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author zhouquan3
 */
public class PreBillDataHandler {

    public Map<String,List> getTailList(String busiType, JSONObject data) {
        if ("GSM_L".equals(busiType)) {
            GSMLContentHandlerImpl handler=new GSMLContentHandlerImpl();
            return handler.getTailList(data);
        }else if ("GSM_T".equals(busiType)) {
            GSMTContentHandlerImpl handler=new GSMTContentHandlerImpl();
            return handler.getTailList(data);
        }else if ("GSM_R".equals(busiType)) {
            GSMRContentHandlerImpl handler=new GSMRContentHandlerImpl();
            return handler.getTailList(data);
        }else if ("GSM_HZ".equals(busiType)) {
            GSMHZContentHandlerImpl handler=new GSMHZContentHandlerImpl();
            return handler.getTailList(data);
        }else if ("SMS".equals(busiType)) {
            SMSContentHandlerImpl handler=new SMSContentHandlerImpl();
            return handler.getTailList(data);
        }else if ("WLANXY".equals(busiType)) {
            WLANXYContentHandlerImpl handler=new WLANXYContentHandlerImpl();
            return handler.getTailList(data);
        }else if ("WLANJT".equals(busiType)) {
            WLANJTContentHandlerImpl handler=new WLANJTContentHandlerImpl();
            return handler.getTailList(data);
        }else if ("WLANAUTO".equals(busiType)) {
            WLANAUTOContentHandlerImpl handler=new WLANAUTOContentHandlerImpl();
            return handler.getTailList(data);
        }else if ("ISMG".equals(busiType)) {
            ISMGContentHandlerImpl handler=new ISMGContentHandlerImpl();
            return handler.getTailList(data);
        }else if("MMS".equals(busiType)){
            MMSContentHandlerImpl handler=new MMSContentHandlerImpl();
            return handler.getTailList(data);
        }else if("GPRS".equals(busiType)){
            GPRSContentHandlerImpl handler=new GPRSContentHandlerImpl();
            return handler.getTailList(data);
        }else if("IGPRS".equals(busiType)){
            IGPRSContentHandlerImpl handler=new IGPRSContentHandlerImpl();
            return handler.getTailList(data);
        }else if("WAP".equals(busiType)){
            WAPContentHandlerImpl handler=new WAPContentHandlerImpl();
            return handler.getTailList(data);
        } else if("LMT".equals(busiType)){
            LMTContentHandlerImpl handler=new LMTContentHandlerImpl();
            return handler.getTailList(data);
         } else if("SMS_MPAY".equals(busiType)){
            SMSMPAYContentHandlerImpl handler=new SMSMPAYContentHandlerImpl();
            return handler.getTailList(data); 
        } else if ("KJAVA".equals(busiType)) {
            KJAVAContentHandlerImpl handler=new KJAVAContentHandlerImpl();
            return handler.getTailList(data);
        } else if ("WLAN".equals(busiType)) {
            WLANContentHandlerImpl handler=new WLANContentHandlerImpl();
            return handler.getTailList(data);
        } else if ("VGSM".equals(busiType)) {
            VGSMContentHandlerImpl handler=new VGSMContentHandlerImpl();
            return handler.getTailList(data);
        }  else if ("MMK".equals(busiType)) {
            MMKContentHandlerImpl handler=new MMKContentHandlerImpl();
            return handler.getTailList(data);
             }  else if ("YXHD".equals(busiType)) {
            YXHDContentHandlerImpl handler=new YXHDContentHandlerImpl();
            return handler.getTailList(data);
        } else if ("SX".equals(busiType)) {
            SXContentHandlerImpl handler=new SXContentHandlerImpl();
            return handler.getTailList(data);
        } else if ("BIP".equals(busiType)) {
            BIPContentHandlerImpl handler=new BIPContentHandlerImpl();
            return handler.getTailList(data);
        } else if ("YYZX".equals(busiType)) {
            YYZXContentHandlerImpl handler=new YYZXContentHandlerImpl();
            return handler.getTailList(data);
        } else if ("YXHD".equals(busiType)) {
            YXHDContentHandlerImpl handler=new YXHDContentHandlerImpl();
            return handler.getTailList(data);
        } else if ("MMS_ZMZL".equals(busiType)) {
            MMSZMZLContentHandlerImpl handler=new MMSZMZLContentHandlerImpl();
            return handler.getTailList(data);
        } else if ("GAMEGP".equals(busiType)) {
            GAMEGPContentHandlerImpl handler=new GAMEGPContentHandlerImpl();
            return handler.getTailList(data);
        } else if ("GAMEDKXF".equals(busiType)) {
            GAMEDKXFContentHandlerImpl handler=new GAMEDKXFContentHandlerImpl();
            return handler.getTailList(data);
        } else if ("VPMN".equals(busiType)) {
            VPMNContentHandlerImpl handler=new VPMNContentHandlerImpl();
            return handler.getTailList(data);
        }else if("INBOSS".equals(busiType)){
            INBOSSContentHandlerImpl handler=new INBOSSContentHandlerImpl();
            return handler.getTailList(data);
        }else if("LGSM".equals(busiType)){
            LGSMContentHandlerImpl handler=new LGSMContentHandlerImpl();
            return handler.getTailList(data);
        }else if("HGPRS".equals(busiType)){
            HGPRSContentHandlerImpl handler=new HGPRSContentHandlerImpl();
            return handler.getTailList(data);
        }else if("SMS_ZMZL".equals(busiType)){
            SMSZMZLContentHandlerImpl handler=new SMSZMZLContentHandlerImpl();
            return handler.getTailList(data);
        }else if("WLAN_IO".equals(busiType)){
            WLANIOContentHandlerImpl handler=new WLANIOContentHandlerImpl();
            return handler.getTailList(data);
        }else {
            throw new UnsupportedOperationException("key["+busiType+"] not supported yet.");
        }
    }

}
