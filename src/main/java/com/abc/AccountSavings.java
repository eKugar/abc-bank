package com.abc;

public class AccountSavings extends Account
{
	private static final String SAVINGS = "Savings";

	public double interestEarned()
	{
		double amount = sumTransactions();
		if( amount <= 1000 )
		{
			amount *= 0.001;
		}
		else
		{
			amount = 1 + ( amount - 1000 ) * 0.002;
		}

		return amount;
	}

	@Override
	public String getAccountType()
	{
		return SAVINGS;
	}
}
