package vn.dazz.bookshop.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.dazz.bookshop.entities.Cart;
import vn.dazz.bookshop.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u from User u where u.username LIKE :username ")
    User findByUsername(String username);

    @Query("select u from User u where u.email like :email")
    User findByEmail(String email);

    @Query("Select u from User u where u.phoneNumber like :phoneNumber")
    User findByPhoneNumber(String phoneNumber);

    User getUserById(Long id);
    User findAllById(Long id);

    @Query("select c from Cart c join User u ON c.userId = u.id WHERE u.id = :id")
    Cart findCartByUserId(@Param("id") Long id);

    @Query("SELECT COUNT(u.id) FROM User u")
    Integer countUserById();

    @Query("SELECT u FROM User u JOIN Orders o ON u.id = o.userId WHERE o.id = :id")
    User findUserByOrderId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO User(username, password, fullname, email, phoneNumber, gender, address, role) " +
            "values (:username, :password, :fullname, :email, :phoneNumber, :gender, :address, :role)", nativeQuery = true)
    void insertUser(String username, String password, String fullname, String email, String phoneNumber, Boolean gender, String address, String role);

    User findUserByCode(String code);
}
