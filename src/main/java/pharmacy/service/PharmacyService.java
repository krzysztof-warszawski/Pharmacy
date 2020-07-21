package pharmacy.service;

import pharmacy.domain.PharmacyData;

import java.util.List;

public interface PharmacyService {

    void updatePharmacyData(String address, String email, String phoneNumber);

    PharmacyData readPharmacyData();

    String[] getPharmacyNameList();

    void setPharmacyDataList();

    List<PharmacyData> getPharmacyDataList();

    void setPharmacyData(int index);

    void setPharmacyNameList();

    void setPharmacyUnitManager(int index);

    String getPharmacyUnitManager();


}
