package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory = getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `midbtest`.`user`(\n" +
                "  id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,\n" +
                "  lastName VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,\n" +
                "  age TINYINT NULL,\n" +
                "  PRIMARY KEY (id));\n";

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
        }
    }

        @Override
        public void dropUsersTable () {
            String sql = "drop table if exists user";

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.createSQLQuery(sql).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            }
        }

        @Override
        public void saveUser (String name, String lastName,byte age){
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                User user = new User(name, lastName, age);
                session.save(user);
                session.getTransaction().commit();
                System.out.format("User с именем - %s добавлен в базу данных \n", lastName);
            } catch (Exception e) {
                e.printStackTrace();
                if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            }
        }

        @Override
        public void removeUserById ( long id){
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                User user = session.get(User.class, id);
                if (user != null) {
                    session.delete(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            }
        }

        @Override
        public List<User> getAllUsers () {
            List<User> users = new ArrayList<>();
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                users = session.createQuery("from User").list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return users;
        }

        @Override
        public void cleanUsersTable () {
            String sql = "DELETE FROM user";

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.createSQLQuery(sql).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            }
        }
    }
