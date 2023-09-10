package pro.sky.telegrambot.component;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationTaskTimerScheduled {
    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

    @Autowired
    private TelegramBot telegramBot;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void run() {
        notificationTaskRepository.findAllByNotificationDateTime
                        (LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .forEach(notificationTask -> {
                    SendMessage message = new SendMessage(notificationTask.getChatId(), notificationTask.getMessage());
                    telegramBot.execute(message);
                    notificationTaskRepository.delete(notificationTask);
                });
    }
}

