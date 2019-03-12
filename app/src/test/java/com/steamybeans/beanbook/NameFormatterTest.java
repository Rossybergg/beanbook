package com.steamybeans.beanbook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameFormatterTest {

    @Test
    void capitalize() {
        NameFormatter nf = new NameFormatter();
        assertEquals("Foo Bar", nf.capitalize("foo bar"));
        assertEquals("Baz Qux-Quux", nf.capitalize("baz qux-quux"));
    }
}