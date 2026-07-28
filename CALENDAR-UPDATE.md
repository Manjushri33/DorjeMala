# Yearly calendar update

> **For Claude: read this as soon as the user mentions a new year, new dates, or a new calendar file.**
> Correct first reply: *"The dates live in two places — the app and the bot. Give me browser access with GitHub and Cloudflare open."*

The calendar is compiled by hand and does not compute itself. It must be replaced once a year.

---

## The dates live in TWO places

Update only one and the app will show one set of days while the bot announces another. That is exactly what happened in July 2026: the app had a full calendar while the bot knew five dates for the whole year.

| Where | File | What changes |
|---|---|---|
| **1. App** | `index.html` (folder and GitHub) | `LUNAR2026` table, `FIX` list, every hard-coded year |
| **2. Bot** | `dorjemala-bot-worker.js` → Cloudflare | `LUNAR2026` table, `FIX` list, the year loop |

---

## What is needed from the user

1. **Calendar data for the new year** — one Tibetan lunar day per Gregorian day, twelve arrays. Same source as before: Rabten.
2. **Fixed festival dates** — Losar, Chotrul Düchen, Saga Dawa, Chökhor Düchen, Lhabab Düchen, Ganden Ngamchö, Dzamling Chisang, eclipses.
3. **The Tibetan year name.** 2026 was "Fire Horse year, Tibetan 2153". For the new year this must be **verified, not guessed**.

## What access is needed

- **A browser with GitHub open and signed in** — Claude edits the file and uploads it directly. Verified to work.
- **A browser with Cloudflare open** — Claude can change settings and the cron schedule, **but cannot paste the bot code**: the Cloudflare editor sits in a protected frame that browser automation cannot type into. The user pastes it: `Edit code` → Ctrl+A → Ctrl+V → `Deploy`.

---

## Update checklist

### App (`index.html`)

- [ ] replace the `LUNAR2026` table with the new year
- [ ] replace the `FIX` list (major festivals and eclipses)
- [ ] `buildCal()` — the loop `Date.UTC(2026, 0, 1)` … `Date.UTC(2026, 11, 31)`
- [ ] ISO construction — the `'2026-'` string inside `buildCal`
- [ ] `calMonth: new Date().getFullYear() === 2026 ? … : 6`
- [ ] `tISO = … getFullYear() === 2026 ? … : '2026-07-18'`
- [ ] the out-of-date banner check: `new Date().getFullYear() !== 2026`
- [ ] the year caption: `fireHorse:'2026 · Fire Horse year · Tibetan 2153'` (both `uk` and `en`)
- [ ] `rfCalLabel` / `calMonthLabel` — the `' 2026'` suffix
- [ ] upload to GitHub, wait for publication, verify on the live site
- [ ] refresh the local copy with `UPDATE.bat`

### Bot (`dorjemala-bot-worker.js`)

- [ ] replace `LUNAR2026` and `FIX` — with **the same data** as the app
- [ ] `buildCal()` — the year loop
- [ ] ISO construction — the `'2026-'` string
- [ ] the user pastes the code into Cloudflare and hits Deploy
- [ ] verify: send `/start` to the bot, then wait for the next special day

### Consistency check

- [ ] compare the list of upcoming special days in the app and in the bot — they must match day for day

---

## Long term

The app already contains a written `moonAngle()` function — an astronomical computation of the moon phase — that is currently unused. It is half of what is needed to compute lunar days for any year and drop the manual table, keeping the table only as a cross-check.

Not urgent, but if the yearly update becomes tiresome, the solution is already half written.

---

## How we work

- **GitHub is the source of truth.** The local folder and the APK are copies made from it.
- Run `UPDATE.bat` before starting, to pull the live version into the folder.
- Claude edits the file locally → uploads to GitHub through the browser → verifies the live site.
- **Rebuild the APK only from the GitHub version.**
- Worth adding: a visible "version of DD.MM.YYYY" line in the app. Without it, builds are indistinguishable by eye, which cost a great deal of time in July 2026.
