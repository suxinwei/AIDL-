// NetManager.aidl
package com.heytap.smarthome.domain.net.cloudstorage;

import com.heytap.smarthome.domain.net.cloudstorage.NetCallback;

interface NetManager {

     void access(long size, int avalidDays, String payCode, NetCallback callback);

     void accessDeviceUse(long size, int avalidDays, String deviceId, NetCallback callback);

     void disableDeviceUse(String deviceId, NetCallback callback);

     void getFileList(String authorType, String authorId, String fileType, int start, int size, String startTime, String endTime, NetCallback callback);

     void getSourceFileDetailList(in Map fileInfoMap, NetCallback callback);

     void uploadFile(String filePath, NetCallback callback);

     void uploadImage(String filePath, NetCallback callback);

     void deleteFileList(in Map fileInfoMap, NetCallback callback);

     void initMultipart(String fileName, int fileSize, NetCallback callback);

     void uploadPart(String uploadId, String bucketName, String key, int partNumber, in byte[] data, NetCallback callback);

     void completeMultipart(String uploadId, String bucketName, String key, String fileName, int fileSize, in Map partETagMap, NetCallback callback);
}
