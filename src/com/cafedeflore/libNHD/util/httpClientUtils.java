package com.cafedeflore.libNHD.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class httpClientUtils implements Runnable{
	
	private String url;
	private DefaultHttpClient client = null;
	
	public String result;
	public String encode;
	
	public httpClientUtils(String url, DefaultHttpClient client){
		this.url = url;
		this.client = client;
		encode = "utf-8";
	}
	
	public String getResult(){
		return result;
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
			System.out.println(result);
			System.out.println("in the thread~~~~~~~~~~");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��Webվ�㷵�ص���Ӧ��ת��Ϊ�ַ�����ʽ
	 * 
	 * @param inputStream
	 *            ��Ӧ��
	 * @param encode
	 *            �����ʽ
	 * @return ת������ַ���
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
