package vn.dazz.bookshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Data
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotEmpty(message = "Không được để trống tên người dùng")
    private String username;

    @NotEmpty(message = "Không được để trống mật khẩu")
    @Size(min = 6, message = "Độ dài mật khẩu từ 6 kí tự trở lên")
    @Column(name = "password")
    private String password;

    @Transient
    private String rePassword;

    @NotEmpty(message = "Không được để trống họ tên")
    @Column(name = "fullname")
    private String fullname;

    @NotEmpty(message = "Không được để trống email")
    @Email(message = "Email không đúng định dạng")
    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")

    private String phoneNumber;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    private String role;

    @Digits(integer = 1, fraction = 0, message = "Status must be a number")
    @Column(name = "status")
    private Integer status;

    @Column(name = "code")
    private String code;
}
