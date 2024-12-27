package vn.dazz.bookshop.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.dazz.bookshop.common.Response;
import vn.dazz.bookshop.dto.UserDto;
import vn.dazz.bookshop.entities.User;
import vn.dazz.bookshop.services.user.UserService;

import java.util.Objects;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @GetMapping(value = "/register/{code}")
    public String register(Model model, @PathVariable String code) {
        Response response = userService.activeAccount(code);
        model.addAttribute("message", response.getMessage());
        if(Objects.equals(response.getCode(), 1)) {
            model.addAttribute("messSuccess", "Your account has been activated");
            return "login";
        } else
            return "register";
    }

    @PostMapping(value = "/register")
    public String register(@Valid @ModelAttribute UserDto user,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        model.addAttribute("userDto", user);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!Objects.equals(user.getPassword(), user.getRePassword())) {
            bindingResult.rejectValue("rePassword", "error.rePassword", "Mật khẩu không trùng nhau");
            return "register";  // Trả về form nếu mật khẩu không khớp
        }

        // Kiểm tra email
        User getUserByEmail = userService.findUserByEmail(user.getEmail());
        if (getUserByEmail != null) {
            bindingResult.rejectValue("email", "error.email", "Email already exists");
        }

        // Kiểm tra số điện thoại
        User getUserByPhoneNumber = userService.findUserByPhone(user.getPhoneNumber());
        if (getUserByPhoneNumber != null) {
            bindingResult.rejectValue("phoneNumber", "error.phoneNumber", "Phone number already exists");
        }

        // Kiểm tra tên người dùng
        User getUserByName = userService.findUserByName(user.getUsername());
        if (getUserByName != null) {
            bindingResult.rejectValue("username", "error.username", "Username already exists");
        }

        // Nếu có lỗi trong bindingResult, trả về trang đăng ký với thông báo lỗi
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("mess", "Account created successfully. Please check your email to verify your account");
            return "redirect:/login";  // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred while creating your account. Please try again later.");
            return "register";  // Trả về form nếu có lỗi trong quá trình đăng ký
        }
    }

}
