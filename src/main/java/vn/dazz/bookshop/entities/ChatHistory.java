package vn.dazz.bookshop.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "chat_history")
public class ChatHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String content;
    private Integer possess;
}
