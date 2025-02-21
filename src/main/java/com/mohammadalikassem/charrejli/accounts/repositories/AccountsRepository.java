package com.mohammadalikassem.charrejli.accounts.repositories;


import com.mohammadalikassem.charrejli.accounts.entities.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long>,
        JpaSpecificationExecutor<Account> {
    @Query(value = """
            select a from Account a
            where a.active = true and a.operator = ?1 and a.status not in ?2 and (a.lastUpdatedAt < ?3 or a.lastUpdatedAt is null) order by a.lastUpdatedAt desc limit 1""")
    Optional<Account> getAccountToRefresh(Account.Operator operator, Collection<Account.Status> statuses, LocalDateTime lastUpdatedAt);
}