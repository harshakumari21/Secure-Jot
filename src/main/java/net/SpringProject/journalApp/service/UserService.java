package net.SpringProject.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.SpringProject.journalApp.entity.User;
import net.SpringProject.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // passwordEncoder will inherit
                                                                                        // all func of bcrypt

    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
        // methods suchb as save delete findbyid are provided us by jpaˀ to achieve orm.
        // we can use these methods by orm tool or pp in our case its hibernate
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {

        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {

        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {

        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
