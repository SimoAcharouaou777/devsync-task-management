package com.youcode.devsync.service;

import com.youcode.devsync.model.User;
import com.youcode.devsync.model.enums.UserRole;
import com.youcode.devsync.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public void registerUser(String username, String password, String firstName, String lastName, String email){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashedPassword, firstName, lastName, email, UserRole.USER);
        userRepository.save(user);
    }

    public boolean authenticateUser(String username, String password){
        User user = userRepository.findByUsername(username);
        return user != null && BCrypt.checkpw(password, user.getPassword());
    }

    public void close(){
        userRepository.close();
    }
}
