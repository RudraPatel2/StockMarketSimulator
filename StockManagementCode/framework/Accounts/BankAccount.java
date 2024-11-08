package Accounts;

import java.util.*;
import Miscellaneous.TextFileEditor;
import java.io.*;
public class BankAccount extends Account {
	private String URL;
	private double currentBalance;
	public static Scanner scn = new Scanner(System.in);
	private TextFileEditor filere = new TextFileEditor();
	public BankAccount(String user) {
		URL = user;
		createFiles();
		currentBalance = filere.readTotalValue(this);		
	}
	public void menu() {
		System.out.println("Bank Account");
		boolean x = true;
		while(x) {
			System.out.println("Please select an option:\n\t1. View account balance\n\t2. Deposit money\n\t3. Withdraw money\n\t4. Print out history\n\t5. Return to previous menu\nOption: ");
			int choice = scn.nextInt();
			if(choice ==1) {
				displayCurrent();
			}else if(choice ==2) {
				depositMoolah();
			}else if(choice ==3) {
				withdrawMoolah();
			}else if(choice==4) {
				displayHistory();
			}else {
				filere.updateTotalValue(this);
				x = false;
			}
		}

	}
	public void displayHistory() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\RudraJavaProjects\\Stock Accounts Management System\\UserInformation\\"+URL+"_bank_transaction_history.txt"));
			 System.out.println(String.format("%-22s%-22s%-22s%-22s", "Event","Amount","Date","Current Balance"));
			 String line ="";
			 while((line=reader.readLine())!=null) {
				 System.out.print(line+'\n');
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void displayCurrent() {
		System.out.println("Current Bank Account Balance: $"+String.format("%.2f", currentBalance));
	}
	public void createFiles() {
		try {
			FileWriter fileCreate = new FileWriter("C:\\RudraJavaProjects\\Stock Accounts Management System\\UserInformation\\"+URL+"_bank_transaction_history.txt",true);
			fileCreate = new FileWriter("C:\\RudraJavaProjects\\Stock Accounts Management System\\UserInformation\\"+URL+"_currentBalance.txt",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void depositMoolah() {
		System.out.println("Please Enter The Amount Of Money You Wish To Deposit: ");
		double val = scn.nextDouble();
		currentBalance+=val;
		filere.bankTransactionLogs(this, true, val, currentBalance);
		
	}
	public void withdrawMoolah() {
		System.out.println("Please enter the amount of money you wish to withdraw: ");
		double wd = scn.nextDouble();
		if(wd<=currentBalance) {
			currentBalance-=wd;
			System.out.println("You have withdrawed $" + String.format("%.2f", wd)+". Your current balance is $"+String.format("%.2f", currentBalance));
			filere.bankTransactionLogs(this, false, wd, currentBalance);
		}else {
			System.out.println("ERROR: CURRENT BALANCE($"+String.format("%.2f",currentBalance)+") IS NOT SUFFICIENT ENOUGH TO WITHDRAW $"+String.format("%.2f",wd));
		}
	}
	public String  getURL() {
		return URL;
	}
	public double getCurrentBalance() {
		return currentBalance;
	}
	
}
