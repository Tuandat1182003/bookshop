package vn.dazz.bookshop.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Orders extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "deliveryMethod")
    private Integer deliveryMethod;

    @Column(name = "deliveryPrice")
    private Float deliveryPrice;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Transient
    private String productsName;

    @Transient
    private Float totalPrice;

    @Transient
    private List<Product> products;

    @Transient
    private User users;
}
