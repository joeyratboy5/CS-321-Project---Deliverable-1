package baseFrontEnd.Screens;

import java.math.BigDecimal;

import baseFrontEnd.User;

public class Transaction {
	public static void perform(User sender, User receiver, BigDecimal amount, NotificationService ns ) {
		sender.sendMoney(receiver, amount, ns);
	}
}