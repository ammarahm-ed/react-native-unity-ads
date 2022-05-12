package com.reactnativeunityads;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

import android.util.Log;

public class UnityAdsManager extends ReactContextBaseJavaModule implements IUnityAdsInitializationListener {
  String unityGameID;
  boolean testMode = true;
  boolean isReady = false;
  Promise initializePromise;
  ReactApplicationContext reactContext;

  @Override
  public String getName(){
    return "UnityAdsManager";
  }

  UnityAdsManager(ReactApplicationContext context){
    super(context);
    reactContext = context;
  }

  @ReactMethod
  public void initialize(String gameId, Boolean test, Promise p){
    initializePromise = p;
    testMode = test;
    unityGameID = gameId;
    if (UnityAds.isInitialized()) {
      initializePromise.resolve((true));
      return;
    }
    UnityAds.initialize(reactContext.getApplicationContext(), unityGameID, testMode,this);
  }

  @ReactMethod
  public void isInitialized(Promise p){
    p.resolve(UnityAds.isInitialized());
  }


  @Override
  public void onInitializationComplete() {
    isReady = true;
    if (initializePromise != null) {
      initializePromise.resolve(true);
    }
  }

  @Override
  public void onInitializationFailed(UnityAds.UnityAdsInitializationError unityAdsInitializationError, String s) {
    isReady = false;

    if (initializePromise != null) {
      initializePromise.resolve(false);
    }
    Log.e("UnityAds", "onInitializationFailed: " + s);
  }
}
