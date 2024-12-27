package vn.dazz.bookshop.services.productReview;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.dazz.bookshop.entities.ProductReview;
import vn.dazz.bookshop.entities.User;

import java.sql.Date;
import java.util.List;

public interface ProductReviewService {
    List<ProductReview> getAllProductReviews();
    List<ProductReview> getAllReviewByProductId(Long id);
    List<User> getAllReviewByUserId(Long id);
    void insertIntoReview(Long userId, Long productId, Integer ratingScore, String content, Boolean isShow, Date createdAt, Date updatedAt);
    void deleteReview(Long id);
    Page<ProductReview> findAll(Integer pageable);
}
