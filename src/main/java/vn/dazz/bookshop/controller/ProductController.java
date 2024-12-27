package vn.dazz.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.entities.ProductReview;
import vn.dazz.bookshop.services.category.CategoryService;
import vn.dazz.bookshop.services.product.ProductService;
import vn.dazz.bookshop.services.productReview.ProductReviewService;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductReviewService productReviewService;

    @GetMapping(value = "/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        List<ProductReview> productReviewList = productReviewService.getAllReviewByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("productReviewList", productReviewList);
        return "detail";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam String name, @RequestParam(required = false, defaultValue = "0") Integer page) {
        Page<Product> productList = productService.findByName(name, page, 12);
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("searchKey", name);

        if(productList.getTotalElements() == 0){
            model.addAttribute("message", "No products found");
        } else {
            model.addAttribute("page", productList);
        }
        return "search/prototype";
    }
}
