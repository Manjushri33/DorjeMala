# DorjeMala Flutter — Setup Guide for Beginners

This guide is for someone who has never used Flutter before.
It explains what we have, what we are building, and how to learn step by step.

---

## What we have now

**DorjeMala** is a mantra counter for Buddhist practice.
It is one HTML file (`app.html`) — about 825 KB.
Everything is inside: styles, logic, images, fonts.

**What it does:**
- Tap to count mantras (vibrates on each tap)
- Retreats — focused practice periods with goals
- Tibetan calendar — special days, festivals, moon phases
- Statistics — lifetime total, daily progress, charts
- Two languages: Ukrainian and English

**Where it runs:**
- In a browser (open the link and use it)
- Inside Telegram (as a Mini App)
- On Android (as an APK file)

**What it cannot do:**
- No cloud sync — if you clear browser data, everything is lost
- No real cross-platform — iPhone users get a web page, not an app
- No push notifications on web
- Hard for other developers to contribute (one file, one class)

---

## What we are building

A **Flutter** version of the same app.

**What is Flutter?**
Flutter is a way to build apps for phones, computers, and the web — using one code.
You write code once, and it runs everywhere: Android, iPhone, Windows, Mac, Linux, Web.

**What is the same:**
- Same design (dark theme, gold accent)
- Same features (counter, retreats, calendar, statistics)
- Same data (mantras, retreats, history)

**What is different:**
- Cloud sync — data saves to the internet, not just your phone
- Real cross-platform — works on Android, iPhone, and web
- Push notifications — reminders even when the app is closed
- Image library — built-in collection of thangkas
- Easy to contribute — other developers can help

**The stack:**
- **Flutter** — the app itself (Dart language)
- **Firebase** — cloud database, user accounts, notifications
- **Supabase** — image storage (thangkas)

---

## Why learn Flutter

Flutter is one of the most popular ways to build apps right now.
Companies like Google, BMW, Toyota, and Alibaba use it.

**What you will learn:**
1. How to build a real app (not a toy project)
2. How to work with cloud services (Firebase, Supabase)
3. How to publish to app stores (Google Play, F-Droid)
4. How to collaborate with other developers (Git, PRs)
5. How to read documentation and follow best practices

**What you can do after:**
- Build your own apps
- Contribute to open source projects
- Get a job as a junior Flutter developer
- Freelance — build apps for clients

---

## How this project helps you learn

### Level 1: Basic setup (Day 1)
- Install Flutter on your computer
- Clone the repository
- Run the app in Chrome
- See how it works

### Level 2: First code (Week 1)
- Change the counter text
- Add a new button
- Change the colors
- Run it again and see the changes

### Level 3: First feature (Week 2-3)
- Add a new screen
- Connect it to the counter
- Save data locally
- Test on your phone

### Level 4: Cloud (Week 3-4)
- Set up Firebase
- Save data to the cloud
- Add user accounts
- Sync between devices

### Level 5: Publishing (Week 4-5)
- Build the Android APK
- Upload to F-Droid or Google Play
- Share with friends
- Get feedback

### Level 6: Advanced (Month 2-3)
- Add push notifications
- Build an image library
- Optimize performance
- Write tests

---

## The setup prompt

Copy everything between the markers below into Claude on your computer.
Claude will guide you through every step.

---

## === COPY FROM HERE ===

I want to learn Flutter by building the DorjeMala mantra counter app.
The app currently exists as one HTML file (app.html).
I want to rewrite it in Flutter to learn cross-platform development.

Please help me set up the development environment on Windows.

### Step 1: Check what I have

Run these commands and show me the output:

```
flutter --version
java -version
git --version
```

If Flutter is not installed, say "Flutter is not installed" and I will tell you how to install it.

### Step 2: Install Flutter (if needed)

1. Go to https://docs.flutter.dev/get-started/install/windows/mobile
2. Download Flutter SDK
3. Extract to C:\flutter\
4. Open a new terminal and run:
```
C:\flutter\bin\flutter doctor
```
5. Tell me what it says — it will show what is missing

### Step 3: Install Android Studio (if needed)

1. Go to https://developer.android.com/studio
2. Download and install
3. Open Android Studio → SDK Manager → install Android 14 (API 34)
4. Run `flutter doctor` again

### Step 4: Clone the project

```
git clone https://github.com/dyaroshevich/DorjeMala.git
cd DorjeMala
```

### Step 5: Read the documentation

Open these files and read them:
- `ROADMAP.md` — what we are building and why
- `AGENTS.md` — project structure and rules
- `FLUTTER-MIGRATION.md` — how we migrate from HTML to Flutter
- `SETUP-PROMPT.md` — this file

### Step 6: Run the app

For now, just run the HTML version:
```
start index.html
```

Tell me if you see the mantra counter in your browser.

### Step 7: Firebase setup (we will do together later)

For now, just create an account:
1. Go to https://console.firebase.google.com
2. Sign in with Google
3. Click "Create a project"
4. Name it: `dorjemala-flutter`
5. Follow the wizard
6. When done, click the Web icon (</>) and add a Web app
7. Copy the config object — I will need it later

### Step 8: Supabase setup (we will do together later)

For now, just create an account:
1. Go to https://supabase.com
2. Sign up with GitHub
3. Click "New project"
4. Name: `dorjemala-storage`
5. Region: West Europe
6. When done, go to Settings → API
7. Copy the Project URL and anon key — I will need them later

### What I need from you

1. Output of `flutter --version`
2. Output of `flutter doctor`
3. Did you see the HTML app in the browser?
4. Firebase project URL (when ready)
5. Supabase URL + anon key (when ready)

### Questions?

Ask me anything. I will help you through every step.
Take your time — there is no rush.

## === COPY TO HERE ===

---

## After the setup

Once Claude has set up your environment, here is what happens next:

1. **You get a Flutter project** — a folder with Dart code
2. **You run it in Chrome** — see the mantra counter
3. **You change something** — text, color, button
4. **You run it again** — see your changes
5. **You build a new feature** — a new screen, a new button
6. **You connect to Firebase** — data saves to the cloud
7. **You build the APK** — install on your phone
8. **You publish** — share with the world

Every step teaches you something new.
Every mistake teaches you more.

---

## Tips for beginners

1. **Don't rush.** Take one step at a time.
2. **Read error messages.** They tell you exactly what is wrong.
3. **Google everything.** If you don't understand, search for it.
4. **Ask Claude.** Paste the error and ask "what does this mean?"
5. **Break things.** If it works, try to break it and see what happens.
6. **Build small.** One button, one screen, one feature at a time.
7. **Celebrate small wins.** Every working feature is a victory.

---

## What you will be able to do after this project

| Skill | What it means |
|---|---|
| Flutter basics | Build apps for phone, web, desktop |
| Dart programming | A language like JavaScript but with types |
| Firebase | Cloud database, user accounts, notifications |
| Supabase | Image storage, file uploads |
| Git + GitHub | Version control, collaboration |
| PR workflow | How real teams work together |
| App publishing | Google Play, F-Droid, App Store |

---

## Ready?

Copy the prompt above into Claude.
Follow the steps.
Ask questions.
Learn by doing.

You've got this.
