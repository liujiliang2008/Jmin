package org.jmin.test.jda.order;

import java.sql.SQLException;

import org.jmin.app.web.Dao;
import org.jmin.app.web.DaoTemplate;

public class ProductDao extends Dao {
	
	/**
	 * 构造函数
	 */
	public ProductDao(DaoTemplate template){
		super(template);
	}
	
	
	public void insert(Product product)throws SQLException{
		this.getDaoTemplate().insert("product.insert",product);
	}
}