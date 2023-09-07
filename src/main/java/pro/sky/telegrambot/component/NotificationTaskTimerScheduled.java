//package pro.sky.telegrambot.component;

//
//@Component
//
//public class NotificationTaskTimerScheduled {
//    private final NotificationTaskRepository notificationTaskRepository;
//    private final TelegramBot telegramBot;
//
//    public NotificationTaskTimerScheduled(NotificationTaskRepository notificationTaskRepository, TelegramBot telegramBot) {
//        this.notificationTaskRepository = notificationTaskRepository;
//        this.telegramBot = telegramBot;
//    }

//    @Scheduled(cron = "0 0/1 * * * *")
//    public void run() {
//
//        notificationTaskRepository.findAllByNotificationDateTime
//                        (LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
//                .forEach(notificationTask -> {
//                    SendMessage message = new SendMessage(notificationTask.getChatId(),notificationTask.getMassage());
//                    telegramBot.execute(message);
//                    notificationTaskRepository.delete(notificationTask);
//                });
//    }
//}

