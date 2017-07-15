package com.example.administrator.first;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;




/**
 * @author allen
 * @email jaylong1302@163.com
 * @date 2013-11-26 涓嬪崍1:19:35
 * @company 瀵屽獟绉戞妧
 * @version 1.0
 * @description 鏍煎瓙甯冨眬(绫讳技4.0涓殑gridlayout)
 */
public class MyGridLayout extends ViewGroup {
	private final String TAG = "MyGridLayout";

	int margin = 2;// 姣忎釜鏍煎瓙鐨勬按骞冲拰鍨傜洿闂撮殧
	int colums = 2;
	private int mMaxChildWidth = 0;
	private int mMaxChildHeight = 0;
	int count = 0;

	GridAdatper adapter;

	public MyGridLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.MyGridLayout);
			colums = a.getInteger(R.styleable.MyGridLayout_numColumns, 2);
			margin = (int) a.getInteger(R.styleable.MyGridLayout_itemMargin, 2);
		}
	}

	public MyGridLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyGridLayout(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		mMaxChildWidth = 0;
		mMaxChildHeight = 0;

		int modeW = 0, modeH = 0;
		if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED)
			modeW = MeasureSpec.UNSPECIFIED;
		if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.UNSPECIFIED)
			modeH = MeasureSpec.UNSPECIFIED;

		final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
				MeasureSpec.getSize(widthMeasureSpec), modeW);
		final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
				MeasureSpec.getSize(heightMeasureSpec), modeH);

		count = getChildCount();
		if (count == 0) {
			super.onMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
			return;
		}
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}

			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

			mMaxChildWidth = Math.max(mMaxChildWidth, child.getMeasuredWidth());
			mMaxChildHeight = Math.max(mMaxChildHeight,
					child.getMeasuredHeight());
		}
		setMeasuredDimension(resolveSize(mMaxChildWidth, widthMeasureSpec),
				resolveSize(mMaxChildHeight, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int height = b - t;// 甯冨眬鍖哄煙楂樺害
		int width = r - l;// 甯冨眬鍖哄煙瀹藉害
		int rows = count % colums == 0 ? count / colums : count / colums + 1;// 琛屾暟
		if (count == 0)
			return;
		int gridW = (width - margin * (colums - 1)) / colums;// 鏍煎瓙瀹藉害
		int gridH = (height - margin * rows) / rows;// 鏍煎瓙楂樺害

		int left = 0;
		int top = margin;

		for (int i = 0; i < rows; i++) {// 閬嶅巻琛�			
			for (int j = 0; j < colums; j++) {// 閬嶅巻姣忎竴琛岀殑鍏冪礌
				View child = this.getChildAt(i * colums + j);
				if (child == null)
					return;
				left = j * gridW + j * margin;
				// 濡傛灉褰撳墠甯冨眬瀹藉害鍜屾祴閲忓搴︿笉涓�牱锛屽氨鐩存帴鐢ㄥ綋鍓嶅竷灞�殑瀹藉害閲嶆柊娴嬮噺
				if (gridW != child.getMeasuredWidth()
						|| gridH != child.getMeasuredHeight()) {
					child.measure(makeMeasureSpec(gridW, EXACTLY),
							makeMeasureSpec(gridH, EXACTLY));
				}
				child.layout(left, top, left + gridW, top + gridH);
				// System.out
				// .println("--top--" + top + ",bottom=" + (top + gridH));

			}
			top += gridH + margin;
		}
	}

	public interface GridAdatper {
		View getView(int index);

		int getCount();
	}

	/** 璁剧疆閫傞厤鍣�*/
	public void setGridAdapter(GridAdatper adapter) {
		this.adapter = adapter;
		// 鍔ㄦ�娣诲姞瑙嗗浘
		int size = adapter.getCount();
		for (int i = 0; i < size; i++) {
			addView(adapter.getView(i));
		}
	}

	public interface OnItemClickListener {
		void onItemClick(View v, int index);
	}

	public void setOnItemClickListener(final OnItemClickListener click) {
		if (this.adapter == null)
			return;
		for (int i = 0; i < adapter.getCount(); i++) {
			final int index = i;
			View view = getChildAt(i);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					click.onItemClick(v, index);
				}
			});
		}
	}

}
