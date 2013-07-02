/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.intercept;

import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * 类的编辑改造器：只允许修改类的当前修改方法，父类方法不允许修改，因为父类有可能有其他子类
 *
 * @author chris liao
 */

public class ClassWrapEditor2 {

	/**
	 * 将方法改名为methodName$impl,然后构造出一个原方法名的方法
	 */    
	public static Class createWrapClass(Class beanClass,Method[] methods)throws NotFoundException, CannotCompileException {
		ClassPool pool = ClassPool.getDefault();
		CtClass targetClass = pool.get(beanClass.getName());
		addInteceptionChainField(pool,targetClass);
		if(methods!=null && methods.length >0){
			for(int i=0;i<methods.length;i++)
			 rebuildMethod(beanClass.getName(),pool,targetClass,methods[i].getName(),methods[i].getParameterTypes());
		}
		
		return targetClass.toClass(beanClass.getClassLoader());
	}

	/**
	 * 将方法改名为methodName$impl,然后构造出一个原方法名的方法
	 * 
	 */    
	public static void rebuildMethod(String classname,ClassPool classPool,CtClass targetClass,String methodName,Class[] paramTypes)throws NotFoundException, CannotCompileException {
		CtClass[] ctParamTypes = new CtClass[paramTypes == null ? 0: paramTypes.length];
		for (int i = 0; i < ctParamTypes.length; i++)
		 ctParamTypes[i] = classPool.get(paramTypes[i].getName());

		CtMethod targetMethod = targetClass.getDeclaredMethod(methodName,ctParamTypes);
		String implMethodName = methodName + "$impl";
		targetMethod.setName(implMethodName);// 方法改名
		
		//方法拷贝构造出一个新方法，该方法以原来方法命名
		CtMethod newMethod = CtNewMethod.copy(targetMethod,methodName,targetClass, null);
		StringBuffer body = new StringBuffer();
		CtClass returnType = targetMethod.getReturnType();
		CtClass[]exceptions = targetMethod.getExceptionTypes();
		
		body.append("{\n");
		body.append("  if(this.interceptorChain!=null){\n");
		body.append("    java.lang.reflect.Method method=null;\n");
		CtClass[] parameterTypes =  targetMethod.getParameterTypes();
    int len = (parameterTypes ==null)? 0:parameterTypes.length;
    body.append("    Object[] paramValues = new Object["+ len + "];\n");
    body.append("    Class[] paramTypes = new Class["+ len + "];\n");
    for (int i = 0; i < len; i++) {
      if (CtClass.booleanType.equals(parameterTypes[i])) {
      	body.append("    paramValues[" + i + "]= new Boolean($" + (i + 1)+ ");\n");
      	body.append("    paramTypes[" + i + "]= Boolean.TYPE;\n");
      } else if (CtClass.charType.equals(parameterTypes[i])) {
      	body.append("    paramValues[" + i + "]= new Character($"+ (i + 1) + ");\n");
      	body.append("    paramTypes[" + i + "]= Character.TYPE;\n");
      } else if (CtClass.byteType.equals(parameterTypes[i])) {
      	body.append("    paramValues[" + i + "]= new Byte($" + (i + 1)+ ");\n");
      	body.append("    paramTypes[" + i + "]= Byte.TYPE;\n");
      } else if (CtClass.shortType.equals(parameterTypes[i])) {
      	body.append("    paramValues[" + i + "]= new Short($" + (i + 1)+ ");\n");
      	body.append("    paramTypes[" + i + "]= Short.TYPE;\n");
      } else if (CtClass.intType.equals(parameterTypes[i])) {
      	body.append("    paramValues[" + i + "]= new Integer($" + (i + 1)+ ");\n");
      	body.append("    paramTypes[" + i + "]=Integer.TYPE;\n");
      } else if (CtClass.longType.equals(parameterTypes[i])) {
      	body.append("    paramValues[" + i + "]= new Long($" + (i + 1)+ ");\n");
      	body.append("    paramTypes[" + i + "]= Long.TYPE;\n");
      } else if (CtClass.floatType.equals(parameterTypes[i])) {
      	body.append("    paramValues[" + i + "]= new Float($" + (i + 1)+ ");\n");
      	body.append("    paramTypes[" + i + "]= Float.TYPE;\n");
      } else if (CtClass.doubleType.equals(parameterTypes[i])) {
      	body.append("    paramValues[" + i + "]= new Double($" + (i + 1)+ ");\n");
      	body.append("    paramTypes[" + i + "]=\"Double.TYPE\";\n");
      } else {
      	body.append("    paramValues[" + i + "]= $" + (i + 1) + ";\n");
      	body.append("    paramTypes[" + i + "] = "+ parameterTypes[i].getName()+".class;\n");
      }
    }
    
    int methodHashCode = methodName.hashCode();
    for (int i = 0; i < paramTypes.length; i++)
    	methodHashCode ^= paramTypes[i].hashCode();
    
		body.append("    method = this.getClass().getDeclaredMethod(\""+implMethodName + "\",paramTypes);\n");
		body.append("    try{\n");
		if("void".equals(returnType.getName())){
			body.append("       this.interceptorChain.intecept(this,method,paramValues,"+methodHashCode +");\n");
		}else{
			body.append("       Object result = this.interceptorChain.intecept(this,method,paramValues,"+methodHashCode +");\n");
			
			body.append("       return " + getResultString(targetMethod.getReturnType())+";\n");
		}
		body.append("    }catch(Throwable e){\n");
    for(int i=0;i< exceptions.length;i++){
      body.append("     if(e instanceof " + exceptions[i].getName() +")\n");
      body.append("      throw ("+ exceptions[i].getName() +")e;\n");
    }
    body.append("                                      \n");
    body.append("     if(e instanceof RuntimeException)\n");
    body.append("       throw (RuntimeException)e;\n");
    body.append("     else if(e instanceof Error)\n");
    body.append("       throw (Error)e;\n");
    body.append("     else \n");
    body.append("       throw new RuntimeException(e.getMessage());\n");
    body.append("   }                             \n");
			
		body.append("  }else{\n");//当拦截不存在时候，直接调用
		if("void".equals(returnType.getName())){
			body.append("    " + implMethodName + "($$);\n");
		}else{
			body.append("    return this." + implMethodName + "($$);\n");
		}
		body.append("  }\n");
	
		body.append("}");
		newMethod.setBody(body.toString());
		targetClass.addMethod(newMethod);
		System.out.println("body for method: " + methodName);
		System.out.println(body.toString());
		System.out.println();
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
	 * 增加一个字段
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
	 * 需要改造的类方法定义
	 * @author chris
	 */
	static class ClassMethod{
		
		/**
		 * 方法名
		 */
		private String methodName;
		
		/**
		 * 参数类型
		 */
		private Class[] paramTypes;
		
		/**
		 * 构造方法
		 */
		public ClassMethod(String methodName,Class[] paramTypes){
			this.methodName = methodName;
			this.paramTypes = paramTypes;
		}
		
		/**
		 * 方法名
		 */
		public String getMethodName() {
			return methodName;
		}
		
		/**
		 * 参数类型
		 */
		public Class[] getParamTypes() {
			return paramTypes;
		}
	}
}
