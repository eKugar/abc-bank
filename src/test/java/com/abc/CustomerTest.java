package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerTest
{
	private static final double DOUBLE_DELTA = 1e-15;

	@Test //Test customer statement generation
	public void testApp()
	{
		Account checkingAccount = new AccountChecking();
		Account savingsAccount = new AccountSavings();

		Customer henry = new Customer( "Henry" ).openAccount( checkingAccount ).openAccount( savingsAccount );

		checkingAccount.deposit( 100.0 );
		savingsAccount.deposit( 4000.0 );
		savingsAccount.withdraw( 200.0 );

		assertEquals( "Statement for Henry\n" +
							  "\n" +
							  "Checking Account\n" +
							  "  deposit $100.00\n" +
							  "Total $100.00\n" +
							  "\n" +
							  "Savings Account\n" +
							  "  deposit $4,000.00\n" +
							  "  withdrawal $200.00\n" +
							  "Total $3,800.00\n" +
							  "\n" +
							  "Total In All Accounts $3,900.00", henry.getStatement() );
	}

	@Test
	public void testOneAccount()
	{
		Customer oscar = new Customer( "Oscar" );
		Account account = new AccountSavings();
		oscar.openAccount( account );
		account.deposit( 2000 );
		assertEquals( 1, oscar.getNumberOfAccounts() );
		assertEquals( 3.0, oscar.totalInterestEarned(), DOUBLE_DELTA );
		assertEquals( "Oscar", oscar.getName() );
	}

	@Test
	public void testTwoAccounts()
	{
		Customer oscar = new Customer( "Oscar" );
		Account account = new AccountSavings();
		oscar.openAccount( account );
		account.deposit( 2000 );
		assertEquals( 1, oscar.getNumberOfAccounts() );
		assertEquals( 3.0, oscar.totalInterestEarned(), DOUBLE_DELTA );

		account = new AccountChecking();
		oscar.openAccount( account );
		account.deposit( 2000 );
		assertEquals( 2, oscar.getNumberOfAccounts() );
		assertEquals( 5.0, oscar.totalInterestEarned(), DOUBLE_DELTA );
	}

	@Test
	public void testThreeAccounts()
	{
		Customer oscar = new Customer( "Oscar" );
		Account account = new AccountSavings();
		oscar.openAccount( account );
		account.deposit( 2000 );
		assertEquals( 1, oscar.getNumberOfAccounts() );
		assertEquals( 3.0, oscar.totalInterestEarned(), DOUBLE_DELTA );

		account = new AccountChecking();
		oscar.openAccount( account );
		account.deposit( 2000 );
		assertEquals( 2, oscar.getNumberOfAccounts() );
		assertEquals( 5.0, oscar.totalInterestEarned(), DOUBLE_DELTA );

		account = new AccountMaxiSavings();
		oscar.openAccount( account );
		account.deposit( 2000 );
		assertEquals( 3, oscar.getNumberOfAccounts() );
		assertEquals( 105.0, oscar.totalInterestEarned(), DOUBLE_DELTA );
	}

	@Test
	public void testTransfer()
	{
		Customer oscar = new Customer( "Oscar" );
		Account account1 = new AccountSavings();
		oscar.openAccount( account1 );
		account1.deposit( 2000 );

		Account account2 = new AccountChecking();
		oscar.openAccount( account2 );
		account2.deposit( 2000 );

		assertEquals( "Statement for Oscar\n" +
							  "\n" +
							  "Savings Account\n" +
							  "  deposit $2,000.00\n" +
							  "Total $2,000.00\n" +
							  "\n" +
							  "Checking Account\n" +
							  "  deposit $2,000.00\n" +
							  "Total $2,000.00\n" +
							  "\n" +
							  "Total In All Accounts $4,000.00", oscar.getStatement() );

		oscar.transfer( account1, account2, 500 );

		assertEquals( "Statement for Oscar\n" +
							  "\n" +
							  "Savings Account\n" +
							  "  deposit $2,000.00\n" +
							  "  withdrawal $500.00\n" +
							  "Total $1,500.00\n" +
							  "\n" +
							  "Checking Account\n" +
							  "  deposit $2,000.00\n" +
							  "  deposit $500.00\n" +
							  "Total $2,500.00\n" +
							  "\n" +
							  "Total In All Accounts $4,000.00", oscar.getStatement() );

	}

	@Test
	public void testTransferIncorrectDestinationAccount()
	{
		Customer oscar = new Customer( "Oscar" );
		Account account1 = new AccountSavings();
		oscar.openAccount( account1 );
		account1.deposit( 2000 );

		Account account2 = new AccountChecking();
		oscar.openAccount( account2 );
		account2.deposit( 2000 );

		Account someoneElsesAcct = new AccountChecking();
		someoneElsesAcct.deposit( 2000 );

		try
		{
			oscar.transfer( account1, someoneElsesAcct, 500 );
			fail( "wrong destination account -- Should have thrown exception - IllegalArgumentException" );
		}
		catch( IllegalArgumentException expectedException )
		{
		}
	}

	@Test
	public void testTransferIncorrectSourceAccount()
	{
		Customer oscar = new Customer( "Oscar" );
		Account account1 = new AccountSavings();
		oscar.openAccount( account1 );
		account1.deposit( 2000 );

		Account account2 = new AccountChecking();
		oscar.openAccount( account2 );
		account2.deposit( 2000 );

		Account someoneElsesAcct = new AccountChecking();
		someoneElsesAcct.deposit( 2000 );

		try
		{
			oscar.transfer( someoneElsesAcct, account1, 500 );
			fail( "wrong source account -- Should have thrown exception - IllegalArgumentException" );
		}
		catch( IllegalArgumentException expectedException )
		{
		}
	}

	@Test
	public void testTransferOverdrawAccount()
	{
		Customer oscar = new Customer( "Oscar" );
		Account account1 = new AccountSavings();
		oscar.openAccount( account1 );
		account1.deposit( 2000 );

		Account account2 = new AccountChecking();
		oscar.openAccount( account2 );
		account2.deposit( 2000 );
		try
		{
			oscar.transfer( account2, account1, 2500 );
			fail( "overdraw -- Should have thrown exception - IllegalArgumentException" );
		}
		catch( IllegalArgumentException expectedException )
		{
		}
	}

	@Test
	public void testTransferLessThenOrEqualToZeroAccount()
	{
		Customer oscar = new Customer( "Oscar" );
		Account account1 = new AccountSavings();
		oscar.openAccount( account1 );
		account1.deposit( 2000 );

		Account account2 = new AccountChecking();
		oscar.openAccount( account2 );
		account2.deposit( 2000 );
		try
		{
			oscar.transfer( account2, account1, -300 );
			fail( "negative number -- Should have thrown exception - IllegalArgumentException" );
		}
		catch( IllegalArgumentException expectedException )
		{
		}

		try
		{
			oscar.transfer( account2, account1, 0 );
			fail( "zero -- Should have thrown exception - IllegalArgumentException" );
		}
		catch( IllegalArgumentException expectedException )
		{
		}
	}

}
