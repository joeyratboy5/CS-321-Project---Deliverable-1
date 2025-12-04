package baseFrontEnd;

import java.math.BigDecimal;

import baseFrontEnd.Screens.NotificationService;

import java.util.ArrayList;

import java.util.List;
 
public class User {
	public String name;
	public String email;
	public BigDecimal balance;
	public List<String> transactionHistory = new ArrayList<>();
	
	public User(String name, String email, BigDecimal startingBalance){
		this.name = name;
		this.email = email;
		this.balance = startingBalance;
	}
	
	public void sendMoney(User receiver, BigDecimal amount, NotificationService ns) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			ns.notifyUser("Amount must be greater than 0.");
			return;
		}
		
		if (balance.compareTo(amount) < 0) {
			ns.notifyUser(name + " does not have enough funds.");
			return;
		}
		balance = balance.subtract(amount);
		String record = "Transaction performed: " + name + " sent $" + amount + " to " + receiver.name;
		
		receiver.balance = receiver.balance.add(amount);
		transactionHistory.add(record);
		ns.notifyUser(record);
	}
	void displayTransactionHistory(){
		System.out.println("-----Transaction History for " + name + "-----");
		for(String str : transactionHistory){
			System.out.println(str);
		}
	}

}
