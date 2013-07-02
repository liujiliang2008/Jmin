/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.config.statictext;

/**
 * SQL表达式的参数符号
 * 
 * @author Chris Liao
 */

public class ParamSymbol {
	
	/**
	 * #{...}
	 */
	public final static ParamSymbol Symbol1 = new ParamSymbol("#{","}");
	
	/**
	 * #[....]
	 */
	public final static ParamSymbol Symbol2 = new ParamSymbol("#[","]");
	
	/**
	 * ${...}
	 */
	public final static ParamSymbol Symbol3 = new ParamSymbol("${","}");
	
	/**
	 * $[.....]
	 */
	public final static ParamSymbol Symbol4 = new ParamSymbol("$[","]");
	
	/**
	 * #...#
	 */
	public final static ParamSymbol Symbol5 = new ParamSymbol("#","#");
	
	/**
	 * 所支持的
	 */
	public final static ParamSymbol[] Symbols = new ParamSymbol[]{Symbol1,Symbol2,Symbol3,Symbol4,Symbol5};
	
	/**
	 * 左边符号
	 */
	private String startSymbol;
	
	/**
	 * 右边符号
	 */
	private String endSymbol;
	
	/**
	 * 构造
	 */
	public ParamSymbol(String leftSymbol){
		this.startSymbol = leftSymbol;
	}
	
	/**
	 * 构造
	 */
	public ParamSymbol(String leftSymbol,String rightSymbol){
		this.startSymbol = leftSymbol;
		this.endSymbol = rightSymbol;
	}

	public String getStartSymbol() {
		return startSymbol;
	}

	public String getEndSymbol() {
		return endSymbol;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof ParamSymbol) {
			ParamSymbol other = (ParamSymbol) obj;
			return (this.startSymbol.equals(other.startSymbol));
		} else {
			return false;
		}
	}
}
