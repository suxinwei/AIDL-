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
import android.widget.Toast;

import com.alibaba.alipay.ThirdPartPayAction;
import com.alibaba.alipay.ThirdPartPayResult;

import androidx.appcompat.app.AppCompatActivity;

public class Client2Activity extends AppCompatActivity {

    private static final String TAG = "Client MainActivity";

    private AlipayConnection alipayConnection;
    private boolean isBind;
    private ThirdPartPayAction thirdPartPayAction;
    private TextView tvTitle;
    private TextView tvMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        bindAlipayService();
        initView();
    }

    /**
     * 綁定支付寶的服務，在现在开发中，其实这部分动作是由支付宝的 SDK 完成
     */
    private void bindAlipayService() {

        Intent intent = new Intent();
        intent.setAction("com.alibaba.alipay.THIRD_PART_PAY");//复制服务端的action 和包名,建議提取至全局
        intent.setPackage("com.example.suxinwei.androidtest");

        alipayConnection = new AlipayConnection();

        isBind = bindService(intent, alipayConnection, BIND_AUTO_CREATE);
        startForegroundService(intent);

        Log.d(TAG, "Client bind service ....");
    }

    private void initView() {

        tvTitle = findViewById(R.id.tvTitle);
        tvMoney = findViewById(R.id.tvMoney);

        findViewById(R.id.btnRecharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (thirdPartPayAction != null) {
                    //进行充值
                    try {
                        thirdPartPayAction.requestPay("充值100QB", 100, new PayCallBack());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }

    private class PayCallBack extends ThirdPartPayResult.Stub {

        @Override
        public void onPaySuccess() throws RemoteException {
            //支付成功，修改 UI内容
            //实际上是取修改数据库，其实支付宝是通过回调URL地址，直接通知我们的后台服务器，后台返回我们客户端进行通知
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvMoney.setText("100");

                    Log.d(TAG, "Client onPaySuccess() -----充值成功 !");

                    Toast.makeText(getBaseContext(), "充值成功！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onPayFaild(int errorCode, String msg) throws RemoteException {

            Log.d(TAG, "Client onPayFaild() ----- errorCode:" + errorCode + "---msg:" + msg);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "充值失敗！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class AlipayConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            //获取支付宝的服务
            thirdPartPayAction = ThirdPartPayAction.Stub.asInterface(service);
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
        if (isBind && alipayConnection != null) {
            unbindService(alipayConnection);
            Log.d(TAG, "Client unbind service ....");
            alipayConnection = null;
            isBind = false;
        }
    }
}