package vn.dazz.bookshop.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.common.Response;
import vn.dazz.bookshop.entities.Category;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.entities.ProductCategory;
import vn.dazz.bookshop.repositories.ProductCategoryRepository;
import vn.dazz.bookshop.repositories.ProductRepository;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public Page<Product> findALl(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 8);
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getAllProduct(String keyword) {
        if(keyword != null) {
            return productRepository.findAll(keyword);
        }
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByOrderId(Long orderId) {
        return productRepository.getProductByOrderId(orderId);
    }

    @Override
    public List<Product> getAllProductByCategoryId(Long id) {
        return productRepository.findAllProductsByCategoryId(id);
    }

    @Override
    public List<Product> getProductByPublisher(String publisher) {
        return productRepository.findProductByPublisher(publisher);
    }

    @Override
    public List<String> getProductByCategory(Long id) {
        return List.of();
    }

    @Override
    public Response create(Product product) {
        Response response = new Response(0, "ERROR", null);
        productRepository.save(product);
        response = new Response(1, "SUCCESS", product);
        return response;
    }

    @Override
    public List<Product> findTop12Lastest() {
        return productRepository.findTop12Lastest();
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findByName(String name, int page, int size) {
        return productRepository.findByName(name, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @Override
    public Page<Product> findByCategoryIdAndPublisherAndPrice(Long categoryId, List<String> publisher, Float min, Float max, int page, int size) {
        if (publisher == null || publisher.isEmpty()) {
            return productRepository.findByCategoryId(categoryId, PageRequest.of(page,size));
        }
        if (min == null || max == null) {
            return productRepository.findByCategoryIdAndPublisher(categoryId, publisher, PageRequest.of(page,size));
        }
        return productRepository.findByCategoryIdAndPublisherAndPrice(categoryId, publisher, min, max, PageRequest.of(page,size));
    }

    @Override
    public List<Product> mostPopularProducts() {
        return productRepository.topTotalBuy();
    }


    @Override
    public List<String> getPublisherByCategory(Long id) {
        return productRepository.findPublishersByCategory(id);
    }

    @Override
    public void deleteProduct(Long id) {
        productCategoryRepository.deleteById(id);
        productRepository.deleteById(id);
    }

    @Override
    public void updateProduct(Long productId, String name, Float price, Float discount,
                         Short quantity, Short totalBuy, String author,
                         String description, String imageName) {
        productRepository.updateProduct(name, price, discount, quantity, totalBuy,
                author, description, imageName, productId);
    }

    @Override
    public Page<Product> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 8);
        return this.productRepository.findAll(pageable);
    }

    @Override
    public List<Product> discountProduct() {
        return productRepository.discountProduct();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    @Override
    public Integer getProductCount() {
        return productRepository.sumProduct();
    }

    @Override
    public void insertProduct(Product product, Long categoryId) {
        product.setName(product.getName());
        product.setPrice(product.getPrice());
        product.setDiscount(product.getDiscount());
        product.setQuantity(product.getQuantity());
        product.setTotalBuy(product.getTotalBuy());
        product.setAuthor(product.getAuthor());
        product.setDescription(product.getDescription());
        product.setImageName(product.getImageName());
        product.setCreatedAt(LocalDateTime.now());

        ProductCategory productCategory = new ProductCategory();
        Category category = new Category();
        category.setId(categoryId);

        productCategory.setProductId(product.getId());
        productCategory.setCategoryId(categoryId);

        productRepository.save(product);
    }
}
