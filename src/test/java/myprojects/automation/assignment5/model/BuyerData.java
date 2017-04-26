package myprojects.automation.assignment5.model;

import java.util.Random;

/**
 * Hold Product information that is used among tests.
 */
public class BuyerData {
    private String name;
    private String surname;
    private String email;

    public BuyerData(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    /**
     * @return New Product object with random name, quantity and price values.
     */
    public static BuyerData generate() {
        Random random = new Random();
        char letter1 = (char) (random.nextInt(26) + 'a');
        char letter2 = (char) (random.nextInt(26) + 'a');
        return new BuyerData(
                "John" + letter1,
                "Doe" + letter2,
                 "john" + random.nextInt(100) + "@gmail.com" );
    }
}
