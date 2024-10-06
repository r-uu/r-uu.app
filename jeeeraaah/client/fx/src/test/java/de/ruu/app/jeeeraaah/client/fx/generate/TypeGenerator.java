package de.ruu.app.jeeeraaah.client.fx.generate;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.dto.TaskDTO;
import de.ruu.lib.gen.GeneratorException;
import de.ruu.lib.gen.java.bean.BeanGenerator;
import de.ruu.lib.gen.java.fx.bean.FXBeanGenerator;
import de.ruu.lib.gen.java.fx.bean.editor.FXBeanEditorFXMLGenerator;
import de.ruu.lib.gen.java.fx.comp.GeneratorFXCompBundle;
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
								"TaskFXBean",
								new ClassFileImporter().importClass(Task.class),
								new ClassFileImporter().importClass(TaskDTO.class)
						);

		fxBeanGenerator.run();

		log.debug("create java fxml for bean editor based on interface Task");
		FXBeanEditorFXMLGenerator fxBeanViewFXMLGenerator =
				new FXBeanEditorFXMLGenerator
						(
								Task.class.getPackageName() + ".view",
								Task.class.getSimpleName() + "View",
								new ClassFileImporter().importClass(Task.class)
						);

		fxBeanViewFXMLGenerator.run();

		log.debug("create java fx component bundle for view");
		GeneratorFXCompBundle fxBeanViewComponentBundleGenerator =
				new GeneratorFXCompBundle
						(
								Task.class.getPackageName() + ".view",
								Task.class.getSimpleName() + "View"
						);

		fxBeanViewComponentBundleGenerator.run();

		log.debug("create java fx component bundle for editor");
		GeneratorFXCompBundle fxBeanEditorComponentBundleGenerator =
				new GeneratorFXCompBundle
						(
								Task.class.getPackageName() + ".editor",
								Task.class.getSimpleName() + "Editor"
						);

		fxBeanEditorComponentBundleGenerator.run();
	}
}