package vn.dazz.bookshop.services.order;

import org.springframework.data.domain.Page;
import vn.dazz.bookshop.entities.Orders;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.entities.User;


import java.sql.Date;
import java.util.List;


public interface OrderService {
    Page<Orders> getOrders(Long userId, int page);
    Orders getOrderById(Long orderId);
    User getUserByOrderId(Long orderId);
    List<Product> getProductsByOrderId(Long orderId);
    Float sumOrderPriceByOrderId(Long orderId);
    void insertOrder(Long userId, Integer status, Integer deliveryMethod, Float deliveryPrice, Date createdAt, Date updatedAt);
    void insertIntoOrderItem(Long orderId, Long productId, Float pirce, Float discount, Integer quantity, Date createdAt, Date updatedAt);
    Long getOrderId(Long orderId);
    Long getLastOrderId();
    List<Orders> getALlOrder();
    List<Orders> getOrdersWithUserAndProduct();
    Integer countOrder();
    void cancelOrder(Long orderId);
    List<Orders> getAllOrders();
    void doneOrder(Long orderId);
    void approveOrder(Long orderId);
}
