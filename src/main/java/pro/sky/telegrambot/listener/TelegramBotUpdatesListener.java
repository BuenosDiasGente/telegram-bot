package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    @Autowired
    private final NotificationTaskRepository notificationTaskRepository;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    public static final String MESSAGE_TEXT = "Добро пожаловать! Это планинг-бот:)! Для планирования задачи отправьте её в формате: 01.01.2022 20:00 Сделать домашнюю работу";
    ;
    public static final String MESSAGE_NOTIFICATION = "Напоминание добавленно";
    public static final String MESSAGE_ERROR = "Некорректный формат сообщения!";


    public TelegramBotUpdatesListener(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update: {}", update);
                String value = update.message().text();
                Long id = update.message().chat().id();

                if (value.contains("/start")) {
                    SendMessage message = new SendMessage(id, MESSAGE_TEXT);
                    telegramBot.execute(message);
                } else {
                    Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
                    Matcher matcher = pattern.matcher(value);
                    if (matcher.matches()) {
                        String date = matcher.group(1);
                        String item = matcher.group(3);
                        LocalDateTime parse = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                        NotificationTask notificationTask = new NotificationTask();
                        notificationTask.setChatId(id);
                        notificationTask.setDateAndTime(parse);
                        notificationTask.setMassage(item);
                        notificationTaskRepository.save(notificationTask);
                        SendMessage message = new SendMessage(id, MESSAGE_NOTIFICATION);
                        telegramBot.execute(message);
                    } else {
                        SendMessage text = new SendMessage(id, MESSAGE_ERROR);
                        telegramBot.execute(text);
                    }
                }
            });
        } catch (Exception e) {
            logger.error("Error while entering a message", e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}


