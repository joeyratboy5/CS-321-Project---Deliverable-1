mport static org.junit.jupiter.api.Assertions.*;
import java.beans.Transient;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import baseFrontEnd.UserTest.MockNotificationService;

public class UserTest {

    @Test
    void testSuccessfulSendMoney(){
        User sender = new User("Romeo", "r@mail.com", new BigDecimal("100"));
        User receiver = new User("Bob", "b@mail.com", new BigDecimal("20"));
        MockNotificationService ns = new MockNotificationService();

        sender.sendMoney(receiver, new BigDecimal("30"), ns);

        assertEquals(new BigDecimal("70"), sender.balance);

        assertEquals(new BigDecimal("50"), receiver.balance);

        assertEquals("Transaction performed: Romeo sent $30 to Bob", ns.lastMessage);
    }

    @Test
    void testNegativeAmount() {
        User sender = new User("Romeo", "r@mail.com", new BigDecimal("100"));
        User receiver = new User("Bob", "b@mail.com", new BigDecimal("20"));
        MockNotificationService ns = new MockNotificationService();

        sender.sendMoney(receiver, new BigDecimal("-10"), ns);

        assertEquals("Amount must be greater than 0.", ns.lastMessage);

    }

    @Test
    void testInsufficientFunds(){
        User sender = new User("Romeo", "r@mail.com", new BigDecimal("10"));
        User receiver = new User("Bob", "b@mail.com", new BigDecimal("20"));

        MockNotificationService ns = new MockNotificationService();

        sender.sendMoney(receiver, new BigDecimal("50"), ns);

        assertEquals("Romeo does not have enough funds.", ns.lastMessage);
    }

    @Test
    void testVeryLargeAmount(){
        User sender = new User("Romeo", "r@mail.com", new BigDecimal("100"));
        User receiver = new User("Bob", "b@mail.com", new BigDecimal("20"));

        MockNotificationService ns = new MockNotificationService();

        sender.sendMoney(receiver, new BigDecimal("100000"), ns);

        assertEquals("Romeo does not have enough funds.", ns.lastMessage);
    }
@Test
    void testSendExactBalance() {
        User sender = new User("Romeo", "r@mail.com", new BigDecimal("50"));
        User receiver = new User("Bob", "b@mail.com", new BigDecimal("10"));

        MockNotificationService ns = new MockNotificationService();

        sender.sendMoney(receiver, new BigDecimal("50"), ns);

        assertEquals(new BigDecimal("0"), sender.balance);
        assertEquals(new BigDecimal("60", receiver.balance));
        assertEquals("Transaction performed: Romeo sent $50 to Bob", ns.lastMessage);
    }

    @Test
    void testZeroAmount(){
        User sender = new User("Romeo", "r@mail.com", new BigDecimal("100"));
        User receiver = new User("Bob", "b@mail.com", new BigDecimal("20"));

        MockNotificationService ns = new MockNotificationService();

        sender.sendMoney(receiver, BigDecimal.ZERO, ns);

        assertEquals("Amount must be greater than 0.", ns.lastMessage);
    }
    @Test
    void testFailure() {
        User sender = new User("Romeo", "r@mail.com", new BigDecimal("10"));
        User receiver = new User("Bob", "b@mail.com", new BigDecimal("20"));

        MockNotificationService ns = new MockNotificationService();

        sender.sendMoney(receiver, new BigDecimal("99"), ns);

        assertEquals(new BigDecimal("10"), sender.balance);
        assertEquals(new BigDecimal("20"), receiver.balance);
    }
    @Test
    void testDecimalAmount() {
        User sender = new User("Romeo", "r@mail.com", new BigDecimal("10.50"));
        User receiver = new User("Bob", "b@mail.com", new BigDecimal("5.25"));

        MockNotificationService ns = new MockNotificationService();

        sender.sendMoney(receiver, new BigDecimal("3.75"), ns);

        assertEquals(new BigDecimal("6.75"), sender.balance);
        assertEquals(new BigDecimal("9.00"), receiver.balance);
    }
    @Test
    void testMultipleTransactions() {
        User sender = new User("Romeo", "r@mail.com", new BigDecimal("100"));
        User receiver = new User("Bob", "b@mail.com", new BigDecimal("20"));

        MockNotificationService ns = new MockNotificationService();

        sender.sendMoney(receiver, new BigDecimal("40"), ns);
        sender.sendMoney(receiver, new BigDecimal("30"), ns);

        assertEquals(new BigDecimal("30"), sender.balance);
        assertEquals(new BigDecimal("90"), receiver.balance);
    }
    static class MockNotificationService extends MockNotificationService{
        String lastMessage;

        @Override
        void notifyUser(String message){
            lastMessage = message;
        }
    }

}