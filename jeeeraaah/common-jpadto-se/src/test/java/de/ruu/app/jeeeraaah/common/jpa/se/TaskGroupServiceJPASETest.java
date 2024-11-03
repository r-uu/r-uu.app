package de.ruu.app.jeeeraaah.common.jpa.se;

import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.TaskGroup;
import de.ruu.app.jeeeraaah.common.jpa.TaskEntityJPA;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntityJPA;
import de.ruu.lib.cdi.common.CDIExtension;
import de.ruu.lib.cdi.se.CDIContainer;
import de.ruu.lib.jpa.se.TransactionalInterceptorCDI;
import de.ruu.lib.junit.DisabledOnServerNotListening;
import jakarta.enterprise.inject.spi.CDI;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisabledOnServerNotListening(propertyNameHost = "database.host", propertyNamePort = "database.port")
@Slf4j
class TaskGroupServiceJPASETest
{
	private TaskGroupServiceJPASE service; // initialisation handled in before each method

	@SuppressWarnings("unchecked")
	@BeforeAll static void beforeAll()
	{
		log.debug("cdi container initialisation");
		CDIContainer.bootstrap
				(
						  TaskGroupServiceJPASETest.class.getClassLoader()
						, List.of(TransactionalInterceptorCDI.class)
						, List.of(CDIExtension.class)
						, List.of(EntityManagerProducer.class, TransactionalInterceptorCDI.class)
				);
	}

	@AfterAll static void afterAll() { }

	@BeforeEach      void beforeEach()
	{
		service = CDI.current().select(TaskGroupServiceJPASE.class).get();
	}

	@Test void testFindAll()
	{
		Set<TaskGroupEntityJPA> all = service.findAll();

		assertThat(all, is(not(nullValue())));

		log.info("\nreceived {} companies", all.size());
	}

	@Test void testCreate()
	{
		String name = "name " + System.currentTimeMillis();

		TaskGroup taskGroup = service.create(new TaskGroupEntityJPA(name));

		log.info("\nreceived taskGroup\n{}", taskGroup);

		if (taskGroup instanceof TaskGroupEntityJPA)
		{
			TaskGroupEntityJPA entity = (TaskGroupEntityJPA) taskGroup;

			assertThat(entity.id  (), is(not(nullValue())));
			assertThat(entity.name(), is(name));

			assertThat(entity.version(), is(not(nullValue())));
			assertThat(entity.version(), is((short) 0       ));
		}
		else
		{
			fail("unexpected type: " + taskGroup.getClass().getName());
		}
	}

	@Test void testRead()
	{
		String name = "name " + System.currentTimeMillis();

		TaskGroup taskGroupIn = service.create(new TaskGroupEntityJPA(name));

		if (taskGroupIn instanceof TaskGroupEntityJPA)
		{
			TaskGroupEntityJPA entity = (TaskGroupEntityJPA) taskGroupIn;

			Optional<TaskGroupEntityJPA> optional = service.read(entity.id());

			assertThat(optional            , is(not(nullValue())));
			assertThat(optional.isPresent(), is(not(false)));

			TaskGroupEntityJPA entityOut = optional.get();

			log.info("\nreceived company\n{}" + entityOut);

			if (entityOut instanceof TaskGroupEntityJPA)
			{
				entity = (TaskGroupEntityJPA) entityOut;

				assertThat(entity.id  (), is(not(nullValue())));
				assertThat(entity.name(), is(name));

				assertThat(entity.version(), is(not(nullValue())));
				assertThat(entity.version(), is((short) 0       ));
			}
			else
			{
				fail("unexpected type: " + entityOut.getClass().getName());
			}
		}
	}

	@Test void testUpdate()
	{
		String name = "name " + System.currentTimeMillis();

		TaskGroupEntityJPA taskGroupIn = service.create(new TaskGroupEntityJPA(name));

		name = "modified " + System.currentTimeMillis();

		taskGroupIn.name(name);

		TaskGroup taskGroupOut = service.update(taskGroupIn);

		log.info("\nreceived task group\n{}" + taskGroupOut);

		if (taskGroupOut instanceof TaskGroupEntityJPA)
		{
			TaskGroupEntityJPA entity = (TaskGroupEntityJPA) taskGroupOut;

			assertThat(entity.id(), is(not(nullValue())));

			assertThat(entity.version(), is(not(nullValue())));
			assertThat(entity.version(), is((short) 1       ));

			assertThat(entity.name(), is(name));
		}
		else
		{
			fail("unexpected type: " + taskGroupOut.getClass().getName());
		}
	}

	@Test void testDelete()
	{
		String name = "name " + System.currentTimeMillis();

		TaskGroup taskGroup = service.create(new TaskGroupEntityJPA(name));

		log.info("\nreceived task group\n{}", taskGroup);

		if (taskGroup instanceof TaskGroupEntityJPA)
		{
			TaskGroupEntityJPA entity = (TaskGroupEntityJPA) taskGroup;

			service.delete(entity.id());

			Optional<TaskGroupEntityJPA> optional = service.read(entity.id());

			assertThat(optional            , is(not(nullValue())));
			assertThat(optional.isPresent(), is(false));
		}
		else
		{
			fail("unexpected type: " + taskGroup.getClass().getName());
		}
	}

	@Test void testFindWithTasks()
	{
		TaskServiceJPASE taskService = CDI.current().select(TaskServiceJPASE.class).get();

		String name = "name " + System.currentTimeMillis();

		TaskGroupEntityJPA taskGroup = service.create(new TaskGroupEntityJPA(name));

		log.info("\nreceived task group\n{}", taskGroup);

		TaskEntityJPA task = new TaskEntityJPA(taskGroup, name);

		task = taskService.create(task);

		Optional<TaskGroupEntityJPA> optional = service.findWithTasks(taskGroup.id());

		assertThat(optional            , is(not(nullValue())));
		assertThat(optional.isPresent(), is(true));

		taskGroup = optional.get();
		@NonNull Optional<Set<TaskEntityJPA>> optionalInner = taskGroup.tasks();

		assertThat(optionalInner            , is(not(nullValue())));
		assertThat(optionalInner.isPresent(), is(true));

		Set<? extends Task> tasks = optionalInner.get();

		assertThat(tasks.size(), is(1));

		for (Task taskInner : tasks)
		{
			log.debug("found task {}", taskInner);
		}
	}
}