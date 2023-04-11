package com.capturisk.accounts.model;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
public class Loan {
	private int loanNumber;
	private int customerId;
	private Date startDate;
	private String loanType;
	private int totalLoan;
	private int amountPaid;
	private int outstandingAmount;
	private String createDate;
}