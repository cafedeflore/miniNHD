package com.cafedeflore.mininhd;

import com.cafedeflore.libNHD.NHDservice;
import com.cafedeflore.mininhd.MyApp;
import com.cafedeflore.mininhd.R;

import android.os.Bundle;
import android.app.Activity;
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
		
		btnView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String un=username.getText().toString().trim();
				String pw=password.getText().toString().trim();
				
				nhdService.postRequest("http");
				
			}
		});
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String un=username.getText().toString();
					String pw=password.getText().toString();
//					un = "cafedeflore";
//					pw = "left1bank";
					nhdService.login(un, pw);
					//new Thread(new httpClientUtils(un,pw,MainActivity.this )).start();
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
