package pro.sky.telegrambot.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notification_task")
public class NotificationTask {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "massage")
    private String massage;

    @Column(name = "date_time")
    private LocalDateTime dateAndTime;


}
