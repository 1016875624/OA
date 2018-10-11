package com.oa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import com.oa.common.okhttp.OkTool;
import com.oa.common.tool.mail.MailData;
import com.oa.common.tool.mail.MailMsgSingle;

import okhttp3.OkHttpClient;
import okhttp3.Request;

@Configuration
//@PropertySource({"classpath:mail.properties"})
@PropertySource({"classpath:alissl.mail.properties"})
public class BeanConfig {

	
	@Bean
	public MailData mailData() {
		MailData mailData=new MailData();
		mailData.getSendTaskThread().start();
		return mailData;
	}
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public MailMsgSingle mailMsgSingle() {
		return new MailMsgSingle();
	}
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
