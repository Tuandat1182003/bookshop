package vn.dazz.bookshop.services.category;


import vn.dazz.bookshop.common.Response;
import vn.dazz.bookshop.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();

    Response create(Category category);

    Category findCategoriesById(Long id);

    void insertCategory(Category category);

    void deleteCategoryById(Long id);
}
