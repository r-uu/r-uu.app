package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TestMapStructWithTaskGroupFXBeanAndTaskGroupBean
{
	@Test void testStandalone()
	{
		TaskGroupBean bean = createTaskGroup();
		bean.description("description");

		TaskGroupFXBean fxBean = bean.toFXTarget();

		// mapping
		TaskGroupBean   bean1  = fxBean.toFXSource();

		assertIs(bean, fxBean);

		// re-mapping
		TaskGroupFXBean taskGroupFXBean1 = bean1.toFXTarget();

		assertThat(fxBean, is(taskGroupFXBean1));
	}

	@Test void testWithTasks()
	{
		TaskGroupBean   bean   = createTaskGroup();
		TaskGroupFXBean fxBean = bean.toFXTarget();

		createTasks(bean, 3).forEach(t -> fxBean.addTask(t.toFXTarget()));

		// mapping
		TaskGroupBean bean1 = fxBean.toFXSource();

		assertIs(bean , fxBean);
		assertIs(bean1, fxBean);

		// re-mapping
		TaskGroupFXBean fxBean1 = bean1.toFXTarget();

		assertIs(bean, fxBean1);
	}

	private void assertIs(TaskGroupBean bean, TaskGroupFXBean fxBean)
	{
		assertThat(bean.id               (), is(fxBean.id               ()));
		assertThat(bean.version          (), is(fxBean.version          ()));
		assertThat(bean.name             (), is(fxBean.name             ()));
		assertThat(bean.description      (), is(fxBean.description      ()));
		assertThat(bean.tasks().isPresent(), is(fxBean.tasks().isPresent()));

		bean.tasks().ifPresent(ts -> assertThat(ts.size(), is(fxBean.tasks().get().size())));
	}

	private TaskGroupBean createTaskGroup() { return new TaskGroupBean("task group name"); }
	private TaskBean      createTask(TaskGroupBean taskGroup, String name)
	{
		TaskBean result = new TaskBean((TaskGroupBean) taskGroup, name);
		return result;
	}
	private List<TaskBean> createTasks(TaskGroupBean taskGroup, int count)
	{
		List<TaskBean> result = new ArrayList<>();
		for (int i = 0; i < count; i++) result.add(createTask(taskGroup, "task " + i));
		return result;
	}
}