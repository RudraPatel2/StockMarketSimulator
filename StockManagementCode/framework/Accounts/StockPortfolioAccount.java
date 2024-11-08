package Accounts;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import YahooStockDTO.StockDto;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import Miscellaneous.TextFileEditor;
import java.io.*;

public class StockPortfolioAccount extends Account{
	private String URL = "";
	private FileWriter  fileCreate;
	private TextFileEditor filere = new TextFileEditor();
	private double totalValue = 10000;
	public static Scanner scn = new Scanner(System.in);
	private ArrayList<String> currentStockHoldings = new ArrayList<String>();
	private ArrayList<Double> graphInfo = new ArrayList<Double>();
	public StockPortfolioAccount(String b) {
		URL = b;
		createFiles();
		totalValue = filere.readTotalValue(this);
		currentStockHoldings = filere.readAllStocks(this);
	}
	public void menu() {
		try {
			Stock s = YahooFinance.get("null");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean x = true;
		while(x) {
		System.out.println("\nStock Portfolio Account");
		System.out.println("Please select an account to access:");
		System.out.println("\t1. Display the price for a stock symbol\n\t2. Display the current portfolio\n\t3. Buy shares\n\t4. Sell shares\n\t5. View transaction history\n\t6. Return to previous menu");
		System.out.println("Option: ");
		int choice = scn.nextInt();
		if(choice==1) {
			this.displayStock();
		}else if(choice==2){
			this.displayCurrent();
		}else if(choice==3) {
			this.buyStocks();
		}else if(choice==4) {
			this.sellStocks();
		}else if(choice==5) {
			this.displayHistory();
		}else {
			x = false;
			updateData();
		}
	}
	}
	private  void displayStock() {
		System.out.println("Please enter the stock symbol: ");
		String b = scn.next();
		try {
			Stock stock = YahooFinance.get(b);
			if(stock == null||stock.getQuote().getPrice()==null) {
				System.out.println("ERROR: Ticker Symbol not found or company does not exist");
			}else{
				System.out.println(String.format("%-22s%-22s", "Company Symbol","Price Per Share"));
				System.out.println(String.format("%-22s%-22s\n", stock.getSymbol(),"$"+stock.getQuote().getPrice()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void updateStockHoldingArrayList(String ticker, int shares, boolean buy) {
		if(buy) {
			int count = 0;
			for(int i = 0; i<currentStockHoldings.size();i++) {
				if(currentStockHoldings.get(i).contains(ticker)) {
				currentStockHoldings.set(i, ticker+ " "+ ((Integer.valueOf(currentStockHoldings.get(i).substring(currentStockHoldings.get(i).lastIndexOf(" ")+1)))+shares));
					count = 1;
				}
			}
			if(count==0) {
				currentStockHoldings.add(ticker+" "+shares);
			}
		}else {
			for(int i = 0;i<currentStockHoldings.size();i++) {
				if(currentStockHoldings.get(i).contains(ticker)) {
					int b = ((Integer.valueOf(currentStockHoldings.get(i).substring(currentStockHoldings.get(i).lastIndexOf(" ")+1)))-shares);
					if(b==0) {
						currentStockHoldings.remove(i);
					}else {
						currentStockHoldings.set(i,ticker+" "+ b);
					}
				}
			}
		}
	}
	private void buyStocks(){
		String tickerSymbol; int shares; double maxValWish;
		System.out.println("Please enter the stock symbol you wish to purchase: ");
		tickerSymbol = scn.next();
		System.out.println("Please enter the number of shares: ");
		shares = scn.nextInt();
		System.out.println("Please enter the maximum amount you are willing to pay per share:  ");
		maxValWish = scn.nextDouble();
		try {
			Stock l = YahooFinance.get(tickerSymbol);
			if(l!=null) {
				if(l.getQuote().getPrice()!=null&&(new BigDecimal(maxValWish)).compareTo(l.getQuote().getPrice())>=0) {
					if(shares!=0&&((l.getQuote().getPrice()).multiply(new BigDecimal(shares)).compareTo(new BigDecimal(totalValue))<=0)){
						double priceBought = l.getQuote().getPrice().doubleValue();
						totalValue-=((l.getQuote().getPrice()).multiply(new BigDecimal(shares))).doubleValue();
						filere.transactionLogStocks(this, true, priceBought, priceBought*shares, shares, tickerSymbol);
						updateStockHoldingArrayList(tickerSymbol, shares,true);
						System.out.println("You have purchased "+ shares+ " shares of "+ tickerSymbol + " at $"+priceBought+" each for a total of $" + String.format("%.2f",(shares*priceBought)));
						updateData();
					}else {
						System.out.println("Error: Your stock portfolio balance is not high enough to purchase " + shares +" share(s) of "+tickerSymbol+" for $"+(String.format("%.2f", l.getQuote().getPrice().doubleValue())));
					}
				}else {
					System.out.println("Error: They minimum price per share is $"+l.getQuote().getPrice());
				}
			}else {
				System.out.println("Error: Either the Ticker Symbol was typed incorrectly or does not exist.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void sellStocks() {
		String tickerSymbol; int shares; double sellValueWish;
		System.out.println("Please enter the stock symbol you wish to sell: ");
		tickerSymbol = scn.next();
		System.out.println("Please enter the number of shares you wish to sell: ");
		shares = scn.nextInt();
		System.out.println("Please enter the minimum value you wish to sell for: ");
		sellValueWish = scn.nextDouble();
		try {
			Stock potential = YahooFinance.get(tickerSymbol);
			if((potential!=null&&containsStock(tickerSymbol)!=-1)&&potential.getQuote().getPrice()!=null) {
				double currentVal = potential.getQuote().getPrice().doubleValue();
				if(currentVal>=sellValueWish) {
					if(shares<=(Integer.valueOf((currentStockHoldings.get(containsStock(tickerSymbol)).substring(currentStockHoldings.get(containsStock(tickerSymbol)).lastIndexOf(" ")+1))))) {
						updateStockHoldingArrayList(tickerSymbol, shares, false);
						totalValue+= shares*currentVal;
						filere.transactionLogStocks(this, false, currentVal, currentVal*shares, shares, tickerSymbol);
						System.out.println("You have sold "+shares+" of "+tickerSymbol+" for $"+currentVal+". Your current balance is $"+String.format("%.2f",totalValue));
						updateData();
					}else {
						System.out.println("Unable to make transaction: You do not possess enough shares of " + tickerSymbol+" to sell "+shares+ " shares!");
					}
				}else {
					System.out.println("Unable to make transaction: Your minimum sell price is greater than the current price per share for"+tickerSymbol+"!");
				}
			}else {
				System.out.println("ERROR: The stock symbol you have entered either does not exist or is not within your portfolio!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private int containsStock(String pot) {
		for(int i = 0; i<currentStockHoldings.size();i++) {
			if(currentStockHoldings.get(i).contains(pot)) {
				return i;
			}
		}
		return -1;
	}

	protected void displayHistory() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\RudraJavaProjects\\Stock Accounts Management System\\UserInformation\\"+URL+"_stock_transaction_history.txt"));
			BufferedReader reader2 = new BufferedReader(new FileReader("C:\\RudraJavaProjects\\Stock Accounts Management System\\UserInformation\\"+URL+"_stockPreviousVals.txt"));
			System.out.println("TRANSACTION HISTORY");
			System.out.printf("%-22s%-22s%-22s%-22s%-22s%-22s%-22s\n","Event","Company Symbol","Number","Price Per Share", "Total Value","Time", "Current Price Per Share");
			double totalPort = 0.0;
			String line ="";
			while((line = reader.readLine())!=null) {
				totalPort += Double.valueOf(line.substring(line.lastIndexOf("$")+1,line.indexOf(" ", line.lastIndexOf("$")+1)));
				String line2 = reader2.readLine();
				double x = (YahooFinance.get(line2.substring(0,line2.indexOf(" "))).getQuote().getPrice()).doubleValue()/Double.valueOf(line2.substring(line2.lastIndexOf(" ")));
				x = (1-x)*100;
				System.out.println(line+"$"+String.format("%.2f", YahooFinance.get(line2.substring(0,line2.indexOf(" "))).getQuote().getPrice())+"("+String.format("%.2f", -1.0*x)+"%)");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	protected void displayCurrent() {
		ArrayList<String> x = filere.readAllStocks(this);
		System.out.println("Cash Balance = $"+String.format("%.2f",totalValue));
		System.out.println(String.format("%-22s%-22s%-22s%-22s", "Company Symbol","Number","Price Per Share","Total Value"));
		double totalPort = 0.0;
		for(String n: x) {
			try {
				Stock b = YahooFinance.get(n.substring(0,n.indexOf(" ")));
				int s = Integer.valueOf(n.substring(n.lastIndexOf(" ")+1));
				totalPort+= (b.getQuote().getPrice()).multiply((new BigDecimal(s))).doubleValue();
				System.out.println(String.format("%-22s%-22s%-22s%-22s", b.getSymbol(), ""+s,"$"+String.format("%.2f",b.getQuote().getPrice()),"$"+String.format("%.2f",(b.getQuote().getPrice()).doubleValue()*s)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Total Portfolio Value: $"+String.format("%.2f",(totalPort+totalValue)));
		
	}
	private void updateData() {
		filere.updateTotalValue(this);
		filere.updateStocks(this, currentStockHoldings);
	}
	public double getTotalValue() {
		return totalValue;
	}
	public String getURL() {
		return URL;
	}
	private void createFiles() {
		try {
			fileCreate = new FileWriter("C:\\RudraJavaProjects\\Stock Accounts Management System\\UserInformation\\"+URL+"_Results.txt",true);
			fileCreate = new FileWriter("C:\\RudraJavaProjects\\Stock Accounts Management System\\UserInformation\\"+URL+"_stock_transaction_history.txt",true);
			fileCreate = new FileWriter("C:\\RudraJavaProjects\\Stock Accounts Management System\\UserInformation\\"+URL+"_currentValue.txt",true);
			fileCreate = new FileWriter("C:\\RudraJavaProjects\\Stock Accounts Management System\\UserInformation\\"+URL+"_stockPreviousVals.txt",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
