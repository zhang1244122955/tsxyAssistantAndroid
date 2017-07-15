package com.example.administrator.first;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.achievement.FamilyTable;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Administrator on 2017/5/20.
 */

public class classroomFragment extends Fragment{

    MyGridLayout grid;

    int[] srcs = { R.drawable.page_02_sign, R.drawable.page_02_note,
            R.drawable.page_02_data,R.drawable.page_02_work, R.drawable.page_02_achievement,R.drawable.page_02_answerlog};
    String titles[] = { "签到", "笔记", "资料", "作业","成绩","上课记录"};

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_02, container, false);

        grid = (MyGridLayout) view.findViewById(R.id.list);
        grid.setGridAdapter(new MyGridLayout.GridAdatper() {

            @Override
            public View getView(int index) {
                View view1 = inflater.inflate(R.layout.actions_item,
                        null);
                ImageView iv = (ImageView) view1.findViewById(R.id.iv);
                TextView tv = (TextView) view1.findViewById(R.id.tv);
                iv.setImageResource(srcs[index]);
                tv.setText(titles[index]);
                return view1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return titles.length;
            }
        });
        grid.setOnItemClickListener(new MyGridLayout.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int index) {
                switch (index){
                    case 0:
                       //先判断是否有网络
                        if(NetWorkUtils.isNetworkConnected(getActivity())){
                        Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),"网络未连接，请检查设置",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        //先判断是否有网络
                        if(NetWorkUtils.isNetworkConnected(getActivity())){
                            //  成绩查询逻辑
                            if (Utils.isLogin(getActivity())){
                                //跳转界面
                                Intent intent = new Intent(getActivity(), FamilyTable.class);
                                getActivity().startActivity(intent);
                            }else {
                                Toast.makeText(getActivity(),"还未登录，请先登录",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getActivity(),"网络未连接，请检查设置",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 5:
                        Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }
}
