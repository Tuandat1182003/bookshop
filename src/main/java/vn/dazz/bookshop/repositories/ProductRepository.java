package vn.dazz.bookshop.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.dazz.bookshop.entities.Product;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    List<Product> findAllByName(String name);

    @Query("SELECT p FROM Product p JOIN OrderItem o ON o.productId = p.id WHERE o.orderId = :orderId")
    List<Product> getProductByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    List<Product> findAll(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p, ProductCategory pc " +
            "WHERE pc.categoryId = :categoryId AND p.id = pc.productId")
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT p.publisher FROM Product p " +
            "JOIN ProductCategory pc ON p.id = pc.productId " +
            "WHERE pc.categoryId = :categoryId AND p.publisher IS NOT NULL GROUP BY p.publisher")
    List<String> findPublishersByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM  Product p, ProductCategory  pc " +
            "where pc.categoryId = :categoryId AND p.id = pc.productId AND p.publisher IN :publisher")
    Page<Product> findByCategoryIdAndPublisher(@Param("categoryId") Long categoryId,@Param("publisher") List<String> publisher, Pageable pageable);

    @Query("Select p from Category c JOIN ProductCategory pc ON c.id = pc.categoryId " +
            "JOIN Product p on pc.productId = p.id where c.id = :id")
    List<Product> findAllProductsByCategoryId(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.publisher LIKE %:publisher%")
    List<Product> findProductByPublisher(@Param("publisher") String publisher);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Page<Product> findByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT p FROM  Product p, ProductCategory  pc " +
            "where pc.categoryId = :categoryId AND p.id = pc.productId AND p.publisher IN :publisher AND p.price < :max AND p.price > :min")
    Page<Product> findByCategoryIdAndPublisherAndPrice(@Param("categoryId") Long categoryId,@Param("publisher") List<String> publisher,@Param("min") Float min,@Param("max") Float max, Pageable pageable);

    @Query(value = "SELECT p.* from product p order by p.createdAt DESC limit 12", nativeQuery = true)
    List<Product> findTop12Lastest();

    @Query("Select p from Product p order by p.totalBuy DESC limit 12")
    List<Product> topTotalBuy();

    @Query("Select p from Product p WHERE p.discount > 0")
    List<Product> discountProduct();

    @Query("Select p from Product p where p.id = :id")
    Product getProductById(Long id);

    @Query("SELECT COUNT(p.id) FROM Product p")
    Integer sumProduct();

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p where p.id = :productId")
    void deleteById(Long productId);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.name = :name, p.price = :price, p.discount = :discount, " +
            "p.quantity = :quantity, p.totalBuy = :totalBuy, p.author = :author, " +
            "p.description = :description, p.imageName = :imageName WHERE p.id = :productId")
    void updateProduct(@Param("name") String name,
                       @Param("price") Float price,
                       @Param("discount") Float discount,
                       @Param("quantity") Short quantity,
                       @Param("totalBuy") Short totalBuy,
                       @Param("author") String author,
                       @Param("description") String description,
                       @Param("imageName") String imageName,
                       @Param("productId") Long productId);

    @Query("SELECT MONTH(p.createdAt) AS month, SUM(p.price * p.quantity) AS revenue " +
            "FROM OrderItem p " +
            "WHERE YEAR(p.createdAt) = :year " +
            "GROUP BY MONTH(p.createdAt) " +
            "ORDER BY MONTH(p.createdAt)")
    List<Object[]> findMonthlyRevenueByYear(@Param("year") int year);


}
