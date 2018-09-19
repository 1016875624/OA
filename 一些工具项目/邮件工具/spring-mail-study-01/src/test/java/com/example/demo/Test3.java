package com.example.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.junit.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test3 {
	OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
	OkHttpClient.Builder builder=new OkHttpClient.Builder();
	{
		builder.proxy(new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", 1080)));
	}
	@Test
	public void name() throws IOException {
		okHttpClient =builder.build();
		Request request=new Request.Builder().url("https://ip.cn").build();
		Response response=okHttpClient.newCall(request).execute();
		System.out.println(response.body().string());
	}
}
