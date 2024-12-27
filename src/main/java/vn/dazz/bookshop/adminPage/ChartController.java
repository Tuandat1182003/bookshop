package vn.dazz.bookshop.adminPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {
    @GetMapping(value = "/adminPage/chart")
    public String chart() {
        return "/adminPage/chart";
    }
}
