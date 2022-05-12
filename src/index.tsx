import { NativeModules, requireNativeComponent, ViewProps } from "react-native";

type UnityAdsManagerType = {
  initialize(gameId: string, testMode: boolean): Promise<boolean>;
  isInitialized(): Promise<boolean>;
};

type UnityAdInterstitialType = {
  loadAd(placementId: string): Promise<boolean>;
  showAd(): Promise<string>;
};

type UnityAdRewardedType = {
  loadAd(placementId: string): Promise<boolean>;
  showAd(): Promise<string>;
};

export const UnityAdsManager: UnityAdsManagerType =
  NativeModules.UnityAdsManager;
export const UnityInterstitialAd: UnityAdInterstitialType =
  NativeModules.UnityInterstitialAd;
export const UnityRewardedAd: UnityAdRewardedType =
  NativeModules.UnityRewardedAd;

type Error = {
  errorMessage: string;
  errorCode: string;
};

interface UnityBannerAdProps extends ViewProps {
  adUnitId: string;
  onAdLoaded?: () => void;
  onAdFailedToLoad?: (error: Error) => void;
  onAdOpened?: () => void;
}

export const UnityAdBanner = requireNativeComponent<UnityBannerAdProps>(
  "UnityAdBanner"
);
