package vn.dazz.bookshop.services.wishlist;


import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.entities.Wishlist;

import java.sql.Date;
import java.util.List;

public interface WishlistService {
    List<Wishlist> getWishlistByUserId(Long id);
    void insertWishlist(Long userId, Long productId, Date createdAt);
    Product findProductByIdAndUserId(Long userId, Long productId);
    void deleteWishlistByUserIdAndProductId(Long userId, Long productId);
    Integer countWishlistByUserId(Long userId);
}
