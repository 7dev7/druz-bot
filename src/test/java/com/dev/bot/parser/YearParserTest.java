package com.dev.bot.parser;

import com.dev.bot.parser.exception.YearParsingException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.Message;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YearParserTest {
    private static YearParser yearParser;
    private Message message;

    @BeforeClass
    public static void beforeClass() {
        yearParser = new YearParser();
    }

    @Before
    public void setup() {
        message = mock(Message.class);
    }

    @Test
    public void parse_CorrectYears_True() throws YearParsingException {
        when(message.getText()).thenReturn("2000 - 2003");
        assertEquals(Pair.of(2000, 2003), yearParser.parse(message));
    }

    @Test
    public void parse_Correct1YearWithSeparator_True() throws YearParsingException {
        when(message.getText()).thenReturn("2000 -");
        assertEquals(Pair.of(2000, null), yearParser.parse(message));
    }

    @Test
    public void parse_Correct1Year_True() throws YearParsingException {
        when(message.getText()).thenReturn("2000 ");
        assertEquals(Pair.of(2000, null), yearParser.parse(message));
    }

    @Test(expected = YearParsingException.class)
    public void parse_EmptyMessage_Exception() throws YearParsingException {
        when(message.getText()).thenReturn(" ");
        yearParser.parse(message);
    }

    @Test(expected = YearParsingException.class)
    public void parse_IncorrectMessage_Exception() throws YearParsingException {
        when(message.getText()).thenReturn(" - ");
        yearParser.parse(message);
    }

    @Test(expected = YearParsingException.class)
    public void parse_YearsContainsChars_Exception() throws YearParsingException {
        when(message.getText()).thenReturn("12b - 78f");
        yearParser.parse(message);
    }

    @Test(expected = YearParsingException.class)
    public void parse_OnlyFirstYearContainsChars_Exception() throws YearParsingException {
        when(message.getText()).thenReturn("12b - ");
        yearParser.parse(message);
    }

    @Test(expected = YearParsingException.class)
    public void parse_OnlySecondYearContainsChars_Exception() throws YearParsingException {
        when(message.getText()).thenReturn(" - 78aawg");
        yearParser.parse(message);
    }
}
