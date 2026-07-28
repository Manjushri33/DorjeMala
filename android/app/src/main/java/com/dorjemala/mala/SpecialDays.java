package com.dorjemala.mala;

import java.util.Calendar;
import java.util.Locale;

/**
 * Special Buddhist days for 2026, generated from the same lunar table the web app uses.
 * Format: "MM-dd|Ukrainian name|English name".
 * Regenerate together with the calendar in index.html — see CALENDAR-UPDATE.md.
 */
public final class SpecialDays {

    public static final int YEAR = 2026;

    private static final String[] DAYS = {
        "01-03|Повня — Будда Амітабга|Full moon — Amitabha",
        "01-13|День Дакіні|Dakini Day",
        "01-17|День захисників Дхарми|Dharma Protectors Day",
        "01-18|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "01-26|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "01-28|День Гуру Рінпоче|Guru Rinpoche Day",
        "02-01|Повня — Будда Амітабга|Full moon — Amitabha",
        "02-12|День Дакіні|Dakini Day",
        "02-16|День захисників Дхарми|Dharma Protectors Day",
        "02-17|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "02-18|Лосар — тибетський Новий рік|Losar — Tibetan New Year",
        "02-24|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "02-26|День Гуру Рінпоче|Guru Rinpoche Day",
        "03-03|Чотрул Дючен|Chotrul Duchen",
        "03-13|День Дакіні|Dakini Day",
        "03-17|День захисників Дхарми|Dharma Protectors Day",
        "03-18|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "03-26|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "03-28|День Гуру Рінпоче|Guru Rinpoche Day",
        "04-01|Повня — Будда Амітабга|Full moon — Amitabha",
        "04-12|День Дакіні|Dakini Day",
        "04-16|День захисників Дхарми|Dharma Protectors Day",
        "04-17|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "04-24|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "04-26|День Гуру Рінпоче|Guru Rinpoche Day",
        "05-01|Повня — Будда Амітабга|Full moon — Amitabha",
        "05-12|День Дакіні|Dakini Day",
        "05-15|День захисників Дхарми|Dharma Protectors Day",
        "05-16|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "05-24|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "05-26|День Гуру Рінпоче|Guru Rinpoche Day",
        "05-31|Сага Дава Дючен|Saga Dawa Duchen",
        "06-10|День Дакіні|Dakini Day",
        "06-14|День захисників Дхарми|Dharma Protectors Day",
        "06-15|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "06-22|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "06-24|День Гуру Рінпоче|Guru Rinpoche Day",
        "06-29|Дзамлінг Чісанг|Dzamling Chisang",
        "07-09|День Дакіні|Dakini Day",
        "07-13|День захисників Дхарми|Dharma Protectors Day",
        "07-14|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "07-18|Чокхор Дючен|Chokhor Duchen",
        "07-22|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "07-24|День Гуру Рінпоче|Guru Rinpoche Day",
        "07-29|Повня — Будда Амітабга|Full moon — Amitabha",
        "08-08|День Дакіні|Dakini Day",
        "08-11|День захисників Дхарми|Dharma Protectors Day",
        "08-12|Сонячне затемнення|Solar eclipse",
        "08-20|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "08-22|День Гуру Рінпоче|Guru Rinpoche Day",
        "08-28|Місячне затемнення|Lunar eclipse",
        "09-06|День Дакіні|Dakini Day",
        "09-10|День захисників Дхарми|Dharma Protectors Day",
        "09-11|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "09-19|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "09-21|День Гуру Рінпоче|Guru Rinpoche Day",
        "09-26|Повня — Будда Амітабга|Full moon — Amitabha",
        "10-05|День Дакіні|Dakini Day",
        "10-09|День захисників Дхарми|Dharma Protectors Day",
        "10-10|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "10-18|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "10-21|День Гуру Рінпоче|Guru Rinpoche Day",
        "10-26|Повня — Будда Амітабга|Full moon — Amitabha",
        "11-01|Лхабаб Дючен|Lhabab Duchen",
        "11-04|День Дакіні|Dakini Day",
        "11-08|День захисників Дхарми|Dharma Protectors Day",
        "11-09|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "11-17|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "11-19|День Гуру Рінпоче|Guru Rinpoche Day",
        "11-24|Повня — Будда Амітабга|Full moon — Amitabha",
        "12-03|День Дакіні|Dakini Day",
        "12-04|Ганден Нгамчо|Ganden Ngamcho",
        "12-07|День захисників Дхарми|Dharma Protectors Day",
        "12-08|Новий місяць — Будда Шакʼямуні|New moon — Shakyamuni",
        "12-17|День Тари та Будди Медицини|Tara & Medicine Buddha Day",
        "12-19|День Гуру Рінпоче|Guru Rinpoche Day",
        "12-23|Повня — Будда Амітабга|Full moon — Amitabha",    };

    private SpecialDays() {}

    /** Returns the name for today, or null if today is an ordinary day. */
    public static String todayName(boolean ukrainian) {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.YEAR) != YEAR) return null;
        String key = String.format(Locale.US, "%02d-%02d",
                c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        for (String row : DAYS) {
            if (row.startsWith(key + "|")) {
                String[] p = row.split("\\|");
                return ukrainian ? p[1] : p[2];
            }
        }
        return null;
    }
}
