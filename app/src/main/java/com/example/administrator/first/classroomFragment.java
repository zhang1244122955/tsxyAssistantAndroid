package com.example.administrator.first;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.achievement.FamilyTable;
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
    private ArrayList<String> mList;
    private ItemTouchHelper mItemTouchHelper;
    int[] abc = new int[20];
    int change;

//    123MyGridLayout grid;
//
    int[] srcs = { R.drawable.page_02_sign, R.drawable.page_02_note,
        R.drawable.page_02_data,R.drawable.page_02_work, R.drawable.page_02_achievement,R.drawable.page_02_answerlog};
//    String titles[] = { "签到", "笔记", "资料", "作业","成绩","上课记录"};

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_02, container, false);


        mList = new ArrayList<>();
        mList.add("签到");
        mList.add("笔记");
        mList.add("资料");
        mList.add("作业");
        mList.add("成绩");
        mList.add("上课记录");
        for (int i = 0; i < 20; i++) {
//            mList.add("张三" + i);
            abc[i] = i;
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new DragAdapter(this, mList,srcs);
        mItemTouchHelper = new ItemTouchHelper(new DragItemCallBack(this));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

//        grid = (123MyGridLayout) view.findViewById(R.id.list);
//        grid.setGridAdapter(new 123MyGridLayout.GridAdatper() {
//
//            @Override
//            public View getView(int index) {
//                View view1 = inflater.inflate(R.layout.actions_item,
//                        null);
//                ImageView iv = (ImageView) view1.findViewById(R.id.iv);
//                TextView tv = (TextView) view1.findViewById(R.id.tv);
//                iv.setImageResource(srcs[index]);
//                tv.setText(titles[index]);
//                return view1;
//            }
//
//            @Override
//            public int getCount() {
//                // TODO Auto-generated method stub
//                return titles.length;
//            }
//        });
//        grid.setOnItemClickListener(new 123MyGridLayout.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(View v, int index) {
//                switch (index){
//                    case 0:
//                       //先判断是否有网络
//                        if(NetWorkUtils.isNetworkConnected(getActivity())){
//                        Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(getActivity(),"网络未连接，请检查设置",Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case 1:
//                        Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
//                        break;
//                    case 4:
//                        //先判断是否有网络
//                        if(NetWorkUtils.isNetworkConnected(getActivity())){
//                            //  成绩查询逻辑
//                            if (Utils.isLogin(getActivity())){
//                                //跳转界面
//                                Intent intent = new Intent(getActivity(), FamilyTable.class);
//                                getActivity().startActivity(intent);
//                            }else {
//                                Toast.makeText(getActivity(),"还未登录，请先登录",Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//                            Toast.makeText(getActivity(),"网络未连接，请检查设置",Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case 5:
//                        Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
        return view;
    }

    @Override
    public void itemOnClick(int position, View view) {
        if (view.getId() == R.id.del) {
            mList.remove(position);
            mAdapter.setData(mList);
            mAdapter.notifyItemRemoved(position);
        } else {
            switch (abc[position]){
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

            Toast.makeText(getActivity(), "当前点击的是" + mList.get(position), Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMove(int from, int to) {
        synchronized (this) {
            if (from > to) {
                int count = from - to;
                for (int i = 0; i < count; i++) {
                    //item名字交换
                    Collections.swap(mList, from - i, from - i - 1);
                    //item图片信息交换
                    change = srcs[from - i];
                    srcs[from - i] = srcs[from - i - 1];
                    srcs[from - i - 1] = change;
                    //item数组对应值交换
                    change = abc[from - i];
                    abc[from - i] = abc[from - i - 1];
                    abc[from - i - 1] = change;
                }
            }
            if (from < to) {
                int count = to - from;
                for (int i = 0; i < count; i++) {
                    Collections.swap(mList, from + i, from + i + 1);
                    change = srcs[from + i];
                    srcs[from + i] = srcs[from + i + 1];
                    srcs[from + i + 1] = change;
                    change = abc[from + i];
                    abc[from + i] = abc[from + i + 1];
                    abc[from + i + 1] = change;
                }
            }
            mAdapter.setData(mList);
            mAdapter.setSrcs(srcs);
            mAdapter.notifyItemMoved(from, to);
//            if(from < to){
//                change = abc[from];
//                for(int i = from;i < to;i++){
//                    abc[i] = abc[i+1];
//                }
//                abc[to] = change;
//            }else{
//                change = abc[from];
//                for(int i = from;i > to;i--){
//                    abc[i] = abc[i-1];
//                }
//                abc[to] = change;
//            }
            mAdapter.show.clear();
            mAdapter.show.put(to, to);
        }
    }
}
