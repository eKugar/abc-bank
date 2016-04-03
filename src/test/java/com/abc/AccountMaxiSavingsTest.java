package com.abc;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountMaxiSavingsTest
{
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void checkAccountTypeTest()
	{
		Account acct = new AccountMaxiSavings();
		assertEquals( acct.getAccountType(), "Maxi Savings" );
	}

	@Test
	public void checkTransactions()
	{
		Account acct = new AccountMaxiSavings();
		acct.deposit( 100 );
		assertEquals( 100, acct.sumTransactions(), DOUBLE_DELTA );

		acct.deposit( 100 );
		assertEquals( 200, acct.sumTransactions(), DOUBLE_DELTA );

		acct.withdraw( 50 );
		assertEquals( 150, acct.sumTransactions(), DOUBLE_DELTA );

		acct.withdraw( 50 );
		assertEquals( 100, acct.sumTransactions(), DOUBLE_DELTA );

		acct.withdraw( 1 );
		assertEquals( 99, acct.sumTransactions(), DOUBLE_DELTA );
	}

	@Test
	public void interestEarnedTest()
	{
		Account acct = new AccountMaxiSavings();
		acct.deposit( 100000 );
		assertEquals( 5000.0, acct.interestEarned(), DOUBLE_DELTA );
		acct.withdraw( 1000 );
		assertEquals( 99.0, acct.interestEarned(), DOUBLE_DELTA );
	}

	@Test
	public void interestEarnedWithdrawalOver10DaysTest()
	{
		// In this test we are mocking the transaction to be 11 days before
		Account acct = new AccountMaxiSavings()
		{
			public Transaction getLastWithdrawal()
			{
				Transaction trans = super.getLastWithdrawal();
				if( trans == null )
				{
					return trans;
				}
				Calendar cal = Calendar.getInstance();
				cal.setTime( trans.getTransactionDate() );
				cal.add( Calendar.DATE, -11 );
				return new Transaction( trans.getAmount(), new Date( cal.getTimeInMillis() ) );
			}


		};
		acct.deposit( 100000 );
		assertEquals( 5000.0, acct.interestEarned(), DOUBLE_DELTA );
		acct.withdraw( 1000 );
		assertEquals( 4950.0, acct.interestEarned(), DOUBLE_DELTA );
	}

	@Test
	public void interestEarnedZeroTest()
	{
		Account acct = new AccountMaxiSavings();
		assertEquals( 0.0, acct.interestEarned(), DOUBLE_DELTA );
	}

	@Test
	public void interestEarnedMultiTest()
	{
		Account acct = new AccountMaxiSavings();
		acct.deposit( 1000 );
		acct.deposit( 1000 );
		assertEquals( 100.0, acct.interestEarned(), DOUBLE_DELTA );
		acct.withdraw( 500 );
		assertEquals( 1.5, acct.interestEarned(), DOUBLE_DELTA );
	}

}
