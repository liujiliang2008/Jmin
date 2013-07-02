package org.jmin.test.jda.order;

import java.sql.SQLException;

import org.jmin.app.web.Dao;
import org.jmin.app.web.DaoTemplate;

public class CustDao extends Dao {
	
	/**
	 * 构造函数
	 */
	public CustDao(DaoTemplate template){
		super(template);
	}
	
	public void insert(Cust cust)throws SQLException{
		this.getDaoTemplate().insert("cust.insert",cust);
	}
 }

