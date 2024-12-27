package vn.dazz.bookshop.adminPage;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.dazz.bookshop.entities.Category;
import vn.dazz.bookshop.entities.Product;
import vn.dazz.bookshop.repositories.ProductCategoryRepository;
import vn.dazz.bookshop.repositories.ProductRepository;
import vn.dazz.bookshop.services.category.CategoryService;
import vn.dazz.bookshop.services.product.ProductService;
import vn.dazz.bookshop.services.productCategory.ProductCategoryService;

import java.util.List;

@Controller
public class ProductManageController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @GetMapping(value = "/adminPage/productManage")
    public String productManage(Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Product> products = productService.getAll(pageNo);
//        List<Product> products = productService.getAllProduct();
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
        return "adminPage/productManage";
    }


    @GetMapping(value = "/adminPage/productManage/addProduct")
    public String productManageAdd(Model model) {
        Product product = new Product();
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "adminPage/product/productManageAdd";
    }

    @PostMapping(value = "/adminPage/productManage/add")
    public String productManagePost(@Valid Product product, BindingResult bindingResult,
            @RequestParam Long categoryId,
            RedirectAttributes redirectAttributes,
            Model model) {
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        if(bindingResult.hasErrors()) {
            return "adminPage/product/productManageAdd";
        }

        productService.insertProduct(product, categoryId);
        productCategoryRepository.insertProductCategory(product.getId(), categoryId);
        redirectAttributes.addFlashAttribute("messageSuccess", "Product added successfully");
        return "redirect:/adminPage/productManage";
    }

    @GetMapping(value = "/adminPage/productManage/delete")
    public String productManageDelete(@RequestParam Long productId,
            RedirectAttributes redirectAttributes) {
        productCategoryService.deleteByProductId(productId);
        productService.deleteProduct(productId);
        redirectAttributes.addFlashAttribute("messageDelete", "Product deleted successfully");
        return "redirect:/adminPage/productManage";
    }

    @GetMapping(value = "/adminPage/productManage/update/{id}")
    public String productManageUpdate(Model model, @PathVariable Long id) {
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);

        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "adminPage/product/productManageUpdate";
    }

    @PostMapping(value = "/adminPage/productManage/update")
    public String productManageUpdate(@Valid @ModelAttribute("product") Product productDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "adminPage/product/productManageUpdate";
        }

        productService.updateProduct(productDTO.getId(), productDTO.getName(), productDTO.getPrice(),
                productDTO.getDiscount(), productDTO.getQuantity(), productDTO.getTotalBuy(),
                productDTO.getAuthor(), productDTO.getDescription(), productDTO.getImageName());

        redirectAttributes.addFlashAttribute("messageUpdate", "Product updated successfully");
        return "redirect:/adminPage/productManage";
    }

}
