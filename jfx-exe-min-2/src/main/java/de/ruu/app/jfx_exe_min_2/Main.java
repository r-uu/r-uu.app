package de.ruu.app.jfx_exe_min_2;

import de.ruu.app.demo.client.datamodel.fx.postaladdress.PostalAddressManagerApp;

import de.ruu.lib.fx.comp.FXCAppRunner;

/**
 * Another Main class as workaround when the JavaFX Application ist started without
 * taking care of Classloader Requirements of JavaFX. (Important when starting from inside NetBeans!)
 */
public class Main
{
    /**
     * Additional main methode to start Application.
     * @param args Commandline Arguments.
     */
//    public static void main(String[] args) {
//        JavaFXApp.main(args);
//    }
    public static void main(String[] args) { FXCAppRunner.run(PostalAddressManagerApp.class, args); }
}