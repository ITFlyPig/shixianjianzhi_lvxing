package com.SelfTourGuide.singapore.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.SelfTourGuide.singapore.base.BaseApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


public class PreferenceUtil {
    private static final String TAG = PreferenceUtil.class.getSimpleName();

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;


    /**
     * 保存在手机里面的默认文件名
     */
    public static final String FILE_NAME = "share_data";

    public static final PreferenceUtil getInstance() {
        checkInit();
        return PreferenceInstance.INSTANCE;
    }

    private static class PreferenceInstance {
        private static final PreferenceUtil INSTANCE = new PreferenceUtil();
    }


    private static void checkInit() {
        if (null != preferences && null != editor) {
            init();
        }
    }


    private static void init() {
        preferences = BaseApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param value
     * @return
     */
    public Object get(String key, Object value) {
        if (value instanceof String) {
            return preferences.getString(key, (String) value);
        } else if (value instanceof Integer) {
            return preferences.getInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            return preferences.getBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            return preferences.getFloat(key, (Float) value);
        } else if (value instanceof Long) {
            return preferences.getLong(key, (Long) value);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public void clearAll() {
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return preferences.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author 谢修谱
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        private static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}