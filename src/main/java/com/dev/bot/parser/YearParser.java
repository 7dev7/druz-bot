package com.dev.bot.parser;

import com.dev.bot.message.MessageTemplate;
import com.dev.bot.parser.exception.YearParsingException;
import org.apache.commons.lang3.tuple.Pair;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Calendar;

public class YearParser {
    public Pair<Integer, Integer> parse(Message message) throws YearParsingException {
        String[] years = message.getText().trim().split("-");
        Integer fromYear;
        Integer toYear = null;
        switch (years.length) {
            case 1:
                fromYear = parseYear(years[0]);
                break;
            case 2:
                fromYear = parseYear(years[0]);
                toYear = parseYear(years[1]);
                if (toYear < fromYear) {
                    throw new YearParsingException(MessageTemplate.toYearLessFromYear());
                }
                break;
            default:
                throw new YearParsingException(MessageTemplate.incorrectYearFormat());
        }
        return Pair.of(fromYear, toYear);
    }

    private Integer parseYear(String rawYear) throws YearParsingException {
        String formattedYear = rawYear.trim();
        if (formattedYear.isEmpty()) {
            throw new YearParsingException(MessageTemplate.incorrectYearFormat());
        }
        try {
            Integer year = Integer.valueOf(formattedYear);
            if (year < 1990 || year > Calendar.getInstance().get(Calendar.YEAR)) {
                throw new YearParsingException(MessageTemplate.notPossibleYear());
            }
            return year;
        } catch (NumberFormatException e) {
            throw new YearParsingException(MessageTemplate.yearContainsNonDigit());
        }
    }
}
