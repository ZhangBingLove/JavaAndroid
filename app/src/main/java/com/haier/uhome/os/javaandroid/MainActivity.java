package com.haier.uhome.os.javaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.unilife.common.entities.UMDB;
import com.unilife.common.ui.UMControlServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ISelfControl mISelfControl;
    private static final String TAG = "zhangbing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnClick = findViewById(R.id.btn_click);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mISelfControl.init(MainActivity.this);
                testMap();
            }
        });

        CasarteSelfControlImpl casarteSelfControl = CasarteSelfControlImpl.getInstance();
        casarteSelfControl.RefreshData(this, new UMControlServiceImpl.UIRecvListener() {
            @Override
            public void onRecvNewUMDB(UMDB umdb) {
                Log.d(TAG, "冰箱状态改变回调");
                Log.d(TAG, "umdb = " + umdb.toString());
            }
        });
        selfControl(casarteSelfControl);

    }

    /**
     * 最常见也是大多数情况下用的最多的，一般在键值对都需要使用
     */
    private void testMap() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("熊大", "棕色");
        map.put("熊二", null);
        map.put("熊三", "红色");
        map.put("熊四", "黑色");
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            Log.e(TAG, mapKey + " : " + mapValue);
            sb.append(mapKey).append(":").append(mapValue).append(" ; ");
        }
        Log.e(TAG, sb.toString());

    }


    /**
     * 设置是否开启本机控制
     */
    public void selfControl(ISelfControl iSelfControl) {
        this.mISelfControl = iSelfControl;
    }
}