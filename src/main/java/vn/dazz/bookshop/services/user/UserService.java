package vn.dazz.bookshop.services.user;


import vn.dazz.bookshop.common.Response;
import vn.dazz.bookshop.dto.UserDto;
import vn.dazz.bookshop.entities.Cart;
import vn.dazz.bookshop.entities.User;


import java.util.List;

public interface UserService {
    User findUserById(Long id);
    Cart findCartByUserId(Long id);
    User findUserByName(String username);
    User findUserByEmail(String email);
    User findUserByPhone(String phone);
    void deleteUserById(Long id);
    List<User> findAllUsers();
    Integer countUserById();
    User findUserByOrderID(Long id);
    User saveUser(UserDto userdto);
    void saveUserFull(User user);
    Response activeAccount(String code);
    void insertUser(String username, String password, String fullname, String email, String phoneNumber, Boolean gender, String address, String role);
}
