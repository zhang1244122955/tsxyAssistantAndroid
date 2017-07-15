package com.example.administrator.CurriculumSchedule;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.JsonBean.Courses;
import com.example.administrator.first.R;
import com.example.administrator.first.Utils;


/**
 * Created by wan on 2016/10/16.
 * GridView的适配器
 */
public class AbsGridAdapter extends BaseAdapter {

    private Context mContext;

    private Courses[][] contents;
    private String[] color;

    private int rowTotal;

    private int columnTotal;

    private int positionTotal;

    public AbsGridAdapter(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        return positionTotal;
    }

    public long getItemId(int position) {
        return position;
    }

    public Courses getItem(int position) {
        //求余得到二维索引
        int column = position % columnTotal;
        //求商得到二维索引
        int row = position / columnTotal;
        return contents[row][column];
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public View getView(final int position, View convertView, ViewGroup parent) {
        int rand=15,flag=0;
        if( convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grib_item, null);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.text);
        //如果有课,那么添加数据
        if( !(getItem(position)==null)) {
            if (!(getItem(position).getNickname() == null)){
                textView.setText(getItem(position).getNickname()+getItem(position).getWhich_room());
            }else{
                textView.setText(getItem(position).getName()+getItem(position).getWhich_room());
            }
            textView.setTextColor(Color.WHITE);
            //变换颜色
            for(int i=0;i<15;i++){
                if(getItem(position).getName().equals(color[i])){
                    rand = i;
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                rand = (int)(Math.random()*15);
                while(!color[rand].equals("")){
                    rand = (int)(Math.random()*15);
                }
                color[rand] = getItem(position).getName();
            }
            switch( rand ) {
                case 0:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.grid_item_bg));
                    break;
                case 1:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_12));
                    break;
                case 2:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_13));
                    break;
                case 3:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_14));
                    break;
                case 4:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_15));
                    break;
                case 5:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_16));
                    break;
                case 6:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_17));
                    break;
                case 7:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_18));
                    break;
                case 8:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_2));
                    break;
                case 9:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_3));
                    break;
                case 10:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_4));
                    break;
                case 11:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_5));
                    break;
                case 12:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_6));
                    break;
                case 13:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_7));
                    break;
                case 14:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_1));
                    break;
                default:
                    break;
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int row = position / columnTotal;
                    int column = position % columnTotal;
                    String con = "课程名称:"+contents[row][column].getName()+"\n"+"课程位置:"+contents[row][column].getWhich_room()+"\n"+"教师名称:"+contents[row][column].getTeacher()+"\n"+"课程学分:"+contents[row][column].getWorth()+"\n"+"是否双周:"+contents[row][column].getParity();;
                    Toast.makeText(mContext, con, Toast.LENGTH_LONG).show();
                }
            });
        }
        return convertView;
    }

    /**
     * 设置内容、行数、列数
     */
    public void setContent(Courses[][] contents, int row, int column) {
        color = new String[15];
        for (int i=0;i<15;i++){
            color[i] = "";
        }
        this.contents = contents;
        this.rowTotal = row;
        this.columnTotal = column;
        positionTotal = rowTotal * columnTotal;
    }


}
