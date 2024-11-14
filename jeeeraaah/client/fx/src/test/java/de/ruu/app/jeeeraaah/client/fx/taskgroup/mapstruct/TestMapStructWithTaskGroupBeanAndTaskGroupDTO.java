package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
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
		bean.description("description");

		// mapping
		TaskGroupEntityDTO dto = bean.toSource();

		assertIs(bean, dto);

		// re-mapping
		TaskGroupBean bean1 = Mapper.INSTANCE.map(dto);

		assertThat(bean, is(bean1));
	}

	@Test void testWithTasks()
	{
		TaskGroupBean bean = createTaskGroup();
		createTasks(bean, 3).forEach(bean::addTask);

		// mapping
		TaskGroupEntityDTO dto = bean.toSource();

		assertIs(bean, dto);

		// re-mapping
		TaskGroupBean bean1 = Mapper.INSTANCE.map(dto);

		assertThat(bean, is(bean1));
	}

	private void assertIs(TaskGroupBean bean, TaskGroupEntityDTO dto)
	{
		assertThat(bean.id               (), is(dto.id               ()));
		assertThat(bean.version          (), is(dto.version          ()));
		assertThat(bean.name             (), is(dto.name             ()));
		assertThat(bean.description      (), is(dto.description      ()));
		assertThat(bean.tasks().isPresent(), is(dto.tasks().isPresent()));

		bean.tasks().ifPresent(ts -> assertThat(ts.size(), is(dto.tasks().get().size())));
	}

	private TaskGroupBean  createTaskGroup() { return new TaskGroupBean("task group name"); }
	private TaskBean       createTask     (TaskGroupBean taskGroup, String name) { return new TaskBean(taskGroup, name); }
	private List<TaskBean> createTasks    (TaskGroupBean taskGroup, int count)
	{
		List<TaskBean> result = new ArrayList<>();
		for (int i = 0; i < count; i++) result.add(createTask(taskGroup, "task " + i));
		return result;
	}
}