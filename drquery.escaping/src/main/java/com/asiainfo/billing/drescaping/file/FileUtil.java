package com.asiainfo.billing.drescaping.file;

import java.io.*;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author zhouquan3
 */
public class FileUtil implements IFile{
    private final static Log log = LogFactory.getLog(FileUtil.class);

    public void putMap2File(String fileName, Map map) {
        try {
            ObjectOutputStream oos=null;
            oos=new ObjectOutputStream(new FileOutputStream(new File(fileName),false));
            oos.writeObject(map);
        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        } 
    }

    public Map getMapFromFile(String fileName) {   
        Map map=null;
        try {
            ObjectInputStream ois=null;
            ois=new ObjectInputStream(new FileInputStream(new File(fileName)));
            map=(Map)ois.readObject();
        } catch (ClassNotFoundException ex) {
            log.error(ex);
        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        } 
        return map;
    }
    
}
