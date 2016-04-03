package com.roisoftstudio.godutch.config;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PropertiesReaderTest {

    @Test
    public void propertiesReader_shouldReadPropertiesFromFile() throws Exception {
        PropertiesReader propertiesReader = new PropertiesReader("testConfig.properties");
        assertThat(propertiesReader.getValue("testVariable"), is("testValue"));
        assertThat(propertiesReader.getValue("testVariable2"), is("testValue2"));
    }
}