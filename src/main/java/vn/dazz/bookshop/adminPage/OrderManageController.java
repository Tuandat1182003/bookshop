package vn.dazz.bookshop.adminPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.dazz.bookshop.entities.Orders;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.services.order.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderManageController {
    @Autowired
    OrderService orderService;

    @GetMapping(value = "/adminPage/orderManage")
    public String orderManage(Model model) {
        List<Orders> orders = orderService.getALlOrder();
        for (Orders order : orders) {
            order.setProducts(orderService.getProductsByOrderId(order.getId()));
            String productsNames = order.getProducts().stream()
                    .map(Product::getName)
                    .collect(Collectors.joining(", "));

            order.setProductsName(productsNames);
        }
        model.addAttribute("orders", orders);
        return "adminPage/orderManage";
    }


    @GetMapping(value = "/adminPage/order/cancel/{id}")
    public String orderUpdate(RedirectAttributes redirectAttributes, @PathVariable Long id) {
        orderService.cancelOrder(id);
        redirectAttributes.addFlashAttribute("message", "Order canceled successfully");
        return "redirect:/adminPage/orderManage" ;
    }

    @GetMapping(value = "/order/approve/{id}")
    public String orderApprove(RedirectAttributes redirectAttributes, @PathVariable Long id) {
        orderService.approveOrder(id);
        redirectAttributes.addFlashAttribute("message", "Order approved successfully");
        return "redirect:/adminPage/orderManage" ;
    }

    @GetMapping(value = "/order/done/{id}")
    public String orderDone(RedirectAttributes redirectAttributes, @PathVariable Long id) {
        orderService.doneOrder(id);
        redirectAttributes.addFlashAttribute("message", "Order checked successfully");
        return "redirect:/adminPage/orderManage";
    }
}
