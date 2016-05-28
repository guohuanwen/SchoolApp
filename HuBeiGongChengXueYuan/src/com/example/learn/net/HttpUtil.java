package com.example.learn.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;
import android.util.Log;

public class HttpUtil {
	// Httpget请求
	public static void httpGet(final String param,
			final HttpCallbackListener listener) {
		Log.i("HttpUtil", "url=" + param);
		try {
			HttpGet httpGet = new HttpGet(param);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Log.i("HttpUtil","httpget"+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();

				String re = EntityUtils.toString(entity, "UTF-8");
				Log.i("HttpUtil", re);

				listener.onFinish(re);
			}
		} catch (Exception e) {
			Log.i("HttpUtil", e.toString());
			listener.onError(e);
		}

	}

	// Httpget请求 带cookie
	public static void httpGet(final String param, final String cookie,
			final HttpCallbackListener listener) {
		Log.i("HttpUtil", "url=" + param);
		try {
			HttpGet httpGet = new HttpGet(param);
			HttpClient httpClient = new DefaultHttpClient();
			httpGet.setHeader("cookie", "JSESSIONID=" + cookie);
			// // 请求超时
			// httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
			// 20000);
			// // 读取超时
			// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
			// 20000);Httpget 带cookie

			HttpResponse httpResponse = httpClient.execute(httpGet);
			Log.i("HttpUtil", "Httpget 带cookie"+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				String re = EntityUtils.toString(entity, "UTF-8");
				listener.onFinish(re);
			}
		} catch (Exception e) {
			Log.i("HttpUtil", e.toString());
			listener.onError(e);
		}

	}

	// HttpPost
	public void httpPost(final String param, List<NameValuePair> postParams,
			final HttpCallbackListener listener) {
		try {
			HttpPost httpPost = new HttpPost(param);
			HttpClient httpClient = new DefaultHttpClient();
			// // 请求超时
			// httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
			// 20000);
			// // 读取超时
			// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
			// 20000);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams,
					"utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.i("HttpUtil", "post"+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				InputStream inputStream = httpResponse.getEntity().getContent();
				InputStreamReader inputRead = new InputStreamReader(
						inputStream, "utf-8");
				BufferedReader bufferReader = new BufferedReader(inputRead);
				String data = "";
				StringBuffer stringBuffer = new StringBuffer();
				while ((data = bufferReader.readLine()) != null) {
					stringBuffer.append(data);
				}
				Log.i("HttpUtil", stringBuffer.toString());
				listener.onFinish(stringBuffer.toString());
			}
		} catch (Exception e) {
			Log.i("HttpUtil", e.toString());
			listener.onError(e);
		}
	}

	// HttpPost带cookie
	public void httpPost(final String param, final String cookie,
			List<NameValuePair> postParams, final HttpCallbackListener listener) {
		try {
			HttpPost httpPost = new HttpPost(param);
			HttpClient httpClient = new DefaultHttpClient();
			httpPost.setHeader("cookie", "JSESSIONID=" + cookie);
			// // 请求超时
			// httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
			// 20000);
			// // 读取超时
			// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
			// 20000);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams,
					"utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.i("HttpUtil",httpResponse.getStatusLine().getStatusCode()+"");
			Log.i("HttpUtil", "cookie  ,post"+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				InputStream inputStream = httpResponse.getEntity().getContent();
				InputStreamReader inputRead = new InputStreamReader(
						inputStream, "utf-8");
				BufferedReader bufferReader = new BufferedReader(inputRead);
				String data = "";
				StringBuffer stringBuffer = new StringBuffer();
				while ((data = bufferReader.readLine()) != null) {
					stringBuffer.append(data);
				}
				Log.i("HttpUtil", stringBuffer.toString());
				listener.onFinish(stringBuffer.toString());
			}
		} catch (Exception e) {
			Log.i("HttpUtil", e.toString());
			listener.onError(e);
		}
	}

	// HttpPost带cookie
	public void httpLoginPost(final String param, final String cookie,
			String id,String password,String checkCode, final HttpCallbackListener listener) {
		try{  
            //通过openConnection 连接  
            URL url = new java.net.URL(param);  
            HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();  
            //设置输入和输出流   
            urlConn.setDoOutput(true);  
            urlConn.setDoInput(true);  
            urlConn.setRequestMethod("POST");  
            urlConn.setUseCaches(false);
            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的    
            urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
            urlConn.setRequestProperty("JSESSIONID",cookie);
            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，  
            // 要注意的是connection.getOutputStream会隐含的进行connect。    
            urlConn.connect();  
            //DataOutputStream流  
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());  
            //要上传的参数  
            String content = "PASSWORD=" + URLEncoder.encode(password, "UTF_8")+
            		"&USERNAME=" +URLEncoder.encode(id, "UTF_8")+
            		"&RANDOMCODE="+ URLEncoder.encode(checkCode, "UTF_8");   
            //将要上传的内容写入流中  
            out.writeBytes(content);     
            //刷新、关闭  
            OutputStream os = urlConn.getOutputStream();  
//            os.write(data.getBytes());  
            os.flush();  
            Log.i("HttpUtil", urlConn.getResponseCode()+"");
            if (urlConn.getResponseCode() == 200) { 
            	listener.onFinish("");
            }   
            out.close();     
              
        }catch(Exception e){   
        	listener.onError(e); 
        } 
	}
}
