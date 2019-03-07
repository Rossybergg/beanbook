package com.steamybeans.beanbook;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {
    @Nested
    @DisplayName("Email encoding")
    class Emailencoder {
        @DisplayName("changes 1 full stop")
        @Test
        void encodeString() {
            Authentication auth = new Authentication();
            assertEquals("beans@beanmail,com", auth.encodeString("beans@beanmail.com"));
        }

        @DisplayName("changes multiple full stops")
        @Test
        void encodeString2() {
            Authentication auth = new Authentication();
            assertEquals("beans@bean,mail,com", auth.encodeString("beans@bean.mail.com"));
        }
    }

    @Nested
    @DisplayName("Valid email tests")
    class Email {
        @DisplayName("Does not contain @")
        @Test
        void validEmail() {
            Authentication auth = new Authentication();
            assertEquals(false, auth.validEmail("bean.beanmail.com"));
        }

        @DisplayName("Does contain @")
        @Test
        void validEmail2() {
            Authentication auth = new Authentication();
            assertEquals(true, auth.validEmail("bean@beanmail.com"));
        }

        @DisplayName("Does not contain .com")
        @Test
        void validEmail3() {
            Authentication auth = new Authentication();
            assertEquals(false, auth.validEmail("bean@beanmail"));
        }

        @DisplayName("Does not contain .com")
        @Test
        void validEmail4() {
            Authentication auth = new Authentication();
            assertEquals(true, auth.validEmail("bean@beanmail.com"));
        }
    }

}
