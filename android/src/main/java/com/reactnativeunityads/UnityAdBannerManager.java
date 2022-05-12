package com.reactnativeunityads;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class UnityAdBannerManager extends SimpleViewManager<UnityAdBanner> {
  public static final String REACT_CLASS = "UnityAdBanner";
  ReactApplicationContext mCallerContext;
  public static final String EVENT_AD_LOADED = "onAdLoaded";
  public static final String EVENT_AD_FAILED_TO_LOAD = "onAdFailedToLoad";
  public static final String EVENT_AD_OPENED = "onAdOpened";

  public UnityAdBannerManager(ReactApplicationContext reactContext) {
    mCallerContext = reactContext;
  }

  @Override
  public UnityAdBanner createViewInstance(ThemedReactContext context) {
    return new UnityAdBanner(context);
  }

  @ReactProp(name = "adUnitId")
  public void setAdUnitId(UnityAdBanner view, String unitId) {
    view.loadAd(unitId);
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }
}

