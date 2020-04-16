package com.suxinwei.demo;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.heytap.smarthome.domain.net.cloudstorage.NetCallback;
import com.heytap.smarthome.domain.net.cloudstorage.NetManager;

import androidx.appcompat.app.AppCompatActivity;

public class ClientActivity extends AppCompatActivity {

    private static final String TAG = "Client MainActivity";

    private NetConnection mNetConnection;
    private boolean isBind;
    private NetManager mNetManager;
    private TextView tvTitle;
    private TextView tvMoney;

    static {
        System.loadLibrary("jni_test");
    }

    class Person {
        public String name;
        public int age;

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


    public native Person test(Person person);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        bindNetService();
        initView();
    }

    private void bindNetService() {

        Intent intent = new Intent();
        intent.setAction("com.heytap.smarthome.CLOUD_STORAGE");
        intent.setPackage("com.example.suxinwei.androidtest");


        mNetConnection = new NetConnection();

        isBind = bindService(intent, mNetConnection, BIND_AUTO_CREATE);

        Log.d(TAG, "Client bind service ....");
    }

    private void initView() {

        tvTitle = findViewById(R.id.tvTitle);
        tvMoney = findViewById(R.id.tvMoney);

        findViewById(R.id.btnRecharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Person person = new Person();
                person.name = "aaa";
                person.age = 1;
                Person newPerson = test(person);

                Log.e("jni", "oldPerson:" + person.toString());
                if (newPerson != null) {
                    Log.e("jni", "newPerson:" + newPerson.toString());
                }

//                if (mNetManager != null) {
//                    try {
//                        mNetManager.access(1, 2, "", new NetCallbackImpl());
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//
//                }

            }
        });

    }

    private class NetCallbackImpl extends NetCallback.Stub {

        @Override
        public void onSuccess(int type, int id, int code, String jsonResponse) throws RemoteException {
            Log.e("sxw", "onSuccess");
        }

        @Override
        public void onFailed(int type, int id, int code, String errorMsg) throws RemoteException {
            Log.e("sxw", "onFailed");
        }
    }

    private class NetConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mNetManager = NetManager.Stub.asInterface(service);
            Log.d(TAG, "Client onServiceConnected----->" + name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Client onServiceDisconnected----->" + name);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBind && mNetConnection != null) {
            unbindService(mNetConnection);
            Log.d(TAG, "Client unbind service ....");
            mNetConnection = null;
            isBind = false;
        }
    }
}