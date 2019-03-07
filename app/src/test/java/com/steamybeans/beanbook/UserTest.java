package com.steamybeans.beanbook;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @DisplayName("setFullName")
    @Test
    void setName() {
        User user = new User();
        user.setFullName("test");
        assertEquals(user.getFullName(), "test");
    }

    @DisplayName("setPassword")
    @Test
    void setPassword() {
        User user = new User();
        user.setPassword("test");
        assertEquals(user.getPassword(), "test");
    }

}
