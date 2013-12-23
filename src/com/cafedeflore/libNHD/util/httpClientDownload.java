package com.cafedeflore.libNHD.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.cafedeflore.mininhd.MyApp;

public class httpClientDownload implements Runnable{
	
	private String url;
	private DefaultHttpClient client = null;
	private MyApp myApp = null;
	
	public String result;
	public String encode;
	
	private Handler handler;
	
	public httpClientDownload(String url, DefaultHttpClient client, MyApp myApp){
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
		int fileSize = 0;
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		String filename = null;
		InputStream is = null;
		String filePath = "/storage/sdcard0/" + myApp.getFilePath();
		System.out.println(filePath);
		
		File path = new File(filePath);
		if (!path.isDirectory()) {
			path.mkdirs();
			System.out.println("aaaaaa");
		}
		FileOutputStream fs = null;
		try {
			httpResponse = client.execute(httpGet);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			httpEntity = httpResponse.getEntity();
			fileSize = (int)httpEntity.getContentLength();
			try {
				is = httpEntity.getContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			filename = getFileName(httpResponse, "Content-disposition");
			//System.out.println(result);
			System.out.println("in the thread~~~~~~~~~~");
			System.out.println(filename);
			System.out.println(fileSize);
			
			try {
				fs = new FileOutputStream(filePath + "/" + filename);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] buffer = new byte[1024 * 1024];
			int pos = 0;
			int byteread = 0;
			try {
				while ((byteread = is.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fs.flush();
				
				handler = new Handler(Looper.getMainLooper());
				handler.post(new Runnable(){
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("in run");
						Toast toast = Toast.makeText(myApp.getApplicationContext(), "œ¬‘ÿÕÍ≥…", Toast.LENGTH_SHORT);
						toast.show();
					}
				});
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			myApp.setFlag(0);
//			myApp.setResult(result);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			myApp.setFlag(1);
			e.printStackTrace();
		}
		return ;
	}
	
	public static String getFileName(HttpResponse response, String cd) {
		Header contentHeader = response.getFirstHeader(cd);
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					try {
					filename = param.getValue();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return filename;
	}
}
