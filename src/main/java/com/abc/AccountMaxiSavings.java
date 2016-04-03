package com.abc;

public class AccountMaxiSavings extends Account
{
	private static final String MAXI_SAVINGS = "Maxi Savings";

	@Override
	public double interestEarned()
	{
		double amount = sumTransactions();
		if( withdrawalsLastTenDays() )
		{
			amount *= .001;
		}
		else
		{
			amount *= .05;
		}
		return amount;
	}

	@Override
	public String getAccountType()
	{
		return MAXI_SAVINGS;
	}
}
