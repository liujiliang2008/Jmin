/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config.xml.element;

/**
 * Store some constants node name
 *
 * @author Chris Liao
 * @version 1.0
 */
public class XMLEelementTags {
	
  /**
   * bean id
   */
  public static final String ID = "id";

  /**
   * bean class
   */
  public static final String Class = "class";

  /**
   * Inteceptor element below bean node
   */
  public static final String Singleton ="singleton";

  /**
   * Inteceptor element below bean node
   */
  public static final String Init_method ="init-method";

  /**
   * Inteceptor element below bean node
   */
  public static final String Destroy_method ="destroy-method";

  /**
   * Inteceptor element below bean node
   */
  public static final String Factory_Bean ="factory-bean";

  /**
   * Inteceptor element below bean node
   */
  public static final String Factory_Method="factory-method";
  
  /**
   * Inteceptor element below bean node
   */
  public static final String Pool_Size="pool-size";
  
  /**
   * super-refID element below bean node
   */
  public static final String Parent="parent";

  /**
   * Constructor element below bean node
   */
  public static final String Constructor ="constructor";

  /**
   * Constructor element below bean node
   */
  public static final String Constructor_arg ="constructor-arg";


  /**
   * Field element below bean node
   */
  public static final String Field ="field";
  
  /**
   * Property element below bean node
   */
  public static final String Property ="property";


  /**
   * invocation element below bean node
   */
  public static final String Invocation ="invocation";

  /**
   * Method invocation parameter values
   */
  public static final String Method_Param_Values="method-param-values";

  /**
   * Method invocation parameter value
   */
  public static final String Method_Param_Value="method-param-value";

  /**
   * proxy interface classes
   */
  public static final String Proxy_Classes ="proxy-classes";

  
  /**
   * interception
   */
  public static final String Interception ="interception";

  /**
   * Inteceptor element below bean node
   */
  public static final String Method_Name="method-name";

  /**
   * Inteceptor element below bean node
   */
  public static final String Method_Param_Types = "method-param-types";

  /**
   * Inteceptor element below bean node
   */
  public static final String Method_Param_Type="method-param-type";

  /**
   * Intercepted Position
   */
  public static final String Intercepted_Position ="intercepted-position";

  /**
   * interceptor xml tag
   */
  public static final String Interceptor ="interceptor";


  /**
   * transaction element below bean node
   */
  public static final String Transaction ="transaction";

}