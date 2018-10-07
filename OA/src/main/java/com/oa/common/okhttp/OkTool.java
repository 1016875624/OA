package com.oa.common.okhttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class OkTool {
	@Autowired
	//private OkHttpClient.Builder cb;
	private OkHttpClient client;
	@Autowired
	private Request.Builder rb;
	private Map<String, String>formData=new HashMap<>();
	
	private String url;
	
	public OkTool url(String url) {
		this.url=url;
		return this;
	}
	
	public OkTool addFormData(String key,String value) {
		formData.put(key, value);
		return this;
	}
	
	public String get() throws IOException {
		Response response=null;
		if (formData.size()==0) {
			response=client.newCall(rb.url(url).build()).execute();
		}else {
			StringBuffer sb=new StringBuffer();
			sb.append(url);
			if (!url.contains("?")) {
				sb.append("?");
			}
			else {
				sb.append("&");
			}
			Set<String>key=formData.keySet();
			for (String string : key) {
				sb.append(string+"="+formData.get(string)+"&");
			}
			url=sb.toString().substring(0,sb.length()-1);
			response=client.newCall(rb.url(url).build()).execute();
		}
		url=null;
		formData.clear();
		if (response!=null) {
			return response.body().string();
		}
		return null;
	}
	
	public String post() throws IOException {
		Response response=null;
		FormBody.Builder fb=new FormBody.Builder();
		if (formData.size()==0) {
			response=client.newCall(rb.url(url).build()).execute();
		}else {
			Set<String>key=formData.keySet();
			for (String string : key) {
				fb.add(string, formData.get(string));
			}
			response=client.newCall(rb.url(url).post(fb.build()).build()).execute();
		}
		url=null;
		formData.clear();
		if (response!=null) {
			return response.body().string();
		}
		return null;
	}
	
	public String json() throws IOException {
		ObjectMapper ob=new ObjectMapper();
		Response response=null;
		//没有加入post的数据,所以自动的用get方法
		if (formData.size()==0) {
			response=client.newCall(rb.url(url).build()).execute();
		}else {
			MediaType mt=MediaType.parse("application/json; charset=utf-8");
			//创建以json方式提交的body
			RequestBody body=RequestBody.create(mt, ob.writeValueAsString(formData));
			response=client.newCall(rb.url(url).post(body).build()).execute();
		}
		//清除数据
		url=null;
		formData.clear();
		if (response!=null) {
			return response.body().string();
		}
		return null; 
	}
	
	public String put() throws IOException {
		Response response=null;
		FormBody.Builder fb=new FormBody.Builder();
		if (formData.size()==0) {
			response=client.newCall(rb.url(url).build()).execute();
		}else {
			Set<String>key=formData.keySet();
			for (String string : key) {
				fb.add(string, formData.get(string));
			}
			response=client.newCall(rb.url(url).put(fb.build()).build()).execute();
		}
		url=null;
		formData.clear();
		if (response!=null) {
			return response.body().string();
		}
		return null;
	}
	
	public String delete() throws IOException {
		Response response=null;
		FormBody.Builder fb=new FormBody.Builder();
		if (formData.size()==0) {
			response=client.newCall(rb.url(url).delete().build()).execute();
		}else {
			Set<String>key=formData.keySet();
			for (String string : key) {
				fb.add(string, formData.get(string));
			}
			response=client.newCall(rb.url(url).delete(fb.build()).build()).execute();
		}
		url=null;
		formData.clear();
		if (response!=null) {
			return response.body().string();
		}
		return null;
	}
	
	public String patch() throws IOException {
		Response response=null;
		FormBody.Builder fb=new FormBody.Builder();
		if (formData.size()==0) {
			response=client.newCall(rb.url(url).build()).execute();
		}else {
			Set<String>key=formData.keySet();
			for (String string : key) {
				fb.add(string, formData.get(string));
			}
			response=client.newCall(rb.url(url).patch(fb.build()).build()).execute();
		}
		url=null;
		formData.clear();
		if (response!=null) {
			return response.body().string();
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		OkTool ok=new OkTool();
		/*ok.formData.put("a", "e");
		ok.formData.put("b", "f");
		ok.formData.put("c", "g");
		ok.formData.put("d", "h");*/
		
		System.out.println(ok.addFormData("date", "20180922").url("http://api.goseek.cn/Tools/holiday").get());
		ObjectMapper ob=new ObjectMapper();
	}
	
}
