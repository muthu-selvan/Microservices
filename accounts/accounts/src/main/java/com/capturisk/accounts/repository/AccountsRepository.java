package com.capturisk.accounts.repository;

import com.capturisk.accounts.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {
	Account findByCustomerId(int customerId);
}