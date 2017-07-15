package com.example.administrator.achievement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.administrator.JsonBean.AchievementBean;
import com.example.administrator.JsonBean.Scores;
import com.example.administrator.first.R;
import com.example.administrator.first.SpinerPopWindow;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class FamilyTable extends Activity {

	private TextView txt_title;
	private TextView tv_choice;
	private SpinerPopWindow<String> mSpinerPopWindow;
	AchievementBean achievement = new AchievementBean();
	private List<String> dataList;
	private List<Scores> scores;
	private TableFixHeaders tableFixHeaders;
	private ImageView img_back;
	private ImageView iv_icon;

	private class NexusTypes {
		private final String name;
		private final List<Nexus> list;

		NexusTypes(String name) {
			this.name = name;
			list = new ArrayList<Nexus>();
		}

		public int size() {
			return list.size();
		}

		public Nexus get(int i) {
			return list.get(i);
		}
	}

	private class Nexus {
		private final String[] data;

		private Nexus(String name, String score, String worth, String exam_method, String type, String quale) {
			data = new String[] {
					name,
					score,
					worth,
					exam_method,
					type,
					quale };
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_02_achievement);

		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("成绩查询");
		tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
		tv_choice = (TextView)findViewById(R.id.tv_choice);
		tv_choice.setOnClickListener(clickListener);
		img_back = (ImageView) findViewById(R.id.img_back);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		img_back.setVisibility(View.VISIBLE);
		iv_icon.setVisibility(View.GONE);

		saveAchiecementJson(this);
		//不能立即存储
		achievement = getAchiecementBean();
		while (achievement == null){
			achievement = getAchiecementBean();
		}

//		Log.e("abc123",achievement.toString() );
		dataList = getDataList(achievement);
		mSpinerPopWindow = new SpinerPopWindow<String>(this, dataList,itemClickListener);
		mSpinerPopWindow.setOnDismissListener(dismissListener);

		tv_choice.setText(dataList.get(dataList.size()-1));
		scores = getScoresList(achievement,dataList.get(dataList.size()-1));
		BaseTableAdapter baseTableAdapter = new FamilyNexusAdapter(FamilyTable.this,scores);
		tableFixHeaders.setAdapter(baseTableAdapter);

		img_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	//获取成绩list
	public List<Scores> getScoresList(AchievementBean achievementBean,String str){
		List<Scores> scoresList = new ArrayList<>();
		for (int i = 0; i < achievement.getScore_tables().size(); i++) {
			if(achievementBean.getScore_tables().get(i).getSemester().equals(str)) {
				scoresList = achievementBean.getScore_tables().get(i).getScores();
			}
		}
		return scoresList;
	}
	public List<String> getDataList(AchievementBean achievementBean){
		List<String> data = new ArrayList<>();
		for (int i = 0; i < achievement.getScore_tables().size(); i++) {
			data.add(achievementBean.getScore_tables().get(i).getSemester());
		}
		return data;
	}

	//获取成绩jsonBean
	public AchievementBean getAchiecementBean(){
		String Json = "";
		File file = new File(FamilyTable.this.getFilesDir(),"achiecement.txt");
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
		Gson gson = new Gson();
		AchievementBean achievementBean = gson.fromJson(Json,AchievementBean.class);
		return achievementBean;
	}

	//存储成绩json
	public void saveAchiecementJson(final Context context){

		SharedPreferences sp = context.getSharedPreferences("StuInfo",Context.MODE_PRIVATE);
		OkHttpClient mOkHttpClient = new OkHttpClient();

		final String credential = sp.getString("userpwd","");
		File filedelete = new File(context.getFilesDir(),"achiecement.txt");
		filedelete.delete();
		final File file = new File(context.getFilesDir(),"achiecement.txt");
		Request.Builder requestBuilder = new Request.Builder()
				.url("https://lidengming.com:2345/api/v1.0/score?score_type=all")
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
					case 401:;
						break;
					default:
						break;

				}
			}
		});

	}


	/**
	 * 监听popupwindow取消
	 */
	private PopupWindow.OnDismissListener dismissListener=new PopupWindow.OnDismissListener() {
		@Override
		public void onDismiss() {
			setTextImage(R.drawable.icon_down);
		}
	};

	/**
	 * popupwindow显示的ListView的item点击事件
	 */
	private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			mSpinerPopWindow.dismiss();
			tv_choice.setText(dataList.get(position));
			scores = getScoresList(achievement,dataList.get(position));

			BaseTableAdapter baseTableAdapter = new FamilyNexusAdapter(FamilyTable.this,scores);
			tableFixHeaders.setAdapter(baseTableAdapter);

		}
	};

	/**
	 * 显示PopupWindow
	 */
	private View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_choice:
					mSpinerPopWindow.setWidth(tv_choice.getWidth());
					mSpinerPopWindow.showAsDropDown(tv_choice);
					setTextImage(R.drawable.icon_up);
					break;
			}
		}
	};

	/**
	 * 给TextView右边设置图片
	 * @param resId
	 */
	private void setTextImage(int resId) {
		Drawable drawable = getResources().getDrawable(resId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
		tv_choice.setCompoundDrawables(null, null, drawable, null);
	}

	public class FamilyNexusAdapter extends BaseTableAdapter {

		private final NexusTypes familys[];
		private final String headers[] = {
				"课程/环节",
				"成绩",
				"学分",
				"考核方式",
				"取得方式",
				"初/重修",
		};

		private final int[] widths = {
				120,
				60,
				60,
				60,
				80,
				60,
		};
		private final float density;

		public FamilyNexusAdapter(Context context,List<Scores> scores) {
			familys = new NexusTypes[] {
					new NexusTypes("考试"),
					new NexusTypes("考查"),
					new NexusTypes("实训、课设及网选"),
					new NexusTypes("补考及重修"),
			};

			density = context.getResources().getDisplayMetrics().density;

			for (Scores scores1 : scores){
				if (scores1.getGet_method().equals("初修取得")){
					switch (scores1.getExam_method()){
						case "考试":
							familys[0].list.add(new Nexus(scores1.getName(),scores1.getScore(),scores1.getWorth(),scores1.getExam_method(),scores1.getGet_method(),scores1.getQuale()));
							break;
						case "考查":
							familys[1].list.add(new Nexus(scores1.getName(),scores1.getScore(),scores1.getWorth(),scores1.getExam_method(),scores1.getGet_method(),scores1.getQuale()));
							break;
						case "":
							familys[2].list.add(new Nexus(scores1.getName(),scores1.getScore(),scores1.getWorth(),scores1.getExam_method(),scores1.getGet_method(),scores1.getQuale()));
							break;
					}
				}else{
					familys[3].list.add(new Nexus(scores1.getName(),scores1.getScore(),scores1.getWorth(),scores1.getExam_method(),scores1.getGet_method(),scores1.getQuale()));
				}
			}

		}

		@Override
		public int getRowCount() {
			return familys[0].size()+familys[1].size()+familys[2].size()+familys[3].size()+4;
		}

		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public View getView(int row, int column, View convertView, ViewGroup parent) {
			final View view;
			switch (getItemViewType(row, column)) {
				case 0:
					view = getFirstHeader(row, column, convertView, parent);
				break;
				case 1:
					view = getHeader(row, column, convertView, parent);
				break;
				case 2:
					view = getFirstBody(row, column, convertView, parent);
				break;
				case 3:
					view = getBody(row, column, convertView, parent);
				break;
				case 4:
					view = getFamilyView(row, column, convertView, parent);
				break;
				default:
					throw new RuntimeException("wtf?");
			}
			return view;
		}

		private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_table_header_first, parent, false);
			}
			((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[0]);
			return convertView;
		}

		private View getHeader(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_table_header, parent, false);
			}
			((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[column + 1]);
			return convertView;
		}

		private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_table_first, parent, false);
			}
			convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
			((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
			return convertView;
		}

		private View getBody(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false);
			}
			convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
			((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
			return convertView;
		}

		private View getFamilyView(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_table_family, parent, false);
			}
			final String string;
			if (column == -1) {
				string = getFamily(row).name;
			} else {
				string = "";
			}
			((TextView) convertView.findViewById(android.R.id.text1)).setText(string);
			return convertView;
		}

		@Override
		public int getWidth(int column) {
			return Math.round(widths[column + 1] * density);
		}

		@Override
		public int getHeight(int row) {
			final int height;
			if (row == -1) {
				height = 35;
			} else if (isFamily(row)) {
				height = 25;
			} else {
				height = 45;
			}
			return Math.round(height * density);
		}

		@Override
		public int getItemViewType(int row, int column) {
			final int itemViewType;
			if (row == -1 && column == -1) {
				itemViewType = 0;
			} else if (row == -1) {
				itemViewType = 1;
			} else if (isFamily(row)) {
				itemViewType = 4;
			} else if (column == -1) {
				itemViewType = 2;
			} else {
				itemViewType = 3;
			}
			return itemViewType;
		}

		private boolean isFamily(int row) {
			int family = 0;
			while (row > 0) {
				row -= familys[family].size() + 1;
				family++;
			}
			return row == 0;
		}

		private NexusTypes getFamily(int row) {
			int family = 0;
			while (row >= 0) {
				row -= familys[family].size() + 1;
				family++;
			}
			return familys[family - 1];
		}

		private Nexus getDevice(int row) {
			int family = 0;
			while (row >= 0) {
				row -= familys[family].size() + 1;
				family++;
			}
			family--;
			return familys[family].get(row + familys[family].size());
		}

		@Override
		public int getViewTypeCount() {
			return 5;
		}
	}
}
