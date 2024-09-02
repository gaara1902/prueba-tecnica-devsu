package com.devsu.customer.enums;

public enum GenderType
{
	MASCULINO("Masculino"), FEMENINO("Femenino"),NO_IDENTIFICA("No Identifica");

	private final String value;

	GenderType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
