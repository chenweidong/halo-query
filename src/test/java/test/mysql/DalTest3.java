package test.mysql;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import halo.query.JdbcSupport;
import halo.query.Query;
import halo.query.dal.DALContext;
import halo.query.dal.DALInfo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import test.SuperBaseModelTest;
import test.bean.TbUser;
import test.bean.UserSeqUtil;

/**
 * Created by akwei on 9/28/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/query-test4.xml"})
@Transactional
public class DalTest3 extends SuperBaseModelTest {

 
	@Autowired
	JdbcSupport jdbcSupport;
	
	@Test
	public void testQuery(){
		String sql = "select * from addresses";
		List<Map<String, Object>> mapList = jdbcSupport.getMapList(sql, null);
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
}
