package vn.dazz.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import vn.dazz.bookshop.entities.Category;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.services.category.CategoryService;
import vn.dazz.bookshop.services.product.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/category/{id}")
    public String category(@PathVariable("id") Long id, Model model, @RequestParam(required = false) List<String> publisher,
                           @RequestParam(required = false) Float min, @RequestParam(required = false) Float max) {
        List<Category> categoryList = categoryService.getAllCategory();
        Page<Product> productPage = productService.findByCategoryIdAndPublisherAndPrice(id, publisher, min, max, 0, 10);
        Category category = categoryService.findCategoriesById(id);
        model.addAttribute("page", productPage);
        model.addAttribute("name", category.getName());
        model.addAttribute("id", id);
        model.addAttribute("publishers", productPage);
        model.addAttribute("selectedPublisher", publisher != null ? publisher : new ArrayList<>());
        model.addAttribute("categoryList", categoryList);
        return "category";
    }

}
