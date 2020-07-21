package pharmacy.service;

import pharmacy.domain.PharmacyStorageData;
import pharmacy.domain.ReceiptData;
import pharmacy.repository.PharmacyStorageRepository;
import pharmacy.repository.PharmacyStorageRepositoryImpl;
import pharmacy.repository.ReceiptRepository;
import pharmacy.repository.ReceiptRepositoryImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {

    private final PharmacyStorageRepository pharmacyStorageRepository;
    private final ReceiptRepository receiptRepository;
    private PharmacyStorageData medicineToBasket;
    private ReceiptData receiptData;
    private List<PharmacyStorageData> pharmacyStorageDataList;
    private List<PharmacyStorageData> basketData;
    private String[] medicinesInStorageList;
    private int pharmacyId;
    private int userId;
    private double total;
    private double totalSale;
    private String[] basket;
    private String basketToString;

    public ReceiptServiceImpl() {
        this.pharmacyStorageRepository = new PharmacyStorageRepositoryImpl();
        this.receiptRepository = new ReceiptRepositoryImpl();
        this.pharmacyId = UserProfileService.getPharmacyId();
        this.userId = UserProfileService.getUserId();
        setPharmacyStorageDataList();
        setMedicineAndPriceDisplayList();
        setBasketData();
        this.basket = new String[medicinesInStorageList.length];
    }

    @Override
    public void addNewReceipt() {
        setBasketToString();
        ReceiptData receipt = new ReceiptData(pharmacyId, userId, total, basketToString);
        receiptRepository.createReceipt(receipt);
    }

    @Override
    public void updatePharmacyStorageQuantityData() {
        for (PharmacyStorageData psd : getPharmacyStorageDataList()) {
            pharmacyStorageRepository.updatePharmacyStorageQuantity(psd);
        }
    }

    @Override
    public void updateMedicineQty(int index) {
        getPharmacyStorageDataList().get(index).setQuantity(getPharmacyStorageDataList().get(index).getQuantity() - 1);
    }

    @Override
    public void setBasketData() {
        this.basketData = new ArrayList<>();
    }

    /* method that checks if the medicine had already been added to the basket
    if YES updates the quantity
    if NO creates a copy of the medicine and adds it to the basket
     */
    @Override
    public void updateBasketData(int index) {
        medicineToBasket = getPharmacyStorageDataList().get(index);
        if (basketData.contains(medicineToBasket)) {
            int index1 = basketData.indexOf(medicineToBasket);
            basketData.get(index1).setQuantity(basketData.get(index1).getQuantity() + 1);
        } else {
            PharmacyStorageData newMedicineToBasket = new PharmacyStorageData();
            newMedicineToBasket.setPharmacyId(medicineToBasket.getPharmacyId());
            newMedicineToBasket.setMedicineId(medicineToBasket.getMedicineId());
            newMedicineToBasket.setMedicineName(medicineToBasket.getMedicineName());
            newMedicineToBasket.setPrice(medicineToBasket.getPrice());
            newMedicineToBasket.setQuantity(1);
            basketData.add(newMedicineToBasket);
        }
        updateTotal();
        addMedicineToBasket();
    }

    @Override
    public void setPharmacyStorageDataList() {
        this.pharmacyStorageDataList = pharmacyStorageRepository.readPharmacyStorageData(pharmacyId);
    }

    @Override
    public List<PharmacyStorageData> getPharmacyStorageDataList() {
        return pharmacyStorageDataList;
    }

    @Override
    public void setMedicineAndPriceDisplayList() {
        this.medicinesInStorageList = new String[getPharmacyStorageDataList().size()];
        int i = 0;
        for (PharmacyStorageData psd : getPharmacyStorageDataList()) {
            getMedicineAndPriceDisplayList()[i] = psd.getMedicineName() + ", price: " + psd.getPrice() + "$";
            i++;
        }
    }

    @Override
    public String[] getMedicineAndPriceDisplayList() {
        return medicinesInStorageList;
    }

    @Override
    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public double getTotal() {
        return total;
    }

    @Override
    public void updateTotal() {
        total += medicineToBasket.getPrice();
        BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
        setTotal(bd.doubleValue());
    }


    @Override
    public void setBasket() {
        this.basket = new String[medicinesInStorageList.length];
    }

    @Override
    public String[] getBasket() {
        return basket;
    }

    @Override
    public void addMedicineToBasket() {
        int i = 1;
        int y = 0;
        for (PharmacyStorageData psd : basketData) {
            String sb = i + ". " + psd.getMedicineName() + " " +
                    psd.getQuantity() + " * " + psd.getPrice() + "$\n";
            basket[y] = sb;
            i++;
            y++;
        }
    }

    @Override
    public String getBasketToString() {
        return basketToString;
    }

    @Override
    public void setBasketToString() {
        basketToString = Arrays.toString(basket).replaceAll("[\\[\\]]|null|, ","");
    }


    /* methods for a manager to display Total Sale and to check receipts */
    @Override
    public void setTotalSale(Date date1, Date date2) {
        this.totalSale = receiptRepository.getTotalSale(pharmacyId, date1, date2).getTotal();
    }

    @Override
    public double getTotalSale() {
        BigDecimal bd = new BigDecimal(totalSale).setScale(2, RoundingMode.HALF_UP);
        this.totalSale = bd.doubleValue();
        return totalSale;
    }

    @Override
    public void setReceiptData(int receiptId) {
        this.receiptData = receiptRepository.readReceipt(receiptId);
    }

    @Override
    public ReceiptData getReceiptData() {
        return receiptData;
    }
}
