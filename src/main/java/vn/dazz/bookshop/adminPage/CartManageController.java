package vn.dazz.bookshop.adminPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartManageController {
    @GetMapping(value = "/adminPage/cartManage")
    public String cart() {
        return "adminPage/cartManage";
    }
}
