package vn.dazz.bookshop.services.cart;

import org.springframework.data.domain.Page;
import vn.dazz.bookshop.entities.Cart;
import vn.dazz.bookshop.entities.CartItem;
import vn.dazz.bookshop.entities.Product;


import java.sql.Date;
import java.util.List;

public interface CartService {
    Page<CartItem> findUserById(Long userId, int page);
    Float totalProductPrice(Long userId);
    void insertIntoCart(Long userId, Date createdAt, Date updatedAt);
    void insertIntoCartItem(Long cartId, Long productId, Integer quantity, Date createdAt, Date updatedAt);
    void insertIntoCartItem(Cart cartByUserId);
    List<Product> getProductByCartId(Long cartId);
    Product getProductByCartIdAndProductId(Long cartId, Long productId);
    void updateCartItemQuantity(Long cartId, Long productId, int quantity, Date updatedAt);
    void deleteProductByCartId(Long cartId, Long productId);
    List<Cart> findAll();
}
