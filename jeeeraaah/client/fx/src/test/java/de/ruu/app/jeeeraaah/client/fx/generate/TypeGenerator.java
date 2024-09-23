package de.ruu.app.jeeeraaah.client.fx.generate;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.lib.gen.GeneratorException;
import de.ruu.lib.gen.java.bean.BeanGenerator;
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

//		log.debug("create java fx bean class based on interface Task");
//		FXBeanGenerator fxBeanGenerator =
//				new FXBeanGenerator
//						(
//								Task.class.getPackageName(),
//								"FXTask",
//								new ClassFileImporter().importClass(Task.class),
//								new ClassFileImporter().importClass(TaskDTO.class)
//						);
//
//		fxBeanGenerator.run();
//
//		log.debug("create java fxml for bean editor based on interface Task");
//		FXBeanEditorFXMLGenerator fxBeanEditorFXMLGenerator =
//				new FXBeanEditorFXMLGenerator
//						(
//								Task.class.getPackageName(),
//								Task.class.getSimpleName() + "FXBeanEditorDemo",
//								new ClassFileImporter().importClass(Task.class)
//						);
//
//		fxBeanEditorFXMLGenerator.run();
//
//		log.debug("create java fx component bundle");
//		GeneratorFXCompBundle fxBeanEditorComponentBundleGenerator =
//				new GeneratorFXCompBundle
//						(
//								Task.class.getPackageName(),
//								"FXBeanEditorDemo"
//						);
//
//		fxBeanEditorComponentBundleGenerator.run();
	}
}