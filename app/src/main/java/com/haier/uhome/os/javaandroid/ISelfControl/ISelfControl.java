package com.haier.uhome.os.javaandroid.ISelfControl;

import android.content.Context;

import com.haier.uhome.os.javaandroid.RefreshListener;

/**
 * @author guok
 */
public interface ISelfControl {
    void init(Context context);
    void startCall(RefreshListener refreshListener);
    void stopCall();
    void openControl();
}
