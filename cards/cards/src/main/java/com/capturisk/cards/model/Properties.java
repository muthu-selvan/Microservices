package com.capturisk.cards.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Properties {
	private String message;
	private String buildVersion;
	private Map<String, String> mailDetails;
	private List<String> activeBranches;
}