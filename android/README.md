# Android build

The APK is a thin WebView shell around the same `index.html` that runs on the web.
The web app is **not** duplicated here — the build workflow copies it from the repository root,
so the APK always carries the current version.

## How it is built

GitHub Actions builds it automatically: see `.github/workflows/android.yml`.
The workflow runs on every change to `index.html` or to anything under `android/`,
and can also be started by hand from the Actions tab.

The finished APK appears as a build artifact named `DorjeMala-apk`.

## What the native side adds

Beyond showing the web app, the shell provides what a web page cannot do on Android:

- **Special-day notifications.** `SpecialDays.java` holds the 2026 calendar, generated from the
  same lunar table the web app uses. A daily alarm at 08:00 checks the date and posts a
  notification when the day is special. No server and no internet connection are involved.
- **Vibration**, through the standard web API, which the shell has permission for.

## Yearly calendar update

`SpecialDays.java` must be regenerated together with the calendar in `index.html`.
See `CALENDAR-UPDATE.md` in the repository root — the Android file is the third place
where the calendar lives, alongside the web app and the bot.

## Signing

The release build is signed with the debug key so the APK installs without extra setup.
For Google Play a real keystore would be required.
