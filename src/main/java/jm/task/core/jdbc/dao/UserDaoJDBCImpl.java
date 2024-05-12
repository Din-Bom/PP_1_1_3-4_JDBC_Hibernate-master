package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        /*String sql = "create table if not exists User\n" +
                "(\n" +
                "    id       INT8 auto_increment,\n" +
                "    name     VARCHAR(45) not null,\n" +
                "    lastName VARCHAR(45) not null,\n" +
                "    age      BINARY      not null,\n" +
                "    constraint id\n" +
                "        primary key (id)\n" +
                ");\n" +
                "\n";*/
        String sql = "CREATE TABLE if not exists `midbtest`.`User` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` INT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;\n";

        try (Connection connection = new Util().getConnection(); Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "drop table if exists User";

        try (Connection connection = new Util().getConnection(); Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user(name, lastName, age) VALUES(?, ?, ?)";

        try (Connection connection = new Util().getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id = ?";

        try (Connection connection = new Util().getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя");
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        List<User> users = new ArrayList<>();

        try (Connection connection = new Util().getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выводе всех пользователей в консоль");
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM user";

        try(Connection connection = new Util().getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы");
        }
    }
}
