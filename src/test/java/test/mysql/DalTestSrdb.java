package test.mysql;

import halo.query.JdbcSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import test.SuperBaseModelTest;

/**
 * 
 * @author chenwd
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/query-test-srdb.xml"})
@Transactional
public class DalTestSrdb extends SuperBaseModelTest {

 
	@Autowired
	JdbcSupport jdbcSupport;
	
	@Test
	public void testQuery(){
//		String sql = "select * from gl.t_gl_user where login_name = ?";
		String sql = "select * from gl.t_gl_user where login_name like ?";
//		Object[] objs = new Object[]{"66006498"};
		Object[] objs = new Object[]{"%66006%"};
		List<Map<String, Object>> mapList = jdbcSupport.getMapList(sql, objs);
		if(mapList != null && mapList.size() > 0){
			for(Map<String,Object> map:mapList){
				Set<String> keySet = map.keySet();
				Iterator<String> iterator = keySet.iterator();
				String val = "[";
				while(iterator.hasNext()){
					String key = iterator.next();
					val =  val + key + "=" + map.get(key) + ";";
				}
				val += "]";
				System.out.println(val);
			}
		}
	}
	@Test
	public void testQueryCount(){
		String sql = "select * from gl.t_gl_user ";
//		String sql = "select * from gl.t_gl_user where login_name = @66006498#";
//		String sql = "select * from gl.t_gl_user where login_name like @%6600649%#";
//		String sql = "select * from gl.t_gl_user where login_name in ( @66006494#,@66006495#)";
//		String sql = "select * from gl.t_gl_user where login_name in ( @66006494#,@66006495#,@66006497#)"
//				+ "	and (name like @娄杰# or password = @3bb5ff3fb6acb83ebba47d493ffd5d301e63d95aaad4dbd87ef37c39#)";
		String sql2 = replaceParaVal(sql,"@[^#]+#");
		Object[] paramVals = getParamVal(sql,"@[^#]+#");
		
		sql2 = "select count(*) from (" + sql2 + ")";
		
		Number num = jdbcSupport.num(sql2, paramVals);
		List<Map<String, Object>> mapList = jdbcSupport.getMapList(sql2, paramVals);
		if(mapList != null && mapList.size() > 0){
			
		}
	}
	@Test
	public void testQuery2(){
//		String sql = "select * from gl.t_gl_user where login_name = @66006498#";
//		String sql = "select * from gl.t_gl_user where login_name like @%6600649%#";
//		String sql = "select * from gl.t_gl_user where login_name in ( @66006494#,@66006495#)";
		String sql = "select * from gl.t_gl_user where login_name in ( @66006494#,@66006495#,@66006497#)"
				+ "	and (name like @娄杰# or password = @3bb5ff3fb6acb83ebba47d493ffd5d301e63d95aaad4dbd87ef37c39#)";
		String sql2 = replaceParaVal(sql,"@[^#]+#");
		Object[] paramVals = getParamVal(sql,"@[^#]+#");
		
		List<Map<String, Object>> mapList = jdbcSupport.getMapList(sql2, paramVals);
		if(mapList != null && mapList.size() > 0){
			for(Map<String,Object> map:mapList){
				Set<String> keySet = map.keySet();
				Iterator<String> iterator = keySet.iterator();
				String val = "[";
				while(iterator.hasNext()){
					String key = iterator.next();
					val =  val + key + "=" + map.get(key) + ";";
				}
				val += "]";
				System.out.println(val);
			}
		}
	}
	
	@Test
	public void testNoContains(){
		String sql = "seLEct DEletd Deleteupdate Call insert CallInseRt";//6个
		int contains = contains(sql,"((D|d)(E|e)(L|l)(E|e)(T|t)(E|e))|((I|i)(N|n)(S|s)(E|e)(R|r)(T|t))|((U|u)(P|p)(D|d)(A|a)(T|t)(E|e))|((C|c)(A|a)(L|l)(L|l))");
		System.out.println(contains);
	}
	
	 public Object[] getParamVal(String str, String reg) {
		 	Object[] objs = new Object[]{};
		 	List<Object> list = new ArrayList<Object>();
	        Pattern p = Pattern.compile(reg);
	        Matcher m = p.matcher(str);
	        String output;
	        int i=0;
	        while (m.find()) {
	        	 int n = m.groupCount();
	            list.add(m.group().substring(1, m.group().length()-1));
	        }
	        return list.toArray(objs);
	 }
	 public String replaceParaVal(String str, String reg) {
		 return str.replaceAll(reg, "?");
	 }
	 
	 public int contains(String str, String reg) {
		 Pattern p = Pattern.compile(reg);
		 Matcher m = p.matcher(str);
		 String output;
		 int i = 0;
		 while (m.find()) {
			i++;
		 }
	     return i;
	 }
	 public void countSubStrReg(String str, String reg) {
		 Pattern p = Pattern.compile(reg);
		 Matcher m = p.matcher(str);
		 String output;
		 while (m.find()) {
			 int n = m.groupCount();
//	        	 for (int i = 0; i <= n; i++) {  
//	                    output = m.group(i);  
//	                    if (output != null) {  
//	                    	System.out.println(output);
//	                    }  
//	                }  
			 System.out.println("--------");
			 System.out.println(m.group());
		 }
//	        while (m.matches()) {
//	        	 int n = m.groupCount();
//	        	 for (int i = 0; i <= n; i++) {  
//	                    output = m.group(i);  
//	                    System.out.println("--------");
//	                    System.out.println(output);
//	                }  
//	        }
//	        return i;
	 }
	 
	 @Test
	 public void testa(){
		 String sql = "select * from gl.t_gl_user where login_name = @sds# and id like @aa#";
//		 countSubStrReg(sql,"@[^#]+#");
//		 http://blog.csdn.net/yuanzhugen/article/details/49333057
		 
		 Object[] paramVal = getParamVal(sql,"@[^#]+#");
		 System.out.println("====================");
		 if(paramVal != null){
			 for(Object obj:paramVal){
				 System.out.println(obj);
			 }
		 }
		 
		 String sql2 = replaceParaVal(sql,"@[^#]+#");
		 System.out.println(sql2);
		 
		 
		 
	 }
}
