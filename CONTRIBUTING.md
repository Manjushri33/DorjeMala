# Contributing to DorjeMala

Thank you for contributing! This guide covers the workflow, code style, and PR format.

---

## Prerequisites

### For Flutter development

| Tool | Path | Version |
|---|---|---|
| Flutter SDK | `K:\program\flutter\flutter\` | 3.44.6 |
| Dart SDK | (bundled) | 3.12.2 |
| Java JDK | `K:\program\jdk17\` | 17 |
| Android SDK | `K:\program\android-sdk\` | — |
| VS Code | (or Android Studio) | Latest |

### For HTML/JS development (original app)

| Tool | Purpose |
|---|---|
| Node.js | Running `UPDATE.bat`, `START.bat` |
| Chrome | Testing the app |

---

## Branching strategy

```
main                    ← stable, deployable
├── feature/counter     ← new features
├── fix/telegram-grey   ← bug fixes
└── experiment/sync     ← experiments
```

### Rules

1. **`main` is always working.** Never push broken code.
2. **Feature branches** for new work. Name them `feature/<short-description>`.
3. **Fix branches** for bug fixes. Name them `fix/<short-description>`.
4. **Delete branches** after merge.

### Creating a branch

```bash
git checkout main
git pull upstream main
git checkout -b feature/my-feature
```

---

## PR workflow

### 1. Before you start

```bash
# Sync with upstream
git fetch upstream
git checkout main
git merge upstream/main
```

### 2. Make changes

```bash
# Work on your feature
git add .
git commit -m "feat: add counter screen"
```

### 3. Push and create PR

```bash
git push origin feature/my-feature
```

Then go to GitHub and create a Pull Request from `feature/my-feature` → `main`.

### 4. PR title format

```
feat: add counter screen
fix: fix Telegram grey screen on Android
docs: update AGENTS.md
refactor: extract counter logic to provider
test: add widget tests for counter
```

### 5. PR description

```markdown
## What
Brief description of the change.

## Why
Why this change is needed.

## How
Technical approach (if not obvious).

## Testing
How to test this change.

## Screenshots
Before/after (if UI changes).
```

### 6. Review

- At least one review before merge
- Address all comments
- Squash if commit history is messy

---

## Code style

### Dart / Flutter

```dart
// Use const constructors when possible
const Text('Hello')

// Prefer final for local variables
final name = 'Vajrasattva';

// Use trailing commas for better formatting
return Scaffold(
  body: Center(
    child: Text('Hello'),
  ),
);

// Name files in snake_case
counter_screen.dart
counter_provider.dart

// Name classes in PascalCase
class CounterScreen extends StatelessWidget { }

// Name variables and functions in camelCase
int mantraCount = 0;
void incrementCount() { }

// Name constants in camelCase (Dart convention)
static const tapDuration = Duration(milliseconds: 130);
```

### File structure

```
lib/
├── core/           # Config, services, models, utils, theme
├── features/       # Feature-specific screens and providers
└── shared/         # Reusable widgets
```

### Naming conventions

| Type | Convention | Example |
|---|---|---|
| File | `snake_case.dart` | `counter_screen.dart` |
| Class | `PascalCase` | `CounterScreen` |
| Variable | `camelCase` | `mantraCount` |
| Function | `camelCase` | `incrementCount()` |
| Constant | `camelCase` | `tapDuration` |
| Enum | `PascalCase` | `ScreenState` |
| Enum value | `camelCase` | `ScreenState.home` |

---

## Testing

### Run tests

```bash
flutter test
```

### Run analyzer

```bash
flutter analyze
```

### No warnings allowed

```bash
flutter analyze --fatal-infos
```

---

## Commit messages

```
feat: add counter screen
fix: fix Telegram grey screen on Android
docs: update AGENTS.md
refactor: extract counter logic to provider
test: add widget tests for counter
chore: update dependencies
style: format code
perf: optimize image loading
ci: add GitHub Actions workflow
```

### Rules

1. **Use conventional commits** (feat, fix, docs, etc.)
2. **Lowercase** subject line
3. **No period** at the end
4. **Imperative mood** ("add" not "added")
5. **50 characters max** for subject line

---

## What to work on

### For beginners

- Fix typos in documentation
- Add comments to complex code
- Write tests for existing features
- Improve error messages

### For intermediate developers

- Implement new screens (start with `counter_screen.dart`)
- Add Provider state management
- Set up Firebase integration
- Add Telegram Mini App support

### For advanced developers

- Optimize performance
- Add accessibility support
- Implement sync strategy
- Set up CI/CD pipeline

---

## Getting help

1. **Read `AGENTS.md`** for project structure and build commands
2. **Read `ROADMAP.md`** for the full vision
3. **Read `FLUTTER-MIGRATION.md`** for technical details
4. **Open an issue** on GitHub
5. **Ask on Telegram** — @dorjemala_bot

---

## Code of Conduct

1. Be respectful
2. Be constructive
3. Be patient (we are all learning)
4. Be helpful

---

## License

By contributing, you agree that your contributions will be licensed under the same license as the project (once a LICENSE file is added).
