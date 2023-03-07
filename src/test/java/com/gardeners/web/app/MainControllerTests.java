package com.gardeners.web.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MainControllerTests {

    @Autowired
    MainController mainController;

    //vienību (unit) tests
    @Test
    public void testHashingPasswords() {
        String hash1 = mainController
                .getHashedPassword("abc");
        assert hash1 != null;
        String hash2 = mainController
                .getHashedPassword("abc");
        assert hash2 != null;
        assert !hash1.equals(hash2);
    }


}
