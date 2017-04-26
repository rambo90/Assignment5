package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.model.BuyerData;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseTest {

    @Test
    public void checkSiteVersion() {
        // TODO open main page and validate website version
// без проверки

    }

    @Test
    public void createNewOrder() {
        // TODO implement order creation test
        actions.openRandomProduct();

        actions.getOpenedProductInfo();

        actions.addToCart();

        actions.procced(BuyerData.generate());

        actions.checkQuantities();
    }

}
