package com.chenwd.web;

import halo.query.JdbcSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/")
public class QueryController {
	
	@Resource(name="glJdbcSupport")
	JdbcSupport glJdbcSupport;
	@Resource(name="jkJdbcSupport")
	JdbcSupport jkJdbcSupport;
	@Resource(name="tzJdbcSupport")
	JdbcSupport tzJdbcSupport;
	
	@Resource(name="mysqlJdbcSupport")
	JdbcSupport mysqlJdbcSupport;

	@RequestMapping(value={"","/query","/query/{flag}"})
	public String query(@PathVariable("flag")String flag,Model model,@ModelAttribute("sql")String sql) throws Exception{
		try{
			if(!hasContainsValid(sql)){
				if("tz".equals(flag)){
							executeQuery(model, sql,tzJdbcSupport);
						}else if("gl".equals(flag)){
							executeQuery(model, sql,glJdbcSupport);
						}else if("jk".equals(flag)){
							executeQuery(model, sql,jkJdbcSupport);
						}else if("mysql".equals(flag)){
							executeQuery(model, sql,mysqlJdbcSupport);
						}
			}else{
				model.addAttribute("message", "仅能执行查询操作");
			}
	
			return "welcome";
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

	}

	private void executeQuery(Model model, String sql,JdbcSupport jdbcSupport) {
		if(queryCount(sql,jdbcSupport) > 500){
			model.addAttribute("message", "查询记录大于500条，请进一步添加过滤条件");
		}else{
			query(sql,jdbcSupport,model);
		}
	}
	
	private int queryCount(String sql,JdbcSupport jdbcSupport){
		String sql2 = replaceParaVal(sql,"@[^#]+#");
		Object[] paramVals = getParamVal(sql,"@[^#]+#");
		
		sql2 = "select count(*) from (" + sql2 + ") tbl1" ;
		
		Number num = jdbcSupport.num(sql2, paramVals);
		
		return num.intValue();
	}
	
	private void query(String sql,JdbcSupport jdbcSupport,Model model){
		String sql2 = replaceParaVal(sql,"@[^#]+#");
		Object[] paramVals = getParamVal(sql,"@[^#]+#");
		List<String> titles = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		
		List<Map<String, Object>> mapList = jdbcSupport.getMapList(sql2, paramVals);
		if(mapList != null && mapList.size() > 0){
			//标题
			Map<String, Object> map1 = mapList.get(0);
			Set<String> keySet1 = map1.keySet();
			Iterator<String> iterator1 = keySet1.iterator();
			while(iterator1.hasNext()){
				String key1 = iterator1.next();
				titles.add(key1);
			}
		}
		model.addAttribute("titles", titles);
		model.addAttribute("list", mapList);
	}
	
	private boolean hasContainsValid(String sql){
//		String sql = "seLEct DEletd Deleteupdate Call insert CallInseRt";//6个
		int contains = contains(sql,"((D|d)(E|e)(L|l)(E|e)(T|t)(E|e))|((I|i)(N|n)(S|s)(E|e)(R|r)(T|t))|((U|u)(P|p)(D|d)(A|a)(T|t)(E|e))|((C|c)(A|a)(L|l)(L|l))");
		if(contains > 0){
			return true;
		}else{
			return false;
		}
	}
	
	private int contains(String str, String reg) {
		 Pattern p = Pattern.compile(reg);
		 Matcher m = p.matcher(str);
		 String output;
		 int i = 0;
		 while (m.find()) {
			i++;
		 }
	     return i;
	 }
	
	private Object[] getParamVal(String str, String reg) {
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
	        if(list.size() > 0){
	        	return list.toArray(objs);
	        }else{
	        	return null;
	        }
	 }
	
	private String replaceParaVal(String str, String reg) {
		 return str.replaceAll(reg, "?");
	 }
	
}
