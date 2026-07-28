# Contextual help — the "?" overlay

Tapping **?** dims the current screen, outlines the meaningful elements with a gold ring and places a short caption beside each. Tapping anywhere closes it.

Captions are keyed by the `data-guide` attribute already present in the markup — the interactive tour uses the same anchors. An element that is not on screen right now is simply skipped, so an empty list produces no stray captions.

The texts below are the source of truth. They also live in the `HELP` map inside `index.html`; keep the two in sync.

Captions are deliberately short — 2 to 6 words. They label a live screen, they do not teach. Longer explanations belong to the tour.

---

## Practice — mantra list (`home`)

| Anchor | Ukrainian | English |
|---|---|---|
| `home-continue` | Остання практика — один тап і ти в лічильнику | Your last practice — one tap to resume |
| `home-list` | Твої постійні мантри | Your ongoing mantras |
| `mantra-card` | Тап — рахувати. Олівець — змінити | Tap to count. Pencil to edit |
| `home-add` | Додати нову мантру | Add a new mantra |
| `home-tools` | Довідка, вібрація, мова | Help, vibration, language |
| `tabbar` | Розділи додатку | App sections |

## Counter (`counter`)

| Anchor | Ukrainian | English |
|---|---|---|
| `counter-tap` | Тап будь-де — плюс одне повторення | Tap anywhere — one repetition |
| `counter-count` | Усього за весь час | Total of all time |
| `counter-today` | За сьогодні й за цю сесію | Today and this session |
| `counter-beads` | Намистини поточного кола | Beads of the current round |
| `counter-goal` | Прогрес до твоєї цілі | Progress toward your goal |
| `counter-mala` | Додати ціле коло одразу | Add a whole round at once |
| `counter-minus` | Прибрати зайвий дотик | Undo an extra tap |
| `counter-img` | Двома пальцями — збільшити | Pinch to zoom |
| `counter-edit` | Змінити мантру | Edit the mantra |
| `counter-stats` | Статистика цієї мантри | Statistics for this mantra |
| `counter-zen` | Спокійний режим — лише лічильник | Calm mode — counter only |
| `counter-back` | Назад до списку | Back to the list |

## Mantra statistics (`stats`)

| Anchor | Ukrainian | English |
|---|---|---|
| `stats-cards` | Разом, ціль, дні поспіль | Total, goal, day streak |
| `stats-chart` | Останні 14 днів | Last 14 days |
| `stats-heat` | Пів року практики — що темніше, то більше | Six months — darker means more |
| `stats-history` | Кожен день окремо | Day by day |
| `stats-back` | Назад до лічильника | Back to the counter |

## Retreats (`retreats`, `retreatDetail`)

| Anchor | Ukrainian | English |
|---|---|---|
| `ret-add` | Створити ретрит | Create a retreat |
| `ret-frommine` | Взяти мантру зі свого списку — лічильник спільний | Take a mantra from your list — shared counter |
| `rd-edit` | Дати, архів, видалення | Dates, archive, delete |

## Retreat form (`rform`)

| Anchor | Ukrainian | English |
|---|---|---|
| `rf-name` | Назва ретриту | Retreat name |
| `rf-dates` | Тап — початок, ще тап — кінець | Tap for start, tap again for end |
| `rf-save` | Зберегти ретрит | Save the retreat |
| `rf-arch` | Завершити й перенести в архів | Finish and move to archive |

## Pick a mantra (`pick`)

| Anchor | Ukrainian | English |
|---|---|---|
| `pick-list` | Начитане зарахується і в ретрит, і в загальне | Counts toward both the retreat and your total |

## Edit mantra (`edit`)

| Anchor | Ukrainian | English |
|---|---|---|
| `edit-name` | Обовʼязкове поле | Required field |
| `edit-text` | Показується під час практики | Shown during practice |
| `edit-goal` | Скільки хочеш накопичити | How many you want to accumulate |
| `edit-mala` | Намистин у колі, зазвичай 108 | Beads in a round, usually 108 |
| `edit-image` | Стане тлом лічильника | Becomes the counter background |
| `edit-save` | Зберегти | Save |

## Lifetime statistics (`life`)

| Anchor | Ukrainian | English |
|---|---|---|
| `life-total` | Усе, що ти начитав у додатку | Everything you have recited |
| `life-rows` | Кожна мантра окремо | Each mantra separately |
| `life-arch` | Завершені мантри й ретрити | Completed mantras and retreats |

## Calendar (`calendar`)

| Anchor | Ukrainian | English |
|---|---|---|
| `cal-nav` | Місяць і тибетський рік | Month and Tibetan year |
| `cal-grid` | Тап по позначеному дню — опис і порада | Tap a marked day for details |
| `cal-legend` | Що означає кожна позначка | What each marker means |
| `cal-upcoming` | Найближчі особливі дні | Nearest special days |
| `cal-remind` | Нагадування приходять у Telegram | Reminders arrive in Telegram |

---

## Adding a new caption

1. Make sure the element carries a `data-guide="name"` attribute in the markup. If not, add one.
2. Add a row to the table above.
3. Add the same entry to the `HELP` map in `index.html`, in the form `'name': ['Ukrainian', 'English']`.

Nothing else is required — the overlay measures whatever it finds on screen.
