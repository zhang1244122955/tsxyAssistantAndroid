package com.example.administrator.first;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.JsonBean.InformationBean;
import com.nineoldandroids.view.ViewHelper;

import java.util.List;
import java.util.Random;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnClickListener{

    private DragLayout dl;
    private ListView lv;

    private long exitTime = 0;

    private SelfDialog selfDialog;



    private ImageView iv_icon;

    // 底部菜单4个RelativeLayout
    private RelativeLayout ll_curriculum;
    private RelativeLayout ll_classroom;
    private RelativeLayout ll_campus;
    private RelativeLayout ll_society;

    // 底部菜单4个ImageView
    private ImageView iv_curriculum;
    private ImageView iv_classroom;
    private ImageView iv_campus;
    private ImageView iv_society;

    // 底部菜单4个菜单标题
    private TextView tv_curriculum;
    private TextView tv_classroom;
    private TextView tv_campus;
    private TextView tv_society;

    // 4个Fragment
    private Fragment curriculumFragment;
    private Fragment classroomFragment;
    private Fragment campusFragment;
    private Fragment societyFragment;

    private TextView tv_stuinfo;
    private TextView tv_cancellation;


    private List<View> views;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDragLayout();
        // 初始化控件
        initView();
        // 初始化底部按钮事件
        initEvent();
        // 初始化并设置当前Fragment
        initFragment(0);

        //每次登陆注销都刷新课表的状态
        //curriculumFragment = new curriculumFragment();

        if(Utils.isLogin(this)){
            InformationBean informationBean = Utils.getstuinfo(this);
            tv_stuinfo.setText(informationBean.getRole());
            tv_cancellation.setText("注销");


        }else{
            tv_stuinfo.setText("未登录");
            tv_cancellation.setText("登录");

        }
    }
    //再按一次退出(抽屉打开情况下按返回键 先关闭抽屉)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!dl.dlinfo()){
            if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
                if((System.currentTimeMillis()-exitTime) > 2000){
                    Toast.makeText(getApplicationContext(), "再按一次退出唐小二", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }else{
            dl.close();
            return true;
        }
    }

    private void initFragment(int index) {
        // 由于是引用了V4包下的Fragment，所以这里的管理器要用getSupportFragmentManager获取
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 隐藏所有Fragment
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (curriculumFragment == null) {
                    curriculumFragment = new curriculumFragment();
                    transaction.add(R.id.fl_content, curriculumFragment);
                } else {
                    transaction.show(curriculumFragment);
                }
                break;
            case 1:
                if (classroomFragment == null) {
                    classroomFragment = new classroomFragment();
                    transaction.add(R.id.fl_content, classroomFragment);
                } else {
                    transaction.show(classroomFragment);
                }

                break;
            case 2:
                if (campusFragment == null) {
                    campusFragment = new campusFragment();
                    transaction.add(R.id.fl_content, campusFragment);
                } else {
                    transaction.show(campusFragment);
                }

                break;
            case 3:
                if (societyFragment == null) {
                    societyFragment = new societyFragment();
                    transaction.add(R.id.fl_content, societyFragment);
                } else {
                    transaction.show(societyFragment);
                }

                break;

            default:
                break;
        }

        // 提交事务
        transaction.commit();

    }



    //隐藏Fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (curriculumFragment != null) {
            transaction.hide(curriculumFragment);
        }
        if (classroomFragment != null) {
            transaction.hide(classroomFragment);
        }
        if (campusFragment != null) {
            transaction.hide(campusFragment);
        }
        if (societyFragment != null) {
            transaction.hide(societyFragment);
        }

    }


    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            @Override
            //打开侧拉栏
            public void onOpen() {
                lv.smoothScrollToPosition(new Random().nextInt(30));
            }

            @Override
            //关闭侧拉栏
            public void onClose() {
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(iv_icon, 1 - percent);
            }
        });
    }

    private void initEvent() {
        // 设置按钮监听
        ll_curriculum.setOnClickListener(this);
        ll_classroom.setOnClickListener(this);
        ll_campus.setOnClickListener(this);
        ll_society.setOnClickListener(this);

    }

    private void initView() {

        // 底部菜单4个RelativeLayout
        this.ll_curriculum = (RelativeLayout) findViewById(R.id.re_weixin);
        this.ll_classroom = (RelativeLayout) findViewById(R.id.re_contact_list);
        this.ll_campus = (RelativeLayout) findViewById(R.id.re_find);
        this.ll_society = (RelativeLayout) findViewById(R.id.re_profile);

        // 底部菜单4个ImageView
        this.iv_curriculum = (ImageView) findViewById(R.id.ib_weixin);
        this.iv_classroom = (ImageView) findViewById(R.id.ib_contact_list);
        this.iv_campus = (ImageView) findViewById(R.id.ib_find);
        this.iv_society = (ImageView) findViewById(R.id.ib_profile);

        // 底部菜单4个菜单标题
        this.tv_curriculum = (TextView) findViewById(R.id.tv_weixin);
        this.tv_classroom = (TextView) findViewById(R.id.tv_contact_list);
        this.tv_campus = (TextView) findViewById(R.id.tv_find);
        this.tv_society = (TextView) findViewById(R.id.tv_profile);

         tv_stuinfo= (TextView)findViewById(R.id.tv_stuinfo);
         tv_cancellation = (TextView)findViewById(R.id.tv_cancellation);



        iv_curriculum.setImageResource(R.drawable.curriculum_pressed);
        tv_curriculum.setTextColor(Color.rgb(18,150,219));

        iv_icon = (ImageView) findViewById(R.id.iv_icon);

        lv = (ListView) findViewById(R.id.lv);

        lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.item_text, new String[] { "第一项","第二项","第三项","第四项","第五项", }));

        //抽屉ite监听
        lv.setOnItemClickListener(new OnItemClickListener() {

            @SuppressLint("ShowToast")
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }
            }
        });

        //头像监听事件
        iv_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
            }
        });

        //注销
        tv_cancellation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前登录状态，如果是登录状态就做注销操作，如果是注销状态就做登录操作。
                if(Utils.isLogin(MainActivity.this)){

                    selfDialog = new SelfDialog(MainActivity.this);
                    selfDialog.setTitle("注销当前账号");
                    selfDialog.setMessage("退出后部分功能会受到影响，是否注销？");
                    selfDialog.setYesOnclickListener("确定注销", new SelfDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {

                            Utils.Cancellation(MainActivity.this);
                            tv_cancellation.setText("登录");
                            tv_stuinfo.setText("未登录");
                            selfDialog.dismiss();
                        }
                    });
                    selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            selfDialog.dismiss();
                        }
                    });
                    selfDialog.show();

                }else{
                    //跳转界面
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                    finish();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void click_gologin(View v){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {

        // 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为灰色，然后根据点击着色
        restartBotton();
        // ImageView和TetxView置为绿色，页面随之跳转
        switch (v.getId()) {
            case R.id.re_weixin:
                iv_curriculum.setImageResource(R.drawable.curriculum_pressed);
                tv_curriculum.setTextColor(Color.rgb(18,150,219));
                initFragment(0);
                break;
            case R.id.re_contact_list:
                iv_classroom.setImageResource(R.drawable.classroom_pressed);
                tv_classroom.setTextColor(Color.rgb(18,150,219));
                initFragment(1);

                break;
            case R.id.re_find:
                iv_campus.setImageResource(R.drawable.campus_pressed);
                tv_campus.setTextColor(Color.rgb(18,150,219));
                initFragment(2);
                break;
            case R.id.re_profile:
                iv_society.setImageResource(R.drawable.society_pressed);
                tv_society.setTextColor(Color.rgb(18,150,219));
                initFragment(3);
                break;

            default:
                break;
        }


    }

    private void restartBotton() {
        // ImageView置为灰色
        iv_curriculum.setImageResource(R.drawable.curriculum_normal);
        iv_classroom.setImageResource(R.drawable.classroom_normal);
        iv_campus.setImageResource(R.drawable.campus_normal);
        iv_society.setImageResource(R.drawable.society_normal);
        // TextView置为灰色色
        tv_curriculum.setTextColor(Color.rgb(154,154,154));
        tv_classroom.setTextColor(Color.rgb(154,154,154));
        tv_campus.setTextColor(Color.rgb(154,154,154));
        tv_society.setTextColor(Color.rgb(154,154,154));
    }



}
