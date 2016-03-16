package com.roisoftstudio.godutch;

public class MyGuiceServletContextListener{  // extends GuiceServletContextListener {
//
//    @Override
//    protected Injector getInjector() {
//        return Guice.createInjector(new LoginModule());
//    }
//




//    new ServletModule() {
//        @Override
//        protected void configureServlets() {
//            bind(new TypeLiteral<Dao<String>>() {
//            }).to(StuffDao.class);
//
//            ResourceConfig rc = new PackagesResourceConfig("com.athaydes.web.server");
//            for (Class<?> resource : rc.getClasses()) {
//                bind(resource);
//            }
//
//            serve("/services/*").with(GuiceContainer.class);
//        }
//    }
}
