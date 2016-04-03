package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer
{
	private String name;
	private List<Account> accounts;

	public Customer( String name )
	{
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	public String getName()
	{
		return name;
	}

	public Customer openAccount( Account account )
	{
		accounts.add( account );
		return this;
	}

	public int getNumberOfAccounts()
	{
		return accounts.size();
	}

	public double totalInterestEarned()
	{
		double total = 0;
		for( Account a : accounts )
		{
			total += a.interestEarned();
		}
		return total;
	}

	public void transfer( Account sourceAccount, Account destAccount, double amount )
	{
		if( !this.accounts.contains( sourceAccount ) )
		{
			throw new IllegalArgumentException( "source account does not belong to customer" );
		}
		if( !this.accounts.contains( destAccount ) )
		{
			throw new IllegalArgumentException( "destination account does not belong to customer" );
		}
		if( sourceAccount.sumTransactions() < amount )
		{
			throw new IllegalArgumentException( "cannot transfer more than the source account contains" );
		}
		if( amount <= 0 )
		{
			throw new IllegalArgumentException( "amount must be greater than zero" );
		}
		sourceAccount.withdraw( amount );
		destAccount.deposit( amount );
	}

	public String getStatement()
	{
		StringBuilder statement = new StringBuilder();
		statement.append( "Statement for " );
		statement.append( name );
		statement.append( "\n" );
		double total = 0.0;
		for( Account a : accounts )
		{
			statement.append( "\n" );
			statement.append( statementForAccount( a ) );
			statement.append( "\n" );
			total += a.sumTransactions();
		}
		statement.append( "\nTotal In All Accounts " );
		statement.append( toDollars( total ) );
		return statement.toString();
	}

	private String statementForAccount( Account a )
	{
		StringBuilder s = new StringBuilder();

		s.append( a.getAccountType() );
		s.append( " Account\n" );


		//Now total up all the transactions
		double total = 0.0;
		for( Transaction t : a.transactions )
		{
			s.append( "  " );
			s.append( t.getAmount() < 0 ? "withdrawal" : "deposit" );
			s.append( " " );
			s.append( toDollars( t.getAmount() ) );
			s.append( "\n" );

			total += t.getAmount();
		}
		s.append( "Total " );
		s.append( toDollars( total ) );
		return s.toString();
	}

	private static String toDollars( double d )
	{
		return String.format( "$%,.2f", abs( d ) );
	}
}
