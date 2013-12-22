package com.cafedeflore.mininhd;

import com.cafedeflore.libNHD.NHDException;
import com.cafedeflore.libNHD.NHDservice;
import com.cafedeflore.mininhd.MyApp;
import com.cafedeflore.mininhd.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	private Button btnLogin;
	private Button btnView;
	private static MyApp myApp;
	private EditText username,password;
	private NHDservice nhdService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		btnLogin=(Button)findViewById(R.id.btnLogin);
		btnView=(Button)findViewById(R.id.btnView);
		username=(EditText)findViewById(R.id.editText1);
		password=(EditText)findViewById(R.id.editText2);
		
		myApp = (MyApp)getApplication();
		nhdService = new NHDservice();
		myApp.setUsernamePassword("lala1", "haha1");
		nhdService.setApplication(myApp);
		myApp.setUsernamePassword("lala2", "haha2");
		myApp.setNhdService(nhdService);
		
		btnView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String un=username.getText().toString().trim();
				String pw=password.getText().toString().trim();
				
				try {
					nhdService.postRequest("http");
				} catch (NHDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, HomeActivity.class);
				System.out.println("starting new activity");
				startActivity(intent);
				finish();
			}
		});
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String un=username.getText().toString();
				String pw=password.getText().toString();
				
				nhdService.setApplication(myApp);
				try {
					nhdService.login(un, pw);
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, HomeActivity.class);
					System.out.println("starting new activity");
					startActivity(intent);
					finish();
				} catch (NHDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
}
