package com.reactnativeunityads;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;

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
    if (unitId == null) return;
    view.loadAd(unitId);
  }

  @ReactProp(name = "size")
  public void setSize(UnityAdBanner view, ReadableMap size) {
    view.setDimensions(size.getInt("width"),size.getInt("height"));
  }


  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  @Nullable
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
    String[] events = {
      EVENT_AD_LOADED,
      EVENT_AD_FAILED_TO_LOAD,
      EVENT_AD_OPENED,
    };
    for (String event : events) {
      builder.put(event, MapBuilder.of("registrationName", event));
    }
    return builder.build();
  }
}

