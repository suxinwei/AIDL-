//
// Created by 80288791 on 2020/4/16.
//

#include <jni.h>
#include <android/log.h>

static const char *kTAG = "jni_log";
#define LOGI(...) \
  ((void)__android_log_print(ANDROID_LOG_INFO, kTAG, __VA_ARGS__))
#define LOGD(...) \
  ((void)__android_log_print(ANDROID_LOG_DEBUG, kTAG, __VA_ARGS__))
#define LOGW(...) \
  ((void)__android_log_print(ANDROID_LOG_WARN, kTAG, __VA_ARGS__))
#define LOGE(...) \
  ((void)__android_log_print(ANDROID_LOG_ERROR, kTAG, __VA_ARGS__))

JNIEXPORT jobject JNICALL Java_com_suxinwei_demo_ClientActivity_test
        (JNIEnv *env, jobject thiz, jobject person) {
    jclass pClass = (*env)->GetObjectClass(env, person);
    jfieldID ageId = (*env)->GetFieldID(env, pClass, "age", "I");
    jfieldID nameId = (*env)->GetFieldID(env, pClass, "name", "Ljava/lang/String;");
    jint age = (*env)->GetIntField(env, person, ageId);
    jstring name = (*env)->GetObjectField(env, person, nameId);
    char *string = (char *) (*env)->GetStringUTFChars(env, name, NULL);
    LOGE("收到的age=%d,name=%s", age, string);
//    LOGE("收到的age=%d", age);
//    jclass clazz = (*env)->FindClass(env, "com/suxinwei/demo/ClientActivity$Person");
    jobject newPerson = (*env)->AllocObject(env, pClass);
    (*env)->SetIntField(env, newPerson, ageId, 2);
    (*env)->SetObjectField(env, newPerson, nameId, (*env)->NewStringUTF(env, "bbb"));
    return newPerson;
}
