package vn.dazz.bookshop.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.dazz.bookshop.entities.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO product_category(productId, categoryId) values (:productId, :categoryId)", nativeQuery = true)
    void insertProductCategory(Long productId, Long categoryId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductCategory pc where pc.productId = :productId")
    void deleteById(Long productId);
}
