package com.example.administrator.first;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.CurriculumSchedule.AbsGridAdapter;
import com.example.administrator.CurriculumSchedule.WeekTitle;
import com.example.administrator.JsonBean.Courses;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/5/20.
 */

public class curriculumFragment extends Fragment {

//    private Spinner spinner;
    private GridView detailCource;
    private Courses[][] contents;
    private AbsGridAdapter secondAdapter;
    private List<String> dataList;
    private ArrayAdapter<String> spinnerAdapter;
    private String json;
    private RelativeLayout rl_gologin;
    private LinearLayout ll_logined;
    private SharedPreferences sp;
    private TextView tv_gologin;
    private Button btn_gologin;
    private TextView tvValue;
    private SpinerPopWindow<String> mSpinerPopWindow;
    private WeekTitle weekTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getActivity().getSharedPreferences("StuInfo",Context.MODE_PRIVATE);
        SharedPreferences sp = getActivity().getSharedPreferences("StuInfo", Context.MODE_PRIVATE);
        String code = Utils.getSys()+sp.getString("studentcode","")+".txt";
        final File file = new File(getActivity().getCacheDir(),code);
        if (Utils.isLogin(getActivity())){
            //获取到json字符串
            if (file.exists() && file.length() > 0){
                json = Utils.getJson(getActivity(),code);
            }else{
                Utils.savejson(getActivity(),code);
                json = Utils.getJson(getActivity(),code);
                //猜测是不能立即存储完毕
                while (json.equals("")){
                    json = Utils.getJson(getActivity(),code);
                }
            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_01, container, false);


        detailCource = (GridView)view.findViewById(R.id.courceDetail);
        rl_gologin = (RelativeLayout) view.findViewById(R.id.rl_gologin);
        ll_logined = (LinearLayout)view.findViewById(R.id.ll_logined);
        tv_gologin = (TextView)view.findViewById(R.id.tv_gologin);
        btn_gologin = (Button)view.findViewById(R.id.btn_gologin);
        weekTitle = (WeekTitle)view.findViewById(R.id.weektitle);

        weekTitle.setCurrentDay(Utils.getWeek(new Date()));
        Canvas cancas = new Canvas();
        weekTitle.onDraw(cancas);

        if (Utils.isLogin(getActivity()) && !json.equals("error")){
            ll_logined.setVisibility(View.VISIBLE);
            rl_gologin.setVisibility(View.GONE);
        }else if(Utils.isLogin(getActivity()) && json.equals("error")){

            ll_logined.setVisibility(View.GONE);
            rl_gologin.setVisibility(View.VISIBLE);
            tv_gologin.setText("暂无您的学生课表");
            btn_gologin.setVisibility(View.GONE);

        }else if(!Utils.isLogin(getActivity())){
            ll_logined.setVisibility(View.GONE);
            rl_gologin.setVisibility(View.VISIBLE);
            tv_gologin.setText("登陆之后即可查看到课表");
            btn_gologin.setVisibility(View.VISIBLE);
        }

        fillDataList();
        tvValue = (TextView) view.findViewById(R.id.tv_value);
        tvValue.setOnClickListener(clickListener);
        mSpinerPopWindow = new SpinerPopWindow<String>(getActivity(), dataList,itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);


        if(Utils.isLogin(getActivity()) && !json.equals("error")){
            mSpinerPopWindow.dismiss();
            tvValue.setText(dataList.get(sp.getInt("week",0)));
            //解析json字符串
            List<Courses> lists = Utils.getLists(json);
            //获取课程数组
            contents = Utils.getCurriculumScheduleString(lists,(double)sp.getInt("week",0)+1.0);
            //创建Adapter
            secondAdapter = new AbsGridAdapter(getActivity());
            secondAdapter.setContent(contents, 5, 7);
            detailCource.setAdapter(secondAdapter);
        }



        return view;
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
            tvValue.setText(dataList.get(position));
            if (Utils.isLogin(getActivity()) && !json.equals("")){
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("week",position);
                editor.commit();
                //解析json字符串
                List<Courses> lists = Utils.getLists(json);
                //获取课程数组
                contents = Utils.getCurriculumScheduleString(lists,(double)position+1.0);
                //创建Adapter
                secondAdapter = new AbsGridAdapter(getActivity());
                secondAdapter.setContent(contents, 5, 7);
                detailCource.setAdapter(secondAdapter);
            }


        }
    };

    /**
     * 显示PopupWindow
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_value:
                    mSpinerPopWindow.setWidth(tvValue.getWidth());
                    mSpinerPopWindow.showAsDropDown(tvValue);
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
        tvValue.setCompoundDrawables(null, null, drawable, null);
    }

    public void fillDataList() {
        dataList = new ArrayList<>();
        for(int i = 1; i < 19; i++) {
            dataList.add("第" + i + "周");
        }
    }
}
