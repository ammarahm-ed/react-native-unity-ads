package com.reactnativeunityads;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class UnityAdBanner extends ReactViewGroup implements BannerView.IListener, IUnityAdsInitializationListener {

  BannerView bannerView;
  ThemedReactContext mContext;
  String placementId;

  public UnityAdBanner(ThemedReactContext context) {
    super(context);
    mContext = context;
  }

  public void loadAd(String adUnitId) {
    placementId = adUnitId;
    if (!UnityAds.isInitialized()) return;

    if (bannerView != null) {
      bannerView.load();
      return;
    }
    bannerView = new BannerView(mContext.getCurrentActivity(),placementId,new UnityBannerSize(this.getWidth(),this.getHeight()));
    bannerView.setListener(this);
    bannerView.load();
    addView(bannerView);
  }


  @Override
  public void onBannerLoaded(BannerView bannerView) {

    sendEvent(UnityAdBannerManager.EVENT_AD_LOADED, Arguments.createMap());
  }

  @Override
  public void onBannerClick(BannerView bannerView) {
    sendEvent(UnityAdBannerManager.EVENT_AD_OPENED, Arguments.createMap());
  }

  @Override
  public void onBannerFailedToLoad(BannerView bannerView, BannerErrorInfo bannerErrorInfo) {
    WritableMap payload = Arguments.createMap();
    payload.putString("errorMessage",bannerErrorInfo.errorMessage);
    payload.putString("errorCode",bannerErrorInfo.errorCode.name());
    sendEvent(UnityAdBannerManager.EVENT_AD_FAILED_TO_LOAD, payload);
  }

  @Override
  public void onBannerLeftApplication(BannerView bannerView) {

  }

  private void sendEvent(String type, WritableMap payload) {
    ((ThemedReactContext) getContext())
      .getJSModule(RCTEventEmitter.class)
      .receiveEvent(getId(), type, payload);
  }

  @Override
  public void onInitializationComplete() {
    if (placementId != null) {
      loadAd(placementId);
    }
  }

  @Override
  public void onInitializationFailed(UnityAds.UnityAdsInitializationError unityAdsInitializationError, String s) {

  }
}

