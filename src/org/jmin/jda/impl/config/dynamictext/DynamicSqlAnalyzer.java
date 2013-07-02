/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.dynamictext;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.impl.config.dynamictext.html.HTMLNode;
import org.jmin.jda.impl.config.dynamictext.html.HTMLTokenizer;
import org.jmin.jda.impl.config.dynamictext.html.HTMLTree;
import org.jmin.jda.impl.config.statictext.ParamResult;
import org.jmin.jda.impl.config.statictext.StaticSqlAnalyzer;
import org.jmin.jda.impl.exception.SqlDefinitionException;
import org.jmin.jda.impl.exception.SqlDynTagException;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.statement.DynTag;
import org.jmin.jda.statement.tag.ChooseTag;
import org.jmin.jda.statement.tag.ForeachTag;
import org.jmin.jda.statement.tag.IfTag;
import org.jmin.jda.statement.tag.OtherwiseTag;
import org.jmin.jda.statement.tag.SetTag;
import org.jmin.jda.statement.tag.TextTag;
import org.jmin.jda.statement.tag.TrimTag;
import org.jmin.jda.statement.tag.WhenTag;
import org.jmin.jda.statement.tag.WhereTag;

/**
 * SQL动态分析器
 * 
 * @author Chris Liao
 */

public class DynamicSqlAnalyzer {
	
	/**
	 * 分析参数的位置信息
	 */
	public static DynTag[] analyzeDynamicSQL(String sqlId,String SQL,Class paramClass,JdaContainer container)throws SQLException{
		try {
			StringReader reader = new StringReader(SQL);
			HTMLTree tree = new HTMLTree(new HTMLTokenizer(reader));
			HTMLNode rootNode = tree.getRootNode();
			if(rootNode.getChildrenCount()==0)
		  	throw new SqlDynTagException(sqlId,"Dynamic sql can't be null or empty");
	
			List dynTagList = new ArrayList();
			Enumeration childEnum = rootNode.getChildren();
			while(childEnum.hasMoreElements()){
				Object child = childEnum.nextElement();
				DynTag dynTag = createDynTag(sqlId,child,paramClass,container);
				dynTagList.add(dynTag);
			 }
			return (DynTag[])dynTagList.toArray(new DynTag[0]);
		} catch (Throwable e) {
			throw new SqlDefinitionException(sqlId,e);
		} 
	}
	
	/**
	 * 找出节点下所有子节点
	 */
	private static DynTag createDynTag(String sqlId,Object element,Class paramClass,JdaContainer container)throws SQLException{
		DynTag dynTag = null;
		if(element instanceof String){//文本SQL
			String textBlock =(String)element;
			if(textBlock!=null && textBlock.trim().length()>0){
				ParamResult result = StaticSqlAnalyzer.analyzeStaticSQL(sqlId,textBlock,paramClass,container);
				ParamUnit[] paramUnits = null;
				if(result.getParamMap()!=null)
					paramUnits=result.getParamMap().getParamUnits();
				
				String sqlText = result.getExeSQL();
				sqlText =XmlSpecialSymbol.replaceSpecialSymbol(sqlText);
				dynTag = new TextTag(sqlText,paramUnits);
			}else if(textBlock!=null && textBlock.trim().length()==0){
				dynTag = new TextTag(textBlock);
			}
		}else{
			HTMLNode node = (HTMLNode)element;
			String tagName = node.getName();
			if("if".equalsIgnoreCase(tagName)){//2
				String test = node.getAttribute("test");
				
				if(StringUtil.isNull(test))
					throw new SqlDynTagException(sqlId,"If tag missed test='xxxx'");
				if(node.getChildrenCount() == 0)
					throw new SqlDynTagException(sqlId,"If tag missed child tag");
			
				test =XmlSpecialSymbol.replaceSpecialSymbol(test);
				IfTag tag = new IfTag(test);
				Enumeration childEnum = node.getChildren();
		    while(childEnum.hasMoreElements()){
		    	Object child = childEnum.nextElement();
		    	tag.addChild(createDynTag(sqlId,child,paramClass,container));
		  	}
		   
		    dynTag = tag;
			}else if("where".equalsIgnoreCase(tagName)){//3
				if(node.getChildrenCount() == 0)
					throw new SqlDynTagException(sqlId,"Where tag missed child tag");
				
				WhereTag tag = new WhereTag();
				Enumeration childEnum = node.getChildren();
		    while(childEnum.hasMoreElements()){
		    	Object child = childEnum.nextElement();
		    	tag.addChild(createDynTag(sqlId,child,paramClass,container));
		  	}
		    
		    dynTag = tag;
			}else if("set".equalsIgnoreCase(tagName)){//4
				if(node.getChildrenCount() == 0)
					throw new SqlDynTagException(sqlId,"Set tag missed child tag");
				
				SetTag tag = new SetTag();
				Enumeration childEnum = node.getChildren();
		    while(childEnum.hasMoreElements()){
		    	Object child = childEnum.nextElement();
		    	tag.addChild(createDynTag(sqlId,child,paramClass,container));
		  	}
		    
		    dynTag = tag;
			}else if("choose".equalsIgnoreCase(tagName)){//5
				if(node.getChildrenCount() == 0)
					throw new SqlDynTagException(sqlId,"Choose tag missed child tag");
				
				ChooseTag tag = new ChooseTag();
				Enumeration childEnum = node.getChildren();
				while(childEnum.hasMoreElements()){
		    	Object child = childEnum.nextElement();
		    	if(child instanceof HTMLNode){
		    	 	HTMLNode childNode =(HTMLNode)child;
		        if("when".equalsIgnoreCase(childNode.getName())){
			      	tag.addWhenTag((WhenTag)createDynTag(sqlId,child,paramClass,container));
		        }else if("otherwise".equalsIgnoreCase(childNode.getName())){
			      	tag.setOtherwiseTag((OtherwiseTag)createDynTag(sqlId,child,paramClass,container));
			      }
		    	}
		  	}
		    
		    if(tag.getSubWhenTagCount()==0)
		  		throw new SqlDynTagException(sqlId,"Chooose tag missed 'when' child tag");
		    if(tag.getOtherwiseTag()==null)
		  		throw new SqlDynTagException(sqlId,"Chooose tag missed 'otherwise' child tag");
		    
		    dynTag = tag;
			}else if("when".equalsIgnoreCase(tagName)){//6
				String test = node.getAttribute("test");
				if(StringUtil.isNull(test))
					throw new SqlDynTagException(sqlId,"When tag missed test=' '");
				if(node.getChildrenCount() == 0)
					throw new SqlDynTagException(sqlId,"When tag missed child tag");
				
				WhenTag tag = new WhenTag(test);
				Enumeration childEnum = node.getChildren();
		    while(childEnum.hasMoreElements()){
		    	Object child = childEnum.nextElement();
		    	tag.addChild(createDynTag(sqlId,child,paramClass,container));
		  	}
		    
		    dynTag = tag;
			}else if("otherwise".equalsIgnoreCase(tagName)){//7
				if(node.getChildrenCount() == 0)
					throw new SqlDynTagException(sqlId,"Otherwise tag missed child tag");
				
				OtherwiseTag tag = new OtherwiseTag();
				Enumeration childEnum = node.getChildren();
		    while(childEnum.hasMoreElements()){
		    	Object child = childEnum.nextElement();
		    	tag.addChild(createDynTag(sqlId,child,paramClass,container));
		  	}
		   
		    dynTag = tag;
			}else if("Foreach".equalsIgnoreCase(tagName)){//8
				String item = node.getAttribute("item");
				String open = node.getAttribute("open");
				String separator = node.getAttribute("separator");
				String close = node.getAttribute("close");
				
				if(item==null || item.trim().length()==0)
					throw new SqlDynTagException(sqlId,"Foreach tag missed 'item' attribute");
				if(node.getChildrenCount() == 0)
					throw new SqlDynTagException(sqlId,"Foreach tag missed sub text content");
				if(node.getChildrenCount() > 1)
					throw new SqlDynTagException(sqlId,"Foreach tag just contains a text content");
				
				Object firstChild=null;
				Enumeration childEnum = node.getChildren();
		    if(childEnum.hasMoreElements()){
		    	firstChild = childEnum.nextElement();
		  	}
			
				if(!(firstChild instanceof String)){
		      throw new SqlDynTagException(sqlId,"Foreach sub tag must be text type");
		    }else{
		    	String textTag =(String)firstChild;
		    	String itemDesc1 ="#{"+item+"}";
		    	String itemDesc2 ="${"+item+"}";
		    	String itemDesc3 ="#"+item+"#";
		    	
		    	if(textTag.indexOf(itemDesc1)==-1 && textTag.indexOf(itemDesc2)==-1 && textTag.indexOf(itemDesc3)==-1)
		    		throw new SqlDynTagException(sqlId,"Foreach sub text content not contains "+ itemDesc1);
		    }
	
				ForeachTag forTag = new ForeachTag(item,(String)firstChild);
				forTag.setStartSymbol(open);
				forTag.setSpaceSymbol(separator);
				forTag.setEndSymbol(close);
				dynTag = forTag;
				
			}else if("trim".equalsIgnoreCase(tagName)){//9
				if(node.getChildrenCount() == 0)
					throw new SqlDynTagException(sqlId,"Trim tag missed sub text content");
				
				TrimTag tag = new TrimTag();
				Enumeration childEnum = node.getChildren();
		    while(childEnum.hasMoreElements()){
		    	Object child = childEnum.nextElement();
		    	tag.addChild(createDynTag(sqlId,child,paramClass,container));
		  	}
		    
		    dynTag = tag;
			}else{
				throw new SqlDynTagException(sqlId,"Unknow tag["+tagName+"]");
			}
		}
		return dynTag;
	}
}
