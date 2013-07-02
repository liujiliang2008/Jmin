package org.jmin.test.jda.order;

import java.sql.SQLException;

import org.jmin.app.web.Dao;
import org.jmin.app.web.DaoTemplate;

public class OrderItemDao extends Dao {
	
	/**
	 * 构造函数
	 */
	public OrderItemDao(DaoTemplate template){
		super(template);
	}
	
	public void insert(OrderItem item)throws SQLException{
		this.getDaoTemplate().insert("orderItem.insert",item);
	}
}