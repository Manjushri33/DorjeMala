# DorjeMala — product context

Written for `/impeccable`. Product truth only; visual decisions live in DESIGN.md.

## What it is

A mantra counter for Buddhist practice. It counts recitations, holds retreats
with their own dates and goals, and carries a Tibetan calendar of special days.
Everything is stored on the device. The app is a single HTML file (`app.html`)
installed as a web app; `index.html` is the landing page whose only job is to
get that installation to happen.

## Audience

Practitioners of any level — someone who has taken refuge and is accumulating
ngöndro, and someone who has just been told to recite a hundred thousand and
does not yet know how to keep count. The page must not gatekeep either way: no
insider jargon that excludes the newcomer, no explaining what a mala is to
someone who has worn one for twenty years.

## Voice

**Contemplative and quiet, like a prayer book.** Not a wellness app, not a
productivity tool, not a startup. The page should feel like something that was
set in type rather than assembled from components. Short sentences. No
exclamation, no urgency, no growth language.

## Anti-references

- Habit trackers and streak-driven wellness apps
- Generic dark-mode SaaS landings: purple-blue gradients, glass cards, neon glow
- Anything that treats practice as self-optimisation

## Material

The project owns a set of thangkas in `uploads/img/` — Vajrasattva, Amitabha,
Guru Rinpoche, the Dakini, Medicine Buddha, the protectors, Shakyamuni,
Tsongkhapa. All of them may be used on the site.

They are devotional images, not decoration. The rule that follows from that:
a thangka is **framed and shown whole**, never cropped into a texture, never
washed out under a dark scrim, never tiled or used as a background pattern.
Framing it in an arch — the shape of a shrine niche — is the sanctioned move.

## The one thing the page must do

Get the visitor to install. One action: **Встановити / Install**. There is no
APK any more, no Telegram, no second route. Everything else on the page exists
to earn that single tap.

## Constraints

- Bilingual Ukrainian and English, switched by a pill in the header. Every
  string ships in both; body type must carry Cyrillic.
- Mobile is the product. Desktop matters, but never at mobile's expense.
- Static file on GitHub Pages. No build step, no framework, no dependencies.
- Screenshots of the app live in `shots/`, 512×1060, captured from the real app.
