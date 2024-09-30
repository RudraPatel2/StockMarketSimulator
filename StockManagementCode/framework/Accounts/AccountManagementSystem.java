package Accounts;

import java.io.*;
import java.util.*;

public class AccountManagementSystem {
	private BufferedReader reader;
	private BufferedWriter writer;
	public AccountManagementSystem() {
		fileCreate();
		try {
			writer = new BufferedWriter(new FileWriter("C:\\AccountManagementSystem\\CurrentLoginInformation.txt",true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.menu();
	}

	private void menu() {
		String x = loginScreen();
		Scanner scn = new Scanner(System.in);
		StockPortfolioAccount s = new StockPortfolioAccount(x);
		BankAccount b = new BankAccount(x);
		boolean c = true;
		System.out.println("Welcome to the Account Management System.\n");
		while (c) {
			System.out.println("Please select an account to access:\n\t1. Stock Portfolio Account\n\t2. Bank Account\n\t3. Exit\nOption:");
			int choice = scn.nextInt();
			if(choice ==1) {
				s.menu();
			}else if(choice ==2) {
				b.menu();
			}else {
				/*try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				outroScreen();
				System.exit(0);
			}
		}
	}
	private void outroScreen() {
		System.out.println("STOCK SIMULATION HAS ENDED!\nTHANK YOU!\n\t\tSarathy Selvam's Stock Simulator!");
	}
	private String loginScreen() {
		Scanner scn = new Scanner(System.in);
		System.out.println("Login Options\n\t1. Login\n\t2. Create New Account");
		int ch = scn.nextInt();
		String x;
		if(ch==1) {
			 x =doesLoginExist();
		}else {
			createAccount();
			 x = doesLoginExist();
		}
		while (x == null) {
			int choice;
			System.out.println("Login Options\n\t1. Login\n\t2. Create New Account");
			choice = scn.nextInt();
			if (choice == 2) {
				createAccount();
				x = doesLoginExist();
			} else {
				x = doesLoginExist();
			}
		}
		return x;
	}
	private String doesLoginExist() {
		try {
			System.out.println("LOGGING IN...");
			Scanner scn = new Scanner(System.in);
			System.out.println("Please enter your username: ");
			String user = scn.next();
			System.out.println("Please enter your password: ");
			String pass = scn.next();
			reader = new BufferedReader(new FileReader("C:\\AccountManagementSystem\\CurrentLoginInformation.txt"));
			String line = "";
			boolean x = true;
			while ((line = reader.readLine()) != null) {
				if (line.substring(line.indexOf(" ")+1,line.lastIndexOf(" ")).equals(user)) {
					x = false;
					if (line.substring(line.lastIndexOf(" ") + 1).equals(pass)) {
						System.out.println("SUCCESSFULLY LOGGING IN:  " + line.substring(0, line.indexOf(" ")));
						return line.substring(line.indexOf(" " )+1, line.lastIndexOf(" "));
					} else {
						System.out.println("ERROR: The password that was entered is invalid.");
						return null;
					}
				}
			}
			if (x) {
				System.out.println("ERROR: Username not found");
			}
			reader.close();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void createAccount() {
		Scanner scn = new Scanner(System.in);
		System.out.println("CREATING ACCOUNT...");
		System.out.println("Please Enter Your Full Name: ");
		String name = scn.nextLine();
		name = name.substring(0, name.indexOf(" ")) + "_" + name.substring(name.indexOf(" ") + 1);
		System.out.println("CREATE USERNAME: ");
		String user = scn.next();
		while (userExists(user)) {
			System.out.println("ERROR: Username already exists");
			System.out.println("CREATE USERNAME: ");
			user = scn.next();
		}
		System.out.println("CREATE PASSWORD: ");
		String pass = scn.next();
		try {
			BufferedWriter c = new BufferedWriter(
					new FileWriter("C:\\AccountManagementSystem\\CurrentLoginInformation.txt",true));
			c.write(name + " " + user + " " + pass + '\n');
			c.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean userExists(String user) {
		try {
			reader = new BufferedReader(new FileReader("C:\\AccountManagementSystem\\CurrentLoginInformation.txt"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line.contains(user)) {
					return true;
				}
			}
			reader.close();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	private void fileCreate() {
		new File("C:\\AccountManagementSystem").mkdir();
		try {
			new FileWriter("C:\\AccountManagementSystem\\CurrentLoginInformation.txt",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
