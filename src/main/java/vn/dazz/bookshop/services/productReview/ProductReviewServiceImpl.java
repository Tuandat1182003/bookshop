package vn.dazz.bookshop.services.productReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.entities.ProductReview;
import vn.dazz.bookshop.entities.User;
import vn.dazz.bookshop.repositories.ProductRepository;
import vn.dazz.bookshop.repositories.ReviewRepository;
import vn.dazz.bookshop.repositories.UserRepository;

import java.sql.Date;
import java.util.List;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductReview> getAllProductReviews() {
        List<ProductReview> list = reviewRepository.findAll();
        for (ProductReview productReview : list) {
            productReview.setUser(userRepository.getUserById(productReview.getUserId()));
            productReview.setProduct(productRepository.getProductById(productReview.getProductId()));
        }
        return list;
    }

    @Override
    public List<ProductReview> getAllReviewByProductId(Long id) {
        List<ProductReview> list = reviewRepository.getProductReviewByProductId(id);
        for(ProductReview productReview : list){
            productReview.setUser(userRepository.findAllById(productReview.getUserId()));
        }
       return reviewRepository.getProductReviewByProductId(id);
    }

    @Override
    public List<User> getAllReviewByUserId(Long id) {
        return reviewRepository.getProductReviewByUserId(id);
    }

    @Override
    public void insertIntoReview(Long userId, Long productId, Integer ratingScore, String content, Boolean isShow, Date createdAt, Date updatedAt) {
        reviewRepository.insertIntoReview(userId, productId, ratingScore, content,  isShow, createdAt, updatedAt);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Page<ProductReview> findAll(Integer pageNo) {
        List<ProductReview> list = reviewRepository.findAll();
        for (ProductReview productReview : list) {
            productReview.setUser(userRepository.getUserById(productReview.getUserId()));
            productReview.setProduct(productRepository.getProductById(productReview.getProductId()));
        }
        Pageable pageable = PageRequest.of(pageNo-1, 10);
        return this.reviewRepository.findAll(pageable);
    }
}
