package pharmacy.service;

import pharmacy.domain.MedicineData;
import pharmacy.repository.MedicineRepository;
import pharmacy.repository.MedicineRepositoryImpl;

import java.util.List;

public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private MedicineData newMedicineData;
    private MedicineData medicineData;
    private List<MedicineData> medicineDataList;
    private String[] medicineNameList;

    public MedicineServiceImpl() {
        this.medicineRepository = new MedicineRepositoryImpl();
        setMedicineDataList();
        setMedicineNameList();
    }

    // =================== CRUD for the Medicine ===================
    @Override
    public void addNewMedicine(String medicineName, double price, String medicineDescription) {
        this.newMedicineData = new MedicineData(medicineName, price, medicineDescription);
        medicineRepository.createMedicine(newMedicineData);
    }

    @Override
    public void updateMedicineData(String medicineName, double price, String medicineDescription) {
        int mId = medicineData.getMedicineId();
        this.medicineData = new MedicineData(medicineName, price, medicineDescription);
        medicineData.setMedicineId(mId);
        medicineRepository.updateMedicine(medicineData);
    }

    @Override
    public void removeMedicine(int index) {
        int mId = medicineDataList.get(index).getMedicineId();
        medicineRepository.deleteMedicine(mId);
        updateMedicineNameList();
    }

    @Override
    public MedicineData readMedicineData() {
        return medicineData;
    }

    // =================== Helping methods ===================
    @Override
    public void updateMedicineNameList() {
        setMedicineDataList();
        setMedicineNameList();
    }

    // =================== Getters/Setters ===================
    @Override
    public String[] getMedicineNameList() {
        return medicineNameList;
    }

    @Override
    public void setMedicineDataList() {
        this.medicineDataList = medicineRepository.getAllMedicines();
    }

    @Override
    public List<MedicineData> getMedicineDataList() {
        return medicineDataList;
    }

    @Override
    public void findMedicineData(int index) {
        int mId = medicineDataList.get(index).getMedicineId();
        this.medicineData = medicineRepository.readMedicine(mId);
    }

    @Override
    public void setMedicineNameList() {
        this.medicineNameList = new String[getMedicineDataList().size()];
        int i = 0;
        for (MedicineData md : getMedicineDataList()) {
            medicineNameList[i] = md.getMedicineName();
            i++;
        }
    }

    public void setMedicineData(MedicineData medicineData) {
        this.medicineData = medicineData;
    }
}
