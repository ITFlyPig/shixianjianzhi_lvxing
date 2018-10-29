package com.SelfTourGuide.bangkok.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SelfTourGuide.bangkok.MessageEvents;
import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.base.BaseActivity;
import com.SelfTourGuide.bangkok.util.NetUtils;
import com.SelfTourGuide.bangkok.util.PayStatusUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IntroductionActivity extends BaseActivity {
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.ll_back)
    LinearLayout ll_back;
    @Bind(R.id.ll_empty_img)
    LinearLayout mUIllempty;
    private String introduction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        introduction = getIntent().getExtras().getString("content");
        ButterKnife.bind(this);
        initData();
    }
    private void initData(){
        if(NetUtils.isNetworkConnected(com.SelfTourGuide.bangkok.ui.IntroductionActivity.this) || PayStatusUtil.isSubAvailable()) {
            mUIllempty.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(introduction)){
                content.setText(introduction);
            }else{
//                AlertToast("jianjiewei");
            }

        }else{
            content.setVisibility(View.GONE);
            mUIllempty.setVisibility(View.VISIBLE);
        }
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvents event) {
        /* Do something */

    };


}
