package com.abc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class Account
{
	public List<Transaction> transactions = new ArrayList<Transaction>();

	public void deposit( double amount )
	{
		if( amount <= 0 )
		{
			throw new IllegalArgumentException( "amount must be greater than zero" );
		}
		else
		{
			transactions.add( new Transaction( amount ) );
		}
	}

	public void withdraw( double amount )
	{
		if( amount <= 0 )
		{
			throw new IllegalArgumentException( "amount must be greater than zero" );
		}
		else if( sumTransactions() < amount )
		{
			throw new IllegalArgumentException( "cannot withdraw more than in the account" );
		}
		else
		{
			transactions.add( new Transaction( -amount ) );
		}
	}

	public double sumTransactions()
	{
		double amount = 0.0;
		for( Transaction t : transactions )
		{
			amount += t.getAmount();
		}
		return amount;
	}

	public Transaction getLastWithdrawal()
	{
		for( int i = transactions.size() - 1; i >= 0; i-- )
		{
			Transaction trans = transactions.get( i );
			if( trans.getAmount() < 0 )
			{
				return trans;
			}
		}
		return null;
	}

	public boolean withdrawalsLastTenDays()
	{
		// get the last transaction
		Transaction lastWithdrawal = getLastWithdrawal();
		if( lastWithdrawal == null )
		{
			return false;
		}

		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.DATE, -11 );

		return lastWithdrawal.getTransactionDate().after( new Date( cal.getTimeInMillis() ) );
	}

	public abstract String getAccountType();

	public abstract double interestEarned();
}
