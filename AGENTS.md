# DorjeMala Flutter — Agent Guide

## Project Overview

DorjeMala is a mantra counter for Buddhist practice, rebuilt in Flutter.
Original: single HTML file (app.html) → now cross-platform Flutter app.

**Target platforms:**
- Flutter Web → Telegram Mini App + PWA
- Flutter Android → APK (F-Droid / Google Play)
- Flutter iOS → App Store (future)

---

## Project Structure

```
K:\projects\malas\dorjemala_flutter\
├── lib/
│   ├── main.dart                        # Entry point
│   ├── app.dart                         # MaterialApp, routing, theme
│   │
│   ├── core/
│   │   ├── config/
│   │   │   ├── env.dart                 # Environment variables
│   │   │   ├── firebase_options.dart    # Firebase config (auto-generated)
│   │   │   └── supabase_config.dart     # Supabase URL + anon key
│   │   │
│   │   ├── services/
│   │   │   ├── firebase_service.dart    # Firebase initialization
│   │   │   ├── supabase_service.dart    # Supabase initialization
│   │   │   ├── auth_service.dart        # Firebase Auth (anonymous, Google, Apple)
│   │   │   ├── firestore_service.dart   # Firestore CRUD operations
│   │   │   ├── storage_service.dart     # Supabase Storage (thangkas)
│   │   │   ├── telegram_service.dart    # Telegram Mini App integration
│   │   │   ├── sync_service.dart        # Local ↔ Cloud sync
│   │   │   └── notification_service.dart # FCM push notifications
│   │   │
│   │   ├── models/
│   │   │   ├── mantra.dart              # Mantra data model
│   │   │   ├── retreat.dart             # Retreat data model
│   │   │   ├── retreat_item.dart        # Retreat item (shared mantra)
│   │   │   ├── user_profile.dart        # User settings
│   │   │   ├── day_log.dart             # Daily statistics
│   │   │   └── shared_practice.dart     # Group practice model
│   │   │
│   │   ├── utils/
│   │   │   ├── tibetan_calendar.dart    # Calendar logic + LUNAR2026 table
│   │   │   ├── haptic_feedback.dart     # Vibration patterns
│   │   │   ├── formatters.dart          # Number/date formatting
│   │   │   └── constants.dart           # App-wide constants
│   │   │
│   │   └── theme/
│   │       ├── app_theme.dart           # Dark theme (gold accent)
│   │       └── app_colors.dart          # Color palette
│   │
│   ├── features/
│   │   ├── auth/
│   │   │   ├── auth_screen.dart         # Login/signup screen
│   │   │   └── auth_provider.dart       # Auth state management
│   │   │
│   │   ├── home/
│   │   │   ├── home_screen.dart         # Mantra list + continue card
│   │   │   └── home_provider.dart       # Mantra list state
│   │   │
│   │   ├── counter/
│   │   │   ├── counter_screen.dart      # Tap area + mala progress
│   │   │   ├── counter_provider.dart    # Counter logic + haptics
│   │   │   └── widgets/
│   │   │       ├── tap_area.dart        # Main tap zone
│   │   │       ├── bead_progress.dart   # Mala bead visualization
│   │   │       └── mantra_image.dart    # Thangka with zoom
│   │   │
│   │   ├── edit/
│   │   │   ├── edit_mantra_screen.dart  # Create/edit mantra form
│   │   │   └── edit_provider.dart       # Form state
│   │   │
│   │   ├── retreats/
│   │   │   ├── retreats_screen.dart     # Retreat list
│   │   │   ├── retreat_detail_screen.dart # Single retreat view
│   │   │   ├── retreat_form_screen.dart # Create/edit retreat
│   │   │   └── retreat_provider.dart    # Retreat state
│   │   │
│   │   ├── statistics/
│   │   │   ├── stats_screen.dart        # Per-mantra stats
│   │   │   ├── lifetime_screen.dart     # All-time totals
│   │   │   └── stats_provider.dart      # Statistics calculations
│   │   │
│   │   ├── calendar/
│   │   │   ├── calendar_screen.dart     # Tibetan calendar grid
│   │   │   └── calendar_provider.dart   # Calendar state
│   │   │
│   │   ├── shared_practices/
│   │   │   ├── shared_screen.dart       # Group practice view
│   │   │   ├── join_screen.dart         # Join by link/QR
│   │   │   └── shared_provider.dart     # Shared practice state
│   │   │
│   │   ├── settings/
│   │   │   ├── settings_screen.dart     # App settings
│   │   │   └── settings_provider.dart   # Settings state
│   │   │
│   │   └── backup/
│   │       ├── backup_screen.dart       # Export/import data
│   │       └── backup_provider.dart     # Backup logic
│   │
│   └── shared/
│       └── widgets/
│           ├── app_bar.dart             # Custom app bar
│           ├── card_tile.dart           # Mantra/retreat card
│           ├── progress_bar.dart        # Goal progress bar
│           ├── toast_message.dart       # Transient messages
│           └── language_switcher.dart   # UK/EN toggle
│
├── web/
│   ├── index.html                       # telegram-web-app.js here
│   ├── manifest.json                    # PWA manifest
│   └── favicon.png
│
├── android/
│   └── app/
│       └── build.gradle                 # compileSdk 34, minSdk 24
│
├── ios/
│
├── test/
│   └── widget_test.dart
│
├── pubspec.yaml
├── analysis_options.yaml
├── AGENTS.md                            # This file
└── .gitignore
```

---

## Environment Setup

All tools on K: drive. Never use C: drive paths.

```bash
set JAVA_HOME=K:\program\jdk17
set ANDROID_HOME=K:\program\android-sdk
set PATH=K:\program\flutter\flutter\bin;K:\program\jdk17\bin;K:\program\android-sdk\platform-tools;%PATH%
set PUB_CACHE=K:\pub-cache
```

### Key Paths

| Tool | Path |
|---|---|
| Flutter SDK | `K:\program\flutter\flutter\bin\flutter.bat` |
| Dart SDK | Bundled inside Flutter |
| Java JDK | `K:\program\jdk17\` |
| Android SDK | `K:\program\android-sdk\` |
| ADB | `K:\program\android-sdk\platform-tools\adb.exe` |
| PUB_CACHE | `K:\pub-cache\` |
| This project | `K:\projects\malas\dorjemala_flutter\` |

### Flutter Version

```
Flutter 3.44.6 • channel stable
Dart 3.12.2 • DevTools 2.57.0
```

---

## Build Commands

### Run in Chrome (development)
```bash
cd K:\projects\malas\dorjemala_flutter
flutter run -d chrome
```

### Run on connected Android device
```bash
flutter devices                        # List devices
flutter run -d <device_id>             # Run on specific device
```

### Run on Android via USB (adb reverse)
```bash
flutter run -d chrome --web-port 8080
adb reverse tcp:8080 tcp:8080
# Open http://localhost:8080 on phone
```

### Build Web (PWA)
```bash
flutter build web --release
# Output: build/web/
```

### Build APK (release)
```bash
flutter build apk --release --target-platform android-arm64
# Output: build/app/outputs/flutter-apk/app-release.apk
```

### Deploy to Firebase Hosting
```bash
flutter build web --release
firebase deploy --only hosting
```

### Copy APK to Desktop
```bash
copy "K:\projects\malas\dorjemala_flutter\build\app\outputs\flutter-apk\app-release.apk" "C:\Users\dy\Desktop\DorjeMala APK\dorjemala.apk" /Y
```

---

## Firebase Configuration

### Services Used

| Service | Purpose | Free Tier |
|---|---|---|
| Firebase Hosting | PWA hosting | 10 GB storage, 360 MB/day |
| Firebase Auth | User authentication | 50K MAU |
| Cloud Firestore | Mantras, retreats, stats | 1 GB storage, 50K reads/day |
| Cloud Messaging | Push notifications | Unlimited |

### Firebase Project Setup

1. Go to https://firebase.google.com
2. Create project: `dorjemala-flutter`
3. Add Web app → copy config to `firebase_options.dart`
4. Enable Authentication:
   - Anonymous sign-in
   - Google sign-in
   - Apple sign-in (iOS)
5. Create Firestore database
6. Enable Cloud Messaging

### Firestore Collections

```
users/{user_id}
  ├── displayName: string
  ├── email: string (optional)
  ├── createdAt: timestamp
  └── settings: { lang: "uk"|"en", vibroOn: true, theme: "dark" }

mantras/{mantra_id}
  ├── userId: string (FK → users)
  ├── name: string (unique per user)
  ├── text: string (mantra text)
  ├── goal: number (default 100000)
  ├── malaSize: number (default 108)
  ├── count: number (lifetime total)
  ├── today: number (today's count)
  ├── malaPos: number (position in current round)
  ├── imageUrl: string (Supabase URL)
  ├── lastAt: timestamp
  └── createdAt: timestamp

retreats/{retreat_id}
  ├── userId: string (FK → users)
  ├── name: string
  ├── startDate: string (ISO date)
  ├── endDate: string (ISO date)
  ├── archived: boolean
  ├── createdAt: timestamp
  └── items: array of {
        id: string,
        mantraId: string (optional, for shared mantras),
        name: string,
        text: string,
        imageUrl: string,
        goal: number,
        count: number,
        today: number,
        malaPos: number,
        malaSize: number
      }

shared_practices/{practice_id}
  ├── createdBy: string (user_id)
  ├── members: array of user_ids
  ├── name: string
  ├── mantraName: string
  ├── goal: number
  ├── combinedCount: number
  ├── createdAt: timestamp
  └── lastActivity: timestamp

lifetime/{user_id}
  └── {mantra_name}: number (total count across all time)

log/{user_id}
  └── {mantra_name}:
        └── {ISO date}: number (count that day)
```

### Firestore Security Rules

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users can only read/write their own data
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }

    // Mantras: owner only
    match /mantras/{mantraId} {
      allow read, write: if request.auth != null &&
        resource.data.userId == request.auth.uid;
      allow create: if request.auth != null;
    }

    // Retreats: owner only
    match /retreats/{retreatId} {
      allow read, write: if request.auth != null &&
        resource.data.userId == request.auth.uid;
      allow create: if request.auth != null;
    }

    // Shared practices: members only
    match /shared_practices/{practiceId} {
      allow read: if request.auth != null &&
        request.auth.uid in resource.data.members;
      allow write: if request.auth != null &&
        request.auth.uid == resource.data.createdBy;
      allow create: if request.auth != null;
    }

    // Lifetime + Log: owner only
    match /lifetime/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    match /log/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
  }
}
```

---

## Supabase Configuration

### Services Used

| Service | Purpose | Free Tier |
|---|---|---|
| Supabase Storage | Thangka images | 1 GB |

### Supabase Project Setup

1. Go to https://supabase.com
2. Create project: `dorjemala-storage`
3. Copy URL + anon key to `supabase_config.dart`
4. Create bucket: `thangkas`
5. Set bucket policy: authenticated users can upload/read

### Storage Structure

```
thangkas/
└── {user_id}/
    ├── vajrasattva.jpg      (~200-500 KB each)
    ├── amitabha.jpg
    └── guru_rinpoche.jpg
```

### Storage Rules

```sql
-- Allow authenticated users to upload to their own folder
CREATE POLICY "Users upload own thangkas"
ON storage.objects FOR INSERT
TO authenticated
WITH CHECK (
  bucket_id = 'thangkas' AND
  (storage.foldername(name))[1] = auth.uid()::text
);

-- Allow anyone to read thangkas (public)
CREATE POLICY "Public read thangkas"
ON storage.objects FOR SELECT
TO public
USING (bucket_id = 'thangkas');
```

---

## Telegram Mini App Integration

### web/index.html

```html
<!DOCTYPE html>
<html>
<head>
  <script src="https://telegram.org/js/telegram-web-app.js" defer></script>
  <!-- Flutter web assets -->
</head>
<body>
  <!-- Flutter mounts here -->
</body>
</html>
```

### Telegram Service (Dart)

```dart
// lib/core/services/telegram_service.dart
import 'dart:js_interop';

@JS('Telegram.WebApp')
external WebAppJs get _webApp;

class TelegramService {
  static void init() {
    try {
      _webApp.ready();
      _webApp.expand();
    } catch (e) {
      // Not in Telegram — ignore
    }
  }

  static String? get startParam => _webApp.initDataUnsafe?.start_param;
  static int? get userId => _webApp.initDataUnsafe?.user?.id;
}
```

---

## Dependencies (pubspec.yaml)

```yaml
name: dorjemala_flutter
description: "Mantra counter for Buddhist practice"
version: 1.0.0+1

environment:
  sdk: ^3.12.2

dependencies:
  flutter:
    sdk: flutter

  # Firebase
  firebase_core: ^3.0.0
  firebase_auth: ^5.0.0
  cloud_firestore: ^5.0.0
  firebase_messaging: ^15.0.0

  # Supabase
  supabase_flutter: ^2.0.0

  # Telegram
  flutter_telegram_miniapp: ^1.0.0

  # State management
  provider: ^6.1.2

  # Local storage
  shared_preferences: ^2.2.0

  # UI
  cupertino_icons: ^1.0.8

  # Utils
  intl: ^0.19.0
  uuid: ^4.0.0

dev_dependencies:
  flutter_test:
    sdk: flutter
  flutter_lints: ^6.0.0

flutter:
  uses-material-design: true
```

---

## Critical Rules

### DO

1. **Test in Chrome first** — faster than Android emulator
2. **Use `provider` for state management** — consistent with EV Trang project
3. **Keep secrets in `.env` files** — never commit to git
4. **Use `flutter build web` before APK** — web is faster to iterate
5. **Follow existing code style** — from EV Trang Flutter projects
6. **Run `flutter analyze` before commit** — no warnings allowed
7. **Test on real device** — Telegram Mini App behavior differs from Chrome

### DON'T

1. **Never use C: drive paths** — all tools on K: drive
2. **Never commit `firebase_options.dart`** — add to .gitignore
3. **Never commit `supabase_config.dart`** — add to .gitignore
4. **Never hardcode API keys** — use environment variables
5. **Don't use `setState` for complex state** — use Provider
6. **Don't skip haptic feedback** — it's core to the UX
7. **Don't break localStorage compatibility** — existing users have data

---

## Data Migration (localStorage → Firestore)

### Import from Original app.html

```dart
// Parse localStorage JSON from original app
Map<String, dynamic> parseOriginalData(String jsonString) {
  final data = json.decode(jsonString);
  return {
    'mantras': (data['mantras'] as List).map((m) => Mantra.fromOriginal(m)),
    'retreats': (data['retreats'] as List).map((r) => Retreat.fromOriginal(r)),
    'lifetime': data['lifetime'],
    'log': data['log'],
  };
}
```

### Sync Strategy

```
Local (SharedPreferences) ←→ Firestore
         ↕
   Cloudflare Worker (bot)
```

1. **Read**: Check Firestore first, fallback to local
2. **Write**: Write to both local and Firestore
3. **Conflict**: Last-write-wins (same as Telegram backup)
4. **Offline**: Queue writes, sync when online

---

## Design System

### Colors (from original app.html)

```dart
class AppColors {
  static const background = Color(0xFF0B0A10);
  static const backgroundGradientEnd = Color(0xFF100E18);
  static const cardSurface = Color(0x0DFFFFFF); // rgba(255,255,255,.045)
  static const accentGold = Color(0xFFE0C48A);
  static const accentGoldStart = Color(0xFFD8B578);
  static const accentGoldEnd = Color(0xFFC9A86A);
  static const primaryText = Color(0xFFF2F0F8);
  static const secondaryText = Color(0x8CE6E6F0); // rgba(232,230,240,.55)
  static const success = Color(0xFF6AC98A);
  static const danger = Color(0xFFC0504E);
}
```

### Typography

```dart
class AppTypography {
  static const interface = 'Manrope';
  static const accent = 'Cormorant Garamond';

  // Sizes
  static const counter = 66.0;
  static const screenTitle = 16.5;
  static const mantraName = 15.5;
  static const body = 13.5;
  static const caption = 11.5;
}
```

### Animation Timing

```dart
class AppAnimation {
  static const tapResponse = Duration(milliseconds: 130);
  static const layoutChange = Duration(milliseconds: 250);
  static const messageAppear = Duration(milliseconds: 300);
  static const backgroundBreathe = Duration(seconds: 7);
}
```

---

## Haptic Patterns

```dart
class HapticPatterns {
  static const tap = [120];                    // Short vibration
  static const mala = [140, 60, 140];          // Mala round complete
  static const goal = [140, 60, 140, 60, 280]; // Goal reached
}
```

---

## Known Issues & Workarounds

### 1. Telegram Mini App grey screen on Android
```dart
// Add delay before main() for Telegram compatibility
void main() async {
  try {
    if (TelegramWebApp.instance.isSupported) {
      TelegramWebApp.instance.ready();
      await Future.delayed(Duration(milliseconds: 200));
      TelegramWebApp.instance.expand();
    }
  } catch (e) {
    await Future.delayed(Duration(milliseconds: 200));
  }
  runApp(MyApp());
}
```

### 2. Cross-drive builds (K: → C:)
```properties
# android/gradle.properties
kotlin.incremental=false
```

### 3. PWA install prompt
- Chrome shows "Add to Home Screen" banner automatically
- iOS requires manual "Share → Add to Home Screen"

---

## Deployment Checklist

### Firebase Hosting
- [ ] `firebase init hosting` (first time)
- [ ] `flutter build web --release`
- [ ] `firebase deploy --only hosting`
- [ ] Test at: https://dorjemala-<project>.web.app

### Android APK
- [ ] `flutter build apk --release --target-platform android-arm64`
- [ ] Test APK on real device
- [ ] Copy to Desktop for distribution

### F-Droid
- [ ] Add LICENSE file (MIT)
- [ ] Remove keystore from repo
- [ ] Submit: https://f-droid.org/forums/topic/add-app/

### Google Play
- [ ] Create developer account ($25)
- [ ] Generate release keystore
- [ ] Upload APK + screenshots
- [ ] Write description

---

## Related Files

| File | Purpose |
|---|---|
| `ARCHITECTURE.md` | Original app architecture (reference) |
| `PRODUCT.md` | Product context and audience |
| `DESIGN.md` | Landing page design system |
| `BACKLOG.md` | Decisions taken and postponed |
| `CALENDAR-UPDATE.md` | Yearly calendar update checklist |
| `HINTS.md` | Contextual help texts |

---

## Contacts

- **Original repo**: https://github.com/Manjushri33/DorjeMala
- **My fork**: https://github.com/dyaroshevich/DorjeMala
- **Bot**: @dorjemala_bot
- **Live app**: https://manjushri33.github.io/DorjeMala
