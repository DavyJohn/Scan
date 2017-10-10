package com.zzh.fq;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 腾翔信息 on 2017/10/9.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private Toolbar mToolBar;
    private RelativeLayout mNoContentLayout;
    private RelativeLayout mContentLayout;
    private ToolBarX mToolBarX;
    private Unbinder unbinder;
    /**
     * 提示消息
     */
    private Toast toast;
    /**
     *
     */
    protected Context mContext;
    protected int screenwidth;
    protected int densityDpi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_main_layout);
        mContext = this;
        initView();
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        View view = getLayoutInflater().inflate(getLayoutId(), mContentLayout, false); //IOC 控制反转：在父类中调用子类的反转
        mContentLayout.addView(view);
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mContentLayout = (RelativeLayout) findViewById(R.id.content);
        // 获取手机分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenwidth = dm.widthPixels;
        densityDpi = dm.densityDpi;
    }

    public abstract int getLayoutId();
    //如何选择toolbar
    public ToolBarX getToolBar() {
        if (null == mToolBarX) {
            mToolBarX = new ToolBarX(mToolBar, this);
        }
        return mToolBarX;
    }

    public void hasToolBar(boolean flag) {
        if (!flag) {
            mToolBar.setVisibility(View.GONE);
        } else {
            mToolBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        finish();
        overridePendingTransition(R.anim.anmi_in_right_left, R.anim.anmi_out_right_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anmi_in_right_left, R.anim.anmi_out_right_left);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.anim_in_left_right, R.anim.anim_out_left_right);
    }

    public void finishAndTransition(Boolean flag) {
        super.finish();
        if (flag) {
            overridePendingTransition(R.anim.anim_in_left_right, R.anim.anim_out_left_right);
        }

    }

    /**
     * 展示Toast消息。
     *
     * @param message
     *            消息内容
     */
    public synchronized void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        }
        if (!TextUtils.isEmpty(message)) {
            toast.setText(message);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAndTransition(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
