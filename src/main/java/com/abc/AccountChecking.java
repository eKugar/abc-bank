package com.abc;

public class AccountChecking extends Account
{
	private static final String CHECKING = "Checking";

	@Override
	public double interestEarned()
	{
		return sumTransactions() * 0.001;
	}

	@Override
	public String getAccountType()
	{
		return CHECKING;
	}
}
