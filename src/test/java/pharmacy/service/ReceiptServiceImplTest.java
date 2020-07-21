package pharmacy.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReceiptServiceImplTest {

    {
        UserProfileService.initializeUserProfile("test","test");
    }
    ReceiptServiceImpl receiptService = new ReceiptServiceImpl();

    @Test
    public void setBasketToString() {
        String[] basket = new String[]{"1. Haemonorphine 1 * 12.5$\n" +
                ", 2. TestMed 1 * 100.0$\n" +
                ", 3. Cornesin 1 * 12.6$\n" +
                ", 4. Lovebazine 1 * 6.0$\n" +
                ", 5. Menotalol 3 * 64.0$\n" +
                ", 6. Butaclude 1 * 126.0$\n" +
                ", null, null, null, null, null"};
        String basketToString = "1. Haemonorphine 1 * 12.5$\n" +
                "2. TestMed 1 * 100.0$\n" +
                "3. Cornesin 1 * 12.6$\n" +
                "4. Lovebazine 1 * 6.0$\n" +
                "5. Menotalol 3 * 64.0$\n" +
                "6. Butaclude 1 * 126.0$\n" +
                "";

        receiptService.setBasket(basket);
        receiptService.setBasketToString();
        assertEquals(basketToString, receiptService.getBasketToString());
    }
}
