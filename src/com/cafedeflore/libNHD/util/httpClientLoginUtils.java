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
		if (res.contains("�û��������벻��ȷ")){
			System.out.println("��¼ʧ��");
		}
		else{
			System.out.println("��¼�ɹ�");
		}
		this.result = res;
	}
	
	/**
	 * ����Http����Webվ��
	 * 
	 * @param path
	 *            Webվ�������ַ
	 * @param map
	 *            Http�������
	 * @param encode
	 *            �����ʽ
	 * @return Webվ����Ӧ���ַ���
	 */
	private String sendHttpClientPost(String path, Map<String, String> map,
			String encode) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				// ����Map���ݵĲ�����ʹ��һ����ֵ�Զ���BasicNameValuePair���档
				list.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		try {
			// ʵ�ֽ����� �Ĳ�����װ��װ��HttpEntity�С�
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
			// ʹ��HttpPost����ʽ
			HttpPost httpPost = new HttpPost(path);
			// �������������Form�С�
			httpPost.setEntity(entity);
			// ִ�����󣬲������Ӧ����
			HttpResponse httpResponse = client.execute(httpPost);
			// �ж��Ƿ�����ɹ���Ϊ200ʱ��ʾ�ɹ����������������⡣
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// ͨ��HttpEntity�����Ӧ��
//				System.out.println("��¼�ɹ���");
//				httpPost=new HttpPost("http://www.nexushd.org/torrents.php");
//				httpResponse= client.execute(httpPost);
				InputStream inputStream = httpResponse.getEntity().getContent();
				return changeInputStream(inputStream, encode);
			}
			System.out.println("��¼ʧ��");
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
