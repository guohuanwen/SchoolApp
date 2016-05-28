package com.example.learn.net;

import java.io.InputStream;

import org.apache.http.client.HttpClient;

public interface HttpCallbackListener {
	//请求成功回调
	void onFinish(String response);
	//请求失败回调
	void onError(Exception e);
}
