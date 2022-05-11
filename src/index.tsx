import { NativeModules } from 'react-native';

type UnityAdsType = {
  loadAd(gameId : string, placementId : string, testMode : boolean) : Promise<boolean>;
  isInitialized() : Promise<boolean>;
  showAd() : Promise<string>;
};

const { UnityAds } = NativeModules;

export default UnityAds as UnityAdsType;
