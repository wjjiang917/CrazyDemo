package com.crazyjiang.library.easyrefreshlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.crazyjiang.library.R;
import com.crazyjiang.library.easyrefreshlayout.ILoadMoreView;
import com.github.ybq.android.spinkit.SpinKitView;

/**
 * Created by guanaj on 16/9/22.
 */
public class SimpleLoadMoreView extends FrameLayout implements ILoadMoreView {

    private TextView tvHitText;
    private SpinKitView spinKitView;
    private View view;

    public SimpleLoadMoreView(Context context) {
        this(context, null);
    }

    public SimpleLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = inflate(context, R.layout.default_load_more, this);
        tvHitText = (TextView) view.findViewById(R.id.tv_hit_content);
        spinKitView = (SpinKitView) view.findViewById(R.id.spin_kit);

    }


    @Override
    public void reset() {
        spinKitView.setVisibility(INVISIBLE);
        tvHitText.setVisibility(INVISIBLE);
        tvHitText.setText(getContext().getString(R.string.footer_loading));
    }

    @Override
    public void loading() {
        spinKitView.setVisibility(VISIBLE);
        tvHitText.setVisibility(VISIBLE);
        tvHitText.setText(getContext().getString(R.string.footer_loading));
    }

    @Override
    public void loadComplete() {
        spinKitView.setVisibility(INVISIBLE);
        tvHitText.setVisibility(VISIBLE);
        tvHitText.setText(getContext().getString(R.string.footer_load_complete));

    }

    @Override
    public void loadFail() {
        spinKitView.setVisibility(INVISIBLE);
        tvHitText.setVisibility(VISIBLE);
        tvHitText.setText(getContext().getString(R.string.footer_load_fail));
    }

    @Override
    public void loadNothing() {
        spinKitView.setVisibility(INVISIBLE);
        tvHitText.setVisibility(VISIBLE);
        tvHitText.setText(getContext().getString(R.string.footer_load_end));
    }

    @Override
    public View getCanClickFailView() {
        return view;
    }
}
