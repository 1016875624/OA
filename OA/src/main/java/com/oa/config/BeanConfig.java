package com.oa.config;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.Proxy.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.oa.common.okhttp.OkTool;

import okhttp3.OkHttpClient;
import okhttp3.Request;

@Configuration
public class BeanConfig {

	
	@Bean
	public OkHttpClient.Builder clientBuilder() {
		OkHttpClient.Builder builder=new OkHttpClient.Builder();
		//builder.proxy(new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)));
		return builder;
	}
	@Bean
	public Request.Builder requestBuilder() {
		Request.Builder builder=new Request.Builder();
		builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
		return builder;
	}
	@Bean
	public OkHttpClient okHttpClient(@Autowired OkHttpClient.Builder builder) {
		return builder.build();
	}
	
	@Bean
	public OkTool okTool() {
		return new OkTool();
	}
}
