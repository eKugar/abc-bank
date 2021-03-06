package com.abc;

import java.util.Date;

public class Transaction
{
	private final double amount;
	private final Date transactionDate;

	public Transaction( double amount )
	{
		this.amount = amount;
		this.transactionDate = DateProvider.getInstance().now();
	}

	// This constructor only used for testing
	public Transaction( double amount, Date transactionDate )
	{
		this.amount = amount;
		this.transactionDate = transactionDate;
	}

	public double getAmount()
	{
		return this.amount;
	}

	public Date getTransactionDate()
	{
		return this.transactionDate;
	}
}
