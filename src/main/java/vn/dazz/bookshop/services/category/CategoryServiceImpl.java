package vn.dazz.bookshop.services.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.common.Response;
import vn.dazz.bookshop.entities.Category;
import vn.dazz.bookshop.repositories.CategoryRepository;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Response create(Category category) {
        Response response = new Response(0, "ERROR", null);
        categoryRepository.save(category);
        response = new Response(1, "SUCCESS", category);
        return response;
    }

    @Override
    public Category findCategoriesById(Long id) {
        return categoryRepository.findCategoriesById(id);
    }

    @Override
    public void insertCategory(Category category) {
        category.setName(category.getName());
        category.setDescription(category.getDescription());
        category.setImageName(category.getImageName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteCategoryById(id);
    }
}
