// NetCallback.aidl
package com.heytap.smarthome.domain.net.cloudstorage;

// Declare any non-default types here with import statements

interface NetCallback {

   void onSuccess(int type, int id, int code, String jsonResponse);

   void onFailed(int type, int id, int code, String errorMsg);
}
