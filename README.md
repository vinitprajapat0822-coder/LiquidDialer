# LiquidDialer

Free, no-ads rebuild of the core PhoneX / Liquid Glass Dialer experience.

- T9 dialer
- Liquid-glass incoming call UI (in progress)
- Dual-SIM aware
- Local spam block list
- All themes unlocked from day 1
- No subscription, no ads

## Status

**Vertical slice (v0.1.0-slice)**

- [x] Project scaffold (Kotlin + Compose + Hilt + Room ready)
- [x] Onboarding + default dialer role request
- [x] Basic glass-style keypad + place call
- [x] InCallService + CallScreeningService stubs
- [ ] Full glass CallScreen with slide-to-answer
- [ ] Contacts + T9 search
- [ ] Call log
- [ ] Theme gallery
- [ ] Flash / Announcer
- [ ] Block list

See `docs/` for full recon + architecture.

## Build

### Android Studio (recommended)

1. Open the `LiquidDialer` folder in Android Studio Hedgehog+ or newer.
2. Let Gradle sync (needs internet first time).
3. Run on device / emulator (API 29+).

### Command line

```bash
cd LiquidDialer
./gradlew :app:assembleDebug
# APK → app/build/outputs/apk/debug/app-debug.apk
```

### Termux note

Full Android builds are heavy. Prefer Android Studio on the Windows laptop or a cloud CI. You can still use Termux for git, editing, and later for testing via `adb`.

## Permissions

The app requests:

- Default dialer role (core)
- Contacts, Call Log, Phone
- Camera (for flash-on-call only)
- Notifications

## License

Personal / educational rebuild. Clean-room implementation. Do not ship original PhoneX assets or proprietary spam databases.

## Next

1. Complete glass InCall UI
2. Room contacts + T9
3. Theme system
4. Push further milestones
