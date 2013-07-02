/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jmin.ioc.BeanContainer;
import org.jmin.ioc.BeanException;
import org.jmin.ioc.element.annotation.Bean;

/**
 * 注解扫描并装载
 * 
 * @author Liao
 */

public class BeanClassScanner {

	/**
	 * 扫描注解，并将注解类蹈入容器中
	 */
	public static void scan(String packageName,BeanContainer container)throws BeanException {
		try {
			boolean recursive = true;
			packageName = packageName.replace('.', '/');
			Enumeration dirs = BeanClassScanner.class.getClassLoader().getResources(packageName);
			while (dirs.hasMoreElements()) { 
				URL url =(URL)dirs.nextElement();   
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					importBeanInfoFromFolder(packageName, filePath, recursive,container);
				} else if ("jar".equals(protocol)) {
					JarFile jar = ((JarURLConnection)url.openConnection()).getJarFile();
					Enumeration<JarEntry> entries = jar.entries();
					while(entries.hasMoreElements()) {
						JarEntry entry = entries.nextElement();
						String name = entry.getName();
						if(name.charAt(0) == '/') 
							name = name.substring(1);
						
						if (name.startsWith(packageName)) {
							int idx = name.lastIndexOf('/'); 
							if (idx != -1)  
								packageName = name.substring(0, idx).replace('/', '.');
						
							if ((idx != -1) || recursive) {
								if (name.endsWith(".class") && !entry.isDirectory()) {  
									String className = name.substring(packageName.length() + 1,name.length() - 6);
								  Class clazz = Class.forName(packageName + '.' + className);
								  if(clazz.isAnnotationPresent(Bean.class)){
								  	BeanClassImporter.register(clazz,container);
								  }
								}
							}
						}
					}
				}
			}
			
		}catch(BeanException e){
			throw e;
		}catch( Exception e) {
		 throw new BeanException("Failed to load annotation from package["+packageName+"]",e);
		}  
  }

	/**  
	 * 以文件的形式来获取包下的所有Class  
	 */
	private static void importBeanInfoFromFolder(String packageName,String packagePath, final boolean recursive, BeanContainer container)throws BeanException {
		try {
			File dir = new File(packagePath);
			if (!dir.exists() || !dir.isDirectory()) 
				return;
			
			File[] dirfiles = dir.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
				}
			});
	 
			for (File file : dirfiles) {
				if (file.isDirectory()) {
					importBeanInfoFromFolder(packageName + "." + file.getName(),file.getAbsolutePath(), recursive,container);
				}else {
					String className = file.getName().substring(0,file.getName().length() - 6);
	        Class clazz = Thread.currentThread().getContextClassLoader().loadClass(packageName.replace('/','.') + '.' + className);
	        if(clazz.isAnnotationPresent(Bean.class)){
	        	BeanClassImporter.register(clazz,container);
				  }
				}
			}
		}catch(BeanException e){
			throw e;
		}catch( Exception e) {
			 throw new BeanException("Failed to load annotation from package["+packageName+"]",e);
		}   		
 }
}
