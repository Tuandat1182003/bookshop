package vn.dazz.bookshop.adminPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/adminPage/admin")
    public String admin() {
        return "adminPage/index";
    }
}
