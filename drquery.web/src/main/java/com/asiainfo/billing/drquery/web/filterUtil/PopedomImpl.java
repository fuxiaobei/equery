package com.asiainfo.billing.drquery.web.filterUtil;

import com.asiainfo.billing.drquery.web.servlet.RoleGrandInfo;
import com.asiainfo.portal.framework.external.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PopedomImpl extends DefaultPopedomImpl implements IPopedom {

    private final static String PORTAL_POPEDOMIMPL_SESSION =
            "PORTAL_POPEDOMIMPL_SESSION";

    /**
     * 判断是否登陆
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return boolean
     */
    public boolean isLogin(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(PORTAL_POPEDOMIMPL_SESSION) == null) {
            return false;
        }
        return true;
    }

    /**
     * 将操作信息放到session中
     *
     * @param operInfo OperInfo
     * @return boolean
     */
    public boolean doSelfSession(HttpServletRequest request,
            HttpServletResponse response,
            OperInfo operInfo) {
        if (operInfo == null) {
            return false;
        }
        
        HttpSession session = request.getSession();
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(operInfo.getOpId());
        userDetail.setOrgId(operInfo.getOporgid());
        userDetail.setOpName(operInfo.getOpname());
        session.setAttribute(PORTAL_POPEDOMIMPL_SESSION, operInfo);
        session.setAttribute("user", userDetail);
        
        StringBuffer rest = new StringBuffer(com.asiainfo.billing.drquery.web.config.Config.getRest());
        rest.append("query/common");

        Map m1 = new HashMap();
        Map m3 = new HashMap();

        m3.put("opId", operInfo.getOpId());
        m3.put("opname", operInfo.getOpname());
        m3.put("oplogname", operInfo.getOplogname());
        m3.put("oporgid", operInfo.getOporgid());
        m3.put("operHostIp", operInfo.getOperHostIp());
        m3.put("hostName", operInfo.getHostName());
        m3.put("macAddress", operInfo.getMacAddress());
        m3.put("userName", operInfo.getUserName());
        m3.put("tokenId", operInfo.getTokenId());
        m3.put("cupSerial", operInfo.getCupSerial());

        m1.put("sOper", m3);
        m1.put("opId", operInfo.getOpId());


        try {
            String result = com.asiainfo.billing.drquery.web.rest.RestManager.postWithJson(rest.toString(), m1);
            if(result!=null){
                JSONObject json=JSONObject.fromString(result);
                JSONArray roleGrandList=json.getJSONArray("roleGrandList");
                RoleGrandInfo  rg=new RoleGrandInfo();
                 List<RoleGrandInfo> list=new ArrayList<RoleGrandInfo>();
                for(int i=0;i<roleGrandList.length();i++){
                      JSONObject roleGrand=roleGrandList.getJSONObject(i);
                        rg.setRoleId(roleGrand.getString("RoleId"));
                        rg.setRoleId(roleGrand.getString("EntId"));
                        rg.setRoleId(roleGrand.getString("EntType"));
                        rg.setRoleId(roleGrand.getString("RoleGrantId"));
                        rg.setRoleId(roleGrand.getString("PrivId")); 
                        rg.setRoleId(roleGrand.getString("Notes"));
                        rg.setRoleId(roleGrand.getString("ValidDate"));
                        rg.setRoleId(roleGrand.getString("State")); 
                        rg.setRoleId(roleGrand.getString("ExpireDate"));
                        rg.setRoleId(roleGrand.getString("State")); 
                        list.add(rg);
                 }
                session.setAttribute("roleGrandList", list);
               
            }
            
        } catch (IOException ex) {
            Logger.getLogger(PopedomImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return true;
    }
}
