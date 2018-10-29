package com.SelfTourGuide.bangkok.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.SelfTourGuide.bangkok.MessageEvents;
import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.util.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Pattern;



/**
 * Created by ${张志珍} on 2016/12/2217:00.
 * Super Mario RunDemo
 * com.superguide.supermariorunsecond.base
 */

public class BaseActivity extends Activity {
    private final String SDcardPath = Environment.getExternalStorageDirectory().getPath()+"/";

    private ConnectionChangeReceiver receiver;  // 广播
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
//        StatService.setDebugOn(true);
        MobclickAgent.enableEncrypt(true);
        //该接口默认参数是true，即采集mac地址，但如果开发者需要在googleplay发布，考虑到审核风险，可以调用该接口，参数设置为false就不会采集mac地址。
        MobclickAgent.setCheckDevice(false);
//        setStatusBar();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.setPriority(1000);
        receiver=new ConnectionChangeReceiver();
        registerReceiver(receiver, filter);
        /*ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
            @Override public void onAvailable(Network network) {
                super.onAvailable(network);

            }
        });*/
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        EventBus.getDefault().unregister(this);
    }

    //接受广播 和网络判断
    public  class ConnectionChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                // 断开
                EventBus.getDefault().post(new MessageEvents(false));
            } else {
                EventBus.getDefault().post(new MessageEvents(true));
            }
        }


    }




    protected void toast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }

    public static boolean isNumeric1(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 隐藏软键盘
     * hideSoftInputView
     * @param
     * @return void
     * @throws
     * @Title: hideSoftInputView
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white));
    }

    // 使用Intent实现activity跳转
    public void goActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param activity
     *            你想要跳转的activity
     * @param paName
     *            值的名称
     * @param param
     *            值
     * @param pName
     *            值的名称
     * @param pname
     *            值
     * @param paaName
     *            值的名称
     * @param panaame
     *         值
     */
    public void goActivity(Class<?> activity, String paName, String param,
                           String pName, String pname, String paaName, String panaame) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(paName, param);
        intent.putExtra(pName, pname);
        intent.putExtra(paaName, panaame);
        startActivity(intent);
    }


    /**
     * 弹出消息提示层。
     *
     * @param message
     */
    public void AlertToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        // 页面埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
//        StatService.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        // 页面结束埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
//        StatService.onPause(this);
    }



}
