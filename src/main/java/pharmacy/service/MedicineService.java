package pharmacy.service;

import pharmacy.domain.MedicineData;

import java.util.List;

public interface MedicineService {

    void addNewMedicine(String medicineName, double price, String medicineDescription);

    void updateMedicineData(String medicineName, double price, String medicineDescription);

    void removeMedicine(int index);

    MedicineData readMedicineData();

    void updateMedicineNameList();

    String[] getMedicineNameList();

    void setMedicineDataList();

    List<MedicineData> getMedicineDataList();

    void findMedicineData(int index);

    void setMedicineNameList();
}
