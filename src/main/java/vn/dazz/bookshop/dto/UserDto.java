package vn.dazz.bookshop.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserDto {
    private Long id;

    @NotEmpty(message = "Không được để trống tên người dùng")
    private String username;

    @NotEmpty(message = "Không được để trống mật khẩu")
    @Size(min = 6, message = "Độ dài mật khẩu từ 6 kí tự trở lên")
    private String password;

    @NotEmpty(message = "Không được để trống mật khẩu xác nhận")
    private String rePassword;

    @NotEmpty(message = "Không được để trống họ tên")
    private String fullname;

    @NotEmpty(message = "Không được để trống email")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotEmpty(message = "Không được để trống số điện thoại")
    private String phoneNumber;

    private Boolean gender;

    @NotEmpty(message = "Không được để trống địa chỉ")
    private String address;

    private String role;

    @Digits(integer = 1, fraction = 0, message = "Status must be a number")
    private Integer status;

    private String code;
}

