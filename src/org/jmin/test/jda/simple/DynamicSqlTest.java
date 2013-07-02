package org.jmin.test.jda.simple;

import org.jmin.jda.JdaContainer;
import org.jmin.jda.JdaSourceInfo;
import org.jmin.jda.JdbcSourceInfo;
import org.jmin.jda.impl.JdaContainerImpl;
import org.jmin.jda.impl.config.dynamictext.DynamicSqlAnalyzer;
import org.jmin.jda.statement.DynTag;


public class DynamicSqlTest {   
    
    /**  
     * 测试程序文件  
     *   
     * @param args  
     */  
    public static void main(String[] args)throws Exception {
    	
//  		charMap.put("&amp;","&");
//  		charMap.put("&lt;","<");
//  		charMap.put("&gt;",">");
//  		charMap.put("&quot;","\"");
//  		charMap.put("&nbsp;"," ");
    	
    	String xmlStr = "select name, sex from USERINFO"
		  +"<where>"
		  +"<if test='name!=null && name.trim()=='''>name=#{name}</if>"
		  +"<if test='sex!=null  && sex.trim()=='''>sex=#{sex}</if>"
		  +"</where>";
    	
    	xmlStr = " update SYS_USER " 
       +" <set> "
       +"  <if test=\"user_name &lt; aaa \">"
       +"  USER_NAME &lt; ${user_name}"
       +"  </if>"
      
    	 
//       +"  <if test=\"password != null\">"
//       +"    ,PASSWORD=${password}"
//       +"   </if>"
//       +"   <if test=\"bch_id != null\">"
//       +"    ,BCH_ID=${bch_id}"
//       +"   </if>"
//       +"  <if test=\"dep_id != null\">"
//       + "   ,DEP_ID=${dep_id}"
//       + "  </if>"
//       + "  <if test=\"email != null\">"
//       + "   ,EMAIL=${email}"
//       + "  </if>"
//       + "  <if test=\"telphone != null\">"
//        +"   ,TELPHONE=${telphone}"
//        +"  </if>"
//       + "  <if test=\"admin_ind != null\">"
//       + "   ,ADMIN_IND=${admin_ind}"
//       + "  </if>"
       +"</set>";
//       +" where"
//       +" USER_ID=${user_id}";

        //String xmlStr ="select * from table<if test='ddd'>  field1=?</if>  <if test='ddd'>field2=?</if>";
    	JdaSourceInfo dataSourceInfo = new JdbcSourceInfo(Link.getDriver(),Link.getURL(),Link.getUser(),Link.getPassword());
	    JdaContainer container = new JdaContainerImpl(dataSourceInfo);
	    DynTag[] tags = DynamicSqlAnalyzer.analyzeDynamicSQL("sql1",xmlStr,Object.class,container);
      StringBuffer buf = new StringBuffer();
        for(int i=0;i<tags.length;i++){
      	tags[i].apend(buf);
      }
      System.out.println(buf);
    
//    	StringReader sr = new StringReader(xmlStr);
//    	HTMLTree tree = new HTMLTree(new HTMLTokenizer(sr));
//    	HTMLNode rootNode = tree.getRootNode();
//    	Enumeration childEnum = rootNode.getChildren();
//    ,
//    	while(childEnum.hasMoreElements()){
//    		Object element = childEnum.nextElement();
//    		if(element instanceof String){
//    			System.out.println("text: " + (String)element);
//    		}else{
//    			System.out.println("Tag: " + ((HTMLNode)element).getName());
//    		}
//    	}
   }   
}  

