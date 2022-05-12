import React, { useEffect } from "react";
import { StyleSheet, View, Text, Button } from "react-native";
import { UnityInterstitialAd, UnityAdsManager } from "react-native-unity-ads";
export default function App() {
  useEffect(() => {
    console.log("load challenge");
    UnityInterstitialAd.loadAd("video");
  }, []);

  const showAd = async () => {
    if (await UnityAdsManager.isInitialized()) {
      let result = await UnityInterstitialAd.showAd();
      console.log(result);
    }
  };

  return (
    <View style={styles.container}>
      <Button onPress={showAd} title={"test"} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
});
