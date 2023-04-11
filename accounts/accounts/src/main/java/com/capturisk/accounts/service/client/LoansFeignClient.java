package com.capturisk.accounts.service.client;

import java.util.List;

import com.capturisk.accounts.model.Customer;
import com.capturisk.accounts.model.Loan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("loans")
public interface LoansFeignClient {

	@PostMapping(value = "/myLoans", consumes = "application/json")
	List<Loan> getLoansDetails(@RequestHeader("capturisk-correlation-id") String correlationid, @RequestBody Customer customer);
}