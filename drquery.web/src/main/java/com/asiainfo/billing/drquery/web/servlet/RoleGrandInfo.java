/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.billing.drquery.web.servlet;

/**
 *
 * @author wuhe
 */
public class RoleGrandInfo {

    private String RoleId;
    private String EntId;
    private String EntType;
    private String RoleGrantId;
    private String PrivId;
    private String Notes;
    private String ValidDate;
    private String ExpireDate;
    private String DoneDate;
    private String State;
    
    
    public RoleGrandInfo(String RoleId, String EntId, String EntType, String RoleGrantId, String PrivId, String Notes, String ValidDate, String DoneDate, String State) {
        this.RoleId = RoleId;
        this.EntId = EntId;
        this.EntType = EntType;
        this.RoleGrantId = RoleGrantId;
        this.PrivId = PrivId;
        this.Notes = Notes;
        this.ValidDate = ValidDate;
        this.DoneDate = DoneDate;
        this.State = State;
    }
   
  
    
    @Override
    public String toString() {
        return "RoleGrandInf{" + "RoleId=" + RoleId + ", EntId=" + EntId + ", EntType=" + EntType + ", RoleGrantId=" + RoleGrantId + ", PrivId=" + PrivId + ", Notes=" + Notes + ", ValidDate=" + ValidDate + ", ExpireDate=" + ExpireDate + ", DoneDate=" + DoneDate + ", State=" + State + '}';
    }

    
    public String getDoneDate() {
        return DoneDate;
    }

    public void setDoneDate(String DoneDate) {
        this.DoneDate = DoneDate;
    }

    public String getEntId() {
        return EntId;
    }

    public void setEntId(String EntId) {
        this.EntId = EntId;
    }

    public String getEntType() {
        return EntType;
    }

    public void setEntType(String EntType) {
        this.EntType = EntType;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String ExpireDate) {
        this.ExpireDate = ExpireDate;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }

    public String getPrivId() {
        return PrivId;
    }

    public void setPrivId(String PrivId) {
        this.PrivId = PrivId;
    }

    public String getRoleGrantId() {
        return RoleGrantId;
    }

    public void setRoleGrantId(String RoleGrantId) {
        this.RoleGrantId = RoleGrantId;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String RoleId) {
        this.RoleId = RoleId;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getValidDate() {
        return ValidDate;
    }

    public void setValidDate(String ValidDate) {
        this.ValidDate = ValidDate;
    }
    
    public RoleGrandInfo(){}
    
}
