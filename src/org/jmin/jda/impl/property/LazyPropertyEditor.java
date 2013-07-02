/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.property;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * 类的编辑改造器
 *
 * @author chris liao
 */

public class LazyPropertyEditor {
	
	/**
	 * 将方法改名为class$impl,然后构造出一个原方法名的方法
	 */    
	public static Class createSubClass(Class beanClass,LazyProperty[]propertyties)throws NotFoundException,IOException, CannotCompileException,Exception {
		ClassPool classPool = ClassPool.getDefault();
		CtClass parentClass = classPool.get(beanClass.getName());
		CtClass subClass = classPool.makeClass(parentClass.getName()+ "$"+genRandomNum(5),parentClass);
		
		CtConstructor[]superConstructors= parentClass.getDeclaredConstructors();
	  addOverrideConstructors(classPool,subClass,superConstructors);
	  
	  for(int i=0;propertyties!=null && i<propertyties.length;i++){
			createLazySetRequestMethod(classPool,subClass,propertyties[i].getPropertyName());
			rebuildLazyPropertyMethod(classPool,parentClass,subClass,propertyties[i]);
		}
		
		return subClass.toClass(beanClass.getClassLoader());
	}
	
	/**
	 * 创建Setxxxx$SqlRequest(SQLRequest request);
	 */
	private static void createLazySetRequestMethod(ClassPool pool,CtClass subClass,String propertyName)throws NotFoundException, CannotCompileException {
		String lazyPropertyName=propertyName +"$SqlRequest";
		String lazyPropertyCalled=propertyName+"$ISCalled";
		String lazyPropertySetMethod=PropertyUtil.getPropertySetMethodName(lazyPropertyName);
		
		CtField reqField = new CtField(pool.get("org.jmin.jda.impl.execution.SqlRequest"),lazyPropertyName,subClass);
		reqField.setModifiers(Modifier.PRIVATE);
		subClass.addField(reqField);
		
		CtField calledField = new CtField(pool.get("boolean"),lazyPropertyCalled,subClass);
		calledField.setModifiers(Modifier.PRIVATE);
		subClass.addField(calledField);
		
		CtMethod setMethod = new CtMethod(CtClass.voidType,lazyPropertySetMethod, new CtClass[]{pool.get("org.jmin.jda.impl.execution.SqlRequest")},subClass);
		setMethod.setModifiers(Modifier.PUBLIC);
		setMethod.setBody("this."+lazyPropertyName+" =$1;");
		subClass.addMethod(setMethod);
	}
	
	/**
	 * 将方法改名为methodName$impl,然后构造出一个原方法名的方法
	 */    
	private static void rebuildLazyPropertyMethod(ClassPool classPool,CtClass parentClass,CtClass subClass,LazyProperty property)throws NotFoundException, CannotCompileException {
		String propertyName = property.getPropertyName();
		Class  propertyType = property.getPropertyType();
		
		String lazyPropertyName=propertyName +"$SqlRequest";
		String lazyPropertyCalled=propertyName+"$ISCalled";		
		String superPropertyGetMethod =PropertyUtil.getPropertyGetMethodName(propertyName);
		String superPropertySetMethod =PropertyUtil.getPropertySetMethodName(propertyName);
		StringBuffer methodBody = new StringBuffer();
	
		methodBody.append("{\n");
		methodBody.append("  if(!this." + lazyPropertyCalled + " && this."+lazyPropertyName+"!=null){\n");
		methodBody.append("    try{\n");
		methodBody.append("     Object result=org.jmin.jda.impl.execution.select.ObjectRelateFinder.getRelaionValue(this."+lazyPropertyName+",true);\n");
		methodBody.append("     this."+lazyPropertyCalled+ "=true;\n");
		methodBody.append("     super."+superPropertySetMethod+"("+getResultString(classPool.get(propertyType.getName()))+");\n");
		methodBody.append("     return " + getResultString(classPool.get(propertyType.getName()))+";\n");
		 
		methodBody.append("    }catch(Throwable e){\n");
		methodBody.append("     throw new org.jmin.jda.impl.exception.PropertyLazyLoadException(e);\n");
		methodBody.append("    }\n");
		methodBody.append("  }else{\n");
		methodBody.append("    return super."+superPropertyGetMethod+"();\n");
		methodBody.append("  }\n");
		methodBody.append("}\n");
	
		CtMethod overrideMethod= new CtMethod(classPool.get(propertyType.getName()),PropertyUtil.getPropertyGetMethodName(propertyName),new CtClass[]{},subClass);
		overrideMethod.setBody(methodBody.toString());
		subClass.addMethod(overrideMethod);
		
}
  /**
   * add override constructors in sub class
   */
  private static void addOverrideConstructors(ClassPool pool, CtClass subClass,CtConstructor[] superConstructors) throws Exception {
    for (int i = 0; i < superConstructors.length; i++) {
      if (Modifier.isPublic(superConstructors[i].getModifiers())) {
        addOverrideConstructor(pool, subClass,superConstructors[i]);
      }
    }
  }
	
  /**
   * 拷贝构造方法
   */
  private static void addOverrideConstructor(ClassPool pool,CtClass targetClass,CtConstructor superConstructor) throws Exception {
    /**
     * New a simliar Constructor with super
     */
    CtConstructor subClassConstructor = new CtConstructor(superConstructor
        .getParameterTypes(), targetClass);
    subClassConstructor.setExceptionTypes(superConstructor
        .getExceptionTypes());
    subClassConstructor.setModifiers(superConstructor.getModifiers());

    /**
     * Construct constructor body
     */
    String body = "super(";
    if (superConstructor.getParameterTypes().length == 0) {
      body += ");";
    } else {

      for (int i = 1; i <= superConstructor.getParameterTypes().length; i++) {
        body += "$" + i;
        if (i < superConstructor.getParameterTypes().length)
          body += ",";
        else
          body += ");";
      }
    }
    subClassConstructor.setBody(body);
    targetClass.addConstructor(subClassConstructor);
  }
	
	/**
	 * 返回结果
	 */
	private static String getResultString(CtClass resultType){
		if(CtClass.booleanType.equals(resultType)){
		  return  "((Boolean)result).getBoolean()";
		}else if(CtClass.byteType.equals(resultType)){
			return  "((Byte)result).byteValue()";
		}else if(CtClass.shortType.equals(resultType)){
			return  "((Short)result).shortValue()";
		}else if(CtClass.intType.equals(resultType)){
			return  "((Integer)result).intValue()";
		}else if(CtClass.longType.equals(resultType)){
			return  "((Long)result).longValue()";
		}else if(CtClass.floatType.equals(resultType)){
			return  "((Float)result).floatValue()";
		}else if(CtClass.doubleType.equals(resultType)){
			return  "((Double)result).doubleValue()";
		}else if(CtClass.charType.equals(resultType)){
			return  "((Character)result).charValue()";
		}else{
			return "("+ resultType.getName()+")result";
		}
	}

  /**  
	 * 生成随即密码  
	 */
	private static String genRandomNum(int len) { 
    char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',  'l', 'm', 
    							 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',  'y', 'z', 
    							 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',  'L', 'M', 
    							 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',  'Y', 'Z', 
    							 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    
    StringBuffer pwd = new StringBuffer("");
    for(int i=0; i<len;i++) {
      int pos =(int)(Math.random()*str.length);
      pwd.append(str[pos]);
    }
		return pwd.toString();
	}   
}
