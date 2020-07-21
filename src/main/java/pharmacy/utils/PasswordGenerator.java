package pharmacy.utils;

import java.util.Random;

public class PasswordGenerator {

    public String setPassword() {
        char[] charArray = new char[12];
        char c;

        for (int i = 0; i < 12; i += 4) {
            c = (char) (new Random().nextInt(25) + 65);
            charArray[i] = c;
            c = (char) (new Random().nextInt(9) + 48);
            charArray[1+i] = c;
            c = (char) (new Random().nextInt(25) + 97);
            charArray[2+i] = c;
            c = (char) (new Random().nextInt(5) + 33);
            charArray[3+i] = c;
        }
        return new String(charArray);
    }
}
