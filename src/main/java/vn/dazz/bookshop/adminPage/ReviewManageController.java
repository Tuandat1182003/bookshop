package vn.dazz.bookshop.adminPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.dazz.bookshop.entities.ProductReview;
import vn.dazz.bookshop.services.productReview.ProductReviewService;


@Controller
public class ReviewManageController {
    @Autowired
    ProductReviewService reviewService;

    @GetMapping("/adminPage/reviewManage")
    public String reviewManage(Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<ProductReview> reviewList = reviewService.findAll(pageNo);
        //List<ProductReview> reviewList = reviewService.getAllProductReviews();
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("totalPages", reviewList.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNo", pageNo);
        return "adminPage/reviewManage";
    }

    @GetMapping("/adminPage/reviewManage/delete")
    public String deleteReview(RedirectAttributes redirectAttributes, @RequestParam Long id) {
        redirectAttributes.addFlashAttribute("messDelete", "The review has id = " + id + " deleted successfully");
        reviewService.deleteReview(id);
        return "redirect:/adminPage/reviewManage";
    }
}
