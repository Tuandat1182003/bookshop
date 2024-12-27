package vn.dazz.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.dazz.bookshop.entities.Cart;
import vn.dazz.bookshop.entities.CartItem;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.services.cart.CartService;
import vn.dazz.bookshop.services.order.OrderService;
import vn.dazz.bookshop.services.user.UserService;
import vn.dazz.bookshop.util.Utils;

import java.sql.Date;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping(value = "cart")
    public String cart(Model model, @RequestParam(required = false, defaultValue = "0") int page, RedirectAttributes redirectAttributes) {
        Page<CartItem> cartItems = cartService.findUserById(Utils.getUserHadLogin().getId(), page);
        model.addAttribute("page", cartItems);
        model.addAttribute("totalPrice", cartService.totalProductPrice(Utils.getUserHadLogin().getId()));
        return "cart";
    }

    @GetMapping(value = "/cart/add")
    public String addCart(@RequestParam Long productId,
                          @RequestParam Integer quantity,
                          @RequestParam(required = false) Date createdAt,
                          @RequestParam(required = false) Date updatedAt, RedirectAttributes redirectAttributes) {
        Date now = new Date(System.currentTimeMillis());
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;

        Cart existingCart = userService.findCartByUserId(Utils.getUserHadLogin().getId());
        if (existingCart == null) {
            cartService.insertIntoCart(Utils.getUserHadLogin().getId(), createdAt, updatedAt);
        }

        Long cartId = userService.findCartByUserId(Utils.getUserHadLogin().getId()).getId();

        // Kiểm tra nếu sản phẩm đã tồn tại trong giỏ hàng
        Product existingProduct = cartService.getProductByCartIdAndProductId(cartId, productId);
        if (existingProduct != null) {
            // Sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            cartService.updateCartItemQuantity(cartId, productId, quantity, updatedAt);
            redirectAttributes.addFlashAttribute("mess", "Added successfully");
        } else {
            // Sản phẩm chưa có, thêm mới vào giỏ hàng
            cartService.insertIntoCartItem(cartId, productId, quantity, createdAt, updatedAt);
            redirectAttributes.addFlashAttribute("mess", "Added successfully");
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/delete")
    public String deleteCart(@RequestParam Long productId, RedirectAttributes redirectAttributes) {
        Long cartId = userService.findCartByUserId(Utils.getUserHadLogin().getId()).getId();
        cartService.deleteProductByCartId(cartId, productId);
        redirectAttributes.addFlashAttribute("messDelete", "Delete successfully");
        return "redirect:/cart";
    }
}
