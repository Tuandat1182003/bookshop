package vn.dazz.bookshop.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.dazz.bookshop.entities.ProductReview;
import vn.dazz.bookshop.entities.User;


import java.sql.Date;
import java.util.List;

public interface ReviewRepository extends JpaRepository<ProductReview, Long> {
    @Query("Select pr from ProductReview pr" +
            " JOIN User u ON pr.userId = u.id " +
            "WHERE pr.productId = :id ORDER BY pr.createdAt desc ")
    List<ProductReview> getProductReviewByProductId(Long id);

    @Query("Select pr from ProductReview pr" +
            " JOIN User u ON pr.userId = u.id " +
            "WHERE u.id = :id")
    List<User> getProductReviewByUserId(Long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO product_review(userId, productId, ratingScore, content, isShow, createdAt, updatedAt) " +
            "values(:userId, :productId, :ratingScore, :content, :isShow, :createdAt, :updatedAt)", nativeQuery = true)
    void insertIntoReview(Long userId, Long productId, Integer ratingScore, String content, Boolean isShow, Date createdAt, Date updatedAt);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductReview p WHERE p.id = :reviewId")
    void deleteProductReviewBy(Long reviewId);
}
