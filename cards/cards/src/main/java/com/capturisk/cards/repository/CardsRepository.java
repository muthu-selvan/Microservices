package com.capturisk.cards.repository;

import com.capturisk.cards.model.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends CrudRepository<Card, Long> {
	List<Card> findByCustomerId(int customerId);
}