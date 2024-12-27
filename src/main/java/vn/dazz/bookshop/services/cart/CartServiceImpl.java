package vn.dazz.bookshop.services.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.entities.Cart;
import vn.dazz.bookshop.entities.CartItem;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.repositories.CartRepository;
import vn.dazz.bookshop.repositories.ProductRepository;

import java.sql.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<CartItem> findUserById(Long userId, int page) {
        Page<CartItem> cartItems = cartRepository.findByUserId(userId, PageRequest.of(page, 10));
        for (CartItem cartItem : cartItems.getContent()) {
            cartItem.setProduct(productRepository.findById(cartItem.getProductId()).orElse(null));
        }
        return cartItems;
    }

    @Override
    public Float totalProductPrice(Long userId) {
        return cartRepository.totalProductPrice(userId);
    }

    @Override
    public void insertIntoCart(Long userId, Date createdAt, Date updatedAt) {
        cartRepository.insertIntoCart(userId, createdAt, updatedAt);
    }

    @Override
    public void insertIntoCartItem(Long cartId, Long productId, Integer quantity, Date createdAt, Date updatedAt) {
        cartRepository.insertIntoCartItem(cartId, productId, quantity, createdAt, updatedAt);
    }

    @Override
    public void insertIntoCartItem(Cart cartByUserId) {

    }

    @Override
    public List<Product> getProductByCartId(Long cartId) {
        return cartRepository.getProductByCartId(cartId);
    }

    @Override
    public Product getProductByCartIdAndProductId(Long cartId, Long productId) {
        return cartRepository.getProductByCartIdAndProductId(cartId, productId);
    }

    @Override
    public void updateCartItemQuantity(Long cartId, Long productId, int quantity, Date updatedAt) {
        cartRepository.updateCartItemQuantity(cartId, productId, quantity, updatedAt);
    }

    @Override
    public void deleteProductByCartId(Long cartId, Long productId) {
        cartRepository.deleteProductByCartId(cartId, productId);
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }
}
