package baseFrontEnd;

import java.math.BigDecimal;

import baseFrontEnd.Screens.NotificationService;

public class User {
	public String name;
	public String email;
	public BigDecimal balance;
	
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
		receiver.balance = receiver.balance.add(amount);
		ns.notifyUser("Transaction performed: " + name + " sent $" + amount + " to " + receiver.name);
	}
}