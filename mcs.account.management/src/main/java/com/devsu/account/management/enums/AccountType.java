package com.devsu.account.management.enums;

public enum AccountType
{
	AHORROS("Ahorros"), CORRIENTE("Corriente");

	private final String value;

	AccountType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
