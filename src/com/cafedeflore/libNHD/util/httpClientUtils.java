package com.cafedeflore.libNHD.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.cafedeflore.mininhd.MyApp;

public class httpClientUtils implements Runnable{
	
	private String url;
	private DefaultHttpClient client = null;
	private MyApp myApp = null;
	
	public String result;
	public String encode;
	
	public httpClientUtils(String url, DefaultHttpClient client, MyApp myApp){
		this.url = url;
		this.client = client;
		encode = "utf-8";
		this.myApp = myApp;
	}
	
	public String getResult(){
		return result;
	}
	
	/**
	 * @return the myApp
	 */
	public MyApp getMyApp() {
		return myApp;
	}

	/**
	 * @param myApp the myApp to set
	 */
	public void setMyApp(MyApp myApp) {
		this.myApp = myApp;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(client == null){
			return ; //TODO throw error
		}
		HttpPost httpPost = new HttpPost(url);
		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream inputStream = null;
		try {
			inputStream = httpResponse.getEntity().getContent();
			result = changeInputStream(inputStream, encode);
			//System.out.println(result);
			System.out.println("in the thread~~~~~~~~~~");
			myApp.setFlag(0);
			myApp.setResult(result);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			myApp.setFlag(1);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			myApp.setFlag(2);
			e.printStackTrace();
		}
	}
	
	/**
	 * 把Web站点返回的响应流转换为字符串格式
	 * 
	 * @param inputStream
	 *            响应流
	 * @param encode
	 *            编码格式
	 * @return 转换后的字符串
	 */
	private String changeInputStream(InputStream inputStream, String encode) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
