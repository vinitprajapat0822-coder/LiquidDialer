# LiquidDialer

**Free liquid-glass Android dialer** (clean-room rebuild of PhoneX core).

- No ads · No subscription · All themes unlocked

---

## ZIP Download

**https://github.com/vinitprajapat0822-coder/LiquidDialer/archive/refs/heads/main.zip**

---

## Status (updated)

| Feature                        | Status      |
|--------------------------------|-------------|
| Project scaffold               | ✅          |
| Default dialer role request    | ✅          |
| Glass-style keypad + call      | ✅          |
| **Liquid Glass Call Screen**   | ✅ **New**  |
| **Slide-to-answer**           | ✅ **New**  |
| CallActivity + InCallService    | ✅ **New**  |
| T9 Matcher (foundation)        | ✅ **New**  |
| Contacts loading + live T9     | ⏳ Next     |
| Call log + Block list          | ⏳ Next     |
| Flash / Announcer / Themes     | ⏳ Later    |

---

## Hindi – जल्दी सेटअप

1. ZIP डाउनलोड करो (ऊपर लिंक)
2. Extract करो
3. Android Studio में खोलो → Sync → Run

या:
```bash
git clone https://github.com/vinitprajapat0822-coder/LiquidDialer.git
cd LiquidDialer
```

APK:
```bash
./gradlew :app:assembleDebug
```

---

## Important

- App को **Default Dialer** बनाना जरूरी है, तभी Call Screen आएगा।
- यह clean-room rebuild है (मूल PhoneX कोड नहीं)।
- Contacts + full T9 अगला काम है।
