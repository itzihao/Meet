package com.eno.framework.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.StringRes;
import androidx.core.content.SharedPreferencesCompat;

/**
 * Created by Hao on 2018/3/17.
 * Email:itzihao@sina.com
 */

public class SPUtils {

    private static final String TAG = "SPUtils";

    private static SPUtils sSPUtils;

    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;
    private static SharedPreferencesCompat.EditorCompat editorCompat = SharedPreferencesCompat.EditorCompat.getInstance();

    private static final String DEFAULT_SP_NAME = "MeetData";
    private static final int DEFAULT_INT = 0;
    private static final float DEFAULT_FLOAT = 0.0f;
    private static final String DEFAULT_STRING = "";
    private static final boolean DEFAULT_BOOLEAN = false;
    private static final Set<String> DEFAULT_STRING_SET = new HashSet<>(0);

    private static String mCurSPName = DEFAULT_SP_NAME;
    private static Context mContext;

    private SPUtils(Context context) {
        this(context, DEFAULT_SP_NAME);
    }

    @SuppressLint("CommitPrefEdits")
    private SPUtils(Context context, String spName) {
        mContext = context.getApplicationContext();
        sSharedPreferences = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        sEditor = sSharedPreferences.edit();
        mCurSPName = spName;
        LogUtils.logE(TAG, "SPUtils: " + mCurSPName);
    }

    public static SPUtils init(Context context) {
        if (sSPUtils == null || !mCurSPName.equals(DEFAULT_SP_NAME)) {
            sSPUtils = new SPUtils(context);
        }
        return sSPUtils;
    }

    public static SPUtils init(Context context, String spName) {
        if (sSPUtils == null) {
            sSPUtils = new SPUtils(context, spName);
        } else if (!spName.equals(mCurSPName)) {
            sSPUtils = new SPUtils(context, spName);
        }
        return sSPUtils;
    }

    public static SPUtils put(@StringRes int key, Object value) {
        return put(mContext.getString(key), value);
    }

    public static SPUtils put(String key, Object value) {

        if (value instanceof String) {
            sEditor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            sEditor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            sEditor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            sEditor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            sEditor.putLong(key, (Long) value);
        } else {
            sEditor.putString(key, value.toString());
        }
        editorCompat.apply(sEditor);
        return sSPUtils;
    }

    public static Object get(@StringRes int key, Object defaultObject) {
        return get(mContext.getString(key), defaultObject);
    }

    public static Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sSharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sSharedPreferences.getInt(key, (int) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sSharedPreferences.getBoolean(key, (boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sSharedPreferences.getFloat(key, (float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sSharedPreferences.getLong(key, (long) defaultObject);
        }
        return null;
    }

    public static SPUtils putInt(String key, int value) {
        sEditor.putInt(key, value);
        editorCompat.apply(sEditor);
        return sSPUtils;
    }

    public static SPUtils putInt(@StringRes int key, int value) {
        return putInt(mContext.getString(key), value);
    }

    public static int getInt(@StringRes int key) {
        return getInt(mContext.getString(key));
    }

    public static int getInt(@StringRes int key, int defValue) {
        return getInt(mContext.getString(key), defValue);
    }

    public static int getInt(String key) {
        return getInt(key, DEFAULT_INT);
    }


    public static int getInt(String key, int defValue) {
        return sSharedPreferences.getInt(key, defValue);
    }

    public static SPUtils putFloat(@StringRes int key, float value) {
        return putFloat(mContext.getString(key), value);
    }

    public static SPUtils putFloat(String key, float value) {
        sEditor.putFloat(key, value);
        editorCompat.apply(sEditor);
        return sSPUtils;
    }

    public static float getFloat(String key) {
        return getFloat(key, DEFAULT_FLOAT);
    }

    public static float getFloat(String key, float defValue) {
        return sSharedPreferences.getFloat(key, defValue);
    }

    public static float getFloat(@StringRes int key) {
        return getFloat(mContext.getString(key));
    }

    public static float getFloat(@StringRes int key, float defValue) {
        return getFloat(mContext.getString(key), defValue);
    }

    public static SPUtils putLong(@StringRes int key, long value) {
        return putLong(mContext.getString(key), value);
    }

    public static SPUtils putLong(String key, long value) {
        sEditor.putLong(key, value);
        editorCompat.apply(sEditor);
        return sSPUtils;
    }

    public static long getLong(String key) {
        return getLong(key, DEFAULT_INT);
    }

    public static long getLong(String key, long defValue) {
        return sSharedPreferences.getLong(key, defValue);
    }

    public static long getLong(@StringRes int key) {
        return getLong(mContext.getString(key));
    }

    public static long getLong(@StringRes int key, long defValue) {
        return getLong(mContext.getString(key), defValue);
    }

    public static SPUtils putString(@StringRes int key, String value) {
        return putString(mContext.getString(key), value);
    }

    public static SPUtils putString(String key, String value) {
        sEditor.putString(key, value);
        editorCompat.apply(sEditor);
        return sSPUtils;
    }

    public static String getString(String key) {
        return getString(key, DEFAULT_STRING);
    }

    public static String getString(String key, String defValue) {
        return sSharedPreferences.getString(key, defValue);
    }

    public static String getString(@StringRes int key) {
        return getString(mContext.getString(key), DEFAULT_STRING);
    }

    public static String getString(@StringRes int key, String defValue) {
        return getString(mContext.getString(key), defValue);
    }

    public static SPUtils putBoolean(@StringRes int key, boolean value) {
        return putBoolean(mContext.getString(key), value);
    }

    public static SPUtils putBoolean(String key, boolean value) {
        sEditor.putBoolean(key, value);
        editorCompat.apply(sEditor);
        return sSPUtils;
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, DEFAULT_BOOLEAN);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sSharedPreferences.getBoolean(key, defValue);
    }

    public static boolean getBoolean(@StringRes int key) {
        return getBoolean(mContext.getString(key));
    }

    public static boolean getBoolean(@StringRes int key, boolean defValue) {
        return getBoolean(mContext.getString(key), defValue);
    }

    public static SPUtils putStringSet(@StringRes int key, Set<String> value) {
        return putStringSet(mContext.getString(key), value);
    }

    public static SPUtils putStringSet(String key, Set<String> value) {
        sEditor.putStringSet(key, value);
        editorCompat.apply(sEditor);
        return sSPUtils;
    }

    public static Set<String> getStringSet(String key) {
        return getStringSet(key, DEFAULT_STRING_SET);
    }


    public static Set<String> getStringSet(String key, Set<String> defValue) {
        return sSharedPreferences.getStringSet(key, defValue);
    }

    public static Set<String> getStringSet(@StringRes int key) {
        return getStringSet(mContext.getString(key));
    }

    public static Set<String> getStringSet(@StringRes int key, Set<String> defValue) {
        return getStringSet(mContext.getString(key), defValue);
    }


    public static boolean contains(String key) {
        return sSharedPreferences.contains(key);
    }

    public static boolean contains(@StringRes int key) {
        return contains(mContext.getString(key));
    }

    public static Map<String, ?> getAll() {
        return sSharedPreferences.getAll();
    }

    public static SPUtils remove(@StringRes int key) {
        return remove(mContext.getString(key));
    }

    public static SPUtils remove(String key) {
        sEditor.remove(key);
        editorCompat.apply(sEditor);
        return sSPUtils;
    }

    public static SPUtils clear() {
        sEditor.clear();
        editorCompat.apply(sEditor);
        return sSPUtils;
    }

    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param object
     */


    public static void putObject(String key, Object object) {
        Gson gson = new GsonBuilder().create();
        sEditor.putString(key, gson.toJson(object));
        sEditor.apply();
    }

    /**
     * 获取sp保存的对象
     *
     * @param key
     * @param
     * @return
     */

    public static Object getObject(String key, Class<?> classOfT) {
        try {
            Gson gson = new GsonBuilder().create();
            String json = sSharedPreferences.getString(key, null);

            Object o = gson.fromJson(json, classOfT);
            return o;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 保存List
     *
     * @param key
     * @param datalist
     */
    public <T> void setDataList(String key, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        sEditor.clear();
        sEditor.putString(key, strJson);
        sEditor.commit();
    }

    /**
     * 获取List
     *
     * @param key
     * @return
     */
    public <T> List<T> getDataList(String key) {
        List<T> datalist = new ArrayList<>();
        String strJson = sSharedPreferences.getString(key, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }

}
