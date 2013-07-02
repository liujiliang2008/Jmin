/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.intercept;

import java.io.IOException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jmin.ioc.impl.util.ObjectUtil;

/**
 * 类的编辑改造器
 *
 * @author chris liao
 */

public class ClassWrapEditor {
	
	/**
	 * 将方法改名为class$impl,然后构造出一个原方法名的方法
	 */    
	public static Class createWrapClass(Class beanClass,Method[] methods)throws NotFoundException,IOException, CannotCompileException,Exception {
		ClassPool classPool = ClassPool.getDefault();
		CtClass sourceClass = classPool.get(beanClass.getName());
	
		//创建AOP包装类
		CtClass targetClass = classPool.makeClass(sourceClass.getName()+ "$"+genRandomNum(5), sourceClass);
		
		CtConstructor[] superConstructors = sourceClass.getDeclaredConstructors();
	  addOverrideConstructors(classPool,targetClass,superConstructors);
		addInteceptionChainField(classPool,targetClass);
		
		if(methods!=null){
			for(int i=0;i<methods.length;i++)
			 createInterceptedMethod(sourceClass,targetClass,classPool,methods[i]);
		}
		
		return targetClass.toClass(beanClass.getClassLoader());
	}
 
	/**
	 * 将方法改名为methodName$impl,然后构造出一个原方法名的方法
	 */    
	private static void createInterceptedMethod(CtClass sourceClass,CtClass targetClass,ClassPool classPool,Method method)throws NotFoundException, CannotCompileException {
		String methodName = method.getName();
		Class[] paramTypes = method.getParameterTypes();
		String Signature = ObjectUtil.getMethodSignature(method);
		CtMethod targetMethod=sourceClass.getMethod(methodName,Signature); 

		//方法拷贝构造出一个新方法，该方法以原来方法命名
		CtMethod newMethodForOut = CtNewMethod.copy(targetMethod,methodName,targetClass, null);
		CtMethod newMethodForIn = CtNewMethod.copy(targetMethod,methodName,targetClass, null);
		StringBuffer newMethodForOutBody = new StringBuffer();
		StringBuffer newMethodForInBody = new StringBuffer();
		CtClass returnType = targetMethod.getReturnType();
		CtClass[]exceptions = targetMethod.getExceptionTypes();
		
		String methodInName = methodName+"$Impl";
		newMethodForIn.setName(methodInName);
		newMethodForOut.setName(methodName);
		
		newMethodForOutBody.append("{\n");
		newMethodForOutBody.append("  if(this.interceptorChain!=null){\n");
		newMethodForOutBody.append("    java.lang.reflect.Method method=null;\n");
		CtClass[] parameterTypes =  targetMethod.getParameterTypes();
    int len = (parameterTypes ==null)? 0:parameterTypes.length;
    newMethodForOutBody.append("    Object[] paramValues = new Object["+ len + "];\n");
    newMethodForOutBody.append("    Class[] paramTypes = new Class["+ len + "];\n");
    for (int i = 0; i < len; i++) {
      if (CtClass.booleanType.equals(parameterTypes[i])) {
      	newMethodForOutBody.append("    paramValues[" + i + "]= new Boolean($" + (i + 1)+ ");\n");
      	newMethodForOutBody.append("    paramTypes[" + i + "]= Boolean.TYPE;\n");
      } else if (CtClass.charType.equals(parameterTypes[i])) {
      	newMethodForOutBody.append("    paramValues[" + i + "]= new Character($"+ (i + 1) + ");\n");
      	newMethodForOutBody.append("    paramTypes[" + i + "]= Character.TYPE;\n");
      } else if (CtClass.byteType.equals(parameterTypes[i])) {
      	newMethodForOutBody.append("    paramValues[" + i + "]= new Byte($" + (i + 1)+ ");\n");
      	newMethodForOutBody.append("    paramTypes[" + i + "]= Byte.TYPE;\n");
      } else if (CtClass.shortType.equals(parameterTypes[i])) {
      	newMethodForOutBody.append("    paramValues[" + i + "]= new Short($" + (i + 1)+ ");\n");
      	newMethodForOutBody.append("    paramTypes[" + i + "]= Short.TYPE;\n");
      } else if (CtClass.intType.equals(parameterTypes[i])) {
      	newMethodForOutBody.append("    paramValues[" + i + "]= new Integer($" + (i + 1)+ ");\n");
      	newMethodForOutBody.append("    paramTypes[" + i + "]=Integer.TYPE;\n");
      } else if (CtClass.longType.equals(parameterTypes[i])) {
      	newMethodForOutBody.append("    paramValues[" + i + "]= new Long($" + (i + 1)+ ");\n");
      	newMethodForOutBody.append("    paramTypes[" + i + "]= Long.TYPE;\n");
      } else if (CtClass.floatType.equals(parameterTypes[i])) {
      	newMethodForOutBody.append("    paramValues[" + i + "]= new Float($" + (i + 1)+ ");\n");
      	newMethodForOutBody.append("    paramTypes[" + i + "]= Float.TYPE;\n");
      } else if (CtClass.doubleType.equals(parameterTypes[i])) {
      	newMethodForOutBody.append("    paramValues[" + i + "]= new Double($" + (i + 1)+ ");\n");
      	newMethodForOutBody.append("    paramTypes[" + i + "]=\"Double.TYPE\";\n");
      } else {
      	newMethodForOutBody.append("    paramValues[" + i + "]= $" + (i + 1) + ";\n");
      	newMethodForOutBody.append("    paramTypes[" + i + "] = "+ parameterTypes[i].getName()+".class;\n");
      }
    }
    
    int methodHashCode = methodName.hashCode();
    for (int i = 0; i < paramTypes.length; i++)
    	methodHashCode ^= paramTypes[i].hashCode();
   
		newMethodForOutBody.append("    method = this.getClass().getDeclaredMethod(\""+methodInName + "\",paramTypes);\n");
		newMethodForOutBody.append("    try{\n");
		if("void".equals(returnType.getName())){
			newMethodForOutBody.append("       this.interceptorChain.intecept(this,method,paramValues,"+methodHashCode +");\n");
		}else{
			newMethodForOutBody.append("       Object result = this.interceptorChain.intecept(this,method,paramValues,"+methodHashCode +");\n");
			
			newMethodForOutBody.append("       return " + getResultString(targetMethod.getReturnType())+";\n");
		}
		newMethodForOutBody.append("    }catch(Throwable e){\n");
    for(int i=0;i< exceptions.length;i++){
      newMethodForOutBody.append("     if(e instanceof " + exceptions[i].getName() +")\n");
      newMethodForOutBody.append("      throw ("+ exceptions[i].getName() +")e;\n");
    }
    newMethodForOutBody.append("                                      \n");
    newMethodForOutBody.append("     if(e instanceof RuntimeException)\n");
    newMethodForOutBody.append("       throw (RuntimeException)e;\n");
    newMethodForOutBody.append("     else if(e instanceof Error)\n");
    newMethodForOutBody.append("       throw (Error)e;\n");
    newMethodForOutBody.append("     else \n");
    newMethodForOutBody.append("       throw new RuntimeException(e.getMessage());\n");
    newMethodForOutBody.append("   }                             \n");
			
		newMethodForOutBody.append("  }else{\n");//当拦截不存在时候，直接调用
		if("void".equals(returnType.getName())){
			newMethodForOutBody.append("    super." + methodName + "($$);\n");
		}else{
			newMethodForOutBody.append("    return super." + methodName + "($$);\n");
		}
		newMethodForOutBody.append("  }\n");
	
		newMethodForOutBody.append("}");
		newMethodForOut.setBody(newMethodForOutBody.toString());
		targetClass.addMethod(newMethodForOut);
//		System.out.println("body for out method: " + methodName);
//		System.out.println(newMethodForOutBody.toString());
//		System.out.println();
		
		newMethodForInBody.append("{\n");
		if("void".equals(returnType.getName()))
			newMethodForInBody.append("   super."+ methodName + "($$);\n"); 
	  else
		 newMethodForInBody.append("  return super."+ methodName +  "($$);\n"); 

		newMethodForInBody.append("}\n");
		newMethodForIn.setBody(newMethodForInBody.toString());
		targetClass.addMethod(newMethodForIn);
//		System.out.println("body for internal method: " + methodName);
//		System.out.println(newMethodForInBody.toString());
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
			return "result;\n";
		}
	}
	
	/**
	 * 增加一个拦截链字段
	 */
	private static void addInteceptionChainField(ClassPool pool,CtClass ct)throws NotFoundException,CannotCompileException{
		CtField field = new CtField(pool.get("org.jmin.ioc.impl.intercept.InterceptorChain"), "interceptorChain", ct);
		field.setModifiers(Modifier.PRIVATE);
		ct.addField(field);

		CtMethod setMethod = new CtMethod(CtClass.voidType,"setInterceptorChain", new CtClass[]{pool.get("org.jmin.ioc.impl.intercept.InterceptorChain")},ct);
		setMethod.setModifiers(Modifier.PUBLIC);
		setMethod.setBody("this.interceptorChain =$1;");
		ct.addMethod(setMethod);
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
