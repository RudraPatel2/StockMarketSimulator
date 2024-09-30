package Miscellaneous;
import java.io.*;
import Accounts.*;
import java.util.*; 
import java.time.*;
import java.time.format.*;
public class TextFileEditor {
	private static BufferedWriter writer;
	private static BufferedReader reader;
	private static Scanner scn = new Scanner(System.in);
	private String dt() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime x = LocalTime.now();
		return ""+dtf.format(x);
	}
	private String date() {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		 LocalDateTime now = LocalDateTime.now();
		 return ""+dtf.format(now);
	}
	public ArrayList<String> readAllStocks(StockPortfolioAccount x) {
		try {
			ArrayList<String> s = new ArrayList<String>();
			reader = new BufferedReader(new FileReader("C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\"+x.getURL()+"_Results.txt"));
			String line = "";
			while((line = reader.readLine())!=null) {
					s.add(line.substring(0, line.indexOf(" "))+ " "+line.substring(line.lastIndexOf(" ")+1));
			}
			reader.close();
			return s;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public void updateStocks(StockPortfolioAccount x, ArrayList<String> b) {
		try {
			int count = 0;
			writer= new BufferedWriter(new FileWriter("C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\"+x.getURL()+"_Results.txt"));
			for(int i = 0; i<b.size();i++) {
				writer.write(b.get(i)+"\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void updateTotalValue(StockPortfolioAccount x){
		try {
			writer = new BufferedWriter(new FileWriter("C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\"+x.getURL()+"_currentValue.txt",true));
			writer.write(x.getTotalValue()+"\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public double readTotalValue(StockPortfolioAccount x) {
		try {
			reader = new BufferedReader(new FileReader("C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\"+x.getURL()+"_currentValue.txt"));
			double l=10000;
			String line ="";
			while((line = reader.readLine())!=null) {
				l = Double.valueOf(line);
			}
			reader.close();
			return l;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Double) null;
	}
	public void transactionLogStocks(StockPortfolioAccount x, boolean val, double currentVal, double total, int shares, String ticker) {
		try {
			//C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\
			writer = new BufferedWriter(new FileWriter("C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\"+x.getURL()+"_stock_transaction_history.txt",true));
			BufferedWriter writer2 = new BufferedWriter(new FileWriter("C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\"+x.getURL()+"_stockPreviousVals.txt",true));
			String type = val?"Buy":"Sell";
			String format = String.format("%-22s%-22s%-22s%-22s%-22s%-22s\n",type,ticker,""+shares,"$"+String.format("%.2f",currentVal), "$"+String.format("%.2f", currentVal*shares),dt());
			writer.write(format);
			writer.close();
			writer2.write(""+ticker+" "+shares+" "+currentVal+'\n');
			writer2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateTotalValue(BankAccount x) {
		try {
			writer = new BufferedWriter(new FileWriter("C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\"+x.getURL()+"_currentBalance.txt",true));
			writer.write(x.getCurrentBalance()+"\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	public double readTotalValue(BankAccount x) {
		try {
			reader = new BufferedReader(new FileReader("C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\"+x.getURL()+"_currentBalance.txt"));
			String line="";
			double currentVal = -1;
			while((line=reader.readLine())!=null) {
				currentVal = Double.valueOf(line);
			}
			reader.close();
			if(currentVal == -1) {
				return 10000;
			}else {
				return currentVal;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
		
	}
	//C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\"+URL+"_bank_transaction_history.txt
	public void bankTransactionLogs(BankAccount x ,boolean deposit, double amount, double currentBalance ) {
		try {
			writer = new BufferedWriter(new FileWriter("C:\\SarathyJavaProjects\\Stock Accounts Management System\\UserInformation\\"+x.getURL()+"_bank_transaction_history.txt",true));
			if(deposit) {
				String l = String.format("%-22s%-22s%-22s%-22s", "Deposit","$"+String.format("%.2f", amount),date(),"$"+String.format("%.2f", currentBalance));
				writer.write(l+"\n");
				writer.close();
			}else {
				String l = String.format("%-22s%-22s%-22s%-22s", "Withdrawal","$"+String.format("%.2f", amount),date(),"$"+String.format("%.2f", currentBalance));
				writer.write(l+"\n");
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
