package vn.dazz.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.dazz.bookshop.entities.Category;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.services.category.CategoryService;
import vn.dazz.bookshop.services.product.ProductService;

import java.util.List;

@Controller
public class ShopController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/shop")
    public String shop(Model model) {
        List<Product> productListDiscount = productService.discountProduct();
        List<Category> categoryList = categoryService.getAllCategory();
        List<Product> getAll = productService.getAllProduct();
        model.addAttribute("productListDiscount", productListDiscount);
        model.addAttribute("products", getAll);
        model.addAttribute("categoryList", categoryList);
        return "shop";
    }


}
