/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.element;

import org.jmin.ioc.BeanElement;
import org.jmin.ioc.BeanElementException;

/**
 * parent ID
 * 
 * 
 *@author Chris Liao
 */

public final class ParentReferenceId implements BeanElement {

	/**
	 * destroy Method
	 */
	private Object referenceId;

	/**
	 * destroyMethod
	 */
	public ParentReferenceId(Object referenceId)throws BeanElementException {
		this.referenceId = referenceId;
		if(referenceId==null)
			throw new BeanElementException("Parent reference id can't be null");
	}

	/**
	 * Returns parent ID
	 */
	public Object getReferenceID() {
		return referenceId;
	}
	
  /**
   * Return hash code for this interception
   */
  public int hashCode() {
  	return referenceId.hashCode();
  }
  
  /**
   * Override method
   */
  public String toString() {
    return "Parent ID:"+ referenceId;
  }
  
  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if(obj instanceof ParentReferenceId) {
    	ParentReferenceId other = (ParentReferenceId) obj;
      return this.referenceId.equals(other.referenceId);
    } else {
      return false;
    }
  }
}