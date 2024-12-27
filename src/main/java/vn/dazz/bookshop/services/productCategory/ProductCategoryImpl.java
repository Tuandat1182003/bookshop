package vn.dazz.bookshop.services.productCategory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.repositories.ProductCategoryRepository;

@Service
public class ProductCategoryImpl implements ProductCategoryService{
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public void insertProductCategory(Long productId, Long CategoryId) {
        productCategoryRepository.insertProductCategory(productId, CategoryId);
    }

    @Override
    public void deleteByProductId(Long productId) {
        productCategoryRepository.deleteById(productId);
    }
}
