package vn.dazz.bookshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Không được để trống tên sản phẩm")
    private String name;

    @NotNull(message = "Không được để trống giá")
    private Float price;

    @NotNull(message = "Nếu không có giảm giá thì để giảm giá là 0")
    private Float discount;

    @NotNull(message = "Nếu không có sản phẩm thì để số lượng là 0")
    private Short quantity;

    @NotNull(message = "Nếu không có lượt mua thì để lượt mua là 0")
    private Short totalBuy;

    @NotEmpty(message = "Không được để trống tên tác giả")
    private String author;
    private Short pages;
    private String publisher;
    private Integer yearPublishing;

    @NotEmpty(message = "Hãy mô tả sản phẩm nhé")
    private String description;

    @NotEmpty(message = "Hãy thêm ảnh")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|jpeg))$)", message = "Định dạng ảnh không hợp lệ, chỉ chấp nhận các định dạng: jpg, png, jpeg")
    private String imageName;

    private Boolean shop;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;

    @Transient
    private Category category;
}
