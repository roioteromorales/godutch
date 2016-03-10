package com.roisoftstudio.godutch;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.roisoftstudio.godutch.login.LoginModule;
import com.roisoftstudio.godutch.login.paths.SignPath;

public class Main {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new LoginModule());


        SignPath signPath = new SignPath();
        signPath.signIn();

    }
}
