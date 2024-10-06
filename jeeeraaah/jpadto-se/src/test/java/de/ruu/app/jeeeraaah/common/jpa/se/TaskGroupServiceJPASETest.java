package de.ruu.app.jeeeraaah.common.jpa.se;

import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.TaskGroup;
import de.ruu.app.jeeeraaah.common.jpa.TaskEntity;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntity;
import de.ruu.lib.cdi.common.CDIExtension;
import de.ruu.lib.cdi.se.CDIContainer;
import de.ruu.lib.jpa.se.TransactionalInterceptorCDI;
import de.ruu.lib.junit.DisabledOnServerNotListening;
import jakarta.enterprise.inject.spi.CDI;
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
		Set<TaskGroupEntity> all = service.findAll();

		assertThat(all, is(not(nullValue())));

		log.info("\nreceived {} companies", all.size());
	}

	@Test void testCreate()
	{
		String name = "name " + System.currentTimeMillis();

		TaskGroup taskGroup = service.create(new TaskGroupEntity(name));

		log.info("\nreceived taskGroup\n{}", taskGroup);

		if (taskGroup instanceof TaskGroupEntity)
		{
			TaskGroupEntity entity = (TaskGroupEntity) taskGroup;

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

		TaskGroup taskGroupIn = service.create(new TaskGroupEntity(name));

		if (taskGroupIn instanceof TaskGroupEntity)
		{
			TaskGroupEntity entity = (TaskGroupEntity) taskGroupIn;

			Optional<TaskGroupEntity> optional = service.read(entity.id());

			assertThat(optional            , is(not(nullValue())));
			assertThat(optional.isPresent(), is(not(false)));

			TaskGroupEntity entityOut = optional.get();

			log.info("\nreceived company\n{}" + entityOut);

			if (entityOut instanceof TaskGroupEntity)
			{
				entity = (TaskGroupEntity) entityOut;

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

		TaskGroupEntity taskGroupIn = service.create(new TaskGroupEntity(name));

		name = "modified " + System.currentTimeMillis();

		taskGroupIn.name(name);

		TaskGroup taskGroupOut = service.update(taskGroupIn);

		log.info("\nreceived task group\n{}" + taskGroupOut);

		if (taskGroupOut instanceof TaskGroupEntity)
		{
			TaskGroupEntity entity = (TaskGroupEntity) taskGroupOut;

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

		TaskGroup taskGroup = service.create(new TaskGroupEntity(name));

		log.info("\nreceived task group\n{}", taskGroup);

		if (taskGroup instanceof TaskGroupEntity)
		{
			TaskGroupEntity entity = (TaskGroupEntity) taskGroup;

			service.delete(entity.id());

			Optional<TaskGroupEntity> optional = service.read(entity.id());

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

		TaskGroupEntity taskGroup = service.create(new TaskGroupEntity(name));

		log.info("\nreceived task group\n{}", taskGroup);

		TaskEntity task = new TaskEntity(taskGroup, name);

		task = taskService.create(task);

		Optional<TaskGroupEntity> optional = service.findWithTasks(taskGroup.id());

		assertThat(optional            , is(not(nullValue())));
		assertThat(optional.isPresent(), is(true));

		taskGroup = optional.get();
		Optional<Set<Task>> optionalInner = taskGroup.tasks();

		assertThat(optionalInner            , is(not(nullValue())));
		assertThat(optionalInner.isPresent(), is(true));

		Set<Task> tasks = optionalInner.get();

		assertThat(tasks.size(), is(1));

		for (Task taskInner : tasks)
		{
			log.debug("found task {}", taskInner);
		}
	}
}