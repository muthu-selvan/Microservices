package com.capturisk.accounts.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Properties {
	private String message;
	private String buildVersion;
	private Map<String, String> mailDetails;
	private List<String> activeBranches;
}