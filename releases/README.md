# 📦 TheMagicScorlami Release Distribution

This directory contains signed production release builds of **TheMagicScorlami**.

---

## 🚀 Latest Stable Release: 1.4.5 (Build 9)

| Artifact | File Name | Size | SHA-256 Checksum | Purpose |
| :--- | :--- | :--- | :--- | :--- |
| **Signed Release APK** | [TheMagicScorlami-v1.4.5-release.apk](TheMagicScorlami-v1.4.5-release.apk) | 15.4 MB | BE01A53F24B3A034730862F0BEBE416C875DA5A078528821B74161BF2BC998DD | Direct phone install / sideloading |
| **Google Play Bundle** | [TheMagicScorlami-v1.4.5-release.aab](TheMagicScorlami-v1.4.5-release.aab) | 15.1 MB | CA4EF1ABDA83E6AF02B322847AB01219DB171773680D55F1084AF34FD5E2E33F | Google Play Store Console distribution |

---

## 📲 Installation Instructions

### Option 1: Direct Phone Install (Sideload)
1. Download TheMagicScorlami-v1.4.5-release.apk to your Android device (Android 8.0+ / API 24+).
2. Open the file in your device's file manager and tap **Install**.
3. If prompted, enable **Install unknown apps** for your browser or file manager.

### Option 2: ADB Sideload via USB
Connect your phone with USB debugging enabled and run:
`ash
adb install -r releases/TheMagicScorlami-v1.4.5-release.apk
`

---

## 🛠️ Building From Source
To compile and generate fresh release artifacts directly into this folder:
`ash
./gradlew buildReleaseApk
`
or
`ash
./gradlew assembleRelease bundleRelease
`
The Gradle build pipeline is configured to automatically sign the APK with the production keystore and copy the resulting binaries directly into eleases/.

---

## 🌐 GitHub Releases
You can also download releases and view changelogs directly from GitHub:
👉 **[https://github.com/cavant/TheMagicScorlami/releases](https://github.com/cavant/TheMagicScorlami/releases)**
