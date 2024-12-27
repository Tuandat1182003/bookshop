package vn.dazz.bookshop.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "cart_item")
@Data
public class CartItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cartId;

    private Long productId;

    private Short quantity;

    private Date createdAt;

    private Date updatedAt;

    @Transient
    private Product product;
}