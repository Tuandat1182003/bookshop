package vn.dazz.bookshop.adminPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.dazz.bookshop.entities.Category;
import vn.dazz.bookshop.services.category.CategoryService;

import java.util.List;

@Controller
public class CategoryManageController {
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/adminPage/categoryManage")
    public String categoryManage(Model model) {
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        return "adminPage/categoryManage";
    }

    @GetMapping(value = "/adminPage/categoryManage/add")
    public String categoryManageAdd(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "adminPage/category/categoryManageAdd";
    }

    @PostMapping(value = "/adminPage/categoryManage/add")
    public String categoryManageAdd(Category category, RedirectAttributes redirectAttributes) {
        categoryService.insertCategory(category);
        redirectAttributes.addFlashAttribute("messSuccess", "Category added successfully");
        return "redirect:/adminPage/categoryManage";
    }

    @GetMapping(value = "/adminPage/categoryManage/delete/{id}")
    public String categoryManageDelete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategoryById(id);
        redirectAttributes.addFlashAttribute("messDeleteSuccess", "Category deleted successfully");
        return "redirect:/adminPage/categoryManage";
    }
}
