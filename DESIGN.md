# DESIGN.md — the landing page

Visual decisions for `index.html`. Product truth lives in [`PRODUCT.md`](PRODUCT.md);
the app bundle is documented in [`ARCHITECTURE.md`](ARCHITECTURE.md).

The landing page and the app do **not** share a design system. The app is dark
violet, sans-serif, rounded — a tool. The page is wood, parchment and brass,
all serif — a prayer book that happens to have an install button. That is
deliberate: the page has to earn a tap from a stranger, the app has to be
invisible during practice.

---

## 1. The world

**Academia / scholarly mobile.** Lacquered wood, parchment and brass. Everything
sits either on the ground or on an oak surface; nothing floats, nothing glows.

| Token | Value | Used for |
|---|---|---|
| `--ground` | `#1C1714` | page background |
| `--oak` | `#251E19` | raised surfaces |
| `--leather` | `#3D332B` | frames |
| `--rule` | `#4A3F35` | hairlines, borders |
| `--parchment` | `#E8DFD4` | primary text, 13.5:1 on ground |
| `--ink` | `#9C8B7A` | secondary text, 5.4:1 — AA at any size |
| `--brass` | `#C9A962` | accents, the install button, 7.9:1 |
| `--brass-lit` | `#E3C98D` | hover / lit state |
| `--seal` | `#8B2635` | 2.05:1 — **non-text only**, never a label |

Corner radius is **4px** everywhere: a shrine is joinery, not a rounded card.
The single exception is the arch (below).

One inversion on the whole page: the closing block is set on parchment, a page
from the book. Nowhere else do the values flip.

## 2. Typography

Two families, both carrying Cyrillic. **No sans-serif anywhere on the page.**

- **Cormorant Garamond** — every display line, every small-caps label.
- **Literata** — reading text, and all numerals.

Rejected and why: Crimson Pro and Cinzel have no Cyrillic, so a Ukrainian
heading would silently fall back mid-sentence.

**Numerals.** Cormorant ships old-style figures — its `7` and `3` drop below
the baseline (measured descent: 28 units against Literata's 1). In the counter,
where digits change under the eye, that reads as a rendering bug. So the tally
uses Literata with `font-variant-numeric: lining-nums tabular-nums` plus the
matching `font-feature-settings`, and tabular figures keep the number from
jittering as it counts.

## 3. The arch

A thangka is a devotional image, not a texture. The rule from PRODUCT.md:
**framed and shown whole** — never cropped into a background, never washed out
under a scrim, never tiled. Framing it in an arch, the shape of a shrine niche,
is the sanctioned move.

```css
border-radius: 999px 999px 4px 4px;
```

`999px` clamps to a true semicircle at any width, so the arch stays correct
from 182px thumbnails to a full-bleed hero without per-breakpoint values. A
vignette (`inset box-shadow`) sits the image *in* the wood rather than on it.

`shots/niche.jpg` is the thangka alone — cropped `(34, 130, 486, 812)` out of a
counter capture, so no app chrome and no page background show inside the frame.

## 4. The live counter

The niche is tappable across its whole area, and it counts, with vibration —
the one place a visitor feels the product before installing it.

- **27 beads in one row.** Each tap lights exactly one. The row fills, resets,
  and starts again: four times round a 27-bead wrist mala is 108.
- Two earlier attempts were wrong and are recorded so they are not repeated:
  27 beads standing for 4 recitations each meant four taps lit one dot, which
  reads as broken; 108 beads in two wrapped rows fixed the feedback but looked
  like a progress bar and left a stub row.
- The numeral below still counts to 108 (`MALA n / 108`).
- Vibration matches the app: `120ms` per tap, `[140, 60, 140]` on a full mala.
- The lightbox is scoped to `.shot` only. When it also matched `.niche`, tapping
  to count opened a zoom instead.

## 5. Install

One action on the page: **Встановити / Install**.

`beforeinstallprompt` is captured and deferred; `appinstalled` sets a flag. The
button says *Open* **only** when the app is genuinely installed —
`matchMedia('(display-mode: standalone)')` or that flag. Otherwise *Install*.

> This block has been deleted once by accident during a cleanup pass and the
> page could not install for about an hour before anyone noticed. If you are
> removing mock-up code from `index.html`, check that the service-worker
> registration and both install listeners survive.

## 6. Screenshots

`shots/` is Ukrainian, `shots-en/` is English — same five screens, same names.
`draw()` rewrites every `.shot img` src when the language changes, because the
gallery shows the app's own interface and it must speak the page's language.
The niche is exempt: it is a thangka with no interface in it.

**How they are captured** (no phone needed):

1. Open `app.html`, seed `localStorage.dm_state` with a believable practice
   history and set `dm_tour_intro = 1` to skip the first-run flow.
2. Force `[data-guide-frame]` to `height: 1060px` so the tab bar lands where it
   would on a phone.
3. Capture the frame region.
4. **Crop `(7, 0, 505)`.** The capture bakes the browser scrollbar into columns
   505–511. It shipped that way once and was visible on the live site. Final
   assets are **498×1060**, JPEG q88.

## 7. Layout

Mobile is the product. `.page` is `max-width: 520px` and centres itself; a
desktop visitor currently gets the mobile column in the middle of the screen.
A real desktop layout is still open — see [`BACKLOG.md`](BACKLOG.md).

The QR panel is `min-width: 1140px`. At the earlier 900px breakpoint it sat on
top of the install button.

## 8. Anti-references

Habit trackers and streak-driven wellness apps. Generic dark-mode SaaS landings:
purple-blue gradients, glass cards, neon glow. Anything that treats practice as
self-optimisation.

Two failure modes seen on this page specifically:

- **"Break the sameness" is not "make everything different."** A pass that gave
  each section its own treatment produced, in the owner's words, a page where
  everything was different and nothing belonged together.
- **Captions are labels, not prose.** Under a screenshot, `PRACTICE` works;
  a sentence describing what the screen does does not.

Screenshot before deploying. Every regression in this file was found by looking
at the rendered page, not by reading the diff.
