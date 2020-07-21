package pharmacy.service;

import pharmacy.domain.PharmacyStorageData;
import pharmacy.domain.ReceiptData;

import java.sql.Date;
import java.util.List;

public interface ReceiptService {

    void addNewReceipt();

    void updatePharmacyStorageQuantityData();

    void updateMedicineQty(int index);

    void setBasketData();

    void updateBasketData(int index);

    String[] getMedicineAndPriceDisplayList();

    void setPharmacyStorageDataList();

    List<PharmacyStorageData> getPharmacyStorageDataList();

    void setMedicineAndPriceDisplayList();

    void setTotal(double total);

    void updateTotal();

    double getTotal();

    void addMedicineToBasket();

    void setBasket();

    String[] getBasket();

    void setReceiptData(int receiptId);

    ReceiptData getReceiptData();

    void setBasketToString();

    void setTotalSale(Date date1, Date date2);

    double getTotalSale();
}
