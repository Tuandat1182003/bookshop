package vn.dazz.bookshop.util;

import org.springframework.security.core.context.SecurityContextHolder;
import vn.dazz.bookshop.config.UserDetailServiceImpl;
import vn.dazz.bookshop.entities.User;


public class Utils {
    public static User getUserHadLogin() {
        SecurityContextHolder.getContext().getAuthentication();
        Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailServiceImpl.userDetailimpl) {
            return ((UserDetailServiceImpl.userDetailimpl) principal).getUser();
        } else {
            return new User();
        }
    }
}
