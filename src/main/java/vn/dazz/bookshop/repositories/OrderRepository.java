package vn.dazz.bookshop.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.dazz.bookshop.entities.Orders;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.entities.User;


import java.sql.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o FROM Orders o, OrderItem ot " +
            "WHERE ot.orderId = o.id AND o.userId = :userId GROUP BY o.id")
    Page<Orders> getAllOrder(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT o, u FROM Orders o JOIN User u ON u.id = o.userId")
    List<Orders> getAllOrder();

    @Query("SELECT SUM(p.price - (p.price * p.discount / 100) + o.deliveryPrice)  FROM Product p JOIN OrderItem ot ON ot.productId = p.id " +
            "JOIN Orders o ON o.id = ot.orderId WHERE ot.orderId = :orderId")
    Float getTotalPriceByOrderId(Long orderId);

    @Query("SELECT o FROM Orders o WHERE o.id = :orderId")
    Orders findByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT o FROM Orders o WHERE o.id = :orderId")
    Long getOrderId(@Param("orderId") Long orderId);

    @Query("SELECT u FROM User u JOIN Orders o ON u.id = o.userId WHERE o.id = :id")
    User findUserByOrderId(@Param("id") Long id);

    @Query("SELECT p FROM Product p JOIN OrderItem ot ON p.id = ot.productId JOIN Orders o " +
            "ON o.id = ot.orderId where o.id = :orderId")
    List<Product> findProductByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT SUM(p.price - (p.price * p.discount / 100)) FROM Product p JOIN OrderItem ot ON p.id = ot.productId JOIN Orders o " +
            "ON o.id = ot.orderId where o.id = :orderId order by o.id ")
    Float sumOrderPriceByOrderId(Long orderId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO orders(userId, status, deliveryMethod, deliveryPrice, createdAt, updatedAt)" +
            " values(:userId, :status, :deliveryMethod, :deliveryPrice, :createdAt, :updatedAt) ", nativeQuery = true)
    void insertIntoOrder(Long userId, Integer status, Integer deliveryMethod, Float deliveryPrice, Date createdAt, Date updatedAt);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO order_item(orderId, productId, price, discount, quantity, createdAt, updatedAt)" +
            " values(:orderId, :productId, :price, :discount, :quantity, :createdAt, :updatedAt) ", nativeQuery = true)
    void insertIntoOrderItem(Long orderId, Long productId, Float price, Float discount, Integer quantity, Date createdAt, Date updatedAt);

    @Query("SELECT o FROM Orders o ORDER BY o.id desc LIMIT 1")
    Orders lastOrderId();

    @Query("SELECT COUNT(o.id) FROM Orders o")
    Integer countOrder();

    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.status = 3 WHERE o.id = :orderId")
    void cancelOrder(Long orderId);

    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.status = 1 WHERE o.id = :orderId")
    void approveOrder(Long orderId);

    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.status = 2 WHERE o.id = :orderId")
    void doneOrder(Long orderId);

}
