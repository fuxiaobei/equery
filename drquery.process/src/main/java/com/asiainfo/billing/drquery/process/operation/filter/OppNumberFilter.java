package com.asiainfo.billing.drquery.process.operation.filter;

import java.util.Map;

public class OppNumberFilter implements NumberFilter{
    
    private String[] destId;
    
    public void setDestId(String[] destId) {
        this.destId = destId;
    }
    
    @Override
    public boolean filter(Map<String, String> data) {
        String temp=data.get("OPP_NUMBER");
        int size=destId.length;
        boolean match=false;
        for(int i=0;i<size;i++){
            if(temp.indexOf(destId[i])>0){
                match=true;
                break;
            }
        }
        return match;
    }

}
