package com.asiainfo.billing.drquery.process.compile;

import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CompileJava {
	
	private final static Log log = LogFactory.getLog(CompileJava.class);

	public static boolean compile(String className, String source, String targetPath, String classPath){
		log.info("compile target path --> " + targetPath);
		
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler(); 
		if(javaCompiler == null){
			throw new RuntimeException("System JavaCompiler is null, please check the JAVA runtime environment whether is above JDK1.6, if the runtime environment is JRE, please replace the JRE with JDK.");
		}
		JavaFileObject fileObject = new JavaStringObject(className, source); 
		if(classPath == null || classPath.equals("")){
            if(CompileJava.class.getClassLoader().getResource("/") != null) {
                String path = CompileJava.class.getClassLoader().getResource("/").getPath();
                path = path.substring(0, path.indexOf("WEB-INF") + 7);
                classPath = path + "/lib";
            }
		}
		
		log.info("Extend lib classPath ---->" + classPath);
		/*CompilationTask getTask(Writer out,
                JavaFileManager fileManager,
                DiagnosticListener<? super JavaFileObject> diagnosticListener,
                Iterable<String> options,
                Iterable<String> classes,
                Iterable<? extends JavaFileObject> compilationUnits);*/
		CompilationTask task;
		try {
			task = javaCompiler.getTask(null, null, null, Arrays.asList("-d", targetPath,  "-extdirs", classPath), null, Arrays.asList(fileObject));
			boolean success = task.call(); 
			if(!success){
				throw new RuntimeException("Class[" + className + ".java]compile error! Please check the source code!");
			}else{
				log.info("Class[" + className + ".java]compile success!");
			}
			return success;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}  
	}

}
