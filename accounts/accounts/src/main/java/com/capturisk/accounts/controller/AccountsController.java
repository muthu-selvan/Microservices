package com.capturisk.accounts.controller;

import java.util.List;

import com.capturisk.accounts.config.AccountsServiceConfig;
import com.capturisk.accounts.model.*;
import com.capturisk.accounts.repository.AccountsRepository;
import com.capturisk.accounts.service.client.CardsFeignClient;
import com.capturisk.accounts.service.client.LoansFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;

@RestController
public class AccountsController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
	
	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private AccountsServiceConfig accountsConfig;
	
	@Autowired
	private LoansFeignClient loansFeignClient;

	@Autowired
	private CardsFeignClient cardsFeignClient;
	
	@PostMapping("/myAccount")
	@Timed(value = "getAccountDetails.time", description = "Time taken to return Account Details")
	public Account getAccountDetails(@RequestBody Customer customer) {
		return accountsRepository.findByCustomerId(customer.getCustomerId());
	}
	
	@GetMapping("/account/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		final Properties properties = new Properties(accountsConfig.getMessage(), accountsConfig.getBuildVersion(),
				accountsConfig.getMailDetails(), accountsConfig.getActiveBranches());
		return ow.writeValueAsString(properties);
	}
	
	@PostMapping("/myCustomerDetails")
	@CircuitBreaker(name = "detailsForCustomerSupportApp",fallbackMethod="myCustomerDetailsFallBack")
	@Retry(name = "retryForCustomerDetails", fallbackMethod = "myCustomerDetailsFallBack")
	public CustomerDetails myCustomerDetails(@RequestHeader("capturisk-correlation-id") String correlationid,@RequestBody Customer customer) {
		logger.info("myCustomerDetails() method started");
		Account account = accountsRepository.findByCustomerId(customer.getCustomerId());
		List<Loan> loans = loansFeignClient.getLoansDetails(correlationid,customer);
		List<Card> cards = cardsFeignClient.getCardDetails(correlationid,customer);

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccount(account);
		customerDetails.setLoans(loans);
		customerDetails.setCards(cards);
		logger.info("myCustomerDetails() method ended");
		return customerDetails;
	}
	
	private CustomerDetails myCustomerDetailsFallBack(@RequestHeader("capturisk-correlation-id") String correlationid,Customer customer, Throwable t) {
		Account account = accountsRepository.findByCustomerId(customer.getCustomerId());
		List<Loan> loans = loansFeignClient.getLoansDetails(correlationid,customer);
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccount(account);
		customerDetails.setLoans(loans);
		return customerDetails;

	}
	
	@GetMapping("/sayHello")
	@RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
	public String sayHello() {
		return "Hello, Welcome to Capturisk";
	}

	private String sayHelloFallback(Throwable t) {
		return "Hi, Welcome to Capturisk";
	}

}