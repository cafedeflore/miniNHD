package com.cafedeflore.libNHD;

import org.apache.http.impl.client.DefaultHttpClient;

import com.cafedeflore.libNHD.util.httpClientDownload;
import com.cafedeflore.libNHD.util.httpClientLoginUtils;
import com.cafedeflore.libNHD.util.httpClientUtils;
import com.cafedeflore.mininhd.MyApp;

public class NHDservice {
//	@Inject
	private MyApp myApp;
	
	public void login(String username, String password) throws NHDException{
		myApp.setUsernamePassword(username, password);
		DefaultHttpClient client = new DefaultHttpClient();
		myApp.setClient(client);
		Thread loginThread = new Thread(new httpClientLoginUtils(username, password, client, myApp));
		loginThread.start();
		try {
			loginThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(myApp.getFlag() == 1){
			throw new NHDException("µÇÂ¼Ê§°Ü1");
		}
		System.out.println("get login un and pw");
	}
	
	public String postRequest(String path) throws NHDException{
		DefaultHttpClient client = myApp.getClient();
		Thread t = new Thread(new httpClientUtils(path, client, myApp));
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(myApp.getFlag() != 0){
			throw new NHDException("Ê§°Ü1");
		}
		return myApp.getResult();
	}
	
	public String getRequest(String path) throws NHDException{
		DefaultHttpClient client = myApp.getClient();
		Thread t = new Thread(new httpClientDownload(path, client, myApp));
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(myApp.getFlag() != 0){
			throw new NHDException("Ê§°Ü1");
		}
		return myApp.getResult();
	}
	
	public void setApplication(MyApp app){
		myApp = app;
	}
	
	private MyApp getApplication(){
		return (MyApp)myApp;
	}
}
