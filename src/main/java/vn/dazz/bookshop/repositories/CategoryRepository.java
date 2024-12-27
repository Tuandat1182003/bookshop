package vn.dazz.bookshop.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.dazz.bookshop.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT cat from Category cat where cat.id = :id")
    Category findCategoriesById(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Category c WHERE c.id = :id")
    void deleteCategoryById(Long id);
}
