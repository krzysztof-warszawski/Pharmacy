package pharmacy.service;

import org.junit.BeforeClass;
import org.junit.Test;
import pharmacy.domain.MedicineData;
import pharmacy.repository.MedicineRepositoryImpl;

import static org.junit.Assert.assertEquals;

public class MedicineServiceImplTest {

    private static MedicineServiceImpl medicineService;
    private static MedicineRepositoryImpl medicineRepository;

    @BeforeClass
    public static void setUp() {
        medicineService = new MedicineServiceImpl();
        medicineRepository = new MedicineRepositoryImpl();
    }

//    @Test // Id = 19
    public void addNewMedicine() {
        medicineService.addNewMedicine("TestMed", 109.99,"TestPurpose");
    }

    @Test
    public void updateMedicineData() {
        MedicineData updated = new MedicineData("TestMed",100.00,"Update");
        updated.setMedicineId(19);

        medicineService.setMedicineData(new MedicineData());
        medicineService.readMedicineData().setMedicineId(19);
        medicineService.updateMedicineData("TestMed",100.00,"Update");
        assertEquals(updated, medicineRepository.readMedicine(19));
    }

    @Test
    public void readMedicineData() {
        MedicineData toBeRead = new MedicineData();
        toBeRead.setMedicineId(19);
        assertEquals(medicineService.readMedicineData(), toBeRead);
    }

}
