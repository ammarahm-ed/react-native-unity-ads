import React, { useEffect } from "react";
import { StyleSheet, View, Text, Button } from "react-native";
import UnityAds from "react-native-unity-ads";
export default function App() {
  useEffect(() => {
    console.log("load challenge");
    UnityAds.loadAd("video");
  }, []);

  const showAd = async () => {
    if (await UnityAds.isInitialized()) {
      let result = await UnityAds.showAd();
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
