package vn.dazz.bookshop.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "product_review")
public class ProductReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long userId;

    private Long productId;

    private Integer ratingScore;

    private String content;

    private boolean isShow;

    private Date createdAt;

    private Date updatedAt;

    @Transient
    private User user;

    @Transient
    private Product product;
}
