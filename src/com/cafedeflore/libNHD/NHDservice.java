package com.cafedeflore.libNHD;

import org.apache.http.impl.client.DefaultHttpClient;

import com.cafedeflore.libNHD.util.httpClientLoginUtils;
import com.cafedeflore.libNHD.util.httpClientUtils;
import com.cafedeflore.mininhd.MyApp;
import com.google.inject.Inject;

public class NHDservice {
//	@Inject
	private MyApp myApp;
	
	public void login(String username, String password){
		myApp.setUsernamePassword(username, password);
		DefaultHttpClient client = new DefaultHttpClient();
		myApp.setClient(client);
		new Thread(new httpClientLoginUtils(username, password, client)).start();
//		System.out.println("get login un and pw");
	}
	
	public String postRequest(String path){
		System.out.println(myApp.getPassword());
		DefaultHttpClient client = myApp.getClient();
		new Thread(new httpClientUtils("http://www.nexushd.org/torrents.php", client)).start();
		return "";
	}
	
	public void setApplication(MyApp app){
		myApp = app;
	}
	
	private MyApp getApplication(){
		return (MyApp)myApp;
	}
}