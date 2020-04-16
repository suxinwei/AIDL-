// ThirdPartPayResult.aidl
package com.alibaba.alipay;

// Declare any non-default types here with import statements

interface ThirdPartPayResult {

    void onPaySuccess();

    void onPayFaild(int errorCode, String msg);
}
