import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class SliceoHeaven {
    public static final double PIZZA_BASE_PRICE = 10.0;
    private static final int PIZZAS_ORDERED_SIZE = 10;
    private static final int PIZZA_SIZES_ORDERED_SIZE = 10;
    private static final int SIDE_DISHES_ORDERED_SIZE = 20;
    private static final int DRINKS_ORDERED_SIZE = 20;

    private String[] pizzasOrdered = new String[PIZZAS_ORDERED_SIZE];
    private String[] pizzaSizesOrdered = new String[PIZZA_SIZES_ORDERED_SIZE];
    private String[] sideDishesOrdered = new String[SIDE_DISHES_ORDERED_SIZE];
    private String[] drinksOrdered = new String[DRINKS_ORDERED_SIZE];
    private int pizzaIndex = 0;
    private int sizeIndex = 0;
    private int sideIndex = 0;
    private int drinkIndex = 0;
    private double totalOrderPrice = 0;

    public String storeName;
    public String storeAddress;

    private String storeMenu;
    public long storePhone;
    public String storeEmail;
    public String pizzaIngredients;
    public double pizzaPrice;
    public String sides;

    private String orderID;
    private String drinks;
    private double orderTotal;

    public static final String DEF_ORDER_ID = "DEF-SOH-099";
    public static final String DEF_PIZZA_INGREDIENTS = "Mozzarella Cheese";
    public static final double DEF_ORDER_TOTAL = 15.00;
    public static final long BLACKLISTED_NUMBER = 12345678901234L;

    private Scanner scanner;

    enum PizzaSelection {
        PEPPERONI("Pepperoni", "Lots of pepperoni and extra cheese", 18),
        HAWAIIAN("Hawaiian", "Pineapple, ham, and extra cheese", 22),
        VEGGIE("Veggie", "Green pepper, onion, tomatoes, mushroom, and black olives", 25),
        BBQ_CHICKEN("BBQ Chicken", "Chicken in BBQ sauce, bacon, onion, green pepper, and cheddar cheese", 35),
        EXTRAVAGANZA("Extravaganza", "Pepperoni, ham, Italian sausage, beef, onions, green pepper, mushrooms, black olives, and extra cheese", 45);
    
        private final String pizzaName;
        private final String pizzaToppings;
        private final double price;
    
        PizzaSelection(String pizzaName, String pizzaToppings, double price) {
            this.pizzaName = pizzaName;
            this.pizzaToppings = pizzaToppings;
            this.price = price;
        }
    
        public String getPizzaName() {
            return pizzaName;
        }
    
        public String getPizzaToppings() {
            return pizzaToppings;
        }
    
        public double getPrice() {
            return price;
        }
    
        @Override
        public String toString() {
            return pizzaName + " Pizza with " + pizzaToppings + ", for €" + price;
        }
    }
    
    enum PizzaToppings {
        HAM("Ham", 2),
        PEPPERONI("Pepperoni", 2),
        BEEF("Beef", 2),
        CHICKEN("Chicken", 2),
        SAUSAGE("Sausage", 2),
        PINEAPPLE("Pineapple", 1),
        ONION("Onion", 0.5),
        TOMATOES("Tomatoes", 0.4),
        GREEN_PEPPER("Green Pepper", 0.5),
        BLACK_OLIVES("Black Olives", 0.5),
        SPINACH("Spinach", 0.5),
        CHEDDAR_CHEESE("Cheddar Cheese", 0.8),
        MOZZARELLA_CHEESE("Mozzarella Cheese", 0.8),
        FETA_CHEESE("Feta Cheese", 1),
        PARMESAN_CHEESE("Parmesan Cheese", 1);
    
        private final String topping;
        private final double toppingPrice;
    
        PizzaToppings(String topping, double toppingPrice) {
            this.topping = topping;
            this.toppingPrice = toppingPrice;
        }
    
        public String getTopping() {
            return topping;
        }
    
        public double getToppingPrice() {
            return toppingPrice;
        }
    
        @Override
        public String toString() {
            return topping + " (€" + toppingPrice + ")";
        }
    }
    
    enum PizzaSize {
        LARGE("Large", 10),
        MEDIUM("Medium", 5),
        SMALL("Small", 0);
    
        private final String pizzaSize;
        private final double addToPizzaPrice;
    
        PizzaSize(String pizzaSize, double addToPizzaPrice) {
            this.pizzaSize = pizzaSize;
            this.addToPizzaPrice = addToPizzaPrice;
        }
    
        public String getPizzaSize() {
            return pizzaSize;
        }
    
        public double getAddToPizzaPrice() {
            return addToPizzaPrice;
        }
    
        @Override
        public String toString() {
            return pizzaSize + ": €" + addToPizzaPrice;
        }
    }
    
    enum SideDish {
        CALZONE("Calzone", 15),
        CHICKEN_PUFF("Chicken Puff", 20),
        MUFFIN("Muffin", 12),
        NOTHING("No side dish", 0);
    
        private final String sideDishName;
        private final double addToPizzaPrice;
    
        SideDish(String sideDishName, double addToPizzaPrice) {
            this.sideDishName = sideDishName;
            this.addToPizzaPrice = addToPizzaPrice;
        }
    
        public String getSideDishName() {
            return sideDishName;
        }
    
        public double getAddToPizzaPrice() {
            return addToPizzaPrice;
        }
    
        @Override
        public String toString() {
            return sideDishName + ": €" + addToPizzaPrice;
        }
    }
    
    enum Drinks {
        COCA_COLA("Coca Cola", 8),
        COCOA_DRINK("Cocoa Drink", 10),
        NOTHING("No drinks", 0);
    
        private final String drinkName;
        private final double addToPizzaPrice;
    
        Drinks(String drinkName, double addToPizzaPrice) {
            this.drinkName = drinkName;
            this.addToPizzaPrice = addToPizzaPrice;
        }
    
        public String getDrinkName() {
            return drinkName;
        }
    
        public double getAddToPizzaPrice() {
            return addToPizzaPrice;
        }
    
        @Override
        public String toString() {
            return drinkName + ": €" + addToPizzaPrice;
        }
    }

    public SliceoHeaven() {
        this.scanner = new Scanner(System.in);
        this.orderID = DEF_ORDER_ID;
        this.pizzaIngredients = DEF_PIZZA_INGREDIENTS;
        this.orderTotal = DEF_ORDER_TOTAL;
        this.sides = "";
        this.drinks = "";
        this.totalOrderPrice = 0;
    }

    public SliceoHeaven(String orderID, String pizzaIngredients, double orderTotal) {
        this.scanner = new Scanner(System.in);
        this.orderID = orderID;
        this.pizzaIngredients = pizzaIngredients;
        this.orderTotal = orderTotal;
        this.totalOrderPrice = 0;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDrinks() {
        return drinks;
    }

    public void setDrinks(String drinks) {
        this.drinks = drinks;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getStoreMenu() {
        return storeMenu;
    }

    public void setStoreMenu(String storeMenu) {
        this.storeMenu = storeMenu;
    }

    
    public void takeOrder() {
        try {
            while (true) {
                System.out.println("Welcome to Slice-o-Heaven Pizzeria. Here’s what we serve:");
                PizzaSelection[] pizzaSelections = PizzaSelection.values();
                for (int i = 0; i < pizzaSelections.length; i++) {
                    System.out.println((i + 1) + ". " + pizzaSelections[i]);
                }
                System.out.println((pizzaSelections.length + 1) + ". Custom Pizza with a maximum of 10 toppings that you choose");
                int pizzaChoice = getValidChoice(1, pizzaSelections.length + 1, "Please enter your choice (1 - " + (pizzaSelections.length + 1) + "): ");

                if (pizzaChoice >= 1 && pizzaChoice <= pizzaSelections.length) {
                    System.out.println("Selected pizza: " + pizzaSelections[pizzaChoice - 1]);
                    PizzaSelection selectedPizza = pizzaSelections[pizzaChoice - 1];
                    pizzasOrdered[pizzaIndex] = selectedPizza.toString();
                    totalOrderPrice += selectedPizza.getPrice();
                    pizzaIndex++;
                } else if (pizzaChoice == pizzaSelections.length + 1) {
                    System.out.println("Available toppings:");
                    PizzaToppings[] pizzaToppings = PizzaToppings.values();
                    for (int i = 0; i < pizzaToppings.length; i++) {
                        System.out.println((i + 1) + ". " + pizzaToppings[i]);
                    }

                    double customPizzaPrice = PIZZA_BASE_PRICE;
                    StringBuilder customPizzaToppings = new StringBuilder();
                    System.out.print("Enter a maximum of 10 choices (separated by spaces): ");
                    String[] toppingChoices = scanner.nextLine().split(" ");
                    int toppingCount = Math.min(toppingChoices.length, 10);
                    for (int i = 0; i < toppingCount; i++) {
                        try {
                            int toppingIndex = Integer.parseInt(toppingChoices[i]) - 1;
                            if (toppingIndex >= 0 && toppingIndex < pizzaToppings.length) {
                                customPizzaToppings.append(pizzaToppings[toppingIndex].getTopping()).append(", ");
                                customPizzaPrice += pizzaToppings[toppingIndex].getToppingPrice();
                            } else {
                                System.out.println("Invalid topping choice. Skipping this one.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid topping choice. Skipping this one.");
                        }
                    }
                    if (customPizzaToppings.length() > 0) {
                        customPizzaToppings.setLength(customPizzaToppings.length() - 2);
                    }
                    String customPizzaDescription = "Custom Pizza with " + customPizzaToppings + ", for €" + customPizzaPrice;
                    pizzasOrdered[pizzaIndex] = customPizzaDescription;
                    totalOrderPrice += customPizzaPrice;
                    pizzaIndex++;
                }

                PizzaSize[] pizzaSizes = PizzaSize.values();
                int sizeChoice = getValidChoice(1, pizzaSizes.length, "Choose a pizza size:", "Enter your choice (1 - " + pizzaSizes.length + "): ");
                System.out.println("Selected pizza size: " + pizzaSizes[sizeChoice - 1]);
                PizzaSize selectedSize = pizzaSizes[sizeChoice - 1];
                pizzaSizesOrdered[sizeIndex] = selectedSize.toString();
                totalOrderPrice += selectedSize.getAddToPizzaPrice();
                sizeIndex++;

                SideDish[] sideDishes = SideDish.values();
                int sideChoice = getValidChoice(1, sideDishes.length, "Choose a side dish:", "Enter your choice (1 - " + sideDishes.length + "): ");
                System.out.println("Selected side dish: " + sideDishes[sideChoice - 1]);
                SideDish selectedSide = sideDishes[sideChoice - 1];
                sideDishesOrdered[sideIndex] = selectedSide.toString();
                totalOrderPrice += selectedSide.getAddToPizzaPrice();
                sideIndex++;

                Drinks[] drinksOptions = Drinks.values();
                int drinkChoice = getValidChoice(1, drinksOptions.length, "Choose a drink:", "Enter your choice (1 - " + drinksOptions.length + "): ");
                System.out.println("Selected drink: " + drinksOptions[drinkChoice - 1]);
                Drinks selectedDrink = drinksOptions[drinkChoice - 1];
                drinksOrdered[drinkIndex] = selectedDrink.toString();
                totalOrderPrice += selectedDrink.getAddToPizzaPrice();
                drinkIndex++;

                System.out.print("Do you want to order more? (Y/N): ");
                String moreOrders = scanner.nextLine();
                if (!moreOrders.equalsIgnoreCase("Y")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int getValidChoice(int min, int max, String... messages) {
        int choice = 0;
        while (true) {
            for (String message : messages) {
                System.out.println(message);
            }
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= min && choice <= max) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please pick only from the given list.");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return choice;
    }

    public void isItYourBirthday() {
        LocalDate birthdate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.println("Enter your birthday (dd/MM/yyyy):");
            String birthdateStr = scanner.next();
            scanner.nextLine();
            try {
                birthdate = LocalDate.parse(birthdateStr, formatter);
                LocalDate fiveYearsAgo = LocalDate.now().minusYears(5);
                LocalDate oneHundredTwentyYearsAgo = LocalDate.now().minusYears(120);
                if (birthdate.isBefore(fiveYearsAgo) && birthdate.isAfter(oneHundredTwentyYearsAgo)) {
                    break;
                }
                System.out.println("Invalid date. You are either too young or too dead to order.");
                System.out.println("Please enter a valid date:");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        }
        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthdate.getYear();
        if (today.getMonthValue() < birthdate.getMonthValue() ||
                (today.getMonthValue() == birthdate.getMonthValue() && today.getDayOfMonth() < birthdate.getDayOfMonth())) {
            age--;
        }
        if (today.getMonthValue() == birthdate.getMonthValue() && today.getDayOfMonth() == birthdate.getDayOfMonth()) {
            System.out.println("Happy birthday! You get a 10% discount on your order.");
            totalOrderPrice *= 0.9;
        }
    }

    @Override
public String toString() {
    StringBuilder orderDetails = new StringBuilder();
    orderDetails.append("Order ID: ").append(orderID).append("\n");
    orderDetails.append("Thank you for dining with Slice-o-Heaven Pizzeria. Your order details are as follows:\n");
    for (int i = 0; i < pizzaIndex; i++) {
        orderDetails.append("Order Item ").append(i + 1).append(":\n");
        orderDetails.append("  Pizza: ").append(pizzasOrdered[i]).append("\n");
        orderDetails.append("  Size: ").append(pizzaSizesOrdered[i]).append("\n");
        orderDetails.append("  Side Dish: ").append(sideDishesOrdered[i]).append("\n");
        orderDetails.append("  Drink: ").append(drinksOrdered[i]).append("\n");
    }
    orderDetails.append("ORDER TOTAL: €").append(totalOrderPrice);
    return orderDetails.toString();
}    

    public static void main(String[] args) {
        SliceoHeaven pizzeria = new SliceoHeaven();
        pizzeria.takeOrder();
        pizzeria.isItYourBirthday();
        System.out.println(pizzeria);
    }
}    