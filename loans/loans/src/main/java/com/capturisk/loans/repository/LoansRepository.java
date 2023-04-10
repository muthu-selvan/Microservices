package com.capturisk.loans.repository;

import com.capturisk.loans.model.Loan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoansRepository extends CrudRepository<Loan, Long> {
    List<Loan> findByCustomerIdOrderByStartDateDesc(int customerId);
}
