package vn.dazz.bookshop.services.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.entities.Wishlist;
import vn.dazz.bookshop.repositories.ProductRepository;
import vn.dazz.bookshop.repositories.WishlistRepository;
import vn.dazz.bookshop.util.Utils;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistImpl implements WishlistService{

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Wishlist> getWishlistByUserId(Long id) {
       List<Wishlist> products = wishlistRepository.findWishlistByUserId(Utils.getUserHadLogin().getId());
       for (Wishlist wishlist : products) {
           wishlist.setProduct(productRepository.findById(wishlist.getProductId()).orElse(null));
       }
       return products;
    }

    @Override
    public void insertWishlist(Long userId, Long productId, Date createdAt) {
        wishlistRepository.insertWishlist(userId, productId, createdAt);
    }

    @Override
    public Product findProductByIdAndUserId(Long userId, Long productId) {
        return wishlistRepository.findProductByIdAndUserId(productId, userId);
    }

    @Override
    public void deleteWishlistByUserIdAndProductId(Long userId, Long productId) {
        wishlistRepository.deleteWishlistByUserIdAndProductId(userId, productId);
    }

    @Override
    public Integer countWishlistByUserId(Long userId) {
        return Optional.ofNullable(wishlistRepository.countWishlistByUserId(userId)).orElse(0);
    }
}
