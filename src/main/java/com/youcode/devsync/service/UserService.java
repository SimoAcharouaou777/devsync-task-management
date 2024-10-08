package com.youcode.devsync.service;

import com.youcode.devsync.model.ChangeRequest;
import com.youcode.devsync.model.Task;
import com.youcode.devsync.model.User;
import com.youcode.devsync.model.enums.UserRole;
import com.youcode.devsync.repository.ChangeRequestRepository;
import com.youcode.devsync.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserService {
    private UserRepository userRepository = new UserRepository();
    private ChangeRequestRepository changeRequestRepository = new ChangeRequestRepository();

    public void registerUser(String username, String password, String firstName, String lastName, String email){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashedPassword, firstName, lastName, email, UserRole.USER);
        userRepository.save(user);
    }

    public User authenticateUser(String username, String password){
        User user = userRepository.findByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())){
            return user;
        }
        return null;
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
    public void addUser(User user) throws Exception{
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
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

    public List<User> getUsersByRole(UserRole role){
        return userRepository.findByRole(role);
    }

    public User findById(long id){
        return userRepository.findById(id);
    }

    public void close(){
        userRepository.close();
    }

    public void updateUserTickets(User user){
        User existingUser = userRepository.findById(user.getId());
        if(existingUser != null){
            existingUser.setTickets(user.getTickets());
            userRepository.update(existingUser);
        }
    }

    public boolean hasChangeRequest(Task task , User requester){
        Optional<ChangeRequest> changeRequest = changeRequestRepository.findByTaskAndRequester(task, requester);
        return changeRequest.isPresent();
    }

    public void addChangeRequest(ChangeRequest changeRequest){
        changeRequestRepository.save(changeRequest);
    }

    public List<ChangeRequest> getAllChangeRequests(){
        return changeRequestRepository.findAll();
    }

    public List<ChangeRequest> getAllByManager(long managerId){
        return changeRequestRepository.findByManager(managerId);
    }

    public void reassignTask(long changeRequestId, long newAssigneeId) {
        changeRequestRepository.reassignTask(changeRequestId, newAssigneeId);
    }

    public void deleteChangeRequest(long changeRequestId){
        changeRequestRepository.deleteChangeRequest(changeRequestId);
    }
}
