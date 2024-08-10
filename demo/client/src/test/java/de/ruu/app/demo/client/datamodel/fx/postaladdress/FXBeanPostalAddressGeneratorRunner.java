package de.ruu.app.demo.client.datamodel.fx.postaladdress;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import de.ruu.app.datamodel.postaladdress.PostalAddress;
import de.ruu.app.datamodel.postaladdress.dto.PostalAddressDTO;
import de.ruu.lib.gen.GeneratorException;
import de.ruu.lib.gen.java.fx.bean.FXBeanGenerator;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class FXBeanPostalAddressGeneratorRunner
{
	public static void main(String[] args) throws IOException, GeneratorException
	{
		// generate FXBean from interface PostalAddress
		String packageName    = FXBeanPostalAddressGeneratorRunner.class.getPackageName();
		String simpleFileName = PostalAddress.class.getSimpleName() + "FXBean";

		FXBeanGenerator fxBeanGenerator =
				new FXBeanGenerator
						(
								packageName,
								simpleFileName,
								new ClassFileImporter().importClass(PostalAddress.class),
								new ClassFileImporter().importClass(PostalAddressDTO.class)
						);

		fxBeanGenerator.run();
	}
}