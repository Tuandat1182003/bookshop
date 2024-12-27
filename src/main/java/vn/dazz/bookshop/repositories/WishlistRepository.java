package vn.dazz.bookshop.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.entities.Wishlist;

import java.sql.Date;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query("SELECT wl FROM Wishlist wl JOIN Product p ON wl.userId = p.id where wl.userId = :id")
    List<Wishlist> findWishlistByUserId(@Param("id") Long userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO wishlist_item(userId, productId, createdAt) values(:userId, :productId, :createdAt)", nativeQuery = true)
    void insertWishlist(Long userId, Long productId, Date createdAt);

    @Query("SELECT w FROM Wishlist w WHERE w.userId = :userId AND w.productId = :productId ")
    Product findProductByIdAndUserId(Long userId, Long productId);

    @Query("SELECT COUNT(w.id) FROM Wishlist w WHERE w.userId = :userId")
    Integer countWishlistByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Wishlist WHERE userId = :userId AND productId = :productId")
    void deleteWishlistByUserIdAndProductId(Long userId, Long productId);
}
