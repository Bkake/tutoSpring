package fr.free.bkake.core.repository;


import fr.free.bkake.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserRespository extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {
    User findByNumber(String number);
}
