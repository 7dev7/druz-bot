package com.dev.bot.command;

import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

public class StartCommand extends GenericCommand {
    private final UserManager userManager;

    public StartCommand() {
        super("start", "With this command you can start the Bot");
        this.userManager = UserManager.getInstance();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        userManager.setUserState(user.getId(), State.NONE);
        String greetings = String.format("Приветствую, %s %s!\n\n Если хочешь хорошенько подумать жмакни /random_question", user.getFirstName(), user.getLastName());
        sendMessage(absSender, chat, greetings);
    }
}