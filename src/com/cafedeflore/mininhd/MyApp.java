package com.cafedeflore.mininhd;

import java.io.File;
import java.io.IOException;

import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.cafedeflore.libNHD.NHDservice;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class MyApp extends Application{
	private String username = "cafedeflore";
	private String password = "left1bank";
	private DefaultHttpClient client = null;
	private Intent intent = null;
	private int flag = 0;
	private String result = null;
	private NHDservice nhdService = null;

	private float bonus;
	private float ratio;
	private String uploaded;
	private String downloaded;
	
	private String filePath;
	
	private Context context;
	
	public void updateProfile(String html){
		Document doc = Jsoup.parse(html);
		Element info_block = doc.getElementById("info_block");
//		System.out.println(info_block);
		//username
		Element username = info_block.select("a[href]").first();
		//System.out.println(username.text());
		
		
		//bonus
		Node magicPower = info_block.select("a[href=mybonus.php]").first().nextSibling();
		//System.out.println(magicPower.toString().replace("]: ", "").replace(",", "").trim());
		this.bonus = Float.parseFloat(magicPower.toString().replace("]: ", "").replace(",", "").trim());
		
		//ratio
		Node ratio = info_block.select("font[class=color_ratio]").first().nextSibling();
//		System.out.println(ratio.toString().replace(",", "").trim());
		this.ratio = Float.parseFloat(ratio.toString().replace(",", "").trim());
		
		//uploaded
		Node uploaded = info_block.select("font[class=color_uploaded]").first().nextSibling();
//		System.out.println(uploaded.toString().trim());
		this.uploaded = uploaded.toString().trim();
		
		//downloaded
		Node downloaded = info_block.select("font[class=color_downloaded]").first().nextSibling();
//		System.out.println(downloaded.toString().trim());
		this.downloaded = downloaded.toString().trim();
		return ;
	}
	
	public String profileToString(){
		return "用户名：" + username + "\n魔力值：" + bonus + "\n分享率：" + ratio + "\n上传量："+ uploaded + "\n下载量：" + downloaded;
	}
	
	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

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
