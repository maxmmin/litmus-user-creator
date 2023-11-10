package org.mxmn.litmus.util.usercreator.config;

import org.mxmn.litmus.repository.human.user.UserRepo;
import org.mxmn.litmus.repository.human.user.UserRepoImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EntityScan(basePackageClasses = org.mxmn.litmus.model.entity.User.class)
public class LitmusUserCreatorConfig {

    @Value("${strength:#{'12'}}")
    private String encoderStrength;

    @Bean
    public PasswordEncoder passwordEncoder () {
        int numEncoderStrength;
        try {
            numEncoderStrength = Integer.parseInt(encoderStrength,10);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("invalid encoder strength");
        }
        return new BCryptPasswordEncoder(numEncoderStrength);
    }

    @Bean
    public UserRepo userRepo () {
        return new UserRepoImpl();
    }
}
