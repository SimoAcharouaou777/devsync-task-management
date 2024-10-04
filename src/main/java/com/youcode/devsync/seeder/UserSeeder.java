package com.youcode.devsync.seeder;

import com.youcode.devsync.model.User;
import com.youcode.devsync.model.enums.UserRole;
import com.youcode.devsync.service.UserService;

public class UserSeeder {
    public static void main(String[] args) {
        UserService userService = new UserService();

        User user1 = new User();
        user1.setUsername("lahcen");
        user1.setFirstName("Lahcen");
        user1.setLastName("ben ammer");
        user1.setEmail("lahcen@gmail.com");
        user1.setPassword("lahcen");
        user1.setUserRole(UserRole.MANAGER);


        User user2 = new User();
        user2.setUsername("soufian");
        user2.setFirstName("soufian");
        user2.setLastName("boushaba");
        user2.setEmail("soufian@gmail.com");
        user2.setPassword("soufian");
        user2.setUserRole(UserRole.USER);


        User user3 = new User();
        user3.setUsername("rabie");
        user3.setFirstName("rabie");
        user3.setLastName("ait imghi");
        user3.setEmail("rabie@gmail.com");
        user3.setPassword("rabie");
        user3.setUserRole(UserRole.USER);

        try{
            userService.addUser(user1);
            userService.addUser(user2);
            userService.addUser(user3);
            System.out.println("Users added successfully");
        }catch (Exception e){
            e.printStackTrace();
    }
}
}
