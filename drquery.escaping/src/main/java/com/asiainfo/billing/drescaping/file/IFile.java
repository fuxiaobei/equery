package com.asiainfo.billing.drescaping.file;

import java.util.Map;

/**
 *
 * @author zhouquan3
 */
public interface IFile {
    
    public void putMap2File(String fileName,Map map);
    
    public Map getMapFromFile(String fileName);
}
