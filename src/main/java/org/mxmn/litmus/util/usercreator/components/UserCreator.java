package org.mxmn.litmus.util.usercreator.components;

import lombok.RequiredArgsConstructor;
import org.mxmn.litmus.model.authentication.Role;
import org.mxmn.litmus.model.entity.User;
import org.mxmn.litmus.repository.human.user.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class UserCreator implements CommandLineRunner {
    @Value("${email:#{null}}")
    private String email;

    @Value("${password:#{null}}")
    private String password;

    @Value("${firstName:#{'admin'}}")
    private String firstName;

    @Value("${middleName:#{'admin'}}")
    private String middleName;

    @Value("${lastName:#{'admin'}}")
    private String lastName;

    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    @Override
    public void run(String... args) {
        User admin = createAdmin();
        System.out.printf("""
                        \n# # # USER WAS SUCCESSFULLY CREATED # # #
                        email: %s
                        password: %s
                        role: %s
                        # # # USER WAS SUCCESSFULLY CREATED # # #\n
                        """
                ,admin.getEmail(),password, admin.getRole());
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private User createAdmin () {
        User admin = new User();

        admin.setRole(Role.ADMIN);

        if (!isEmpty(firstName)) {
            admin.setFirstName(firstName);
        }

        if (!isEmpty(middleName)) {
            admin.setMiddleName(middleName);
        }

        if (!isEmpty(lastName)) {
            admin.setLastName(lastName);
        }

        if (!isEmpty(email)) {
            admin.setEmail(email);
        } else throw new RuntimeException("email should not be empty");

        if (!isEmpty(password)) {
            admin.setPassword(passwordEncoder.encode(password));
        } else throw new RuntimeException("password should not be empty");

        admin.setFirstName(firstName);
        admin.setMiddleName(middleName);
        admin.setLastName(lastName);
        admin.setRole(Role.ADMIN);

        return userRepo.create(admin);
    }
}
