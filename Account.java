package Accounts;

abstract class Account {
	public double totalValue;
	abstract protected void displayHistory();
	abstract protected void displayCurrent();
}
