package de.ruu.app.demo.client.datamodel.fx.postaladdress;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import de.ruu.app.datamodel.postaladdress.PostalAddress;
import de.ruu.lib.gen.GeneratorException;
import de.ruu.lib.gen.java.fx.tableview.GeneratorFXTableViewConfigurator;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class FXTableViewPostalAddressConfiguratorGeneratorRunner
{
	public static void main(String[] args) throws IOException, GeneratorException
	{
		// generate FXBean from interface PostalAddress
		String packageName    = FXBeanPostalAddressGeneratorRunner.class.getPackageName();
		String simpleFileName = PostalAddress.class.getSimpleName() + "FXTableViewConfigurator";

//		Path path =
//				Path.of(
//						"./src/gen/resources",
//						Files.toDirectoryName(packageName),
//						simpleFileName + ".fxml");
//		Files.writeToFile("", path);

		// generate table view configurator
		GeneratorFXTableViewConfigurator generatorFXTableViewConfigurator =
				new GeneratorFXTableViewConfigurator
						(
								packageName,
								simpleFileName,
								new ClassFileImporter().importClass(PostalAddressFXBean.class)
						);

		generatorFXTableViewConfigurator.run();
	}
}