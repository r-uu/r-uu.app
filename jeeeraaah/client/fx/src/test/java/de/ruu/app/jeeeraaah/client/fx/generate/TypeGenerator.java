package de.ruu.app.jeeeraaah.client.fx.generate;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.lib.gen.GeneratorException;
import de.ruu.lib.gen.java.bean.BeanGenerator;
import de.ruu.lib.gen.java.fx.bean.FXBeanGenerator;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class TypeGenerator
{
	public static void main(String[] args) throws IOException, GeneratorException
	{
		log.debug("create java bean class based on interface Task");
		BeanGenerator beanGenerator =
				new BeanGenerator
						(
								Task.class.getPackageName(),
								"TaskBean",
								new ClassFileImporter().importClass(Task.class)
						);

		beanGenerator.run();

		log.debug("create java fx bean class based on interface Task");
		FXBeanGenerator fxBeanGenerator =
				new FXBeanGenerator
						(
								Task.class.getPackageName(),
								"FXTask",
								new ClassFileImporter().importClass(JavaModelDemo.class),
								new ClassFileImporter().importClass(JavaModelDemoDTO.class)
						);

		fxBeanGenerator.run();

		log.debug("create java fxml for bean editor based on interface JavaModelDemo");
		FXBeanEditorFXMLGenerator fxBeanEditorFXMLGenerator =
				new FXBeanEditorFXMLGenerator
						(
								GeneratorRunner.class.getPackageName(),
								"FXBeanEditorDemo.grid.",
								new ClassFileImporter().importClass(JavaModelDemo.class)
						);

		fxBeanEditorFXMLGenerator.run();

		log.debug("create java fx component bundle");
		GeneratorFXCompBundle fxBeanEditorComponentBundleGenerator =
				new GeneratorFXCompBundle
						(
								GeneratorRunner.class.getPackageName(),
								"FXBeanEditorDemo"
						);

		fxBeanEditorComponentBundleGenerator.run();
	}
}