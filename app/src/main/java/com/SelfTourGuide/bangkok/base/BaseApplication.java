/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.SelfTourGuide.bangkok.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.SelfTourGuide.bangkok.R;
import com.SelfTourGuide.bangkok.util.Advertisement;
import com.millennialmedia.MMException;
import com.millennialmedia.MMSDK;



public class BaseApplication extends MultiDexApplication {

    private static final String TAG = com.SelfTourGuide.bangkok.base.BaseApplication.class.getSimpleName();

    private static com.SelfTourGuide.bangkok.base.BaseApplication INSTANCE;

    public static com.SelfTourGuide.bangkok.base.BaseApplication getInstance() {
        return INSTANCE;
    }
    Context context;
    @Override
    public void onCreate() {
        super.onCreate();

            if (INSTANCE == null) {
                INSTANCE = this;
            }
       /* MediationMetaData mediationMetaData = new MediationMetaData(this);
        mediationMetaData.setName("Example mediation network");
        mediationMetaData.setVersion("1.2.3");
        mediationMetaData.commit();*/

        try {
            MMSDK.initialize(this);
        }catch (MMException e){
            Log.e(TAG, "初始化MM广告SDK错误");
        }

        try {
            Advertisement.getInstance().init(this, getString(R.string.ad_unit_id),getString(R.string.ad_unit_id));
        } catch (Exception e) {
            Log.e(TAG, "初始化广告SDK错误");
        }

    }




}
