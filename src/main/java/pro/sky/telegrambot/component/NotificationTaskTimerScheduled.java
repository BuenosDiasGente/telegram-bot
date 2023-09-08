package pro.sky.telegrambot.component;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class NotificationTaskTimerScheduled {
    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

    @Autowired
    private TelegramBot telegramBot;

    @Scheduled(cron = "0 0/1 * * * *")

    public void run() {
        notificationTaskRepository.findAllByNotificationDateTime
                        (LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .forEach(notificationTask -> {
                    SendMessage message = new SendMessage(notificationTask.getChatId(),notificationTask.getMassage());
                    telegramBot.execute(message);
                    notificationTaskRepository.delete(notificationTask);
                });
    }
}

