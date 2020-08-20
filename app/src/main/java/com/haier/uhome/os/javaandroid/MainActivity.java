package com.haier.uhome.os.javaandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.haier.uhome.os.javaandroid.ISelfControl.CasarteSelfControlImpl;
import com.haier.uhome.os.javaandroid.ISelfControl.ISelfControl;
import com.haier.uhome.os.javaandroid.generics.GenericClass;
import com.haier.uhome.os.javaandroid.generics.GenericsInterfaceImp;
import com.haier.uhome.os.javaandroid.uitls.Logger;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ISelfControl mISelfControl;
    private static final String TAG = "zhangbing";
    private final static int PERMISSIONS_OK = 10001;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private Button btnClick;
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(TAG, "冰箱状态改变回调");
        if (!checkPermissionAllGranted(PERMISSIONS_STORAGE)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    PERMISSIONS_STORAGE, PERMISSIONS_OK);
        }
        setContentView(R.layout.activity_main);
        btnClick = findViewById(R.id.btn_click);
        tvText = findViewById(R.id.tv_text);
        btnClick.setOnClickListener((view -> {
            User user = new User.Build().name("张三").age("10").build();
            Logger.e(TAG, user.toString());
        }));

    }


    private void testGenericClass() {
        GenericClass<String> genericClass = new GenericClass<>();
        genericClass.setData("测试泛型");
        String data = genericClass.getData();
        Logger.e(data);

        GenericsInterfaceImp<String> genericsInterfaceImp = new GenericsInterfaceImp<>();
        genericsInterfaceImp.setdata("测试泛型");
        String getdata = genericsInterfaceImp.getdata();
        Logger.e(getdata);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_OK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                break;
            default:
                break;
        }
    }


    /**
     * 测试本机控制
     */
    private void testSelfControl() {
        mISelfControl = new CasarteSelfControlImpl();
        mISelfControl.init(this);
        mISelfControl.startCall(new RefreshListener() {
            @Override
            public void onRefresh(Map<String, String> data) {
                Logger.d(TAG, "冰箱状态改变回调");
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    String mapKey = entry.getKey();
                    String mapValue = entry.getValue();
                    Logger.d(TAG, mapKey + " : " + mapValue);
                }
            }
        });
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
        StringBuilder a = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            Log.e(TAG, mapKey + " : " + mapValue);
            a.append(mapKey).append(": ").append(mapValue).append(" ; ");
        }
        Logger.e(TAG, a.toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mISelfControl.stopCall();
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}