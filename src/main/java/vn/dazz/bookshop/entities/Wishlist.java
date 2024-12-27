package vn.dazz.bookshop.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "wishlist_item")
public class Wishlist extends BaseEntity{
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "productId")
    private Long productId;

    @Column(name = "createdAt")
    private Date createdAt;

    @Transient
    private Product product;
}
