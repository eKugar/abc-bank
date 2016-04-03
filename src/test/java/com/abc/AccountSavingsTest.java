package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountSavingsTest
{
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void checkAccountTypeTest()
	{
		Account acct = new AccountSavings();
		assertEquals( acct.getAccountType(), "Savings" );
	}

	@Test
	public void checkTransactions()
	{
		Account acct = new AccountSavings();
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
		Account acct = new AccountSavings();
		acct.deposit( 100000 );
		assertEquals( 199.0, acct.interestEarned(), DOUBLE_DELTA );
	}

	@Test
	public void interestEarnedZeroTest()
	{
		Account acct = new AccountSavings();
		assertEquals( 0.0, acct.interestEarned(), DOUBLE_DELTA );
	}

	@Test
	public void interestEarnedMultiTest()
	{
		Account acct = new AccountSavings();
		acct.deposit( 1000 );
		acct.deposit( 1000 );
		acct.withdraw( 500 );
		assertEquals( 2.0, acct.interestEarned(), DOUBLE_DELTA );
	}

}
