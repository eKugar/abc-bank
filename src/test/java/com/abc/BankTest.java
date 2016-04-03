package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest
{
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummary()
	{
		Bank bank = new Bank();
		Customer john = new Customer( "John" );
		john.openAccount( new AccountChecking() );
		bank.addCustomer( john );

		assertEquals( "Customer Summary\n - John (1 account)", bank.customerSummary() );
	}

	@Test
	public void customerSummary2Accounts()
	{
		Bank bank = new Bank();
		Customer john = new Customer( "John" );
		john.openAccount( new AccountChecking() );
		john.openAccount( new AccountSavings() );
		bank.addCustomer( john );

		assertEquals( "Customer Summary\n - John (2 accounts)", bank.customerSummary() );
	}

	@Test
	public void customerSummary4Accounts()
	{
		Bank bank = new Bank();
		Customer john = new Customer( "John" );
		john.openAccount( new AccountChecking() );
		john.openAccount( new AccountSavings() );
		john.openAccount( new AccountSavings() );
		john.openAccount( new AccountMaxiSavings() );
		bank.addCustomer( john );

		assertEquals( "Customer Summary\n - John (4 accounts)", bank.customerSummary() );
	}

	@Test
	public void customerSummaryMultipleCustomersAndAccounts()
	{
		Bank bank = new Bank();
		Customer john = new Customer( "John" );
		john.openAccount( new AccountChecking() );
		john.openAccount( new AccountSavings() );
		john.openAccount( new AccountSavings() );
		john.openAccount( new AccountMaxiSavings() );
		bank.addCustomer( john );

		Customer sue = new Customer( "Sue" );
		sue.openAccount( new AccountChecking() );
		sue.openAccount( new AccountSavings() );
		sue.openAccount( new AccountMaxiSavings() );
		bank.addCustomer( sue );

		Customer joe = new Customer( "Joe" );
		joe.openAccount( new AccountChecking() );
		joe.openAccount( new AccountMaxiSavings() );
		bank.addCustomer( joe );

		Customer ann = new Customer( "Ann" );
		ann.openAccount( new AccountChecking() );
		bank.addCustomer( ann );

		assertEquals( "Customer Summary\n" +
							  " - John (4 accounts)\n" +
							  " - Sue (3 accounts)\n" +
							  " - Joe (2 accounts)\n" +
							  " - Ann (1 account)", bank.customerSummary() );
	}

	@Test
	public void checkingAccount()
	{
		Bank bank = new Bank();
		Account checkingAccount = new AccountChecking();
		Customer bill = new Customer( "Bill" ).openAccount( checkingAccount );
		bank.addCustomer( bill );

		checkingAccount.deposit( 1000.0 );
		assertEquals( 1.0, bank.totalInterestPaid(), DOUBLE_DELTA );

		checkingAccount.withdraw( 500.0 );
		assertEquals( 0.5, bank.totalInterestPaid(), DOUBLE_DELTA );
	}

	@Test
	public void savings_account()
	{
		Bank bank = new Bank();
		Account checkingAccount = new AccountSavings();
		bank.addCustomer( new Customer( "Bill" ).openAccount( checkingAccount ) );

		checkingAccount.deposit( 1500.0 );
		assertEquals( 2.0, bank.totalInterestPaid(), DOUBLE_DELTA );

		checkingAccount.withdraw( 750.0 );
		assertEquals( 0.75, bank.totalInterestPaid(), DOUBLE_DELTA );
	}

	@Test
	public void maxi_savings_account()
	{
		Bank bank = new Bank();
		Account checkingAccount = new AccountMaxiSavings();
		bank.addCustomer( new Customer( "Bill" ).openAccount( checkingAccount ) );

		checkingAccount.deposit( 3000.0 );
		assertEquals( 150.0, bank.totalInterestPaid(), DOUBLE_DELTA );

		checkingAccount.deposit( 3000.0 );
		assertEquals( 300.0, bank.totalInterestPaid(), DOUBLE_DELTA );

		checkingAccount.withdraw( 1000.0 );
		assertEquals( 5.0, bank.totalInterestPaid(), DOUBLE_DELTA );
	}

	@Test
	public void multipleCustomersInterestCheck()
	{
		Bank bank = new Bank();
		Customer bill = new Customer( "Bill" );
		Account account = new AccountChecking();
		bill.openAccount( account );
		bank.addCustomer( bill );
		account.deposit( 3000.0 );
		assertEquals( 3.0, bank.totalInterestPaid(), DOUBLE_DELTA );

		account = new AccountSavings();
		bill.openAccount( account );
		account.deposit( 1500.0 );
		assertEquals( 5.0, bank.totalInterestPaid(), DOUBLE_DELTA );

		account = new AccountMaxiSavings();
		bill.openAccount( account );
		account.deposit( 10000.0 );
		assertEquals( 505.0, bank.totalInterestPaid(), DOUBLE_DELTA );


		Customer john = new Customer( "John" );
		account = new AccountChecking();
		john.openAccount( account );
		bank.addCustomer( john );
		account.deposit( 3000.0 );
		assertEquals( 508.0, bank.totalInterestPaid(), DOUBLE_DELTA );

		account = new AccountSavings();
		john.openAccount( account );
		account.deposit( 1500.0 );
		assertEquals( 510.0, bank.totalInterestPaid(), DOUBLE_DELTA );

		account = new AccountMaxiSavings();
		john.openAccount( account );
		account.deposit( 10000.0 );
		assertEquals( 1010.0, bank.totalInterestPaid(), DOUBLE_DELTA );


		Customer sue = new Customer( "Sue" );
		account = new AccountChecking();
		sue.openAccount( account );
		bank.addCustomer( sue );
		account.deposit( 3000.0 );
		assertEquals( 1013.0, bank.totalInterestPaid(), DOUBLE_DELTA );

		account = new AccountSavings();
		john.openAccount( account );
		account.deposit( 1500.0 );
		assertEquals( 1015.0, bank.totalInterestPaid(), DOUBLE_DELTA );

		account = new AccountMaxiSavings();
		john.openAccount( account );
		account.deposit( 10000.0 );
		assertEquals( 1515.0, bank.totalInterestPaid(), DOUBLE_DELTA );
	}

}
