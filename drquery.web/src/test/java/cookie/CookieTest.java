package cookie;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CookieTest {
	
	private static String sessionId;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		URL url = new URL("http://localhost:8080/app/cookieTest");
		//URL url2 = new URL("http://localhost:8080/app/cookieTest?m=!createSession");
		String content = "test";
		get(url, content, url);
		System.out.println("end...");  
	}
	
//	public static InputStream getStream(URL url,String post,URL cookieurl){  
//		System.out.println("begin...");
//        HttpURLConnection connection;  
//        String cookieVal = null ;  
//        //String sessionId = "" ;  
//        String key=null ;  
//        //if (cookieurl!= null ){              
//            try {  
//                connection = (HttpURLConnection)cookieurl.openConnection();  
//                for  ( int  i =  1 ; (key = connection.getHeaderFieldKey(i)) !=  null ; i++ ) {
//                	//System.out.println("key = " + key );
//                    if  (key.equalsIgnoreCase( "set-cookie" )) {  
//                        cookieVal = connection.getHeaderField(i);  
//                        cookieVal = cookieVal.substring(0 , cookieVal.indexOf( ";" ));  
//                        sessionId = cookieVal+";" ;  
//                        System.out.println("key --> " + key + ", value --> " + cookieVal);
//                    }  
//                }  
//                InputStream in = connection.getInputStream();  
//                System.out.println("sessionId ---> " + sessionId);  
//                return in;
//            }catch (MalformedURLException e){  
//                System.out.println("url can't connection" );  
//                return   null ;  
//            }catch (IOException e){ 
//            	e.printStackTrace();
//                return   null ;  
//            }  
//        //}  
//  
//       
//        
//    }
	
	
	public static void get(URL url,String post,URL cookieurl){
		HttpURLConnection connection;  
        String cookieVal = null ;  
        String key=null ;  
		try  {  
            connection = (HttpURLConnection)url.openConnection();  
            if (cookieurl!= null ){  
                //connection.addRequestProperty("Cookie" , "JSESSIONID=41F6FEAF3701B942029860D207478E6B;");  
                connection.addRequestProperty("Cookie" , "billId=18601134210;");  
            }  
            if (post!= "" ){  
                connection.setDoOutput(true );  
                connection.setRequestMethod("POST" );  
                connection.getOutputStream().write(post.getBytes());  
                connection.getOutputStream().flush();  
                connection.getOutputStream().close();  
            }  
            int  responseCode = connection.getResponseCode();  
            int  contentLength = connection.getContentLength();  
            // System.out.println("Content length: "+contentLength);   
            if  (responseCode != HttpURLConnection.HTTP_OK )  {
            	
            }
            InputStream in = connection.getInputStream();  
        }  
        catch (Exception e) {  
        	e.printStackTrace();
            // System.out.println(e);   
            // e.printStackTrace();   
        }  
	}


}
