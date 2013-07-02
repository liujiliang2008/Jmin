/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.ioc.impl.config.xml;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Element;
import org.jmin.ioc.impl.exception.BeanConfiguerationFileException;
import org.jmin.ioc.impl.util.StringUtil;

/**
 * 文件查找
 * 
 * @author chris liao
 */
public class BeanXMLFileFinder {
	
	/**
	 * 查找文件
	 */
	public static URL find(String filename)throws BeanConfiguerationFileException{
		return find(System.getProperty("user.dir"),filename);
	}
	
	/**
	 * 查找文件
	 */
	public static URL find(String currentFolder,String filename)throws BeanConfiguerationFileException{
		if(StringUtil.isNull(filename))
		 throw new NullPointerException("File name can;t be null");
		
		filename=filename.trim();
		String extension = getExtension(filename);
		if(!extension.toLowerCase().equals("xml"))
			throw new BeanConfiguerationFileException("File["+ filename +"] is not a valid xml file");
		
		filename = StringUtil.getFilterValue(filename);//过滤环境变量
		if(filename.toLowerCase().startsWith("cp:")){//需要从classpath中查找
			String filename2=filename.substring(3).trim();
			return findFromClassPath(filename2);
		}else if(filename.toLowerCase().startsWith("classpath:")){//需要从classpath中查找
			String filename2=filename.substring(10).trim();
			return findFromClassPath(filename2);
		}else{//无法确定文件是是一个完整的路径还是需要从classpath中寻找，因此需要尝试不同的寻找方法
			
			URL fileURL = null;
			try{
				fileURL = findFromClassPath(filename);
			}catch(Exception e){
			}
			
			try{
				if(fileURL==null)
					fileURL = findFromSytemFolder(filename);
			}catch(Exception e){
			}
			
			if(fileURL == null && !StringUtil.isNull(currentFolder)){//文件没有被找到
				 String fileFullName = currentFolder + filename;
				if(!filename.startsWith("/") && !filename.startsWith("\\"))
					fileFullName = currentFolder + File.separatorChar + filename;
				try{
					fileURL = findFromSytemFolder(fileFullName);
				}catch(Exception e){
				}
			}
			
			if(fileURL==null)
				throw new BeanConfiguerationFileException("Not found ioc file:"+filename);
			else
				return fileURL;
		}
	}
	
	/**
	 * 从类路径中查找
	 */
	private static URL findFromClassPath(String filename)throws BeanConfiguerationFileException{
		return (BeanXMLFileFinder.class.getClassLoader()).getResource(filename);
	}
	
	/**
	 * 直接从目录中匹配
	 */
	private static URL findFromSytemFolder(String filename)throws BeanConfiguerationFileException{
		File file = new File(filename);
		try {
			if(!file.exists())
				throw new BeanConfiguerationFileException("Not found ioc xml file:"+filename);
			if(!file.isFile())
				throw new BeanConfiguerationFileException("["+filename+"] is not a file,but a folder");
			
			return file.toURL();
		} catch (MalformedURLException e) {
		 throw new BeanConfiguerationFileException(e);
		}
	}
	
	/**
	 * get File extension
	 */
	private static String getExtension(String filename) {
		return getExtension(filename, "");
	}

	/**
	 * get File extension
	 */
	private static String getExtension(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			if ((i > -1) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return defExt;
	}
	
	/**
	 * 验证XML文件
	 */
	public static void validateXMLFile(URL url)throws BeanConfiguerationFileException {
		if (url == null)
			throw new BeanConfiguerationFileException("File URL can't be null");
		String extension = getExtension(url.getFile());
		if (!extension.toUpperCase().equals("XML"))
			throw new BeanConfiguerationFileException("File["+url+"]is not a valid XML file");
	}

	/**
	 * 验证顶级节点
	 */
	public static void validateXMLRoot(Element rootElement, String rootName,String filename)throws BeanConfiguerationFileException {
		if (rootElement == null)
			throw new BeanConfiguerationFileException("Missed root node in file:"+filename);
		if (!rootElement.getName().equalsIgnoreCase(rootName))
			throw new BeanConfiguerationFileException("Missed root node<"+ rootName +"> in file:"+filename);
	}
	
	/**
	 * 获得文件的路径
	 */
	public static String getFilePath(String fileName) {
		int point = fileName.lastIndexOf('/');
		if (point == -1)
			point = fileName.lastIndexOf('\\');
		String folder = fileName;
		if (point > 0) {
			folder = folder.substring(0, point);
		}

		if (folder.startsWith("/")) {
			folder = folder.substring(1);
		}
		return folder;
	}
}
