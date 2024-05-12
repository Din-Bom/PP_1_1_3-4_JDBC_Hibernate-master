package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь



        UserServiceImpl usi = new UserServiceImpl();
        usi.createUsersTable();
        usi.saveUser("Victor", "Abuh", (byte) 11);
        usi.saveUser("Albert", "Rock", (byte) 22);
        usi.saveUser("Irina", "Pink", (byte) 33);
        usi.saveUser("Alexandr", "Stone", (byte) 44);
        for (User user : usi.getAllUsers()) {
            System.out.println(user.toString());
        }
        usi.cleanUsersTable();
        usi.cleanUsersTable();
    }
}
