package com.haier.uhome.os.javaandroid.ISelfControl;

import android.content.Context;
import android.content.Intent;

import com.haier.uhome.os.javaandroid.uitls.Logger;
import com.haier.uhome.os.javaandroid.RefreshListener;
import com.unilife.common.entities.UMDB;
import com.unilife.common.ui.UMControlServiceImpl;

import java.util.Map;

/**
 * @author guok
 */
public class CasarteSelfControlImpl implements ISelfControl {

    private UMControlServiceImpl controlServiceIml;

    private static final String TAG = "CasarteSelfControlImpl";

    private static final String ACTION = "com.unilife.fridge.haierbase.settings";

    private Context context;
    private Map<String, String> data;

    @Override
    public void init(Context context) {
        this.context = context;
        controlServiceIml = new UMControlServiceImpl(context);
        controlServiceIml.addControlServiceConnListener(new UMControlServiceImpl.ControlServiceConnListener() {
            @Override
            public void onConnect() {
                //服务绑定
                Logger.d(TAG, "服务绑定");
            }

            @Override
            public void onDisconnect() {
                //服务解绑
                Logger.d(TAG, "服务解绑");
            }
        });

        //绑定服务
        controlServiceIml.startCall(ACTION);
    }

    /**
     * 实时获取设备本机控制APP的数据
     */
    @Override
    public void startCall(final RefreshListener refreshListener) {
        if (controlServiceIml == null) {
            return;
        }
        controlServiceIml.setRecvListener(new UMControlServiceImpl.UIRecvListener() {
            @Override
            public void onRecvNewUMDB(UMDB umdb) {

                //冰箱状态改变回调，可用于触发刷新UI
                //获取某个属性的方法 如，速冷状态 一般是0关1开
                //mControlServiceIml.getValue(FridgeDB.FastChill);
                Logger.d(TAG, "冰箱状态改变回调");
                if (umdb == null) {
                    return;
                }
                data = umdb.getDb();
                refreshListener.onRefresh(data);

                for (Map.Entry<String, String> entry : data.entrySet()) {
                    String mapKey = entry.getKey();
                    String mapValue = entry.getValue();
                    Logger.d(TAG, mapKey + " : " + mapValue);
                }

            }
        });
    }

    /**
     * 解绑服务和反注册广播
     */
    @Override
    public void stopCall() {
        if (controlServiceIml != null) {
            controlServiceIml.stopCall();
        }
    }

    /**
     * 打开本机控制的设备控制页面
     */
    @Override
    public void openControl() {
        if (context == null) {
            return;
        }
        try {
            Intent intent = new Intent(ACTION);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
