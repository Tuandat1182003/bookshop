package vn.dazz.bookshop.services.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.common.Response;
import vn.dazz.bookshop.dto.UserDto;
import vn.dazz.bookshop.entities.Cart;
import vn.dazz.bookshop.entities.User;
import vn.dazz.bookshop.repositories.UserRepository;
import vn.dazz.bookshop.services.email.EmailDetails;
import vn.dazz.bookshop.services.email.EmailService;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailService emailService;

    @Override
    public User findUserById(Long id) {
        return userRepository.findAllById(id);
    }

    @Override
    public Cart findCartByUserId(Long id) {
        return userRepository.findCartByUserId(id);
    }

    @Override
    public User findUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByPhone(String phone) {
        return userRepository.findByPhoneNumber(phone);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Integer countUserById() {
        return userRepository.countUserById();
    }

    @Override
    public User findUserByOrderID(Long id) {
        return userRepository.findUserByOrderId(id);
    }

    @Override
    public void insertUser(String username, String password, String fullname, String email, String phoneNumber, Boolean gender, String address, String role) {
        userRepository.insertUser(username, password, fullname, email, phoneNumber, gender, address, role);
    }

    @Override
    public User saveUser(UserDto userDto) {

        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        //@TODO : Kiểm tra xem có tồn tại tài khoản hay chưa

        String code = UUID.randomUUID().toString();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus(0);
        user.setCode(code);
        user.setRole("CUSTOMER");
        userRepository.save(user);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(user.getEmail());
        emailDetails.setSubject("ĐĂNG KÝ TÀI KHOẢN");
        emailDetails.setMsgBody("Click vào <a href=\"http://localhost:8080/register/" +
                code +
                "\">đây<a> để kích hoạt tài khoản");
        emailService.sendSimpleMail(emailDetails);
        return user;
    }

    @Override
    public void saveUserFull(User user) {
        // Map UserDto to User entity
        user.setFullname(user.getFullname());
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setAddress(user.getAddress());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setStatus(user.getStatus());
        user.setRole(user.getRole());

        // Save user to the database
        userRepository.save(user);
    }

    public Response activeAccount(String code) {
        User user = userRepository.findUserByCode(code);
        if (user == null) new Response(0, "Không tồn tại tài khoản", null);
        if (Objects.equals(user.getStatus(), 1))
            return new Response(0, "Tài khoản không hợp lệ", null);

        user.setStatus(1);
        user.setCode(null);
        userRepository.save(user);
        return new Response(1, "Kích hoạt thành công", null);
    }
}
