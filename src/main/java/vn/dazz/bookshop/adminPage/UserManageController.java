package vn.dazz.bookshop.adminPage;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.dazz.bookshop.dto.UserDto;
import vn.dazz.bookshop.entities.User;
import vn.dazz.bookshop.services.user.UserService;

import java.util.List;

@Controller
public class UserManageController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/adminPage/userManage")
    public String userManage(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "adminPage/userManage";
    }

    @GetMapping(value = "/adminPage/userManage/add")
    public String userManageAdd(Model model) {
        model.addAttribute("user", new User());
        return "adminPage/user/userManageAdd";
    }

    @PostMapping(value = "/adminPage/userManage/add")
    public String userManagePost(@Valid @ModelAttribute("user") User user,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        User existingUser = userService.findUserByEmail(user.getEmail());
        User existingUser2 = userService.findUserByPhone(user.getPhoneNumber());
        User existingUser3 = userService.findUserByName(user.getUsername());

        if(existingUser != null) {
            result.rejectValue("email", "email.exists", "This email already exists");
        }

        if(existingUser2 != null) {
            result.rejectValue("phoneNumber", "phone.exists", "This phone already exists");
        }

        if(existingUser3 != null) {
            result.rejectValue("username", "username.exists", "This username already exists");
        }

        if (result.hasErrors()) {
            return "adminPage/user/userManageAdd"; // Return to the form view to display errors
        }

        userService.saveUserFull(user);
        redirectAttributes.addFlashAttribute("messageAddUser", "User added successfully");
        return "redirect:/adminPage/userManage";
    }

    @GetMapping("/adminPage/userManage/delete")
    public String userManageDelete(@RequestParam Long id, Model model) {
        userService.deleteUserById(id);
        return "redirect:/adminPage/userManage";
    }
}
