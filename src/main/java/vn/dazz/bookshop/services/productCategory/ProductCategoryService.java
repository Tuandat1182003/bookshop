package vn.dazz.bookshop.services.productCategory;

import org.springframework.stereotype.Service;

public interface ProductCategoryService {
    void insertProductCategory(Long productId, Long CategoryId);
    void deleteByProductId(Long productId);
}
