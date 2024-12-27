package vn.dazz.bookshop.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.entities.Orders;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.entities.User;
import vn.dazz.bookshop.repositories.OrderRepository;
import vn.dazz.bookshop.services.product.ProductService;
import vn.dazz.bookshop.services.user.UserService;

import java.sql.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public Page<Orders> getOrders(Long userId, int page) {
        Page<Orders> orderPage = orderRepository.getAllOrder(userId, PageRequest.of(page, 100));
        for (Orders order : orderPage.getContent()) {
            List<Product> products = productService.getProductsByOrderId(order.getId());
            order.setProducts(products);
            Float totalPrice = orderRepository.getTotalPriceByOrderId(order.getId());
            order.setTotalPrice(totalPrice);
        }
        return orderPage;
    }

    @Override
    public Orders getOrderById(Long id) {
        return orderRepository.findByOrderId(id);
    }

    @Override
    public User getUserByOrderId(Long orderId) {
        return orderRepository.findUserByOrderId(orderId);
    }

    @Override
    public List<Product> getProductsByOrderId(Long orderId) {
        return productService.getProductsByOrderId(orderId);
    }

    @Override
    public Float sumOrderPriceByOrderId(Long orderId) {
        return orderRepository.sumOrderPriceByOrderId(orderId);
    }

    @Override
    public void insertOrder(Long userId, Integer status, Integer deliveryMethod, Float deliveryPrice, Date createdAt, Date updatedAt) {
        orderRepository.insertIntoOrder(userId, status, deliveryMethod, deliveryPrice, createdAt, updatedAt);
    }

    @Override
    public void insertIntoOrderItem(Long orderId, Long productId, Float price, Float discount, Integer quantity, Date createdAt, Date updatedAt) {
        orderRepository.insertIntoOrderItem(orderId, productId, discount, price, quantity, createdAt, updatedAt);
    }

    @Override
    public Long getOrderId(Long orderId) {
        return orderRepository.getOrderId(orderId);
    }

    @Override
    public Long getLastOrderId() {
        Orders lastOrder = orderRepository.lastOrderId();
        return lastOrder != null ? lastOrder.getId() : null;
    }

    @Override
    public List<Orders> getALlOrder() {
        List<Orders> orders = orderRepository.getAllOrder();
        for (Orders order : orders) {
            User user = userService.findUserByOrderID(order.getId());
            order.setUsers(user);
        }
        return orderRepository.getAllOrder();
    }

    @Override
    public List<Orders> getOrdersWithUserAndProduct() {

        return List.of();
    }

    @Override
    public Integer countOrder() {
        return 0;
    }

    @Override
    public void cancelOrder(Long orderId) {
        orderRepository.cancelOrder(orderId);
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void doneOrder(Long orderId) {
        orderRepository.doneOrder(orderId);
    }

    @Override
    public void approveOrder(Long orderId) {
        orderRepository.approveOrder(orderId);
    }
}
