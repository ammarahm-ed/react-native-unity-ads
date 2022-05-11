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

public class UnityAdsModule extends ReactContextBaseJavaModule implements IUnityAdsInitializationListener {

  String unityGameID;
  String unityPlacementID;
  boolean testMode = true;
  boolean isReady = false;
  boolean isAdLoaded = false;
  Promise showPromise;
  Promise loadPromise;
  Promise initializePromise;

  ReactApplicationContext reactContext;

  @Override
  public String getName(){
    return "UnityAds";
  }
  UnityAdsModule(ReactApplicationContext context){
    super(context);
    reactContext = context;
  }

  private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
    @Override
    public void onUnityAdsAdLoaded(String placementId) {
      isAdLoaded = true;
      if (loadPromise != null) {
        loadPromise.resolve(true);
        loadPromise = null;
      }
    }

    @Override
    public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
      isAdLoaded = false;
      if (loadPromise != null) {
        loadPromise.resolve(false);
        loadPromise = null;
      }
    }
  };

  private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
    @Override
    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
      isAdLoaded = false;
      if (showPromise != null) {
        showPromise.resolve(false);
        showPromise = null;
      }
    }

    @Override
    public void onUnityAdsShowStart(String placementId) {
      Log.v("UnityAds", "onUnityAdsShowStart: " + placementId);
    }

    @Override
    public void onUnityAdsShowClick(String placementId) {
      if (showPromise != null) {
        showPromise.resolve(true);
        showPromise = null;
      }
    }

    @Override
    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
      isAdLoaded = false;
      if (showPromise != null) {
        showPromise.resolve(true);
        showPromise = null;
      }
    }
  };



  @ReactMethod
  public void initialize(String gameId, Boolean test, Promise p){
    initializePromise = p;
    testMode = test;
    unityGameID = gameId;
    UnityAds.initialize(reactContext.getApplicationContext(), unityGameID, testMode,this);
  }

  @ReactMethod
  public void loadAd(String placementId, Promise p){
    loadPromise = p;
    unityPlacementID = placementId;
    UnityAds.load(unityPlacementID, loadListener);
  }

  @ReactMethod
  public void isInitialized(Promise p){
    p.resolve(isReady);
  }

  @ReactMethod
  public void showAd(Promise p){
    showPromise = p;
    if(isAdLoaded){
      UnityAds.show(reactContext.getCurrentActivity(), unityPlacementID, new UnityAdsShowOptions(), showListener);
    }else{
      showPromise.resolve(false);
      showPromise = null;
    }
  }

  public void cleanUp(){
    showPromise = null;
    loadPromise = null;
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
