package vn.dazz.bookshop.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.entities.User;
import vn.dazz.bookshop.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new userDetailimpl(user);
    }

    public static class userDetailimpl implements UserDetails {

        @Getter
        User user;

        public userDetailimpl(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return  List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return Objects.equals(1, user.getStatus());
        }
    }
}
