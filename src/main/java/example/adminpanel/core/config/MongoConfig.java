package example.adminpanel.core.config;

import java.util.UUID;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import example.adminpanel.core.dto.Role;
import example.adminpanel.core.dto.User;

@Configuration
@PropertySource(value = "classpath:user.properties")
public class MongoConfig implements InitializingBean {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SecurityConfig securityConfig;
    @Value("${db.admin.login}")
    private String adminLogin;
    @Value("${db.admin.password}")
    private String adminPass;

    @Override
    public void afterPropertiesSet() {
        Query query = new Query();
        query.addCriteria(Criteria.where("login").is(adminLogin));
        if (!mongoTemplate.exists(query, User.class)) {
            User admin = new User(adminLogin, securityConfig.encoder().encode(adminPass));
            admin.setId(UUID.randomUUID().toString());
            admin.setRole(Role.ROLE_ADMIN);
            mongoTemplate.insert(admin);
        }


    }
}
