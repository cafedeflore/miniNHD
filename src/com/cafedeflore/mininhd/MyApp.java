package com.cafedeflore.mininhd;

import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Application;

public class MyApp extends Application{
	private String username = "cafedeflore";
	private String password = "left1bank";
	private DefaultHttpClient client = null;
	
	public DefaultHttpClient getClient(){
		return client;
	}
	
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	
	public void setClient(DefaultHttpClient client){
		this.client = client;
		return;
	}
	
	public void setUsernamePassword(String username, String password){
		this.username = username;
		this.password = password;
		return ;
	}
	
	@Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
	}
}
