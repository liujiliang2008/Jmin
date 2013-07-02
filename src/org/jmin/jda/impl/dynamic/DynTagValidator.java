/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.dynamic;

import java.sql.SQLException;
import java.util.Map;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.impl.exception.ParamMapException;
import org.jmin.jda.impl.exception.SqlDynTagException;
import org.jmin.jda.impl.mapping.param.ParamUnitImpl;
import org.jmin.jda.impl.property.PropertyException;
import org.jmin.jda.impl.property.PropertyUtil;
import org.jmin.jda.impl.statement.SqlOperationType;
import org.jmin.jda.impl.util.StringUtil;
import org.jmin.jda.mapping.ParamUnit;
import org.jmin.jda.mapping.ParamValueMode;
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
 * 动态标签验证
 * 
 * @author Chris
 */

public class DynTagValidator {
	
	/**
	 * 检查节点
	 */
	public static void checkDynTag(String id,DynTag dynTag,Class paramClass,SqlOperationType sqlOpType,JdaContainer container)throws SQLException{
	  if(dynTag instanceof TextTag){//1
			TextTag tag = (TextTag)dynTag;
			if(tag.getText()==null)
				throw new SqlDynTagException(id,"text tag missed text content");
			
			ParamUnit[] paramUnits = tag.getParamUnit();
			for(int i=0;paramUnits!=null&& i<paramUnits.length;i++){
				ParamUnitImpl unit=(ParamUnitImpl)paramUnits[i];
			  ParamValueMode valueMode=unit.geParamValueMode();
				
				if(StringUtil.isNull(unit.getPropertyName()))
					throw new ParamMapException(id,"Parameter unit["+i+"]property name can't be null");	
				if(unit.getMapOwner()!=null)
					throw new ParamMapException(id,"Parameter unit["+i+"]("+unit.getPropertyName()+")has been used in another map");
				if(!SqlOperationType.Procedure.equals(sqlOpType) && !ParamValueMode.IN.equals(valueMode))
					throw new ParamMapException(id,"Parameter unit["+i+"]("+unit.getPropertyName()+")can't be out type parameter in sql type:"+sqlOpType);
				
				if(unit.getPropertyType()==null && !Map.class.isAssignableFrom(paramClass)){
					try {
							unit.setPropertyType(PropertyUtil.getPropertyType(paramClass,unit.getPropertyName()));
					} catch (PropertyException e) {
						throw new ParamMapException(id,"Parameter unit["+i+"]("+unit.getPropertyName()+")property exception",e);
					}
				} 
					
//				if(unit.getPropertyType()==null)
//					throw new ParamMapException(id,"Parameter unit["+i+"]("+unit.getPropertyName()+")property type can't be null");
//				if(!StringUtil.isNull(unit.getParamColumnTypeName()))
//					unit.setParamColumnTypeCode(container.getJdbcTypeCode(unit.getParamColumnTypeName()));
//				if(unit.getJdbcTypePersister()==null)
//					unit.setJdbcTypePersister(container.getTypePersister(unit.getPropertyType(),unit.getParamColumnTypeName()));
//				if(unit.getJdbcTypePersister()==null)
//					throw new ParamMapException(id,"Parameter unit["+i+"]persister can't be null");	
			 }
	
		}else if(dynTag instanceof IfTag){//2
			IfTag tag =(IfTag)dynTag;
			if(tag.getExpression()==null || tag.getExpression().trim().length()==0)
				throw new SqlDynTagException(id,"If tag missed test condition: test='xxx')");
			if(tag.getChildrenCount()==0)
				throw new SqlDynTagException(id,"If tag missed children tag");
			
			DynTag[]dynTags = tag.getChildren();
			for(int i=0;i<dynTags.length;i++)
				checkDynTag(id,dynTags[i],paramClass,sqlOpType,container);
		}else if(dynTag instanceof WhereTag){//3
			WhereTag tag =(WhereTag)dynTag;
			if(tag.getChildrenCount()==0)
				throw new SqlDynTagException(id,"Where tag missed children");
			DynTag[]dynTags = tag.getChildren();
			for(int i=0;i<dynTags.length;i++)
				checkDynTag(id,dynTags[i],paramClass,sqlOpType,container);
		}else if(dynTag instanceof ChooseTag){//4
			ChooseTag tag =(ChooseTag)dynTag;
			if(tag.getSubWhenTagCount()==0)
				throw new SqlDynTagException(id,"Choose tag missed 'when' children tag");
			if(tag.getOtherwiseTag()==null)
				throw new SqlDynTagException(id,"Choose tag missed 'otherwise' child tag");
		
			WhenTag[]tags = tag.getSubWhenTags();
			for(int i=0;i<tags.length;i++)
				checkDynTag(id,tags[i],paramClass,sqlOpType,container);
			checkDynTag(id,tag.getOtherwiseTag(),paramClass,sqlOpType,container);
		}else if(dynTag instanceof WhenTag){//5
			WhenTag tag =(WhenTag)dynTag;
			if(tag.getExpression()==null || tag.getExpression().trim().length()==0)
				throw new SqlDynTagException(id,"When tag missed test condition: test='xxx')");
			if(tag.getChildrenCount()==0)
				throw new SqlDynTagException(id,"When tag missed children tag");
			
			DynTag[]dynTags = tag.getChildren();
			for(int i=0;i<dynTags.length;i++)
				checkDynTag(id,dynTags[i],paramClass,sqlOpType,container);
		}else if(dynTag instanceof OtherwiseTag){//6
			OtherwiseTag tag =(OtherwiseTag)dynTag;
			if(tag.getChildrenCount()==0)
				throw new SqlDynTagException(id,"Otherwise tag missed children");
			DynTag[]dynTags = tag.getChildren();
			for(int i=0;i<dynTags.length;i++)
				checkDynTag(id,dynTags[i],paramClass,sqlOpType,container);
		
		}else if(dynTag instanceof SetTag){//7
			SetTag tag =(SetTag)dynTag;
			if(tag.getChildrenCount()==0)
				throw new SqlDynTagException(id,"Set tag missed children");
			DynTag[]dynTags = tag.getChildren();
			for(int i=0;i<dynTags.length;i++)
				checkDynTag(id,dynTags[i],paramClass,sqlOpType,container);
		}else if(dynTag instanceof TrimTag){//8
			TrimTag tag =(TrimTag)dynTag;
			if(tag.getChildrenCount()==0)
				throw new SqlDynTagException(id,"Trim tag missed children");
			DynTag[]dynTags = tag.getChildren();
			for(int i=0;i<dynTags.length;i++)
				checkDynTag(id,dynTags[i],paramClass,sqlOpType,container);
		}else if(dynTag instanceof ForeachTag){//9
			ForeachTag tag =(ForeachTag)dynTag;
			if(tag.getPropetyName()==null || tag.getPropetyName().trim().length()==0)
				throw new SqlDynTagException(id,"Foreach tag missed 'item'");
			
			if(tag.getStartSymbol()==null || tag.getStartSymbol().trim().length()==0)
				throw new SqlDynTagException(id,"Foreach tag missed 'open'");
			if(tag.getSpaceSymbol()==null || tag.getSpaceSymbol().trim().length()==0)
				throw new SqlDynTagException(id,"Foreach tag missed 'separator'");
			if(tag.getEndSymbol()==null || tag.getEndSymbol().trim().length()==0)
				throw new SqlDynTagException(id,"Foreach tag missed 'close'");
		}
	}
}
