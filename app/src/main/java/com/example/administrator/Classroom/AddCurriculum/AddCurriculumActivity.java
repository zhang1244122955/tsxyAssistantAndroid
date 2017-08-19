package com.example.administrator.Classroom.AddCurriculum;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.JsonBean.StructrueBean.StructrueBean;
import com.example.administrator.first.R;
import com.example.administrator.first.SpinerPopWindow;
import com.example.administrator.first.Utils;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class AddCurriculumActivity extends Activity {

    private TextView txt_title;
    private ImageView img_back;
    private ImageView iv_icon;
    private TextView tv_department;
    private String json;
    private ProgressDialog progressDialog;
    private SpinerPopWindow<String> mSpinerPopWindow;

    private List<String> dataList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_02_addcurriculum);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("添加课程");
        tv_department = (TextView)findViewById(R.id.tv_department);
        tv_department.setOnClickListener(clickListener);
        img_back = (ImageView) findViewById(R.id.img_back);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        img_back.setVisibility(View.VISIBLE);
        iv_icon.setVisibility(View.GONE);

        //初始化对话框
        initProgressDialog();
        //显示对话框
        Log.e("asd123", "click_getInfo:0 ");
        progressDialog.show();

            new Thread(){
                public void run(){
                    String code = Utils.getSys()+"structure.txt";
                    String apipath = "/api/v1.0/school/get-structure";
                    File file = new File(getCacheDir(),code);
                    //获取到json字符串
                    if (file.exists() && file.length() > 0){
                        json = Utils.getJson(getApplicationContext(),code);
                        final Gson gson = new Gson();
                        final StructrueBean structrueBean = gson.fromJson(json,StructrueBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dataList = getDataList(structrueBean);
                                mSpinerPopWindow = new SpinerPopWindow<String>(getApplicationContext(), dataList,itemClickListener);
                                mSpinerPopWindow.setOnDismissListener(dismissListener);
                                //隐藏对话框
                                progressDialog.dismiss();
                            }
                        });
                    }else{
                        Utils.savejson(getApplicationContext(),code,apipath);
                        json = Utils.getJson(getApplicationContext(),code);
                        //猜测是不能立即存储完毕
                        while (json.equals("")){
                            json = Utils.getJson(getApplicationContext(),code);
                        }
                        final Gson gson = new Gson();
                        final StructrueBean structrueBean = gson.fromJson(json,StructrueBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dataList = getDataList(structrueBean);
                                mSpinerPopWindow = new SpinerPopWindow<String>(getApplicationContext(), dataList,itemClickListener);
                                mSpinerPopWindow.setOnDismissListener(dismissListener);
                                //隐藏对话框
                                progressDialog.dismiss();
                            }
                        });
                    }
                }
            }.start();


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这么做不知道对不对 但是能实现
                finish();
            }
        });
    }


    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);//循环滚动
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在拉取，请稍后...");
        progressDialog.setCancelable(true);//false不能取消显示，true可以取消显示
    }

    public List<String> getDataList(StructrueBean structrueBean){
        List<String> date = new ArrayList<String>();
        for (int i = 0; i < structrueBean.getSchool_years().size(); i++) {
            for (int j = 0;j < structrueBean.getSchool_years().get(i).getDepartments().size();j++){
                Log.e("asd123", "run123"+structrueBean.getSchool_years().get(i).getDepartments().get(j).getName()+structrueBean.getSchool_years().get(i).getYear());
                date.add(structrueBean.getSchool_years().get(i).getDepartments().get(j).getName()+structrueBean.getSchool_years().get(i).getYear());
            }

        }
        return date;
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
            tv_department.setText(dataList.get(position));

        }
    };

    /**
     * 显示PopupWindow
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_department:
                    mSpinerPopWindow.setWidth(tv_department.getWidth());
                    mSpinerPopWindow.showAsDropDown(tv_department);
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
        tv_department.setCompoundDrawables(null, null, drawable, null);
    }

}
