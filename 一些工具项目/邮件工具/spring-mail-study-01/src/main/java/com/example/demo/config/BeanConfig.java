package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.example.demo.mail.MailData;
import com.example.demo.mail.MailMsgSingle;

import okhttp3.OkHttpClient;
import okhttp3.Request;

@Configuration
@PropertySource({"classpath:mail2.properties"})
public class BeanConfig {

	@Bean
	public MailData mailData() {
		return new MailData();
	}
	@Bean
	public MailMsgSingle mailMsgSingle() {
		return new MailMsgSingle();
	}
	@Bean
	public OkHttpClient.Builder clientBuilder() {
		OkHttpClient.Builder builder=new OkHttpClient.Builder();
		return builder;
	}
	@Bean
	public Request.Builder requestBuilder() {
		Request.Builder builder=new Request.Builder();
		builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
		return builder;
	}
}
