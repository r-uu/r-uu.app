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

		TaskGroupFXBean fxBean   = MapperFX.INSTANCE.map(bean); // mapping
		TaskGroupBean   remapped = fxBean.toFXSource();         // re-mapping

		assertIs  (bean  , fxBean      );
		assertThat(fxBean, is(remapped));
	}

	@Test void testWithTasks()
	{
		TaskGroupBean   bean   = createTaskGroup();
		TaskGroupFXBean fxBean = MapperFX.INSTANCE.map(bean);     // mapping

		createTasks(bean, 3).forEach(t -> fxBean.addTask(MapperFX.INSTANCE.map(t)));

		TaskGroupBean mapped = fxBean.toFXSource();               // re-mapping

		assertIs(bean  , fxBean);
		assertIs(mapped, fxBean);

		TaskGroupFXBean reMapped = MapperFX.INSTANCE.map(mapped); // re-re-mapping

		assertIs(bean, reMapped);
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