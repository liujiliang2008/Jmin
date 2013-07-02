/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Element;
import org.jmin.jda.impl.exception.SqlDefinitionFileException;
import org.jmin.jda.impl.util.StringUtil;

/**
 * 文件查找
 * 
 * @author chris liao
 */
public class SqlFileFinder {
	
	/**
	 * 查找文件
	 */
	public static URL find(String filename)throws SqlDefinitionFileException{
		return find(System.getProperty("user.dir"),filename);
	}
	
	/**
	 * 查找文件
	 */
	public static URL find(String currentFolder,String filename)throws SqlDefinitionFileException{
		if(StringUtil.isNull(filename))
			 throw new NullPointerException("File name cann;t be null");
			
			filename=filename.trim();
			String extension = getExtension(filename);
			if(!extension.toLowerCase().equals("xml"))
				throw new SqlDefinitionFileException("File["+ filename +"] is not a valid xml file");
			
		 filename = SqlFileFinder.getFilterValue(filename);//过滤环境变量
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
					throw new SqlDefinitionFileException("Not found sql map file:"+filename);
				else
					return fileURL;
			}
	}
	
	/**
	 * 从类路径中查找
	 */
	private static URL findFromClassPath(String filename)throws SqlDefinitionFileException{
		return (SqlFileFinder.class.getClassLoader()).getResource(filename);
	}
 
	/**
	 * 直接从目录中匹配
	 */
	private static URL findFromSytemFolder(String filename)throws SqlDefinitionFileException{
		File file = new File(filename);
		try {
			if(!file.exists())
				throw new SqlDefinitionFileException("Not found sql map file:"+filename);
			if(!file.isFile())
				throw new SqlDefinitionFileException("["+filename+"] is not a file,but a folder");
			return file.toURL();
		 
		} catch (MalformedURLException e) {
		 throw new SqlDefinitionFileException(null,e);
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
	public static void validateXMLFile(URL url)throws SqlDefinitionFileException {
		if (url == null)
			throw new SqlDefinitionFileException("File URL can't be null");
		String extension = getExtension(url.getFile());
		if (!extension.toUpperCase().equals("XML"))
			throw new SqlDefinitionFileException("A XML file is required");
	}

	/**
	 * 验证顶级节点
	 */
	public static void validateXMLRoot(Element rootElement, String rootName)throws SqlDefinitionFileException {
		if (rootElement == null)
			throw new SqlDefinitionFileException("Missed root node");
		if (!rootElement.getName().equalsIgnoreCase(rootName))
			throw new SqlDefinitionFileException("Error root node name must be "+ rootName);
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
	
	/**
	 * 获得过滤环境变量
	 */
	public static String getFilterValue(String value){
		int[] pos = getVariablePos(value);
		while(pos[1] >0 && pos[1] > pos[0]){
			String variableName = value.substring(pos[0]+2,pos[1]);//参数变量名
			String variableBlock = value.substring(pos[0],pos[1]+1);//参数变量块
			
			if(!StringUtil.isNull(variableName)){
				String variableValue = System.getProperty(variableName.trim());
				if(StringUtil.isNull(variableValue))
					throw new IllegalArgumentException("Not found system variable value with name:"+variableName);
					value = StringUtil.replace(value,variableBlock,variableValue);
			}
			pos = getVariablePos(value);
		}
		
		return value;
	}
	
	/**
	 * 获取环境变量位置
	 */
	private static int[]getVariablePos(String text){
		int index =text.indexOf("#{");
		int index2 =text.indexOf("${");
		int endPos =text.indexOf("}");
		int startPos= -1;
		
		if(index>=0 && index2>=0){
			startPos=(index<=index2)?index:index2;
		}else if(index==-1 && index2>=0){
			startPos = index2;
		}else if(index>=0 && index2==-1){
			startPos = index;
		}else if(index==-1 && index2==-1){
			startPos = -1;
		}
		
		return new int[]{startPos,endPos};
	}
}
