package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.TaskGroup;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TestMapStructWithTaskGroupBeanAndTaskGroupDTO
{
	@Test void testStandalone()
	{
		TaskGroupBean bean = createTaskGroup();

		// mapping
		TaskGroupDTO dto = bean.toSource();

		assertIs(bean, dto);
	}

	@Test void testWithTasks()
	{
		TaskGroupBean bean = createTaskGroup();
		createTasks(bean, 3).forEach(bean::addTask);

		// mapping
		TaskGroupDTO dto = bean.toSource();

		assertIs(bean, dto);
	}

	private void assertIs(TaskGroupBean bean, TaskGroupDTO dto)
	{
		assertThat(bean.name             (), is(dto.name             ()));
		assertThat(bean.description      (), is(dto.description      ()));
		assertThat(bean.tasks().isPresent(), is(dto.tasks().isPresent()));

		bean.tasks().ifPresent(ts -> assertThat(ts.size(), is(dto.tasks().get().size())));
	}

	private TaskGroupBean createTaskGroup() { return new TaskGroupBean("task group name"); }
	private TaskBean      createTask(TaskGroup<?> taskGroup, String name)
	{
		TaskBean result = new TaskBean((TaskGroupBean) taskGroup);
		result.name(name);
		return result;
	}
	private List<TaskBean> createTasks(TaskGroupBean taskGroup, int count)
	{
		List<TaskBean> result = new ArrayList<>();
		for (int i = 0; i < count; i++) result.add(createTask(taskGroup, "task " + i));
		return result;
	}
}