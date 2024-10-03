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

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean updateUser(String username, String password, String firstName, String lastName, String email){
        User user = userRepository.findByUsername(username);
        if(user != null){
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            userRepository.update(user);
            return true;
        }
        return false;
    }

    public boolean updateUsername(String currentUsername, String newUsername){
        User user = userRepository.findByUsername(currentUsername);
        if(user != null){
            user.setUsername(newUsername);
            userRepository.update(user);
            return true;
        }
        return false;
    }
    public void deleteUser(String username){
        User user = userRepository.findByUsername(username);
        if(user != null){
            userRepository.delete(user);
        }
    }
    public String getHashedPassword(String username){
        User user = userRepository.findByUsername(username);
        if(user != null){
            return user.getPassword();
        }
        return null;
    }

    public void close(){
        userRepository.close();
    }
}
