package com.example.administrator.first;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.drag.DragHolderCallBack;
import com.example.administrator.drag.RecycleCallBack;

import java.util.List;


/**
 * Author: zhuwt
 * Date: 2016/6/21 18:07
 * Description: 说明
 * PackageName: com.ancun.autopack.ProjectAdapter
 * Copyright: 杭州存网络科技有限公司
 **/
public class DragAdapter extends RecyclerView.Adapter<DragAdapter.DragHolder> {

    List<Item_02> mItemList;


    private RecycleCallBack mRecycleClick;
    public SparseArray<Integer> show = new SparseArray<>();

    public DragAdapter(RecycleCallBack click, List<Item_02> mItemList) {
        this.mItemList = mItemList;
        this.mRecycleClick = click;
    }

    public void setData(List<Item_02> data) {
        this.mItemList = data;
    }

    @Override
    public DragHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new DragHolder(view, mRecycleClick);
    }

    @Override
    public void onBindViewHolder(final DragHolder holder, final int position) {
        //设置item的名字
        holder.text.setText(mItemList.get(position).getItem_name());
        //设置item的图片
        holder.icon.setImageResource(mItemList.get(position).getItem_src());

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    public class DragHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, DragHolderCallBack {

        public TextView text;
        public ImageView icon;
        public RelativeLayout item;
        private RecycleCallBack mClick;

        public DragHolder(View itemView, RecycleCallBack click) {
            super(itemView);
            mClick = click;
            item = (RelativeLayout) itemView.findViewById(R.id.item);
            text = (TextView) itemView.findViewById(R.id.text);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            item.setOnClickListener(this);
        }

        @Override
        public void onSelect() {
            Log.e("abc123","选中" );
            show.clear();
            show.put(getAdapterPosition(), getAdapterPosition());
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onClear() {
            notifyDataSetChanged();
            Log.e("abc123","放下" );
            itemView.setBackgroundResource(R.drawable.actions_item_selector);

        }

        @Override
        public void onClick(View v) {
            Log.e("abc123","点一下");
            if (null != mClick) {
                show.clear();
                mClick.itemOnClick(getAdapterPosition(), v);
            }
        }
    }
}
