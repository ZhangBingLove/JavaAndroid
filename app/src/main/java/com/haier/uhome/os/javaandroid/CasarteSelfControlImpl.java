package com.haier.uhome.os.javaandroid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.unilife.common.entities.UMDB;
import com.unilife.common.ui.UMControlServiceImpl;

public class CasarteSelfControlImpl implements ISelfControl {

    private static CasarteSelfControlImpl instance;

    public static CasarteSelfControlImpl getInstance() {
        if (instance == null) {
            instance = new CasarteSelfControlImpl();
        }
        return instance;

    }

    private UMControlServiceImpl controlServiceIml;

    private static final String TAG = "CasarteSelfControlImpl";

    private Context context;

    private static final String ACTION = "com.unilife.fridge.haierbase.settings";

    @Override
    public void init(Context context) {
        this.context = context;
        Log.d(TAG, "init()");
    }

    /**
     * 实时获取设备本机控制APP的数据
     *
     * @param context
     * @param recvListener
     */
    public void RefreshData(Context context, final UMControlServiceImpl.UIRecvListener recvListener) {
        this.context = context;
        controlServiceIml = new UMControlServiceImpl(context);
        controlServiceIml.addControlServiceConnListener(new UMControlServiceImpl.ControlServiceConnListener() {
            @Override
            public void onConnect() {
                //服务绑定
                Log.d(TAG, "服务绑定");
            }

            @Override
            public void onDisconnect() {
                //服务解绑
                Log.d(TAG, "服务解绑");
            }
        });

        //绑定服务
        controlServiceIml.startCall(ACTION);

        controlServiceIml.setRecvListener(new UMControlServiceImpl.UIRecvListener() {
            @Override
            public void onRecvNewUMDB(UMDB umdb) {
                recvListener.onRecvNewUMDB(umdb);
                //冰箱状态改变回调，可用于触发刷新UI
                //获取某个属性的方法 如，速冷状态 一般是0关1开
                //mControlServiceIml.getValue(FridgeDB.FastChill);
                Log.d(TAG, "冰箱状态改变回调");
                Log.d(TAG, "umdb = " + umdb.toString());
            }
        });

    }

    /**
     * 解绑服务和反注册广播
     */
    public void stopCall() {
        if (controlServiceIml != null) {
            controlServiceIml.stopCall();
        }
    }

    /**
     * 拉起本机控制App的控制详情页
     */
    public void startSelfCotrolActivity(String action) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(action);
        context.startActivity(intent);
    }

}
