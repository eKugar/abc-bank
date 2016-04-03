package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AccountCheckingTest
{
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void checkAccountTypeTest()
	{
		Account acct = new AccountChecking();
		assertEquals( acct.getAccountType(), "Checking" );
	}

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
	public void interestEarnedTest()
	{
		Account acct = new AccountChecking();
		acct.deposit( 100000 );
		assertEquals( 100.0, acct.interestEarned(), DOUBLE_DELTA );
	}

	@Test
	public void interestEarnedZeroTest()
	{
		Account acct = new AccountChecking();
		assertEquals( 0.0, acct.interestEarned(), DOUBLE_DELTA );
	}

	@Test
	public void interestEarnedMultiTest()
	{
		Account acct = new AccountChecking();
		acct.deposit( 100 );
		acct.deposit( 100 );
		acct.withdraw( 50 );
		assertEquals( 0.15, acct.interestEarned(), DOUBLE_DELTA );
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

}
