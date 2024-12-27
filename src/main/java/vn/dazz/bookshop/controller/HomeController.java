package vn.dazz.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.dazz.bookshop.entities.*;
import vn.dazz.bookshop.services.cart.CartService;
import vn.dazz.bookshop.services.category.CategoryService;
import vn.dazz.bookshop.services.product.ProductService;
import vn.dazz.bookshop.services.productReview.ProductReviewService;
import vn.dazz.bookshop.services.wishlist.WishlistService;
import vn.dazz.bookshop.util.Utils;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CartService cartService;

    @Autowired
    WishlistService wishlistService;

    @GetMapping(value = {"/", "home"})
    public String home(Model model, @RequestParam(required = false) Integer page) {
        Integer numberOfWishlistItems = wishlistService.countWishlistByUserId(Utils.getUserHadLogin().getId());
        List<Category> categories = categoryService.getAllCategory();
        List<Product> products = productService.findTop12Lastest();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("numberOfWishList", numberOfWishlistItems);
        return "index";
    }
}
