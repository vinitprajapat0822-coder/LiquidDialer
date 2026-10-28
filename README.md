# LiquidDialer

**Free liquid-glass Android dialer** (clean-room rebuild of PhoneX core features).

- No ads
- No subscription
- All themes unlocked
- T9 dialer + dual-SIM ready structure
- Kotlin + Jetpack Compose

---

## हिंदी में जल्दी सेटअप

1. GitHub से क्लोन करो:
   ```bash
   git clone https://github.com/vinitprajapat0822-coder/LiquidDialer.git
   cd LiquidDialer
   ```

2. **Android Studio** में फोल्डर खोलो (Hedgehog या नया)।

3. Gradle sync होने दो (पहली बार इंटरनेट चाहिए)।

4. फोन/एमुलेटर पर Run दबाओ (Android 10+ / API 29+)।

**APK बनाना:**
```bash
./gradlew :app:assembleDebug
```
APK मिलेगा: `app/build/outputs/apk/debug/app-debug.apk`

---

## English – Quick Start

```bash
git clone https://github.com/vinitprajapat0822-coder/LiquidDialer.git
cd LiquidDialer
```

Open in **Android Studio** → Sync → Run on device (API 29+).

Or build APK:
```bash
./gradlew :app:assembleDebug
```

---

## Current Status (v0.1.0-slice)

| Feature                        | Status      |
|--------------------------------|-------------|
| Project scaffold               | ✅ Done     |
| Default dialer role request    | ✅ Done     |
| Glass-style keypad + call      | ✅ Done     |
| InCallService + Screening stubs| ✅ Done     |
| Full liquid-glass Call Screen  | ⏳ Next     |
| Contacts + T9 search           | ⏳ Next     |
| Call log + Block list          | ⏳ Next     |
| Themes + Flash + Announcer     | ⏳ Later    |

Full plan: see `docs/architecture.md` and `docs/recon.md`

---

## Important Notes

- This is a **clean-room rebuild**. Original PhoneX assets / code are **not** included (legal).
- Works best when set as **default dialer**.
- Tested structure for Redmi / MIUI (Android 10+).
- No Google Play Billing, no ads, no premium locks.

---

## Repo Link

**https://github.com/vinitprajapat0822-coder/LiquidDialer**

Clone → Open in Android Studio → Build & Run.
