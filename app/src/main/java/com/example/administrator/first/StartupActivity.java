package com.example.administrator.first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class StartupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_main);
        final Intent it = new Intent(this, MainActivity.class); // 下一步转向Mainctivity
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); // 执行意图
                finish();
                System.exit(0);
            }
        };
        timer.schedule(task, 1000 * 3); // 2秒后跳转，这里根据自己需要设定时间
    }

}
