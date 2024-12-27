package vn.dazz.bookshop.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "category")
@Data// Thư viện loombok
public class Category extends BaseEntity {

    @Id// Đánh dấu n là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto increment
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "imageName")
    String imageName;
}
