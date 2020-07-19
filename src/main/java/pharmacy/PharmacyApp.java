package pharmacy;

import pharmacy.gui.admin.AdminMenuPanel;
import pharmacy.gui.manager.ManagerPanel;
import pharmacy.gui.pharmacist.PharmacistPanel;
import pharmacy.service.UserProfileService;

import static pharmacy.Main.mainFrame;

public class PharmacyApp {

    private final String PHARMACIST = "Pharmacist";
    private final String UNIT_MANAGER = "Unit Manager";
    private final String ADMIN = "Admin";

    private PharmacistPanel pharmacistPanel;
    private AdminMenuPanel adminMenuPanel;
    private ManagerPanel managerPanel;

    public boolean login(String userLogin, String userPassword) {
        UserProfileService.initializeUserProfile(userLogin, userPassword);
        if (UserProfileService.isCorrect()) {
            switch (UserProfileService.getJobTitle()) {
                case PHARMACIST:
                    pharmacistPanel = new PharmacistPanel();
                    mainFrame.panelSwitchOver(pharmacistPanel);
                    break;
                case UNIT_MANAGER:
                    managerPanel = new ManagerPanel();
                    mainFrame.panelSwitchOver(managerPanel);
                    break;
                case ADMIN:
                    adminMenuPanel = new AdminMenuPanel();
                    mainFrame.panelSwitchOver(adminMenuPanel);
                    break;
            }
            return true;
        } else {
            return false;
        }
    }
}
