# DorjeMala Flutter — Setup for Claude

Copy everything between the lines below into Claude on your Windows computer.
Claude will set up the development environment step by step.

---

## === COPY FROM HERE ===

I need to set up a Flutter development environment for the DorjeMala mantra counter app.
The app is being rewritten from HTML (app.html) to Flutter. Here is what I need:

### About the project

- Original app: https://manjushri33.github.io/DorjeMala
- Source code: https://github.com/Manjushri33/DorjeMala
- My fork: https://github.com/dyaroshevich/DorjeMala
- It's a mantra counter for Buddhist practice
- Runs in Telegram, browser, and as Android APK

### What to do

**Step 1: Check if Flutter is installed**

Run this command and tell me the output:

```
flutter --version
```

If Flutter is not installed, download it from:
https://docs.flutter.dev/get-started/install/windows/mobile

**Step 2: Check Android Studio**

Run:
```
flutter doctor
```

Tell me what it says. If Android Studio is missing, download from:
https://developer.android.com/studio

**Step 3: Clone the repo**

Run:
```
git clone https://github.com/dyaroshevich/DorjeMala.git
cd DorjeMala
```

**Step 4: Read the documentation**

Read these files in the repo:
- `ROADMAP.md` — what we are building and why
- `AGENTS.md` — project structure and rules
- `FLUTTER-MIGRATION.md` — how we migrate from HTML to Flutter
- `CONTRIBUTING.md` — how to work together

**Step 5: Set up Firebase (I will guide you)**

1. Go to https://console.firebase.google.com
2. Click "Create a project"
3. Name: `dorjemala-flutter`
4. Follow the setup wizard
5. When done, click the Web icon (</>) to add a Web app
6. App name: `dorjemala-web`
7. Copy the config object that looks like this:
```
const firebaseConfig = {
  apiKey: "...",
  authDomain: "...",
  projectId: "...",
  ...
};
```
8. Send me the config — I will put it in the code

Then:
1. In Firebase Console, go to Authentication → Get started
2. Enable "Anonymous" sign-in
3. Enable "Google" sign-in
4. Go to Firestore Database → Create database
5. Choose "Start in test mode"
6. Pick a location (closest to Ukraine: europe-west or europe-central)

**Step 6: Set up Supabase (I will guide you)**

1. Go to https://supabase.com
2. Sign up with GitHub
3. Click "New project"
4. Name: `dorjemala-storage`
5. Database password: (choose something strong)
6. Region: West Europe (closest)
7. When done, go to Settings → API
8. Copy:
   - Project URL (looks like: https://xxxxx.supabase.co)
   - anon/public key (long string)
9. Send me both

Then:
1. Go to Storage → Create bucket
2. Name: `thangkas`
3. Make it public
4. Click Create

**Step 7: Test the setup**

Run:
```
flutter doctor -v
```

Copy the entire output to me. I will check if everything is ready.

### What I need from you

1. Firebase config object
2. Supabase URL + anon key
3. Output of `flutter doctor -v`

Once I have these, I will write the code and send it to you.

### Questions?

Ask me anything. I will help you through every step.

## === COPY TO HERE ===
