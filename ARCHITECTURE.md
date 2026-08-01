# DorjeMala — architecture and technical reference

**Document version:** 26 July 2026
**Live app:** `manjushri33.github.io/DorjeMala`
**Bot:** `@dorjemala_bot`

---

## 1. What this is

An app for mantra accumulation — a practice where a person counts repetitions over years toward large goals (classically 100,000 or 111,111). Three pillars:

1. **Counter** — a tap counts a repetition, vibration gives feedback so the screen need not be watched.
2. **Retreats** — a bounded period of focused practice with its own dates, mantras and goals.
3. **Tibetan calendar** — special days when the fruits of practice multiply, with reminders through the bot.

Data never vanishes: even after a mantra is deleted, its repetitions remain in the lifetime counter.

---

## 2. Architecture

### 2.1 Overall shape

The app is **one self-contained HTML file** of about 825 KB. It holds everything: markup, styles, logic, fonts and images. No network requests after load except the Telegram script.

```
index.html
├── bundle loader              ~10 KB, unpacks assets into blob URLs
├── __bundler/manifest         base64 assets: fonts, deity images
├── __bundler/ext_resources    React 18 + ReactDOM (production)
└── __bundler/template         the app itself, as a JSON string
    ├── <style>                CSS animations and font faces
    ├── markup                 ~125 KB, x-dc template language
    └── <script type="text/x-dc">
        └── class Component    ~120 KB, all logic
```

### 2.2 Template language

Markup uses the `x-dc` runtime on top of React:

| Construct | Meaning |
|---|---|
| `{{ key }}` | substitutes a value from `renderVals()` |
| `<sc-if value="{{ key }}">` | conditional block |
| `<sc-for list="{{ arr }}" as="it">` | loop |
| `sc-camel-on-click="{{ handler }}"` | click handler |
| `sc-camel-view-box="..."` | the `viewBox` attribute for SVG |

**Critical when editing markup.** camelCase attributes must be written with the `sc-camel-` prefix. Writing `viewBox` directly makes the HTML parser lowercase it to `viewbox`, and the SVG silently fails to render. This is the single most common trap in this codebase.

### 2.3 One class

All logic lives in `class Component extends DCLogic`:

| Block | Purpose |
|---|---|
| `state` | single source of truth: screen, mantras, retreats, statistics, dialog state |
| `T` | dictionary for both languages, including tour texts |
| `HELP` | contextual help texts keyed by `data-guide` anchor |
| `renderVals()` | assembles the object consumed by the markup — effectively a view model |
| `calVals()` / `calValsMemo()` | calendar section, memoized |
| `TOURDEF` | interactive tour script, 53 steps |

`renderVals()` runs on every render and returns roughly 300 keys. The calendar is memoized separately on a key built from screen, language, month and retreat-form state — otherwise every tap on the counter would rebuild the whole month grid.

### 2.4 Why this shape

A one-time bundle into a single file gives: instant start without network, trivial publishing (one file to any static host), identical behaviour in Telegram, the browser and a WebView. The price is that edits are made to a built file rather than to comfortable sources. This is a deliberate trade-off, not an oversight.

---

## 3. Data

### 3.1 Local storage

Key `dm_state` in `localStorage`, one JSON object:

```jsonc
{
  "mantras": [{
    "id": "m1737…",          // permanent, never changes
    "name": "Vajrasattva",   // unique among active and archived
    "text": "OM VAJRA…",
    "img": "data:image/jpeg;base64,…",
    "goal": 100000,
    "malaSize": 108,
    "count": 34500,          // lifetime for this mantra
    "today": 324,
    "malaPos": 47,           // position in the current round
    "lastAt": 1785000000000  // last practice, drives list order
  }],
  "retreats": [{
    "id": "r1737…", "name": "…", "start": "2026-08-01", "end": "2026-08-21",
    "archived": false,
    "items": [{ "id": "ri…", "linkId": "m1737…", "goal": 21000, "count": 0 }]
  }],
  "archivedMantras": [ /* full mantra object + archivedAt */ ],
  "lifetime": { "Vajrasattva": 34500 },
  "log":      { "Vajrasattva": { "2026-07-26": 324 } },
  "vibroOn": true, "lang": "uk", "day": "2026-07-26"
}
```

**Statistics keys.** `lifetime` and `log` are indexed by mantra **name**, not by `id`. This is safe only because of two rules:

1. Duplicate names are rejected — the app offers to restore from the archive or open the existing mantra instead.
2. On rename, `renameStats()` moves both records to the new name.

Without those two rules the approach would be dangerous. With them it is correct and simpler than migrating to `id`.

**Shared counters.** A retreat item carrying `linkId` has no name or image of its own — it points at a main mantra. Repetitions are added to both. In `lifetime` the repetition is counted **once**.

### 3.2 Telegram cloud backup

`cloudBackup()` writes to `tg.CloudStorage` at most once every two minutes. The copy is slimmed — images are stripped, because the limit is 4 KB per key. It is split into 3500-character chunks; the chunk count lives in key `dm_n`.

`cloudRestore()` runs at startup: if local storage is empty and the cloud has data, it pulls it back. This covers a Telegram cache wipe and gives phone ↔ desktop sync.

### 3.3 Limits and error handling

`localStorage` holds about 5 MB. Base64 images take most of it. On overflow `setItem` throws, and the error is **shown to the user** rather than swallowed.

### 3.4 Export and import

The lifetime statistics screen offers **Save a copy** and **Restore from a copy**. Export writes a JSON file with mantras, retreats, archive, lifetime totals and the day log; import replaces the current state with it. This is the only backup available in the web version and the APK, where Telegram cloud storage does not exist.

---

## 4. Screens and behaviour

| Screen | `screen` key | Contents |
|---|---|---|
| Practice | `home` | mantra list, "Continue" card, add button |
| Counter | `counter` | tap area, mala round, progress, image |
| Mantra statistics | `stats` | totals, 14 days, six months, day history |
| Retreats | `retreats` | list of active retreats |
| Retreat | `retreatDetail` | retreat mantras, combined progress |
| Retreat form | `rform` | name, dates via calendar, preset lengths |
| Edit mantra | `edit` | fields, image, delete |
| Pick mantra | `pick` | add an existing mantra to a retreat |
| Lifetime statistics | `life` | all-time total, per mantra, archive |
| Archive | `arch` | completed mantras and retreats |
| Calendar | `calendar` | month grid, legend, upcoming days |
| Calm mode | `zen` | mantra and counter only |

### 4.1 Counter

- **Tap anywhere in the area** — one repetition, short vibration.
- **Completing a mala round** — a stronger vibration and a golden flash.
- **Haptic patterns** live in one place, `vibrate(kind)`: `tap: 120`,
  `mala: [140, 60, 140]`, `goal: [140, 60, 140, 60, 280]` (ms). Inside Telegram
  the native `HapticFeedback` is preferred; `notificationOccurred` is used for
  mala and goal because `impactOccurred` stays silent when the system's
  "vibrate on touch" is off. These values were raised from 70/90 — the original
  set was too faint to feel with the phone resting in the palm.
- **Two fingers on the image** — magnify up to 4×; one finger pans a magnified image. A tap counts a repetition **always**, including while zoomed; a drag beyond 8 pixels does not count.
- Top row: back, help, edit, statistics, calm mode.

**Zoom is never persisted.** It lives in memory only and resets when leaving the practice screen or switching mantras. It is a one-off "let me look closer" gesture, not a setting. The image always opens whole — there is deliberately no "fill the screen" mode that crops the edges, because a thangka should be seen in full.

**The zoom hint** is shown once in the lifetime of the install, on the first practice screen that has an image: an animation of two fingers with a caption. The flag lives in key `dm_zoom_hint`.

### 4.2 Remove versus delete

A single "Delete mantra" button opens a choice:

- **Remove from practice** — the card moves to the archive; everything recited and the history are kept, and it can be restored at any time with the same counter and round position.
- **Delete completely** — the mantra, its `lifetime` and its `log` are all removed.

### 4.3 Contextual help

The **?** button dims the screen, outlines the meaningful elements of the current screen and puts a short caption beside each. Captions are keyed by the `data-guide` attribute, so an element that is not currently on screen is simply skipped. Texts for all screens in both languages live in `HINTS.md` and in the `HELP` map in code.

### 4.4 Retreats

A retreat mantra is either new or taken from the user's list, in which case the counter is shared. Items can be edited, removed (with confirmation) and reordered. When every goal is met, the retreat is archived automatically with a summary screen.

---

## 5. Design system

### 5.1 Colour

| Role | Value |
|---|---|
| Background | `#0b0a10` → `#100e18` (vertical gradient) |
| Card surface | `rgba(255,255,255,.045)` |
| Accent, gold | `#e0c48a`, gradient `#d8b578 → #c9a86a` |
| Primary text | `#f2f0f8` |
| Secondary text | `rgba(232,230,240,.55)` |
| Success, completed | `#6ac98a` |
| Danger | `#c0504e` |

### 5.2 Typography

`Manrope` for the interface, `Cormorant Garamond` (italic) for accent lines and Sanskrit names. Sizes: 66px counter, 16.5px screen title, 15.5px mantra name, 13.5px body, 11.5px letter-spaced captions.

### 5.3 Icons

All control icons are drawn on a **24×24** grid with a stroke width of **1.8**, round caps, colour inherited from the button. 20px inside a button, 15–18px in dense places. Buttons are 38×38 with a 13px radius.

**Never use Unicode text characters as icons.** This codebase previously used `‹ › ▦ ⤢ ∿ ✎ ✕` as controls. They come from different fonts, carry different stroke weights and optical sizes, and on some devices do not render at all. They were replaced with inline SVG.

Calendar symbols (`☸ ✦ ○ ● ◆ ❁`) are the exception: they are content, not chrome.

### 5.4 Motion

Animations are short and restrained: `.13s` tap response, `.25s` layout change, `.3s` message appearance. Background breathing cycles run 6–9 seconds. The rule: during practice the screen should be quiet.

**Startup.** Three things used to happen in a row when the installed app was
opened: the system splash showed the mark, the page's own overlay covered it
with flat `#0b0a10`, and the mark appeared again on the intro screen. It read as
the logo showing twice. The overlay (`#__dc_splash`) now carries the same
`icon.svg`, centred at 86px, so the mark is continuous from launch to first
paint and never blinks out.

### 5.5 Messages

A transient message is a card centred on screen, at most 280px wide, with wrapping text. Duration scales with text length, from 2.2 to 7 seconds.

---

## 6. Calendar

### 6.1 How it works

The source is the `LUNAR2026` table: one Tibetan lunar day per Gregorian day of the year, from the published Rabten calendar (Fire Horse year, Tibetan 2153).

`buildCal()` walks the year and places events:

- **monthly**, by lunar day number: 8 — Tara and Medicine Buddha, 10 — Guru Rinpoche, 15 — full moon, 25 — Dakini, 29 — protectors, 30 — new moon;
- **fixed** festivals from the `FIX` table: Losar, Chotrul, Saga Dawa, Chökhor, Lhabab, Ganden Ngamchö, Dzamling Chisang, eclipses.

**Doubled** (`lhag`) and **skipped** (`chad`) lunar days are handled explicitly: if a day repeats, the festival goes to the second one; if a day is omitted, it goes to the previous one.

### 6.2 Yearly update

The calendar is compiled for 2026 and must be updated by hand once a year — **in two places**: the app and the bot. The full checklist of every location where the year is hard-coded is in `CALENDAR-UPDATE.md`.

After New Year, until the calendar is updated, the app says so plainly rather than pretending it is still July 2026.

---

## 7. Telegram bot

Cloudflare Worker, file `dorjemala-bot-worker.js`.

**What it does:** answers `/start` with a launch button, subscribes via `/start remind`, unsubscribes via `/stop`, and broadcasts special-day reminders daily at 04:00 UTC.

**Required Worker configuration:**

| Type | Name | Purpose |
|---|---|---|
| KV binding | `SUBS` | subscriber list |
| Secret | `BOT_TOKEN` | token from BotFather |
| Secret | `WEBHOOK_SECRET` | protects the webhook from forged updates |
| Cron | `0 4 * * *` | daily broadcast |

**The calendar in the bot is a copy of the one in the app.** This duplication is deliberate: the Worker cannot read the app. Both change during the yearly update.

**Why reminders only go through the bot.** Notifications are unavailable inside a Telegram Mini App by design. The current APK has no notification support either — it holds only the internet and vibrate permissions. On mobile web a service worker would be required, and none is registered. The bot is the only path that works everywhere, provided the person has Telegram.

---

## 8. Build and publishing

### 8.1 Source of truth

**GitHub is the single source of truth.** The local folder and the APK are copies made from it, never the reverse.

```
edit index.html → GitHub (Add file → Upload files) → Pages publishes in 1–3 min
                                ↓
                  Telegram and web update themselves
```

`UPDATE.bat` pulls the current version from GitHub into the folder, keeping the previous one as `index.previous.html`. Run it **before** working, not after.

`START.bat` serves the folder on port 8080 for testing.

### 8.2 How to edit the bundled file

Markup and logic live inside a JSON string in the `__bundler/template` tag. Edits must be made by **targeted replacement inside the escaped text**, not by re-serialising the whole file: the original escapes `</` as `</`, and a round trip through a standard JSON encoder does not reproduce that.

Working procedure:

1. Decode the template.
2. Find the exact fragment to change.
3. Encode both the search and replacement strings with the same escaping rule.
4. Replace exactly one occurrence, and **assert that exactly one was found** — abort otherwise.
5. Only then write the file.

After every edit, run a syntax check on the extracted JavaScript (`node --check`).

### 8.3 Android

The current APK is a WebView wrapper with locally packaged files and only `INTERNET` and `VIBRATE` permissions. Rebuild **only from the GitHub version**.

---

## 9. Known limitations

| Limitation | Cause | Consequence |
|---|---|---|
| Calendar covers 2026 only | table compiled by hand, in three places | yearly update |
| Images live in `localStorage` | no separate store yet | practical ceiling around 10–12 mantras with images |
| No vibration in mobile web on iPhone | Apple exposes no API | works in Telegram on iPhone |
| No notifications in mobile web | push delivery not implemented | Android app notifies on its own; elsewhere the bot does |
| No screen-reader support | project owner's decision | the app targets sighted users |

### 9.0 Where notifications come from

| Platform | Channel |
|---|---|
| Android app | the app itself — a daily alarm checks the built-in calendar, offline, no server |
| Telegram | the bot, once subscribed from the reminders screen |
| Web | the bot, if the person uses Telegram — the service worker is registered and can display a push, but nothing sends one yet |

Wiring up web push would require VAPID keys, a subscription stored server-side, and encrypted delivery from the Worker. Until that exists, **the interface must not promise iPhone notifications.**

Page zoom is allowed — the `maximum-scale` restriction was removed, so the interface can be magnified with two fingers.

### 9.1 ⚠️ What must not be deleted "for cleanliness"

An automated search for unused strings produces about forty false positives. These are **Buddhist festival and day names** in the dictionaries: `losar`, `chotrul`, `sagadawa`, `chokhor`, `lhabab`, `guru`, `dakini`, `tara`, `full`, `newm`, `prot9`, `prot29`, `ganden`, `dzam`, `bad9`, `chad`, `lhag`, `mir`, `eclS`, `eclL`, together with their sub-fields `n`, `d`, `m`, `p` and the contents of `advL` (`hair`, `trade`, `events`).

They **are** used, but access is dynamic — `t.EV[eventType]`, where the type is computed from the calendar. A search by name will not find them.

**Deleting any of them breaks the calendar.** Before pruning dictionaries, always check whether the key belongs to `EV` or `advL`.

---

## 10. Where to take it next

**Near term:** a built-in image library; data export to a file for web and APK; the remaining contextual help polish.

**Later:** algorithmic computation of lunar days instead of a yearly table; daily goals; showing progress in mala rounds rather than percentages; wake lock so the screen does not sleep during practice.

**What to be careful with.** Practice is not fitness. The day streak is shown but never punishes a gap: no red badges, no guilt. That is a deliberate decision, not an unfinished feature.

---

## 11. Companion documents

| File | Contents |
|---|---|
| `ARCHITECTURE.md` | this file — architecture, data, design, build |
| `DESIGN.md` | the landing page — a separate design system from the app, do not mix them |
| `PRODUCT.md` | audience, voice, and the rule for using thangkas |
| `BACKLOG.md` | decisions taken and postponed |
| `CALENDAR-UPDATE.md` | yearly calendar update checklist |
| `HINTS.md` | contextual help texts for every screen |
| `dorjemala-bot-worker.js` | bot code with setup instructions at the end |

**Maintenance rule.** Every functional change updates this document in the same pass: a new feature goes in section 4, a data change in section 3, a visual change in section 5. Anything removed from the code is removed from the documentation too.
