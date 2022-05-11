import { NativeModules } from "react-native";

type UnityAdsType = {
  initialize(gameId: string, testMode: boolean): Promise<boolean>;
  loadAd(placementId: string): Promise<boolean>;
  isInitialized(): Promise<boolean>;
  showAd(): Promise<string>;
};

const { UnityAds } = NativeModules;

export default UnityAds as UnityAdsType;
