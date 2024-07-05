package de.ruu.app.demo.client.fx;

import java.io.IOException;

import de.ruu.lib.gen.GeneratorException;
import de.ruu.lib.gen.java.fx.comp.GeneratorFXCompBundle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class CompanyMainGeneratorRunner
{
	public static void main(String[] args) throws IOException, GeneratorException
	{
		GeneratorFXCompBundle generator;
		String                packageName = CompanyMainGeneratorRunner.class.getPackageName();

		log.debug("creating java fx component {} bundles", packageName);
		generator = new GeneratorFXCompBundle(packageName, "Main");
		generator.run();
		log.debug("created  java fx component {} bundles", packageName);
	}
}