package org.jmin.test.jda.order;

import java.sql.SQLException;
import java.util.Date;

import org.jmin.app.web.DaoTemplate;

public class Test {
	
	/**
	 * test method
	 * #propertyName:columnTypeName:PropertyType:ParamPersister:ParamValueMode#
	 */
	public static void main(String[] args){
		DaoTemplate template = null;
		try {
			String file ="org/jmin/test/jda/order/Test.xml";
			template = new DaoTemplate();
			template.setSourceFile(file);
			ProductDao productDao = new ProductDao(template);
			CustDao custDao = new CustDao(template);
			OrderDao orderDao = new OrderDao(template);
			OrderItemDao orderItemDao = new OrderItemDao(template);
			
			template.beginTransaction();
			Product product = new Product();
			product.setProductNo((int)System.currentTimeMillis());
			product.setProductType("衣服");
			product.setProductName("Levis衣服");
			product.setProductDesc("很好用");
			product.setProductSize("90");
			product.setProductColor("Blue");
			productDao.insert(product);
			
			Cust cust = new Cust();
			cust.setCustNo((int)System.currentTimeMillis());
			cust.setName("Chris");
			cust.setGender("Man");
			cust.setMobile("13632790758");
			cust.setPhone("0755-876543");
			cust.setAddress("东莞Glossmind生产地");
			custDao.insert(cust);
		
			Order order = new Order();
			order.setOrderNo("" +((int)System.currentTimeMillis()));
			order.setCustNo(""+cust.getCustNo());
			order.setOpenDate(new Date());
			orderDao.insert(order);
 			
			OrderItem item = new OrderItem();
			item.setOrderNo(order.getOrderNo());
			item.setProductNo(product.getProductNo());
			item.setRemark("有货，可以优惠");
			orderItemDao.insert(item);
			template.commitTransaction();
	
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if(template!=null)template.rollbackTransaction();
			} catch (SQLException e1) {
				 
			}
		} 
	}
}
