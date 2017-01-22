package fr.free.bkake.core.repository;

import fr.free.bkake.core.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface AccountRepository extends JpaRepository<Account, Long>, QueryDslPredicateExecutor<Account> {
}
