package de.ruu.app.demo.client.datamodel.fx.postaladdress;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import de.ruu.app.datamodel.postaladdress.PostalAddress;
import de.ruu.lib.gen.GeneratorException;
import de.ruu.lib.gen.java.fx.bean.editor.FXBeanViewFXMLGenerator;
import de.ruu.lib.gen.java.fx.comp.GeneratorFXCompBundle;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class FXBeanEditorPostalAddressGeneratorRunner
{
	public static void main(String[] args) throws IOException, GeneratorException
	{
		// generate FXBean editor from interface PostalAddress
		String   packageName    = FXBeanEditorPostalAddressGeneratorRunner.class.getPackageName() + ".edit";
		Class<?> generatorInput = PostalAddress.class;
		String   simpleFileName = PostalAddress.class.getSimpleName() + "FXBeanEditor";

		log.debug("create java fxml for bean editor based on {}", generatorInput.getClass().getName());
		FXBeanViewFXMLGenerator fxBeanEditorFXMLGenerator =
				new FXBeanViewFXMLGenerator
						(
								packageName,
								simpleFileName,
								new ClassFileImporter().importClass(generatorInput)
						);

		fxBeanEditorFXMLGenerator.run();

		log.debug("create java fx component bundle for bean editor based on {}", generatorInput.getClass().getName());
		GeneratorFXCompBundle fxBeanEditorComponentBundleGenerator =
				new GeneratorFXCompBundle
						(
								packageName,
								simpleFileName
						);

		fxBeanEditorComponentBundleGenerator.run();
	}
}