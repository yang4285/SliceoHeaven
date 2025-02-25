public class SliceoHeaven {
public void takeOrder(String orderId, double total, String side, String drink) {
        System.out.println("Order ID: " + orderId);
        System.out.println("Total: " + total);
        System.out.println("Side: " + side);
        System.out.println("Drink: " + drink);
    }

    public static void main(String[] args) {
        SliceoHeaven pizzeria = new SliceoHeaven();
        pizzeria.takeOrder("DEF-SOH-099", 15.00, "", "");
    }
}