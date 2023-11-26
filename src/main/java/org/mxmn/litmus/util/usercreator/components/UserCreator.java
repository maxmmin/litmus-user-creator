package org.mxmn.litmus.util.usercreator.components;

import lombok.RequiredArgsConstructor;
import org.mxmn.litmus.model.authentication.Role;
import org.mxmn.litmus.model.entity.user.User;
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

    @Value("${role:#{null}}")
    private String role;

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
        User admin = createUser();
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

    private User createUser() {
        User user = new User();

        Role parsedRole;
        if (isEmpty(role)) throw new RuntimeException("empty role");
        try {
            parsedRole = Role.valueOf(role);
        } catch (IllegalArgumentException exception) {
            throw new RuntimeException("invalid role");
        }

        user.setRole(parsedRole);

        if (!isEmpty(firstName)) {
            user.setFirstName(firstName);
        }

        if (!isEmpty(middleName)) {
            user.setMiddleName(middleName);
        }

        if (!isEmpty(lastName)) {
            user.setLastName(lastName);
        }

        if (!isEmpty(email)) {
            user.setEmail(email);
        } else throw new RuntimeException("email should not be empty");

        if (!isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        } else throw new RuntimeException("password should not be empty");

        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);

        return userRepo.create(user);
    }
}
