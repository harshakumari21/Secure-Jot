package net.SpringProject.journalApp.service;

import net.SpringProject.journalApp.entity.User;
import net.SpringProject.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUserName(username);
//        System.out.println(user.getUserName());
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()   //ss vale user ko idhar bulaya, upar user model ka tha
                    .username(user.getUserName())     //ss ka user bulaya usme hmne db vle user ki details add ki hai
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
