package vn.dazz.bookshop.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.dazz.bookshop.entities.Cart;
import vn.dazz.bookshop.entities.CartItem;
import vn.dazz.bookshop.entities.Product;


import java.sql.Date;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT ci FROM CartItem ci JOIN Cart c ON ci.cartId = c.id WHERE c.userId = :userId")
    Page<CartItem> findByUserId(@Param("userId") Long userId, Pageable page);

    @Query("SELECT SUM(ci.quantity * p.price) FROM CartItem ci JOIN Product p ON ci.productId = p.id JOIN Cart c ON ci.cartId = c.id WHERE c.userId = :userId")
    Float totalProductPrice(Long userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Cart (userId, createdAt, updatedAt) values (:userId, :createdAt, :updatedAt)", nativeQuery = true)
    void insertIntoCart(Long userId, Date createdAt, Date updatedAt);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cart_item (cartId, productId, quantity, createdAt, updatedAt) VALUES (:cartId, :productId, :quantity, :createdAt, :updatedAt)", nativeQuery = true)
    void insertIntoCartItem(Long cartId, Long productId, Integer quantity, Date createdAt, Date updatedAt);

    @Query("SELECT c FROM Cart c WHERE c.userId = :userId")
    Cart findByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM CartItem ct JOIN Product p ON ct.productId = p.id JOIN Cart c " +
            "ON c.id = ct.cartId where c.id = :cartId")
    List<Product> getProductByCartId(@Param("cartId") Long cartId);

    @Query("SELECT p FROM CartItem ct JOIN Product p ON ct.productId = p.id WHERE ct.cartId = :cartId AND " +
            "p.id = :productId ")
    Product getProductByCartIdAndProductId(Long cartId, Long productId);

    @Modifying
    @Transactional
    @Query("UPDATE CartItem c SET c.quantity = :quantity, c.updatedAt = :updatedAt WHERE c.cartId = :cartId AND c.productId = :productId")
    void updateCartItemQuantity(@Param("cartId") Long cartId,
                                @Param("productId") Long productId,
                                @Param("quantity") Integer quantity,
                                @Param("updatedAt") Date updatedAt);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CartItem ci where ci.productId = :productId AND ci.cartId = :cartId")
    void deleteProductByCartId(Long cartId, Long productId);
}
