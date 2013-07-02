package org.jmin.test.jda.order;

import java.sql.SQLException;

import org.jmin.app.web.Dao;
import org.jmin.app.web.DaoTemplate;

public class OrderDao extends Dao {
	
	/**
	 * 构造函数
	 */
	public OrderDao(DaoTemplate template){
		super(template);
	}
	
	
	public void insert(Order order)throws SQLException{
		this.getDaoTemplate().insert("order.insert",order);
	}
}
