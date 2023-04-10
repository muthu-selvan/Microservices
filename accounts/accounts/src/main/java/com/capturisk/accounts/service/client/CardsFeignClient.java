package com.capturisk.accounts.service.client;

import java.util.List;

import com.capturisk.accounts.model.Card;
import com.capturisk.accounts.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("cards")
public interface CardsFeignClient {

	@RequestMapping(method = RequestMethod.POST, value = "myCards", consumes = "application/json")
	List<Card> getCardDetails(@RequestHeader("capturisk-correlation-id") String correlationid, @RequestBody Customer customer);
}