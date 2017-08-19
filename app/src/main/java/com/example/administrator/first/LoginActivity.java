package com.example.administrator.first;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.JsonBean.InformationBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends Activity {

	private TextView txt_title;
	private ImageView img_back;
	private ImageView iv_icon;
	private Button btn_login;
	private EditText et_name, et_pwd;
	private OkHttpClient mOkHttpClient;
	private SharedPreferences sp;
	private ProgressDialog progressDialog;
	private CheckBox cb_ischeck;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("登录");
		img_back = (ImageView) findViewById(R.id.img_back);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		img_back.setVisibility(View.VISIBLE);
		iv_icon.setVisibility(View.GONE);
		btn_login = (Button) findViewById(R.id.btn_login);
		et_name = (EditText) findViewById(R.id.et_usertel);
		et_pwd = (EditText) findViewById(R.id.et_password);
		cb_ischeck = (CheckBox)findViewById(R.id.cb_ischeck);
		//初始化对话框
		initProgressDialog();

		img_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//这么做不知道对不对 但是能实现
				finish();
			}
		});

		//打开Preferences，名称为userInfo，如果存在则打开它，否则创建新的Preferences
		//Context.MODE_PRIVATE：指定该SharedPreferences数据只能被本应用程序读、写
		//Context.MODE_WORLD_READABLE：指定该SharedPreferences数据能被其他应用程序读，但不能写
		//Context.MODE_WORLD_WRITEABLE：指定该SharedPreferences数据能被其他应用程序读写。
		sp = this.getSharedPreferences("StuInfo", Context.MODE_PRIVATE);

		String name=sp.getString("studentcode", "");
		String pwd=sp.getString("studentpwd", "");
		boolean check=sp.getBoolean("check", false);

		//把name 和 pwd显示到edittext控件上
		et_name.setText(name);
		et_pwd.setText(pwd);
		cb_ischeck.setChecked(check);

		boolean Sign2 = et_name.getText().length() > 0;
		boolean Sign3 = et_pwd.getText().length() > 6;
		if (Sign2 & Sign3) {
			btn_login.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_bg_green));
			btn_login.setEnabled(true);
		} else {
			btn_login.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_enable_green));
			btn_login.setTextColor(0xFFD0EFC6);
			btn_login.setEnabled(false);
		}

		et_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				boolean Sign2 = et_name.getText().length() > 0;
				boolean Sign3 = et_pwd.getText().length() > 6;
				if (Sign2 & Sign3) {
					btn_login.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_bg_green));
					btn_login.setEnabled(true);
				} else {
					btn_login.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_enable_green));
					btn_login.setTextColor(0xFFD0EFC6);
					btn_login.setEnabled(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		et_pwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				boolean Sign2 = et_name.getText().length() > 0;
				boolean Sign3 = et_pwd.getText().length() > 4;
				if (Sign2 & Sign3) {
					btn_login.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_bg_green));
					btn_login.setEnabled(true);
				} else {
					btn_login.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_enable_green));
					btn_login.setTextColor(0xFFD0EFC6);
					btn_login.setEnabled(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initProgressDialog() {
		progressDialog = new ProgressDialog(LoginActivity.this);
		progressDialog.setIndeterminate(false);//循环滚动
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("登录中...");
		progressDialog.setCancelable(true);//false不能取消显示，true可以取消显示
	}

	public void click_login(View v){

			final String name = et_name.getText().toString();
			final String pwd = et_pwd.getText().toString();

			if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
				Toast.makeText(this, "用户和密码不能为空", LENGTH_SHORT).show();
				return;
			}

		//先判断是否有网络
		if(NetWorkUtils.isNetworkConnected(this)){
			//显示对话框
			progressDialog.show();
			mOkHttpClient = new OkHttpClient();

			String path = getString(R.string.url)+"/api/v1.0/users/myself";
			Log.e("asd123", "click_login: "+path);
			final String credential = Credentials.basic(name, pwd);
			Request.Builder requestBuilder = new Request.Builder()
					.url(path)
					.header("Authorization", credential);
			//可以省略，默认是GET请求
			requestBuilder.method("GET",null);
			Request request = requestBuilder.build();
			Call call = mOkHttpClient.newCall(request);
			call.enqueue(new Callback(){

				@Override
				public void onFailure(Call call, IOException e) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(),"服务器忙，请稍后。",Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					int code = response.code();
					final String Json = response.body().string();

					switch (code){
						case 200:
							Gson gson = new Gson();
							final InformationBean informationBean = gson.fromJson(Json,InformationBean.class);

							//记住用户名、密码、
							SharedPreferences.Editor editor = sp.edit();
							editor.putInt("week",0);
							editor.putString("userpwd",credential);
							editor.putString("studentcode",name);
							if (cb_ischeck.isChecked()){
								editor.putString("studentpwd",pwd);
								editor.putBoolean("check", true);
							}else{
								editor.putString("studentpwd","");
								editor.putBoolean("check", false);
							}
							editor.putInt("id", informationBean.getId());
							editor.putString("username", informationBean.getUesrname());
							editor.putString("role",informationBean.getRole());
							editor.putInt("permissions",informationBean.getPermissions());
							editor.putString("url", informationBean.getUrl());
							editor.putString("school_code", informationBean.getSchool_code());
							editor.putString("member_since",informationBean.getMember_since());
							editor.putString("last_seen",informationBean.getLast_seen());
							editor.putBoolean("isok",true);
							editor.commit();

							//隐藏对话框
							progressDialog.dismiss();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_LONG).show();
								}
							});

							//跳转界面
							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							LoginActivity.this.startActivity(intent);
							finish();
							break;
						case 401:
							//隐藏对话框
							progressDialog.dismiss();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(getApplicationContext(),"学号或密码错误",Toast.LENGTH_LONG).show();
								}
							});
							break;
						default:
							//隐藏对话框
							progressDialog.dismiss();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(getApplicationContext(),"服务器忙，请稍后。",Toast.LENGTH_LONG).show();
								}
							});
							break;
					}

				}
			});
		}else{
			Toast.makeText(this,"网络未连接，请检查设置",Toast.LENGTH_SHORT).show();
		}





	}


}