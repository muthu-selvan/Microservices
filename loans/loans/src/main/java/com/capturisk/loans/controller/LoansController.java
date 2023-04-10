package com.capturisk.loans.controller;

import com.capturisk.loans.config.LoansServiceConfig;
import com.capturisk.loans.model.Customer;
import com.capturisk.loans.model.Loan;
import com.capturisk.loans.model.Properties;
import com.capturisk.loans.repository.LoansRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoansController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoansController.class);

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private LoansServiceConfig loansConfig;

    @PostMapping("/myLoans")
    public List<Loan> getLoansDetails(@RequestHeader("capturisk-correlation-id") String correlationid, @RequestBody Customer customer) {
        LOGGER.info("getLoansDetails() method started");
        List<Loan> loans = loansRepository.findByCustomerIdOrderByStartDateDesc(customer.getCustomerId());
        LOGGER.info("getLoansDetails() method ended");
        return loans;
    }

    @GetMapping("/loans/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(loansConfig.getMessage(), loansConfig.getBuildVersion(),
                loansConfig.getMailDetails(), loansConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

}
