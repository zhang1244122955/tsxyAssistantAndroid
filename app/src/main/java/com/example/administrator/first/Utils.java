package com.example.administrator.first;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.JsonBean.Courses;
import com.example.administrator.JsonBean.CurriculumBean;
import com.example.administrator.JsonBean.InformationBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {

	public static void showLongToast(Context context, String pMsg) {
		Toast.makeText(context, pMsg, Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(Context context, String pMsg) {
		Toast.makeText(context, pMsg, Toast.LENGTH_SHORT).show();
	}


	/**
	 * 判断登录状态
	 */
	public static boolean isLogin(Context context) {
		SharedPreferences sp = context.getSharedPreferences("StuInfo",Context.MODE_PRIVATE);

			return sp.getBoolean("isok",false);

	}

	/**
	 * 获取学生信息
	 */
	public static InformationBean getstuinfo(Context context) {
		SharedPreferences sp = context.getSharedPreferences("StuInfo",Context.MODE_PRIVATE);
		InformationBean informationBean = new InformationBean();
		informationBean.setMember_since(sp.getString("member_since",""));
		return informationBean;

	}
	/**
	 * 注销
	 */
	public static void Cancellation(Context context) {
		SharedPreferences sp = context.getSharedPreferences("StuInfo",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("userpwd","");
		editor.putString("studentcode","");
		editor.putInt("id", 0);
		editor.putString("username", "");
		editor.putString("url", "");
		editor.putString("school_code", "");
		editor.putString("member_since","");
		editor.putString("last_seen","");
		editor.putBoolean("isok",false);
		editor.commit();
		//跳转界面
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);

	}

	public static String getJson(Context context,String path){
		String Json = "";
		final File file = new File(context.getCacheDir(),path);
		try
			{
				BufferedReader in = new BufferedReader(new FileReader(file));
				String str;
				while ((str = in.readLine()) != null)
				{
					System.out.println(str);
					Json += str;
				}
				in.close();
			}
			catch (IOException e)
			{
				e.getStackTrace();
			}
		return Json;
	}

	//获取学年学期
	public static String getSys(){
		String schoolyear = "";
		String semester = "";
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		if (month>8){
			schoolyear = String.valueOf(year);
		}else{
			schoolyear = String.valueOf(year-1);
		}
		if (month>2 && month<9){
			semester = "1";
		}else{
			semester = "0";
		}
		return schoolyear+semester;
	}

	//获取星期几
	public static int getWeek(Date date){

		Calendar cal = Calendar.getInstance();//可以对每个时间域单独修改

		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK)-2;
		if(week_index<0){
			week_index = 6;
		}
		return week_index;
	}

	//读取json.txt转换为String
	public static void savejson(final Context context, final String path){

					final File file = new File(context.getCacheDir(),path);

					SharedPreferences sp = context.getSharedPreferences("StuInfo",Context.MODE_PRIVATE);
					OkHttpClient mOkHttpClient = new OkHttpClient();

					final String credential = sp.getString("userpwd","");
					Request.Builder requestBuilder = new Request.Builder()
							.url("https://lidengming.com:2345/api/v1.0/schedule/get-schedule")
							.header("Authorization", credential);
					//可以省略，默认是GET请求
					requestBuilder.method("GET",null);
					Request request = requestBuilder.build();
					Call call = mOkHttpClient.newCall(request);
					call.enqueue(new Callback() {
						@Override
						public void onFailure(Call call, IOException e) {

						}

						@Override
						public void onResponse(Call call, Response response) throws IOException {
							int code = response.code();
							switch (code){
								case 200:
									final InputStream in = response.body().byteStream();
									FileOutputStream fos = new FileOutputStream(file);
									int len = -1;
									byte[] buffer = new byte[1024];// 1kb
									while ((len = in.read(buffer)) != -1) {
										fos.write(buffer, 0, len);
									}
									fos.close();
									in.close();
									break;
								case 401:
									//暂无课表
									String str1 = "error";
									byte bytes1[]=new byte[512];
									bytes1=str1.getBytes();
									int l1 = str1.length();
									FileOutputStream fos1=new FileOutputStream(file);
									fos1.write(bytes1,0,l1);
									fos1.close();
									break;
								default:
									//暂无课表
									String str2 = "error";
									byte bytes2[]=new byte[512];
									bytes2=str2.getBytes();
									int l2 = str2.length();
									FileOutputStream fos2=new FileOutputStream(file);
									fos2.write(bytes2,0,l2);
									fos2.close();
									break;

							}
						}
					});

	}

	//解析课表json
	public static List<Courses> getLists(String Json){

		List<Courses> newsLists = new ArrayList<>();
		Gson gson = new Gson();
		CurriculumBean curriculumBean = gson.fromJson(Json,CurriculumBean.class);
		for (int i = 0; i < curriculumBean.getCourses().size(); i++) {
			newsLists.add(curriculumBean.getCourses().get(i));
		}
		return newsLists;
	}

	//设置课表
	public static Courses[][] getCurriculumScheduleString(List<Courses> lists,double week){
		Courses[][] TotalCurriculumSchedule = new Courses[5][7];
		int i,j;
		for(i=0;i<5;i++){
			for (j=0;j<7;j++){
				TotalCurriculumSchedule[i][j] = null;
			}
		}

		int row,col;
		for (Courses test : lists){
			//行
			row = Integer.parseInt(test.getWhen_code());
			row =row%10;
			row -=1;
			//列
			col = Integer.parseInt(test.getWhen_code());
			col = col/10;
			col -=1;
			ArrayList arr = test.getWeek();
			if(arr.contains(week)){
				TotalCurriculumSchedule[row][col] = test;
			}
				else{
				continue;
			}
		}

		return TotalCurriculumSchedule;
	}





	public static Date stringToDate(String str) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date = null;
		try {
			// Fri Feb 24 00:00:00 CST 2012
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}



}
