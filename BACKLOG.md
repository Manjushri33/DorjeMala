# Backlog — decisions taken and postponed

> **For Claude: read this before proposing platform work.** Several things below were
> deliberately decided against, not overlooked. Do not re-suggest them as new ideas.

Last updated: 1 August 2026.

---

## Current decision: Android only

The product is the **Android APK**. The web page stays as the way people install it,
not as a platform in its own right.

**iPhone is dropped for now.** Nothing on iOS is being built, tested or promised.
The reasoning is recorded below so the decision can be revisited on evidence rather
than re-argued from scratch.

What is still in the code and was left alone on purpose: the landing page keeps its
"Add to Home Screen" hint for iOS visitors. It costs nothing and an iPhone visitor
who ignores it simply sees the counter in Safari. Strip it only if the project
decides to turn iPhone users away outright.

---

## Postponed: iPhone support

Whoever picks this up should know that on iOS **there is no free distribution route
at all**. Every path outside the App Store — TestFlight, Ad Hoc, Enterprise — still
requires the $99/year Apple Developer membership. Alternative marketplaces such as
AltStore PAL exist only in the EU, Japan, Brazil, Australia and the UK; Ukraine is
not covered. Sideloading with a free Apple ID re-signs every seven days and needs a
computer, which no ordinary user will do. So the realistic choice is: a web app added
to the Home Screen, for nothing, or $99 a year.

A Mac is **not** required to build for iOS — GitHub Actions provides macOS runners,
the same place the APK is already built. The membership fee is the real obstacle.

### 1. Vibration is the real loss, not notifications

The counter is designed to be used without looking: the phone rests in the palm and
answers each tap with a short vibration. Safari has no Vibration API and Apple exposes
no substitute, so on iPhone the whole interaction model collapses into "watch the
screen".

The partial remedy is **sound**: Web Audio works on iOS, so a quiet click on each tap
and a fuller tone on a completed mala restores the feedback through the ear instead of
the hand. Not equivalent, but it keeps the eyes free — and with headphones it may even
suit some practitioners better. Any iPhone effort should start here, not with features.

### 2. Storage can be evicted

Safari clears site storage that has not been touched for a while, and evicts under
disk pressure. Home Screen web apps get gentler treatment, but no guarantee. All
counters and history live in that storage.

Three layers of defence, in increasing strength:

| Layer | Effect | Cost |
|---|---|---|
| `navigator.storage.persist()` | exempts storage from automatic eviction | a few lines |
| warn when not persisted | offer the existing file export before data is at risk | ~1 hour |
| copy off the device | the only real guarantee — see sync below | ~1 day |

Note the useful coincidence: **Safari grants persistent storage only once notification
permission is granted.** One permission buys both reminders and data safety.

Neither survives the user deleting the icon — that removes the data with it, exactly
as for a native app.

### 3. The permission prompt — one shot only

iOS shows its notification prompt **once**. A refusal cannot be re-asked
programmatically; the user must go into Settings, which nobody does. So the prompt
must never be spent carelessly.

The sequence:

1. Check the app is running from the Home Screen. In a Safari tab the Push API does
   not exist at all — show the install hint instead and stop.
2. Show **our own** card first, in the app's own language: reminders on special days,
   and practice history that will not vanish. Buttons: *Allow* and *Not now*.
3. *Not now* must not call the system API at all. The shot is preserved and we can ask
   again later.
4. *Allow* calls `Notification.requestPermission()` **inside the same tap** — iOS
   requires a user gesture.
5. On success: `navigator.storage.persist()`, then subscribe to push. No further
   dialogs; the user sees only "done".

**When to ask:** not on first launch, when the person has invested nothing and will
refuse. After the first completed mala, or on the second day of practice — by then
"so this is not lost" refers to something real.

### 4. Web push — the hard part is already done

`tools/webpush/webpush.js` implements RFC 8291 (aes128gcm) and RFC 8292 (VAPID) on
bare WebCrypto, with no dependencies. It runs unchanged in Cloudflare Workers.

`tools/webpush/webpush.test.mjs` verifies it by playing the receiving browser:
generating subscription keys, encrypting as the server would and decrypting back.
Twelve checks, all passing, including rejection of tampered ciphertext and
verification of the VAPID signature. Run it with `node webpush.test.mjs`.

Still missing:

- VAPID key pair — generate with `tools/vapid-keys.html`, which runs entirely in the
  browser. Private key goes into the Cloudflare secret `VAPID_PRIVATE` and nowhere
  else; public key into `VAPID_PUBLIC` and into the app.
- Subscribe and unsubscribe endpoints in the bot worker, storing into the existing
  `SUBS` namespace under a `wp:` prefix. **The Telegram broadcast loop lists that same
  namespace — it must skip the new prefix, or it will try to send Telegram messages to
  push subscriptions.**
- CORS on the worker for the app origins.
- Extend the existing daily cron to send push alongside Telegram. The calendar, the
  event table and the paging are already there.
- A notifications button in the app, wired to the permission sequence above.

The `push` and `notificationclick` handlers in `sw.js` are already written and expect
`{title, body, tag}`.

---

## Next up on the landing page

**Two layouts, chosen automatically.** *Still open — the only landing item left.*
The page is mobile-first at 520 px and simply sits in the middle of a desktop screen. A desktop visitor should get a layout built
for the width — the phone mock beside the text rather than under it, features in a
row, the QR panel folded into the page instead of floating over it. Same file, media
queries, no separate desktop URL.

**Real screenshots — done.** Both language sets are captured from the running
app and live in `shots/` (Ukrainian) and `shots-en/` (English); the gallery
swaps folders with the language pill. The capture recipe, including the
scrollbar the raw captures bake in, is in [`DESIGN.md`](DESIGN.md) section 6.

**Design skills the project owner wants installed.** Neither is in the Claude plugin
registry, so they are installed by hand, not by the agent:

- [impeccable](https://github.com/pbakaus/impeccable) — `/plugin marketplace add pbakaus/impeccable`, then install from `/plugin`. Or `npx impeccable install` from the project root.
- [ui-ux-pro-max-skill](https://github.com/nextlevelbuilder/ui-ux-pro-max-skill).

---

## Postponed: everything else

**Sync between devices.** Random 128-bit key shown as text and QR instead of accounts;
data in Cloudflare KV. The hard part is not transport but merging: counters grow on
both devices, so last-write-wins loses recitations. `log[name][date]` holds daily
figures and merges as a per-date maximum, with `count` recomputed. Deletions need
`updatedAt` and tombstones, otherwise a mantra removed on one device returns from the
other. This matters more on iPhone than on Android, where app storage is safe.

**`targetSdk 36`.** Currently 34. Google Play requires Android 16 for new apps from
31 August 2026. Needed even without Play, simply to keep the build current. Check the
reminder alarms — the rules for exact alarms changed between 34 and 36.

**A LICENSE file.** There is none. F-Droid and IzzyOnDroid both refuse without one:
code without a licence is not formally free.

**The signing key.** `android/keystore/dorjemala.jks` sits in the repository with the
password `dorjemala` in `build.gradle`. That was a deliberate simplification for
hand-to-hand distribution. It also means anyone can build a forged version that
installs over the real one as an update. Move it to GitHub Secrets before the app is
distributed any wider.
