package com.SelfTourGuide.bangkok.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.SelfTourGuide.bangkok.util.LogUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by Frank on 2015/12/15.
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    protected abstract void injectContext();

    protected abstract int getLayoutResource();

    protected abstract void initVariables(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void handleUIEvent();

    protected TextView mUILabelSwipeContent;
    protected ProgressBar mUISwipeProgress;
    protected ImageView mUIImageSwipeIndicator;
    protected Boolean mLastState = null;
    private Boolean mLastLoadState = null;
    protected ProgressBar mUILoadingProgress;
    protected ImageView mUIImageLoadingIndicator;
    protected TextView mUILabelLoadingContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        injectContext();
        View rootView = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, rootView);
        initVariables(savedInstanceState);
        initData(savedInstanceState);
        handleUIEvent();
        LogUtil.e("当前Fragment>>" + getFragmentName());
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * Start Activity with Bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Start Activity without Bundle
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        startActivity(clazz, null);
    }


    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd(getFragmentName());
//        StatService.onPageEnd(getActivity(), getFragmentName());
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart(getFragmentName());
//        StatService.onPageStart(getActivity(), getFragmentName());
    }

    public String getFragmentName() {
        String contextString = this.toString();
        try {
            return contextString.substring(0, contextString.indexOf("{"));
        } catch (Exception e) {
            return contextString;
        }
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



}
