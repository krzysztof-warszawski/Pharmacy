package pharmacy.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotSame;

@RunWith(Parameterized.class)
public class PasswordGeneratorTest {

    private final PasswordGenerator passwordGenerator = new PasswordGenerator();

    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[20][]);
    }

    @org.junit.Test
    public void setPassword() {
        String input = passwordGenerator.setPassword();
        String output = passwordGenerator.setPassword();
        assertNotSame(input, output);
//        assertEquals(input, output);
    }
}
