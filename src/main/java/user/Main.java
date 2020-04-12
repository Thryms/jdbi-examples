package user;

import ex9.LegoSetDao;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();
            User user = User.builder()
                    .username("007")
                    .password("JBPswd")
                    .name("James Bond")
                    .email("jbond@email.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(true)
                    .build();

            User user1 = User.builder()
                    .username("dont")
                    .password("know")
                    .name("Dont Know")
                    .email("dont@know.com")
                    .gender(User.Gender.FEMALE)
                    .dob(LocalDate.parse("2020-04-12"))
                    .enabled(true)
                    .build();


            dao.insert(user);
            dao.insert(user1);
            dao.list().stream().forEach(System.out::println);
            System.out.println(dao.findById(1));
            System.out.println(dao.findByUsername("dont"));
            dao.delete(user1);
            dao.list().stream().forEach(System.out::println);

        }
    }
}