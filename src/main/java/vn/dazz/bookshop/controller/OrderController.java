package vn.dazz.bookshop.controller;

import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.dazz.bookshop.entities.Orders;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.services.cart.CartService;
import vn.dazz.bookshop.services.order.OrderService;
import vn.dazz.bookshop.services.user.UserService;
import vn.dazz.bookshop.util.Utils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @GetMapping(value = "/order")
    public String order(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (orderService != null) { // Check to avoid NullPointerException
            model.addAttribute("orders", orderService.getOrders(Utils.getUserHadLogin().getId(), page));
        } else {
            model.addAttribute("orders", new ArrayList<>());
        }
        return "order";
    }

    @GetMapping(value = "/order/addOrder")
    public String addOrder(RedirectAttributes redirectAttributes,
                           @RequestParam(required = false) Long orderId) {
        Date now = new Date(System.currentTimeMillis());

        Long cartId = userService.findCartByUserId(Utils.getUserHadLogin().getId()).getId();

        if(orderId == null) {
            orderService.insertOrder(Utils.getUserHadLogin().getId(), 4, 1, 10.000f, now, now);
            orderId = orderService.getLastOrderId();
        }

        Orders order = orderService.getOrderById(orderId);
        List<Product> list = cartService.getProductByCartId(cartId);

        for (Product product : list) {
            orderService.insertIntoOrderItem(order.getId(), product.getId(), product.getDiscount(), product.getPrice(), Integer.valueOf(product.getQuantity()), now, now);
        }

        redirectAttributes.addFlashAttribute("mess", "Successfully added order, please wait for approve");
        return "redirect:/order";
    }

    @GetMapping(value = "/order/cancel/{id}")
    public String orderUpdate(RedirectAttributes redirectAttributes, @PathVariable Long id) {
        orderService.cancelOrder(id);
        redirectAttributes.addFlashAttribute("message", "Order canceled successfully");
        return "redirect:/order" ;
    }
}
