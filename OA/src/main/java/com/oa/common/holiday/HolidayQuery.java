package com.oa.common.holiday;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oa.common.okhttp.OkTool;
@Component
public class HolidayQuery {
	@Autowired
	private OkTool ok;
	private String apiAddress="http://api.goseek.cn/Tools/holiday";
	//成功则返回一个 日期:类型的值 0代表
	/**
	* <p>方法名称: queryByApi</p>
	* <p>描述：查询节假日的api,可以传入一个date值</p>
	* @param date String类型 格式为yyyyMMdd 例如20180921
	* @return map 一个map key为日期 value 为0 工作日 1为周六日 2为节假日
	* @throws IOException Map<String,String> 返回类型
	*/
	public Map<String, String> queryByApi(String date) throws IOException {
		ObjectMapper objectMapper=new ObjectMapper();
		String str=ok.url(apiAddress).addFormData("date", date).get();
		HashMap<String, String> map=objectMapper.readValue(str, HashMap.class);
		if (String.valueOf(map.get("code")).equals("10000")) {
			String temp=String.valueOf(map.get("data"));
			map.clear();
			map.put(date, temp);
			return map; 
		}
		return objectMapper.readValue(str, HashMap.class);
	}
	
	/**
	* <p>方法名称: queryByApi</p>
	* <p>描述：TODO(这里用一句话描述这个方法的作用)</p>
	* @param dates 任意长度的数组 格式为yyyyMMdd 例如20180921
	* @return map 一个map key为日期 value 为0 工作日 1为周六日 2为节假日
	* @throws IOException Map<String,String> 返回类型
	*/
	public Map<String, String> queryByApi(String... dates) throws IOException {
		ObjectMapper objectMapper=new ObjectMapper();
		Map<String, String>resmap=new HashMap<>();
		for (String date : dates) {
			String jsonstr=ok.url(apiAddress).addFormData("date", date).get();
			HashMap<String, String> map=objectMapper.readValue(jsonstr, HashMap.class);
			if (String.valueOf(map.get("code")).equals("10000")) {
				String temp=String.valueOf(map.get("data"));
				resmap.put(date, temp);
			}
		}
		return resmap;
	}
	
}
