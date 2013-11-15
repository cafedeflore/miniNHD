package com.cafedeflore.libNHD.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class httpClientLoginUtils implements Runnable{
	
	private String url;
	private DefaultHttpClient client = null;
	
	private String username;
	private String password;
	
	public String result;
	public String encode;
	
	public httpClientLoginUtils(String username, String password, DefaultHttpClient client){
		this.url = "http://www.nexushd.org/takelogin.php";
		this.client = client;
		this.username = username;
		this.password = password;
		encode = "utf-8";
	}
	
	public String getResult(){
		return result;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
//		params.put("username", "cafedeflore");
//		params.put("password", "left1bank");
		if(client == null){
			return ; //TODO throw error
		}
		
		String res = sendHttpClientPost(url, params, "utf-8");
		if (res.contains("用户名或密码不正确")){
			System.out.println("登录失败");
		}
		else{
			System.out.println("登录成功");
		}
		this.result = res;
	}
	
	/**
	 * 发送Http请求到Web站点
	 * 
	 * @param path
	 *            Web站点请求地址
	 * @param map
	 *            Http请求参数
	 * @param encode
	 *            编码格式
	 * @return Web站点响应的字符串
	 */
	private String sendHttpClientPost(String path, Map<String, String> map,
			String encode) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				// 解析Map传递的参数，使用一个键值对对象BasicNameValuePair保存。
				list.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		try {
			// 实现将请求 的参数封装封装到HttpEntity中。
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
			// 使用HttpPost请求方式
			HttpPost httpPost = new HttpPost(path);
			// 设置请求参数到Form中。
			httpPost.setEntity(entity);
			// 执行请求，并获得响应数据
			HttpResponse httpResponse = client.execute(httpPost);
			// 判断是否请求成功，为200时表示成功，其他均问有问题。
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 通过HttpEntity获得响应流
//				System.out.println("登录成功啦");
//				httpPost=new HttpPost("http://www.nexushd.org/torrents.php");
//				httpResponse= client.execute(httpPost);
				InputStream inputStream = httpResponse.getEntity().getContent();
				return changeInputStream(inputStream, encode);
			}
			System.out.println("登录失败");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
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
