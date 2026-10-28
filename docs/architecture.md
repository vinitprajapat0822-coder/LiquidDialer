# Architecture: LiquidDialer (rebuild of PhoneX core)

Free, no-ads, all themes unlocked. Local-first Android dialer.

## Stack

| layer | choice | why |
| --- | --- | --- |
| language | Kotlin 2.0+ | Official Android, best Telecom support |
| UI | Jetpack Compose + Material 3 | Modern, glass-friendly |
| navigation | Navigation Compose | Screen graph |
| local DB | Room (SQLite) | Contacts cache, call log, themes, block list |
| DI | Hilt | Standard |
| async | Coroutines + Flow | Call state, T9 |
| call handling | Telecom + InCallService + CallScreeningService | Required for default dialer |
| build | AGP 8.7+, minSdk 29, target 35 | Matches original + Redmi Note 7S |

No backend, no auth, no payments, no cloud.

## Schema

See schema.sql (7 tables: contacts, phone_numbers, call_log, blocked_numbers, themes, settings, fake_calls).

## Build order

1. Vertical slice (done): Onboarding + basic dialer + service stubs
2. Must-haves: Contacts + T9, Call log, Block list, Announcer, Flash, Dual-SIM, Themes
3. Should-haves: Fake call, quick replies, etc.
4. OEM polish (MIUI)

## Size

M (few weeks for solid vertical + must-haves).
