package com.gardeners.app.service;

import com.gardeners.app.services.GardenerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GardenerServiceTests {

    @Autowired
    GardenerService gardenerService;

    //vienību (unit) tests
    @Test
    public void testHashingPasswords() {
        String hash1 = gardenerService
                .hashPassword("abc");
        assert hash1 != null;
        String hash2 = gardenerService
                .hashPassword("abc");
        assert hash2 != null;
        assert !hash1.equals(hash2);
    }


}
