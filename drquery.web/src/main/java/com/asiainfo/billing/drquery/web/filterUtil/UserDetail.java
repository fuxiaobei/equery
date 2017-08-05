
package com.asiainfo.billing.drquery.web.filterUtil;

/**
 *
 * @author wuhe
 */
class UserDetail {
   private String userId;
   private String orgId;
   private String opName;
   private String oplogname;

    public String getOplogname() {
        return oplogname;
    }

    public void setOplogname(String oplogname) {
        this.oplogname = oplogname;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public UserDetail(){}
    
    public UserDetail(String userId, String orgId) {
        this.userId = userId;
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
   
    
    
}
