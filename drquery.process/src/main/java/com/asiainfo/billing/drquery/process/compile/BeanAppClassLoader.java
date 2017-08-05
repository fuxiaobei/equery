package com.asiainfo.billing.drquery.process.compile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.billing.drquery.process.operation.fieldEscape.model.FieldEscapeModel;

/**
 * 自定义ClassLoader 
 * 可根据BeanAppClassLoader 从文件中动态生成类
 * 
 */
public class BeanAppClassLoader extends ClassLoader {
	
	private final static Log log = LogFactory.getLog(BeanAppClassLoader.class);

	private String classPath;
	
	public BeanAppClassLoader(ClassLoader webAppClassLoader){
		super(webAppClassLoader);
		log.info("System WebAppClassLoader --> " + webAppClassLoader);
	}
	
	@Override
	public Class loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class clz = null;
        // 如是系统类直接装载并记录后返回
        if(name.startsWith("java.")) {
            clz = findSystemClass(name); // 调用父类方法
            //System.out.println("loadClass: SystemLoading " + name);
            return clz;
        }

        try{
            clz = findLoadedClass(name);
            if(clz != null) {
                log.info(name + " have loaded");
                return clz;
            }
            try{
                clz = findClass(name);
            }catch (Exception ex) {
            }
            if(clz == null){
            	clz = this.getParent().loadClass(name);	//从servlet WebAPPClassLoader中加载类
            }
            if(clz == null) {
                clz = findSystemClass(name);	//如果BeanAppClassLoader没加载class，从父加载器中加载类
            }
            if(resolve && (clz != null)) {
                resolveClass(clz);
            }
            return clz;
        }catch (Exception e) {
            throw new ClassNotFoundException(e.toString());
        }
	}
	
	
	/**
	 * 根据类名字符串从指定的目录查找类，并返回类对象
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = null;
		try {
			classData = loadClassData(name);
		} catch (IOException e) {
		}
		return super.defineClass(name, classData, 0, classData.length);
	}

	
	
	/**
	 * 根据类名字符串加载类 byte 数据流
	 * 
	 * @param name
	 *            类名字符串 例如： com.cmw.entity.SysEntity
	 * @return 返回类文件 byte 流数据
	 * @throws IOException
	 */
	private byte[] loadClassData(String name) throws IOException {
		File file = getFile(name);
		FileInputStream fis = new FileInputStream(file);
		byte[] arrData = new byte[(int) file.length()];
		fis.read(arrData);
		return arrData;
	}

	
	
	/**
	 * 根据类名字符串返回一个 File 对象
	 * 
	 * @param name
	 *            类名字符串
	 * @return File 对象
	 * @throws FileNotFoundException
	 */
	private File getFile(String name) throws FileNotFoundException {
		File dir = new File(classPath);
		if (!dir.exists())
			throw new FileNotFoundException(classPath + " 目录不存在！");
		String _classPath = classPath.replaceAll("[\\\\]", "/");
		int offset = _classPath.lastIndexOf("/");
		name = name.replaceAll("[.]", "/");
		if (offset != -1 && offset < _classPath.length() - 1) {
			_classPath += "/";
		}
		_classPath += name + ".class";
		dir = new File(_classPath);
		if (!dir.exists())
			throw new FileNotFoundException(dir + " 不存在！");
		return dir;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, Exception {
		BeanAppClassLoader fileClsLoader = new BeanAppClassLoader(null);
		fileClsLoader.setClassPath("D:\\drquery\\runtime\\classes\\");
		Class cls = fileClsLoader.loadClass("com.asiainfo.billing.drquery.process.operation.fieldEscape.LocalFieldEscape");
		Object obj = cls.newInstance();
		
		Method method = obj.getClass().getDeclaredMethod("DEST_START_TIME_A1", Map.class, String.class, FieldEscapeModel.class); 
		
		Map map = new HashMap();
		map.put("SRC_OPP_EXT_INFO", "LEIXUE");
		Object value = method.invoke(obj, new Object[]{map, "SRC_OPP_EXT_INFO", null});
		System.out.println(value);
		
		Method[] mthds = cls.getMethods();
		for (Method mthd : mthds) {
			String methodName = mthd.getName();
			System.out.println("mthd.name=" + methodName);
		}
		System.out.println("obj.class=" + obj.getClass().getName());
		System.out.println("obj.class=" + cls.getClassLoader().toString());
		System.out.println("obj.class=" + cls.getClassLoader().getParent().toString());
	}

}
