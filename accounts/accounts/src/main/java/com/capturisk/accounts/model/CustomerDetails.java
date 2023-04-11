package com.capturisk.accounts.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CustomerDetails {
	private Account account;
	private List<Loan> loans;
	private List<Card> cards;
}