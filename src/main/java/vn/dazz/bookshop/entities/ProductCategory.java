package vn.dazz.bookshop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product_category")
@Data
public class ProductCategory extends BaseEntity {

    @Id
    @Column(name = "productId")
    private Long productId;

    @Column(name = "categoryId")
    private Long categoryId;
}
