# Roadmap — DorjeMala Flutter

**Last updated:** 5 August 2026

---

## Why Flutter

The current app is a single HTML file (`app.html`, ~825 KB). It works well — it runs in Telegram, in the browser, and as an Android APK. But it has hard limits:

1. **No cloud sync.** Data lives in `localStorage`. If you clear the cache or switch devices, everything is gone (except the Telegram backup, which is limited to 4 KB per key and strips images).
2. **No real cross-platform.** iPhone users get a web page. Android APK is a WebView wrapper. There is no native iOS app.
3. **No push notifications on web.** The Telegram bot handles reminders, but standalone web users get nothing.
4. **No image library.** Users manually find and attach thangkas. There is no built-in collection.
5. **One developer bottleneck.** The entire app is one file, one class, one template language. It is hard for anyone else to contribute.

Flutter solves all of this: cross-platform (web, Android, iOS, desktop), built-in cloud integrations, proper state management, and a large ecosystem.

---

## What stays the same

The **product** does not change:

- Tap to count, vibration feedback, mala rounds
- Retreats with dates, goals, and shared mantras
- Tibetan calendar with special days and reminders
- Statistics: lifetime, daily, heat map
- Two languages: Ukrainian and English

The **design** does not change:

- Dark background with gold accent
- Manrope + Cormorant Garamond fonts
- Same color palette, same animation timing
- Same haptic patterns

The **data model** stays compatible:

- Same fields for mantras, retreats, statistics
- Same localStorage format during migration
- Same Telegram cloud backup structure

---

## What changes

| Aspect | Current (HTML) | Future (Flutter) |
|---|---|---|
| Platform | Single HTML file | Flutter Web + Android + iOS |
| Storage | localStorage (5 MB) | Firestore + SharedPreferences |
| Images | Base64 in localStorage | Supabase Storage (cloud) |
| Sync | Telegram backup (4 KB limit) | Firestore real-time sync |
| Auth | Anonymous | Anonymous + Google + Apple |
| Notifications | Telegram bot only | FCM push + Telegram bot |
| State | Single class, x-dc | Provider + Dart |
| Build | Upload HTML | `flutter build` |
| Testing | Manual in browser | Chrome + device testing |

---

## Phases

### Phase 1: Foundation (Week 1-2)

**Goal:** Flutter project runs in Chrome with a working counter.

- [ ] Create Flutter project
- [ ] Set up Provider state management
- [ ] Implement counter screen with tap area
- [ ] Add mala round logic and haptic feedback
- [ ] Test in Chrome

**Deliverable:** `flutter run -d chrome` shows a working mantra counter.

---

### Phase 2: Firebase Setup (Week 2-3)

**Goal:** Firebase project configured, data persists in Firestore.

- [ ] Create Firebase project
- [ ] Enable Auth (anonymous, Google, Apple)
- [ ] Create Firestore database
- [ ] Set up Firestore CRUD for mantras
- [ ] Set up Firestore CRUD for retreats
- [ ] Test data persistence across browser sessions

**Deliverable:** Data saves to Firestore, survives cache clear.

---

### Phase 3: Supabase + Images (Week 3-4)

**Goal:** Thangka images stored in cloud, loaded on demand.

- [ ] Create Supabase project
- [ ] Set up `thangkas` bucket
- [ ] Implement image upload (authenticated users)
- [ ] Implement image download with caching
- [ ] Migrate existing base64 images to URLs

**Deliverable:** Images load from Supabase, not localStorage.

---

### Phase 4: Telegram Mini App (Week 4-5)

**Goal:** Flutter web app works inside Telegram.

- [ ] Add `telegram-web-app.js` to `web/index.html`
- [ ] Implement TelegramService in Dart
- [ ] Handle `start_param` for shared practice links
- [ ] Test on real device via Telegram

**Deliverable:** App launches from Telegram bot, counts correctly.

---

### Phase 5: All Screens (Week 5-8)

**Goal:** All screens ported from HTML to Flutter.

- [ ] Home screen (mantra list)
- [ ] Edit mantra screen
- [ ] Retreat screens (list, detail, form)
- [ ] Statistics screens (per-mantra, lifetime)
- [ ] Tibetan calendar screen
- [ ] Settings screen
- [ ] Backup/export screen
- [ ] Contextual help overlay

**Deliverable:** Feature parity with HTML version.

---

### Phase 6: Android APK (Week 8-9)

**Goal:** Android APK builds and works.

- [ ] Configure `android/app/build.gradle`
- [ ] Set `compileSdk 34`, `minSdk 24`
- [ ] Test on real Android device
- [ ] Copy APK to Desktop
- [ ] Test on Android 10, 11, 12, 13, 14

**Deliverable:** `dorjemala.apk` on Desktop, installs on Android.

---

### Phase 7: PWA + F-Droid (Week 9-10)

**Goal:** PWA works, F-Droid submission ready.

- [ ] Configure `manifest.json` for PWA
- [ ] Add service worker for offline support
- [ ] Add LICENSE file (MIT)
- [ ] Remove keystore from repo
- [ ] Submit to F-Droid

**Deliverable:** PWA installs on Android/iOS, F-Droid submission.

---

### Phase 8: Polish + Deploy (Week 10-12)

**Goal:** Production-ready, deployed to Firebase Hosting.

- [ ] Firebase Hosting setup
- [ ] Deploy Flutter web to Firebase
- [ ] Test on real devices
- [ ] Performance optimization
- [ ] Accessibility review
- [ ] Documentation update

**Deliverable:** Live at `dorjemala-flutter.web.app`

---

## How to contribute

### For Manjushri33 (junior developer)

1. **Start with Phase 1.** Create the Flutter project, get the counter working in Chrome.
2. **Read `AGENTS.md`** for the full project structure and build commands.
3. **Read `CONTRIBUTING.md`** for PR workflow and code style.
4. **Ask questions.** Open an issue or ping on Telegram.

### For anyone else

1. **Pick an open task** from the phases above.
2. **Fork the repo** and create a feature branch.
3. **Follow `CONTRIBUTING.md`** for code style and PR format.
4. **Submit a PR** to `dyaroshevich/DorjeMala`.

---

## Timeline

| Phase | Weeks | What ships |
|---|---|---|
| 1-2 | 1-3 | Working counter in Chrome |
| 3-4 | 3-5 | Firebase + Supabase + Telegram |
| 5 | 5-8 | All screens |
| 6-7 | 8-10 | Android APK + PWA |
| 8 | 10-12 | Production deploy |

**Total:** ~3 months for feature parity.
**MVP (counter only):** ~2 weeks.

---

## Risks

| Risk | Mitigation |
|---|---|
| Telegram Mini App compatibility | Test early (Phase 4), use proven patterns |
| Firebase costs | Free tier covers 50K MAU, 1 GB storage |
| Supabase costs | Free tier covers 1 GB storage |
| Manjushri33 busy | Async collaboration, clear docs, PR reviews |
| iOS App Store ($99/year) | PWA first, native iOS later if demand exists |

---

## Decision log

| Date | Decision | Rationale |
|---|---|---|
| Aug 2026 | Flutter over React Native | Better web support, Dart is simpler, larger ecosystem |
| Aug 2026 | Firebase + Supabase | Free tier, no credit card, proven at scale |
| Aug 2026 | Provider over Riverpod | Simpler, consistent with EV Trang project |
| Aug 2026 | Android only (no iOS yet) | Free distribution, no $99/year fee |
| Aug 2026 | Firestore over Realtime DB | Better querying, cheaper at scale |
| Aug 2026 | Supabase Storage over Firebase Storage | Firebase requires Blaze plan (credit card) |

---

## Contacts

- **Original repo:** https://github.com/Manjushri33/DorjeMala
- **My fork:** https://github.com/dyaroshevich/DorjeMala
- **Bot:** @dorjemala_bot
- **Live app:** https://manjushri33.github.io/DorjeMala
