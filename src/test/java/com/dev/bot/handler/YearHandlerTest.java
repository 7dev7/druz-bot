package com.dev.bot.handler;

import com.dev.bot.handler.impl.YearHandler;
import com.dev.bot.message.MessageTemplate;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YearHandlerTest {
    private static YearHandler yearHandler;
    private static UserManager userManager;
    private Message message;
    private User user;

    @BeforeClass
    public static void beforeClass() {
        yearHandler = new YearHandler();
        userManager = UserManager.getInstance();
    }

    @Before
    public void setup() {
        message = mock(Message.class);
        user = mock(User.class);
    }

    @Test
    public void handle_Correct2Years_Equals() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(message.getText()).thenReturn("2000 - 2003");
        assertEquals(MessageTemplate.approvedYears(), yearHandler.handle(message));
    }

    @Test
    public void handle_Correct1Year_Equals() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(message.getText()).thenReturn("2000 - ");
        assertEquals(MessageTemplate.approvedYears(), yearHandler.handle(message));
    }

    @Test()
    public void handle_EmptyMessage_Equals() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(message.getText()).thenReturn(" ");
        assertEquals(MessageTemplate.incorrectYearFormat(), yearHandler.handle(message));
    }

    @Test()
    public void handle_IncorrectMessage_Equals() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(message.getText()).thenReturn(" - ");
        assertEquals(MessageTemplate.incorrectYearFormat(), yearHandler.handle(message));
    }

    @Test()
    public void handle_YearsContainsChars_Equals() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(message.getText()).thenReturn("12b - 78f");
        assertEquals(MessageTemplate.yearContainsNonDigit(), yearHandler.handle(message));
    }

    @Test()
    public void handle_OnlyFirstYearContainsChars_Equals() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(message.getText()).thenReturn("12b -");
        assertEquals(MessageTemplate.yearContainsNonDigit(), yearHandler.handle(message));
    }

    @Test()
    public void handle_OnlySecondYearContainsChars_Equals() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(message.getText()).thenReturn(" - 78aawg");
        assertEquals(MessageTemplate.incorrectYearFormat(), yearHandler.handle(message));
    }

    @Test()
    public void handle_ToYearLessFromYear_Equals() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(message.getText()).thenReturn("2012 - 2011");
        assertEquals(MessageTemplate.toYearLessFromYear(), yearHandler.handle(message));
    }

    @Test
    public void handle_ChangedStateToNone_Equals() {
        when(message.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(message.getText()).thenReturn("2000 - 2003");
        yearHandler.handle(message);
        State userState = userManager.getUserState(user.getId());
        assertEquals(State.NONE, userState);
    }
}
