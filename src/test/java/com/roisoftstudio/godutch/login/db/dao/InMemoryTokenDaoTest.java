package com.roisoftstudio.godutch.login.db.dao;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class InMemoryTokenDaoTest {
    @Test
    public void whenAddingToken_shouldContainToken() throws Exception {
        InMemoryTokenDao inMemoryTokenDao = new InMemoryTokenDao();
        inMemoryTokenDao.addToken("email","token");
        assertThat(inMemoryTokenDao.hasToken("token")).isTrue();
    }
}