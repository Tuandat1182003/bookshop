package vn.dazz.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.dazz.bookshop.entities.ProductReview;
import vn.dazz.bookshop.services.productReview.ProductReviewService;
import vn.dazz.bookshop.util.Utils;

import java.sql.Date;
import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    ProductReviewService reviewService;

    @PostMapping("/review/{id}")
    public String review(@RequestParam("content") String content,
                         @PathVariable Long id) {
        Date now = new Date(System.currentTimeMillis());
        reviewService.insertIntoReview(Utils.getUserHadLogin().getId(), id, 5, content, true, now, now);
        return "redirect:/detail/" + id;
    }
}
