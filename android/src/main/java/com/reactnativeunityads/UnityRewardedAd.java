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

public class UnityRewardedAd extends ReactContextBaseJavaModule {
  String unityPlacementID;
  boolean isAdLoaded = false;
  Promise showPromise;
  Promise loadPromise;

  ReactApplicationContext reactContext;

  @Override
  public String getName(){
    return "UnityRewardedAd";
  }
  UnityRewardedAd(ReactApplicationContext context){
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

    }

    @Override
    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
      isAdLoaded = false;

      try {

        if (state.equals(UnityAds.UnityAdsShowCompletionState.COMPLETED) && showPromise != null) {
        showPromise.resolve(true);
        } else {
        showPromise.resolve(false);
        }
        showPromise = null;
      } catch(Exception e) {
        
      }
      

    }
  };


  @ReactMethod
  public void loadAd(String placementId, Promise p){
    if (!UnityAds.isInitialized()) {
      p.resolve(false);
        return;
    }
    if (isAdLoaded) {
       p.resolve(true);
      return;
    }
    loadPromise = p;
    unityPlacementID = placementId;
    UnityAds.load(unityPlacementID, loadListener);
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
}
