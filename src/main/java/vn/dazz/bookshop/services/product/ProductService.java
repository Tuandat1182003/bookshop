package vn.dazz.bookshop.services.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.dazz.bookshop.common.Response;
import vn.dazz.bookshop.entities.Product;


import java.util.List;

public interface ProductService {

    Page<Product> findALl(int pageNumber);

    List<Product> getAllProduct(String keyword);

    List<Product> getProductsByOrderId(Long orderId);

    List<Product> getAllProductByCategoryId(Long id);

    List<Product> getProductByPublisher(String publisher);

    List<String> getProductByCategory(Long id);

    Response create(Product product);

    List<Product> findTop12Lastest();

    List<Product> getAllProduct();

    Page<Product> findByName(String name, int page, int size);

    Page<Product> findByCategoryIdAndPublisherAndPrice(Long categoryId, List<String> publisher, Float min, Float max, int page, int size);

    List<Product> mostPopularProducts();

    List<Product> discountProduct();

    Product getProductById(Long id);

    Integer getProductCount();

    void insertProduct(Product product, Long categoryId);

    List<String> getPublisherByCategory(Long id);

    void deleteProduct(Long id);

    void updateProduct(Long productId, String name, Float price, Float discount,
                       Short quantity, Short totalBuy, String author,
                       String description, String imageName);

    Page<Product> getAll(Integer integer);
}
