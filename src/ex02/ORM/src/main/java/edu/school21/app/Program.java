package edu.school21.app;

import edu.school21.models.User;
import edu.school21.repositories.EmbeddedDataSource;
import edu.school21.services.OrmManager;

import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        User user1 = new User(1L, "Ivan", "Ivanov", 20);
        User user2 = new User(3L, "Ann", "Sir", 30);
        try {
            OrmManager ormManager = new OrmManager(dataSource.getDataSource());
            System.out.println();
            ormManager.save(user1);
            System.out.println();
            ormManager.save(user2);
            System.out.println();
            user1.setLastName("Kim");
            user1.setAge(null);
            ormManager.update(user1);
            System.out.println();
            System.out.println(ormManager.findById(1L, User.class));
            System.out.println();
            System.out.println(ormManager.findById(2L, User.class));

        } catch (SQLException ex) {
            System.err.println("Error SQL: " + ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.err.println("Error access: " + ex.getMessage());
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
            System.err.println("Error instantiation");
        }
    }
}
