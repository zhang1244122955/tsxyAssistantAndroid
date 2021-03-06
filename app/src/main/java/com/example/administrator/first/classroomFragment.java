package com.example.administrator.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.Classroom.Achievement.FamilyTable;
import com.example.administrator.Classroom.AddCurriculum.AddCurriculumActivity;
import com.example.administrator.drag.DragItemCallBack;
import com.example.administrator.drag.RecycleCallBack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2017/5/20.
 */

public class classroomFragment extends Fragment implements RecycleCallBack {

    private RecyclerView mRecyclerView;
    private DragAdapter mAdapter;
    private Item_02 item;
    private ArrayList<Item_02> mItemList;
    private SharedPreferences sp;
    private Button btn_finish;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_02, container, false);

        String[] name = { "签到", "笔记", "资料", "作业","成绩","上课记录"};
        int[] srcs = { R.drawable.page_02_sign, R.drawable.page_02_note,
                R.drawable.page_02_data,R.drawable.page_02_work, R.drawable.page_02_achievement,R.drawable.page_02_answerlog};

        sp = getActivity().getSharedPreferences("Info_02", Context.MODE_PRIVATE);
        mItemList = new ArrayList<>();
        for(int i = 0;i < (name.length);i++){
            item = new Item_02();
            item.setItem_name(name[sp.getInt("Item_c"+i,i)]);
            item.setItem_src(srcs[sp.getInt("Item_c"+i,i)]);
            item.setItem_c(sp.getInt("Item_c"+i,i));
            mItemList.add(item);
        }
        btn_finish = (Button)view.findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                for(int i = 0;i < mItemList.size();i++){
                    editor.putInt("Item_c"+i,mItemList.get(i).getItem_c());
                }
                editor.commit();
                Toast.makeText(getActivity(), "已保存",Toast.LENGTH_LONG).show();
                btn_finish.setVisibility(View.GONE);
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new DragAdapter(this, mItemList);
        mItemTouchHelper = new ItemTouchHelper(new DragItemCallBack(this));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void itemOnClick(int position, View view) {

            switch (mItemList.get(position).getItem_c()){
                case 0:
                    //先判断是否有网络
                    if(NetWorkUtils.isNetworkConnected(getActivity())){
                        //跳转界面
                        Intent intent = new Intent(getActivity(), AddCurriculumActivity.class);
                        getActivity().startActivity(intent);
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

            Toast.makeText(getActivity(), "当前点击的是" + mItemList.get(position).getItem_name(), Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();


    }


    @Override
    public void onMove(int from, int to) {
        btn_finish.setVisibility(View.VISIBLE);
        synchronized (this) {
            if (from > to) {
                int count = from - to;
                for (int i = 0; i < count; i++) {
                    //item交换
                    Collections.swap(mItemList, from - i, from - i - 1);
                }
            }
            if (from < to) {
                int count = to - from;
                for (int i = 0; i < count; i++) {
                    Collections.swap(mItemList, from + i, from + i + 1);

                }
            }
            mAdapter.setData(mItemList);
            mAdapter.notifyItemMoved(from, to);

            mAdapter.show.clear();
            mAdapter.show.put(to, to);
        }
    }
}
