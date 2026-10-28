# LiquidDialer

Free liquid-glass Android dialer (clean-room rebuild of PhoneX core features).

**No ads · No subscription**

---

## ZIP Download (latest source)

**https://github.com/vinitprajapat0822-coder/LiquidDialer/archive/refs/heads/main.zip**

---

## Status (honest)

| Feature                      | Status |
|-----------------------------|--------|
| Scaffold + permissions      | ✅     |
| Default dialer role         | ✅     |
| Glass keypad + place call   | ✅     |
| Liquid Glass Call Screen    | ✅     |
| Slide-to-answer             | ✅     |
| Contacts + live T9          | ✅     |
| Recents / Call Log          | ✅     |
| Bottom nav (Dialer/Recents) | ✅     |
| Block list / Flash / Themes | ⏳ not done |
| Dual-SIM / Fake call        | ⏳ not done |

This is a working core, not 100% PhoneX clone.

---

## APK कैसे बनाएँ

यहाँ Android SDK नहीं है → **signed APK यहाँ से नहीं मिल सकती**।

### Android Studio (सबसे आसान)

1. ZIP डाउनलोड या `git clone https://github.com/vinitprajapat0822-coder/LiquidDialer.git`
2. Android Studio में फोल्डर खोलो
3. Gradle Sync
4. **Build → Build Bundle(s) / APK(s) → Build APK(s)**
5. APK path: `app/build/outputs/apk/debug/app-debug.apk`
6. फोन पर copy → install (Unknown sources on)

### Signed release APK

Android Studio → **Build → Generate Signed Bundle / APK** → अपना keystore चुनो।

---

## Permissions

Default dialer, Contacts, Call Log, Phone
