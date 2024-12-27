package vn.dazz.bookshop.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    private Long orderId;
    private Long productId;
    private Double price;
    private Double discount;
    private Integer quantity;
    private Date createdAt;
    private Date updatedAt;
}
