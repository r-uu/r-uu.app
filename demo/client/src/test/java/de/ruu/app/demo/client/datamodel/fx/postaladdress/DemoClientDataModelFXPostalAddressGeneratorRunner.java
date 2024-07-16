package de.ruu.app.demo.client.datamodel.fx.postaladdress;

import de.ruu.lib.gen.GeneratorException;
import de.ruu.lib.gen.java.fx.comp.GeneratorFXCompBundle;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class DemoClientDataModelFXPostalAddressGeneratorRunner
{
	public static void main(String[] args) throws IOException, GeneratorException
	{
		GeneratorFXCompBundle generator;
		String                packageName = DemoClientDataModelFXPostalAddressGeneratorRunner.class.getPackageName();

		log.debug("creating java fx component {} bundles", packageName);
		generator = new GeneratorFXCompBundle(packageName, "Main");
		generator.run();
		log.debug("created  java fx component {} bundles", packageName);
	}
}