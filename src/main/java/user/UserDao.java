package user;


import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.Optional;


@RegisterBeanMapper(User.class)
public interface UserDao {

    @SqlUpdate("""
        CREATE TABLE user (
            id IDENTITY PRIMARY KEY,
            username VARCHAR UNIQUE,
            password VARCHAR NOT NULL,
            name VARCHAR NOT NULL,
            email VARCHAR UNIQUE,
            gender VARCHAR NOT NULL,
            dob DATE NOT NULL,
            enabled BOOLEAN NOT NULL
        )
        """
    )

    void createTable();

    @SqlUpdate("INSERT INTO user (username, password, name, email, gender, dob, enabled) VALUES (:username, :password, :name, :email, :gender, :dob, :enabled) ")
    @GetGeneratedKeys
    long insert(@BindBean User user);

    @SqlQuery("SELECT * FROM User WHERE id = :id")
    Optional<User> findById(@Bind("id") long id);

    @SqlQuery("SELECT * FROM User WHERE username = :username")
    Optional<User> findByUsername(@Bind("username") String username);

    @SqlUpdate("DELETE FROM user WHERE username = :username")
    void delete(@BindBean User user);

    @SqlQuery("SELECT * FROM user")
    List<User> list();




}
