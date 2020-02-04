package example.adminpanel.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import example.adminpanel.core.dto.User;


public interface AdminUserRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<User> {
    User findByLogin(String login);

    boolean existsByLogin(String login);
}
