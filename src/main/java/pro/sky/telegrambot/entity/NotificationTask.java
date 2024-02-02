package pro.sky.telegrambot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @SequenceGenerator(name="notification_taskSequence",sequenceName = "notification_task_sequence",allocationSize = 1,initialValue =1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "notification_taskSequence")
    @Column(name ="id")
    private Long id;

    @Column(name ="chat_id")
    private Long chatId;

    @Column(name ="message")
    private String message;

    @Column(name ="date_time")
    private LocalDateTime dateAndTime;


}
