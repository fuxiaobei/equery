package com.asiainfo.billing.drquery.rest;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * Created by wangkai8 on 15/5/28.
 */
public class RestUtil {

    private static int _timeout = 7200000;
    private static final String CONTENT_CHARSET = "UTF-8";
    private static Log log = LogFactory.getLog(RestUtil.class);

    public static byte[] get(String uri) throws IOException {

        HttpClient httpClient= new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CONTENT_CHARSET);
        httpClient.setConnectionTimeout(_timeout);
        httpClient.setTimeout(_timeout);

        GetMethod getMethod = null;
        try{
            log.debug("send data to http : ["+uri+"]");
            getMethod= new GetMethod();
            getMethod.setURI(new URI(uri, false));
            getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            getMethod.setRequestHeader("Connection", "close");
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                log.error("Method failed: " + getMethod.getStatusLine());
                throw new RuntimeException(getMethod.getStatusLine() + "");
            }

            InputStream is = getMethod.getResponseBodyAsStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally{
            if(getMethod!=null) getMethod.releaseConnection();
        }
    }


    public static void main(String[] args) throws Exception {
        byte[] data = get("http://localhost:8080/test?id=0001");
        File file = new File("/Users/wangkai8/Downloads/phoenix-4.3.0-bin.tar---xiaomiemie.gz");
        OutputStream os = new FileOutputStream(file);
        os.write(data);
        System.out.println("write file done!");
        System.out.println(data.length);
    }
}
