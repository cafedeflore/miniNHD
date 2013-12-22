package com.cafedeflore.mininhd;

import org.apache.http.impl.client.DefaultHttpClient;

import com.cafedeflore.libNHD.NHDservice;

import android.app.Application;
import android.content.Intent;

public class MyApp extends Application{
	private String username = "cafedeflore";
	private String password = "left1bank";
	private DefaultHttpClient client = null;
	private Intent intent = null;
	private int flag = 0;
	private String result = null;
	private NHDservice nhdService = null;
	
	public DefaultHttpClient getClient(){
		return client;
	}
	
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	
	/**
	 * @return the intent
	 */
	public Intent getIntent() {
		return intent;
	}

	/**
	 * @param intent the intent to set
	 */
	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
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

	/**
	 * @return the nhdService
	 */
	public NHDservice getNhdService() {
		return nhdService;
	}

	/**
	 * @param nhdService the nhdService to set
	 */
	public void setNhdService(NHDservice nhdService) {
		this.nhdService = nhdService;
	}
}
