package com.asiainfo.billing.drquery.connection;

/**
 * Created by wangkai8 on 15/6/10.
 */
public interface PasswordDecoder {

    public String getPassword(String password);


    public static class DefaultDecoder implements PasswordDecoder {

        @Override
        public String getPassword(String value) {
            return value;
        }
    }

}
