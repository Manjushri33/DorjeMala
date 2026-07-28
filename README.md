# DorjeMala — mantra counter

A mantra counter for Buddhist practice: accumulation, retreats, and the Tibetan calendar. Runs as a Telegram Mini App, as a web page, and as an Android app.

**Open:** [manjushri33.github.io/DorjeMala](https://manjushri33.github.io/DorjeMala)
**Telegram:** [@dorjemala_bot](https://t.me/dorjemala_bot)

---

## Features

**Counter.** Tapping anywhere in the counting area adds one repetition. Every tap gives a short vibration, completing a mala round gives a stronger one, reaching the goal stronger still. This is what lets you count without looking at the screen, with the phone resting in your palm.

**Practice image.** A thangka can be attached to a mantra and becomes the counter background. Pinch with two fingers to magnify up to 4× and study a detail — taps keep counting while zoomed, because practice should not be interrupted.

**Retreats.** A separate period of focused practice with its own dates, mantras and goals. A mantra can be taken from your own list, in which case repetitions count toward both the retreat and your lifetime total. When every goal is reached, the retreat moves to the archive on its own.

**Tibetan calendar.** Guru Rinpoche and Dakini days, full and new moons, the four Düchen festivals, eclipses — each with a description, practice advice and a legend of markers. When creating a retreat, the calendar suggests auspicious start dates.

**Reminders** about special days are delivered by the Telegram bot.

**Statistics.** Lifetime total, progress toward the goal, day streak, a two-week chart, a six-month heat map, and a day-by-day history.

**Two languages** — Ukrainian and English, detected automatically.

---

## Data

Everything is stored on the device in browser storage. Nothing is sent to third-party servers.

**Backup.** Inside Telegram, counters, history and retreats are copied automatically to the user's Telegram cloud storage. This protects against a cache wipe and gives free sync between phone and desktop. Images are not included — they are too large for the 4 KB per-key limit.

**Nothing disappears silently.** A mantra can be removed from the practice screen: it moves to the archive together with everything recited, and can be restored at any time with the same counter and position in the current round. Full deletion, including history, is a separate action behind an explicit warning.

---

## Project files

**The three things that actually run**

| File | Purpose |
|---|---|
| `index.html` | the entire web app in a single file — this is the product |
| `DorjeMala.apk` | the Android build, produced automatically from `index.html` |
| `dorjemala-bot-worker.js` | the Telegram bot — a Cloudflare Worker |

**Documentation**

| File | Purpose |
|---|---|
| `README.md` | this file — start here |
| `ARCHITECTURE.md` | architecture, data model, design system, how to edit the bundle |
| `CALENDAR-UPDATE.md` | the yearly calendar update, step by step |
| `HINTS.md` | contextual help texts for every screen |

**Folders**

| Folder | Contents |
|---|---|
| `android/` | the Android project, built by GitHub |
| `.github/` | the automatic APK build |
| `imajes/` | source images |
| `tools/` | local helper scripts, not part of the repository |
| `archive/` | previous versions of the app, kept in case a rollback is needed |

**Files the website needs next to `index.html`**

`manifest.json`, `sw.js`, `icon.svg` — these must stay in the root; the site loads them by path.

## Helper scripts (`tools/`)

| Script | What it does |
|---|---|
| `UPDATE.bat` | pulls the live version from GitHub into this folder; the previous copy goes to `archive/versions/` |
| `GET-APK.bat` | downloads the latest built APK into this folder |
| `START.bat` | serves the folder on `localhost:8080` for testing |

---

## Running and publishing

Locally: `START.bat` serves the folder on `localhost:8080`.

Publishing: upload `index.html` to the repository. GitHub Pages refreshes the site within one to three minutes; Telegram and the web version pick it up automatically.

**GitHub is the single source of truth.** The local folder and the APK are copies made from it, never the other way round. Run `UPDATE.bat` before starting work.

Technical details, the data schema and the rules for editing the bundled file are in [`ARCHITECTURE.md`](ARCHITECTURE.md).

---

## Calendar

The calendar is compiled for 2026 (Rabten, Fire Horse year, Tibetan 2153) and is updated by hand once a year — **in two places at once**: in the app and in the bot. Full checklist in [`CALENDAR-UPDATE.md`](CALENDAR-UPDATE.md).

Until the calendar is updated, the app says so honestly instead of showing dates from the wrong year.

---

## Images

The thangkas in `imajes/` come from open sources. If you use this project as a base, check the terms of each image separately.
