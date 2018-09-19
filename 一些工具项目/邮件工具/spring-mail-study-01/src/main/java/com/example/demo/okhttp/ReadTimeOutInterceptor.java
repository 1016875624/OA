package com.example.demo.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReadTimeOutInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		chain.withReadTimeout(100, TimeUnit.SECONDS);
		return chain.proceed(chain.request());
	}

}
