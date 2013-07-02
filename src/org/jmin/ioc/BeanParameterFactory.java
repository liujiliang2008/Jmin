/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jmin.ioc.parameter.ClassParameter;
import org.jmin.ioc.parameter.InstanceParameter;
import org.jmin.ioc.parameter.ReferenceParameter;
import org.jmin.ioc.parameter.collection.ArrayParameter;
import org.jmin.ioc.parameter.collection.CollectionParameter;
import org.jmin.ioc.parameter.collection.ListParameter;
import org.jmin.ioc.parameter.collection.MapParameter;
import org.jmin.ioc.parameter.collection.SetParameter;
import org.jmin.ioc.parameter.primitive.BooleanParameter;
import org.jmin.ioc.parameter.primitive.ByteParameter;
import org.jmin.ioc.parameter.primitive.CharParameter;
import org.jmin.ioc.parameter.primitive.DoubleParameter;
import org.jmin.ioc.parameter.primitive.FloatParameter;
import org.jmin.ioc.parameter.primitive.IntegerParameter;
import org.jmin.ioc.parameter.primitive.LongParameter;
import org.jmin.ioc.parameter.primitive.ShortParameter;

/**
 * Bean parameter factory
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanParameterFactory {

  /**
   * Create a boolean type ioc value
   */
  public BeanParameter createBooleanParameter(boolean value)throws BeanParameterException {
    return new BooleanParameter(value);
  }

  /**
   * Create a boolean type ioc value
   */
  public BeanParameter createBooleanParameter(Boolean value)throws BeanParameterException {
    return new BooleanParameter(value);
  }

  /**
   * Create a byte type ioc value
   */
  public BeanParameter createByteParameter(byte value)throws BeanParameterException {
    return new ByteParameter(value);
  }
  /**
   * Create a byte type ioc value
   */
  public BeanParameter createByteParameter(Byte value)throws BeanParameterException {
    return new ByteParameter(value);
  }

  /**
   * Create a char type ioc value
   */
  public BeanParameter createCharParameter(char value)throws BeanParameterException {
    return new CharParameter(value);
  }

  /**
   * Create a char type ioc value
   */
  public BeanParameter createCharParameter(Character value)throws BeanParameterException {
    return new CharParameter(value);
  }

  /**
   * Create a short type ioc value
   */
  public BeanParameter createShortParameter(short value)throws BeanParameterException {
    return new ShortParameter(value);
  }

  /**
   * Create a short type ioc value
   */
  public BeanParameter createShortParameter(Short value) throws BeanParameterException{
    return new ShortParameter(value);
  }

  /**
   * Create a int type ioc value
   */
  public BeanParameter createIntegerParameter(int value)throws BeanParameterException {
    return new IntegerParameter(value);
  }

  /**
   * Create a int type ioc value
   */
  public BeanParameter createIntegerParameter(Integer value)throws BeanParameterException {
    return new IntegerParameter(value);
  }

  /**
   * Create a long type ioc value
   */
  public BeanParameter createLongParameter(long value) throws BeanParameterException{
    return new LongParameter(value);
  }

  /**
   * Create a long type ioc value
   */
  public BeanParameter createLongParameter(Long value)throws BeanParameterException {
    return new LongParameter(value);
  }

  /**
   * Create a float type ioc value
   */
  public BeanParameter createFloatParameter(float value)throws BeanParameterException {
    return new FloatParameter(value);
  }

  /**
   * Create a float type ioc value
   */
  public BeanParameter createFloatParameter(Float value)throws BeanParameterException {
    return new FloatParameter(value);
  }

  /**
   * Create a double type ioc value
   */
  public BeanParameter createDoubleParameter(double value)throws BeanParameterException {
    return new DoubleParameter(value);
  }

  /**
   * Create a double type ioc value
   */
  public BeanParameter createDoubleParameter(Double value)throws BeanParameterException {
    return new DoubleParameter(value);
  }

  /**
   * Create a String type ioc value
   */
  public BeanParameter createStringParameter(String value)throws BeanParameterException {
    return new InstanceParameter(value);
  }

  /**
   * Create a String type ioc value
   */
  public BeanParameter createInstanceParameter(Object value)throws BeanParameterException {
    return new InstanceParameter(value);
  }

  /**
   * Create a class class type ioc value
   */
  public BeanParameter createClassParameter(Class value)throws BeanParameterException {
    return new ClassParameter(value);
  }

  /**
   * Create a class class name ioc value
   */
  public BeanParameter createClassParameter(String value)throws BeanParameterException{
    return new ClassParameter(value);
  }

  /**
   * Create a reference name ioc value
   */
  public BeanParameter createReferenceParameter(Object value)throws BeanParameterException {
    return new ReferenceParameter(value);
  }
  
  /**
   * Create a collection ioc value
   */
  public BeanParameter createCollectionParameter(Collection value)throws BeanParameterException {
    return new CollectionParameter(value);
  }
  
  /**
   * Create a collection ioc value
   */
  public BeanParameter createListParameter(List value)throws BeanParameterException {
    return new ListParameter(value);
  }
 
  /**
   * Create a collection ioc value
   */
  public BeanParameter createSetParameter(Set value)throws BeanParameterException {
    return new SetParameter(value);
  }
 
  /**
   * Create a collection ioc value
   */
  public BeanParameter createMapParameter(Map value)throws BeanParameterException {
    return new MapParameter(value);
  }

  /**
   * Create a collection ioc value
   */
  public BeanParameter createArrayParameter(Class arrayType,Object array)throws BeanParameterException {
    return new ArrayParameter(arrayType,array);
  }
}