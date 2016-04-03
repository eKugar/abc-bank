package com.abc;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest
{
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void checkTransactions()
	{
		Account acct = new AccountChecking();
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
	public void zeroDepositTest()
	{
		Account acct = new AccountChecking();
		try
		{
			acct.deposit( 0 );
			fail( "0 deposit -- Should have thrown exception - IllegalArgumentException" );
		}
		catch( IllegalArgumentException expectedException )
		{
		}
	}

	@Test
	public void zeroWithdrawTest()
	{
		Account acct = new AccountChecking();
		try
		{
			acct.withdraw( 0 );
			fail( "0 withdrawal -- Should have thrown exception - IllegalArgumentException" );
		}
		catch( IllegalArgumentException expectedException )
		{
		}
	}

	@Test
	public void overDrawTest()
	{
		Account acct = new AccountChecking();
		acct.deposit( 50 );
		try
		{
			acct.withdraw( 60 );
			fail( "Account overdrawn -- Should have thrown exception - IllegalArgumentException" );
		}
		catch( IllegalArgumentException expectedException )
		{
		}
	}

	@Test
	public void lastWithdrawalNullTest()
	{
		Account acct = new AccountChecking();
		acct.deposit( 50 );
		acct.deposit( 50 );
		acct.deposit( 50 );
		acct.deposit( 50 );
		acct.deposit( 50 );
		assertTrue( null == acct.getLastWithdrawal() );
	}

	@Test
	public void lastWithdrawalTest()
	{
		Account acct = new AccountChecking();
		acct.deposit( 50 );
		acct.deposit( 50 );
		acct.withdraw( 50 );
		acct.deposit( 50 );
		acct.deposit( 50 );
		assertEquals( -50.0, acct.getLastWithdrawal().getAmount(), DOUBLE_DELTA );

		acct.withdraw( 5 );
		acct.withdraw( 65 );
		assertEquals( -65.0, acct.getLastWithdrawal().getAmount(), DOUBLE_DELTA );

		acct.deposit( 50 );
		assertEquals( -65.0, acct.getLastWithdrawal().getAmount(), DOUBLE_DELTA );

		acct.deposit( 50 );
		assertEquals( -65.0, acct.getLastWithdrawal().getAmount(), DOUBLE_DELTA );
	}

	@Test
	public void withdrawalsLast5DaysNullTest()
	{
		Account acct = new AccountChecking();
		acct.deposit( 50 );
		acct.deposit( 50 );
		acct.deposit( 50 );
		acct.deposit( 50 );
		acct.deposit( 50 );
		assertFalse( acct.withdrawalsLastTenDays() );
	}

	@Test
	public void withdrawalsLast5DaysTest()
	{
		Account acct = new AccountChecking();
		acct.deposit( 50 );
		acct.deposit( 50 );
		acct.withdraw( 50 );
		acct.deposit( 50 );
		acct.deposit( 50 );
		assertTrue( acct.withdrawalsLastTenDays() );

		acct.withdraw( 5 );
		acct.withdraw( 65 );
		assertTrue( acct.withdrawalsLastTenDays() );

		acct.deposit( 50 );
		assertTrue( acct.withdrawalsLastTenDays() );

		acct.deposit( 50 );
		assertTrue( acct.withdrawalsLastTenDays() );
	}

	@Test
	public void withdrawalsLast5DaysForTransactionsBefore10daysTest()
	{
		// In this test we are mocking the transaction to be 11 days before
		Account acct = new AccountChecking()
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

		acct.deposit( 50 );
		acct.deposit( 50 );
		acct.withdraw( 50 );
		acct.deposit( 50 );
		acct.deposit( 50 );
		assertFalse( acct.withdrawalsLastTenDays() );

		acct.withdraw( 5 );
		acct.withdraw( 65 );
		assertFalse( acct.withdrawalsLastTenDays() );

		acct.deposit( 50 );
		assertFalse( acct.withdrawalsLastTenDays() );

		acct.deposit( 50 );
		assertFalse( acct.withdrawalsLastTenDays() );
	}

}
