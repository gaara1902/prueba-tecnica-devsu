package com.devsu.account.management.enums;

public enum TransactionType
{
	DEBITO("Debito"), CREDITO("Credito");

	private final String value;

	TransactionType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
