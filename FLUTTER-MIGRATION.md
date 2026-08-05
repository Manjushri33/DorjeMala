# Flutter Migration — Technical Plan

**Last updated:** 5 August 2026

This document explains how the original HTML app (`app.html`) maps to the Flutter version.

---

## Overview

The migration is **incremental**, not a rewrite. We preserve:
- The data model (same fields, same structure)
- The design (same colors, fonts, animations)
- The behavior (same tap-to-count, same haptic patterns)
- The Telegram integration (same bot, same backup)

What changes:
- HTML → Flutter widgets
- JavaScript → Dart
- localStorage → Firestore
- Base64 images → Supabase Storage
- Single file → Feature-based structure

---

## Data model mapping

### localStorage → Firestore

| localStorage key | Firestore collection | Document ID |
|---|---|---|
| `dm_state.mantras[]` | `mantras/{mantra_id}` | `mantra_id` |
| `dm_state.retreats[]` | `retreats/{retreat_id}` | `retreat_id` |
| `dm_state.archivedMantras[]` | `mantras/{mantra_id}` | `mantra_id` (with `archived: true`) |
| `dm_state.lifetime` | `lifetime/{user_id}` | `user_id` |
| `dm_state.log` | `log/{user_id}` | `user_id` |
| `dm_state.vibroOn` | `users/{user_id}.settings.vibroOn` | `user_id` |
| `dm_state.lang` | `users/{user_id}.settings.lang` | `user_id` |

### Field mapping

#### Mantra

| localStorage field | Firestore field | Notes |
|---|---|---|
| `id` | `id` (document ID) | Same format |
| `name` | `name` | Unique per user |
| `text` | `text` | Mantra text |
| `img` | `imageUrl` | Supabase URL (not base64) |
| `goal` | `goal` | Default 100000 |
| `malaSize` | `malaSize` | Default 108 |
| `count` | `count` | Lifetime total |
| `today` | `today` | Today's count |
| `malaPos` | `malaPos` | Position in current round |
| `lastAt` | `lastAt` | Timestamp (ms → seconds) |
| — | `userId` | New: FK to users |
| — | `createdAt` | New: creation timestamp |

#### Retreat

| localStorage field | Firestore field | Notes |
|---|---|---|
| `id` | `id` (document ID) | Same format |
| `name` | `name` | Retreat name |
| `start` | `startDate` | ISO date string |
| `end` | `endDate` | ISO date string |
| `archived` | `archived` | Boolean |
| `items[]` | `items[]` | Subcollection or array |
| — | `userId` | New: FK to users |
| — | `createdAt` | New: creation timestamp |

#### Lifetime / Log

| localStorage | Firestore | Notes |
|---|---|---|
| `lifetime.Vajrasattva: 34500` | `lifetime/{uid}.Vajrasattva: 34500` | Same structure |
| `log.Vajrasattva["2026-07-26"]: 324` | `log/{uid}.Vajrasattva."2026-07-26": 324` | Same structure |

---

## Screen mapping

| HTML screen (`screen` key) | Flutter screen | File |
|---|---|---|
| `home` | `HomeScreen` | `lib/features/home/home_screen.dart` |
| `counter` | `CounterScreen` | `lib/features/counter/counter_screen.dart` |
| `stats` | `StatsScreen` | `lib/features/statistics/stats_screen.dart` |
| `retreats` | `RetreatsScreen` | `lib/features/retreats/retreats_screen.dart` |
| `retreatDetail` | `RetreatDetailScreen` | `lib/features/retreats/retreat_detail_screen.dart` |
| `rform` | `RetreatFormScreen` | `lib/features/retreats/retreat_form_screen.dart` |
| `edit` | `EditMantraScreen` | `lib/features/edit/edit_mantra_screen.dart` |
| `pick` | `PickMantraScreen` | `lib/features/edit/pick_mantra_screen.dart` |
| `life` | `LifetimeScreen` | `lib/features/statistics/lifetime_screen.dart` |
| `arch` | `ArchiveScreen` | `lib/features/home/archive_screen.dart` |
| `calendar` | `CalendarScreen` | `lib/features/calendar/calendar_screen.dart` |
| `zen` | `ZenModeScreen` | `lib/features/counter/zen_mode_screen.dart` |

---

## Component mapping

### Counter area

| HTML | Flutter | Notes |
|---|---|---|
| `<div class="tap-area" ...>` | `TapArea` widget | GestureDetector + haptic |
| `vibrate('tap')` | `HapticFeedback.lightImpact()` | Short vibration |
| `vibrate('mala')` | `HapticFeedback.heavyImpact()` | Mala complete |
| `vibrate('goal')` | `HapticFeedback.heavyImpact()` | Goal reached |
| Mala progress bar | `BeadProgress` widget | Custom painter |
| Image zoom (pinch) | `InteractiveViewer` | Built-in Flutter |

### State management

| HTML | Flutter | Notes |
|---|---|---|
| `this.state` | Provider | `ChangeNotifierProvider` |
| `renderVals()` | Computed properties | In provider |
| `calVals()` | Calendar provider | Memoized |
| `this.T[lang]` | Localization | `AppLocalizations` or simple map |

---

## Data flow

### Current (HTML)

```
User tap → state.count++ → renderVals() → DOM update
                  ↓
            localStorage.setItem('dm_state', JSON.stringify(state))
```

### Future (Flutter)

```
User tap → CounterProvider.increment()
              ↓
         Firestore.update({ count: newValue })
              ↓
         SharedPreferences (local cache)
              ↓
         UI rebuild (Provider listener)
```

---

## Image handling

### Current (HTML)

```
1. User selects file
2. FileReader reads as base64
3. Stored in localStorage as data:image/jpeg;base64,...
4. ~200-500 KB per image
5. 5 MB localStorage limit → ~10-12 mantras max
```

### Future (Flutter)

```
1. User selects file
2. Upload to Supabase Storage: thangkas/{user_id}/{filename}
3. Store URL in Firestore: mantras/{id}.imageUrl
4. Load on demand with caching
5. No practical limit
```

### Migration path

```dart
// Step 1: Read existing base64 from localStorage
// Step 2: Decode to bytes
// Step 3: Upload to Supabase
// Step 4: Save URL to Firestore
// Step 5: Delete base64 from localStorage
```

---

## Haptic feedback mapping

| Pattern | HTML (ms) | Flutter | Notes |
|---|---|---|---|
| Tap | `120` | `HapticFeedback.lightImpact()` | Short |
| Mala | `[140, 60, 140]` | Custom vibrate pattern | Medium |
| Goal | `[140, 60, 140, 60, 280]` | Custom vibrate pattern | Long |

### Flutter implementation

```dart
// lib/core/utils/haptic_feedback.dart
import 'package:flutter/services.dart';

class HapticFeedback {
  static void tap() {
    SystemChannels.platform.invokeMethod('HapticFeedback.lightImpact');
  }

  static void mala() {
    // Pattern: vibrate, pause, vibrate
    Vibrate.vibrate(pattern: [0, 140, 60, 140]);
  }

  static void goal() {
    // Pattern: vibrate, pause, vibrate, pause, long vibrate
    Vibrate.vibrate(pattern: [0, 140, 60, 140, 60, 280]);
  }
}
```

---

## Calendar migration

### Current (HTML)

```javascript
const LUNAR2026 = [1,1,2,3,4,5,6,7,8,8,9,...]; // 365 values
const FIX = { 'losar': ['01-29', ...], ... };
```

### Future (Flutter)

```dart
// lib/core/utils/tibetan_calendar.dart
const lunar2026 = [1,1,2,3,4,5,6,7,8,8,9,...]; // Same data
const fixEvents = {
  'losar': ['01-29', ...],
  // Same structure
};
```

### Yearly update

Both versions need the same yearly update. The calendar data is identical — just different syntax.

---

## Telegram Mini App integration

### Current (HTML)

```html
<script src="https://telegram.org/js/telegram-web-app.js"></script>
<script>
  Telegram.WebApp.ready();
  Telegram.WebApp.expand();
  const user = Telegram.WebApp.initDataUnsafe.user;
</script>
```

### Future (Flutter)

```dart
// web/index.html
<script src="https://telegram.org/js/telegram-web-app.js"></script>

// lib/core/services/telegram_service.dart
import 'dart:js_interop';

@JS('Telegram.WebApp')
external dynamic get _webApp;

class TelegramService {
  static void init() {
    try {
      _webApp.callMethod('ready');
      _webApp.callMethod('expand');
    } catch (e) {
      // Not in Telegram
    }
  }
}
```

---

## Backup strategy

### Current (HTML)

```
localStorage → Telegram CloudStorage (4 KB limit, no images)
```

### Future (Flutter)

```
SharedPreferences (local cache)
     ↕
Firestore (cloud, unlimited)
     ↕
Export/Import JSON file
```

### Migration for existing users

1. Read `localStorage` from HTML version
2. Parse JSON
3. Upload to Firestore
4. Show success message
5. Redirect to Flutter app

---

## Testing strategy

### Phase 1: Chrome testing

```bash
flutter run -d chrome
# Test counter, haptics, navigation
```

### Phase 2: Device testing

```bash
flutter run -d <device_id>
# Test on real Android device
```

### Phase 3: Telegram testing

```bash
# Deploy to Firebase Hosting
flutter build web --release
firebase deploy --only hosting

# Test in Telegram bot
# Open @dorjemala_bot → /start → Launch app
```

### Phase 4: Cross-browser testing

- Chrome (Android, desktop)
- Safari (iOS)
- Telegram (Android, iOS)
- Firefox (desktop)

---

## Performance considerations

| Metric | Target | How |
|---|---|---|
| First paint | < 1s | Lazy load images |
| Tap response | < 130ms | Haptic on tap, not on rebuild |
| Image load | < 500ms | Cache in memory |
| Offline support | Full | SharedPreferences + Firestore cache |

---

## Rollback plan

If Flutter version has critical issues:

1. Keep HTML version as fallback
2. Firebase Hosting can serve both versions
3. Users can switch back to HTML version
4. Data sync ensures no data loss

---

## Related documents

| File | Purpose |
|---|---|
| `AGENTS.md` | Project structure, build commands, rules |
| `ROADMAP.md` | Vision, timeline, how to contribute |
| `CONTRIBUTING.md` | PR workflow, code style |
| `ARCHITECTURE.md` | Original app architecture (reference) |
| `BACKLOG.md` | Decisions taken and postponed |
