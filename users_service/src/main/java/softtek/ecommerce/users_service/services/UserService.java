package softtek.ecommerce.users_service.services;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softtek.ecommerce.users_service.entities.User;
import softtek.ecommerce.users_service.repositories.interfaces.UsersRepo;
*/
import java.util.ArrayList;
import java.util.List;

//@Service
public class UserService/* implements UserDetailsService*/ {
    /*@Autowired
    UsersRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByEmail(username);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add( new SimpleGrantedAuthority( user.getRole().getName() ));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles );

        return userDetails;
    }*/
}
