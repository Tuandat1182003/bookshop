package vn.dazz.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.entities.Wishlist;
import vn.dazz.bookshop.services.wishlist.WishlistService;
import vn.dazz.bookshop.util.Utils;

import java.sql.Date;

@Controller
public class WishListController {
    @Autowired
    WishlistService wishlistService;

    @GetMapping(value = "wishlist")
    public String wishlist(Model model) {
        model.addAttribute("wishList", wishlistService.getWishlistByUserId(Utils.getUserHadLogin().getId()));
        return "wishlist";
    }

    @GetMapping("/wishlist/add")
    public String wishlistAdd(@RequestParam Long productId, RedirectAttributes redirectAttributes) {
        Date now = new Date(System.currentTimeMillis());
        Product existingProduct = wishlistService.findProductByIdAndUserId(Utils.getUserHadLogin().getId(), productId);
        try {
            wishlistService.insertWishlist(Utils.getUserHadLogin().getId(), productId, now);
            redirectAttributes.addFlashAttribute("message", "Added successfully");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("messageExist", "Wishlist already have it");
        }
        return "redirect:/wishlist";
    }

    @GetMapping(value = "/wishlist/delete")
    public String wishlistDelete(@RequestParam Long productId, RedirectAttributes redirectAttributes) {
        wishlistService.deleteWishlistByUserIdAndProductId(Utils.getUserHadLogin().getId(), productId);
        redirectAttributes.addFlashAttribute("messageDelete", "Deleted successfully");
        return "redirect:/wishlist";
    }
}
