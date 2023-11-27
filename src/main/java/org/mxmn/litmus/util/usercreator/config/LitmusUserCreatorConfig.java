package org.mxmn.litmus.util.usercreator.config;


import org.mxmn.litmus.repository.user.UserRepo;
import org.mxmn.litmus.repository.user.UserRepoImpl;
import org.mxmn.litmus.security.LitmusSecurityAuditorAware;
import org.mxmn.litmus.security.LitmusSecurityAuditorAwareImpl;
import org.mxmn.litmus.util.audit.users.BasicUserAuditUtil;
import org.mxmn.litmus.util.audit.users.UserAuditUtil;
import org.mxmn.litmus.util.timestamp.TimestampProvider;
import org.mxmn.litmus.util.timestamp.TimestampProviderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EntityScan(basePackageClasses = {
        org.mxmn.litmus.model.entity.CoreEntity.class
})
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

    @Bean
    public LitmusSecurityAuditorAware litmusSecurityAuditorAware (UserRepo userRepo) {
        return new LitmusSecurityAuditorAwareImpl(userRepo);
    }

    @Bean
    public TimestampProvider timestampProvider () {
        return new TimestampProviderImpl();
    }
    @Bean
    public UserAuditUtil userAuditUtil(LitmusSecurityAuditorAware securityAuditorAware,
                                       UserRepo userRepo, TimestampProvider timestampProvider) {
        return new BasicUserAuditUtil(securityAuditorAware, userRepo, timestampProvider);
    }
}
