package vn.dazz.bookshop.adminPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping(value = "/adminPage/contactWithUser")
    public String contactWithUser() {
        return "/adminPage/contactWithUsers";
    }

}
