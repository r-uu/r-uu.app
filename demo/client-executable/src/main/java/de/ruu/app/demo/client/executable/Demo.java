package de.ruu.app.demo.client.executable;

import de.ruu.app.demo.client.datamodel.fx.postaladdress.PostalAddressManagerApp;
import de.ruu.lib.fx.comp.FXCAppRunner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo
{
    public static void main(String[] args)
    {
        log.debug("starting {}", Demo.class.getName());
        FXCAppRunner.run(PostalAddressManagerApp.class, args);
        log.debug("finished {}", Demo.class.getName());
    }
}