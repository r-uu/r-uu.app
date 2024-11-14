package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TestMapStructWithTaskBeanAndTaskDTO
{
	@Test
	void testStandaloneTaskBean()
	{
		TaskBean bean = createTask(createTaskGroup(), "task name");

		// mapping
		TaskEntityDTO dto = bean.toSource();

		assertIs(bean, dto);

		// re-mapping
		TaskBean bean1 = Mapper.INSTANCE.map(dto);

		assertThat(bean, is(bean1));
	}

	@Test void testWithTasks()
	{
		int count = 3;
		TaskGroupBean taskGroupBean = createTaskGroup();

		TaskBean taskBean       = createTask(taskGroupBean, "the task");
		TaskBean taskBeanParent = createTask(taskGroupBean, "task's parent");

		taskBean.parent(taskBeanParent);

		createTasks(taskGroupBean, 3, "child "      ).forEach(taskBean::addChild);
		createTasks(taskGroupBean, 3, "predecessor ").forEach(taskBean::addPredecessor);
		createTasks(taskGroupBean, 3, "successor "  ).forEach(taskBean::addSuccessor);

		// mapping
		TaskEntityDTO dto = taskBean.toSource();

		assertIs(taskBean, dto);

		// re-mapping
		TaskBean taskBean1 = Mapper.INSTANCE.map(dto);

		assertThat(taskBean, is(taskBean1));
	}

	private void assertIs(TaskBean taskBean, TaskEntityDTO taskDTO)
	{
		assertThat(taskDTO.name           (), is(taskBean.name           ()));
		assertThat(taskDTO.description    (), is(taskBean.description    ()));
		assertThat(taskDTO.startEstimated (), is(taskBean.startEstimated ()));
		assertThat(taskDTO.finishEstimated(), is(taskBean.finishEstimated()));
		assertThat(taskDTO.startActual    (), is(taskBean.startActual    ()));
		assertThat(taskDTO.finishActual   (), is(taskBean.finishActual   ()));
		assertThat(taskDTO.effortEstimated(), is(taskBean.effortEstimated()));
		assertThat(taskDTO.effortActual   (), is(taskBean.effortActual   ()));

		assertThat(taskBean.parent      ().isPresent(), is(taskDTO.parent      ().isPresent()));
		assertThat(taskBean.children    ().isPresent(), is(taskDTO.children    ().isPresent()));
		assertThat(taskBean.predecessors().isPresent(), is(taskDTO.predecessors().isPresent()));
		assertThat(taskBean.successors  ().isPresent(), is(taskDTO.successors  ().isPresent()));

		taskBean.children    ().ifPresent(ts -> assertThat(ts.size(), is(taskDTO.children    ().get().size())));
		taskBean.predecessors().ifPresent(ts -> assertThat(ts.size(), is(taskDTO.predecessors().get().size())));
		taskBean.successors  ().ifPresent(ts -> assertThat(ts.size(), is(taskDTO.successors  ().get().size())));
	}

	private TaskGroupBean createTaskGroup() { return new TaskGroupBean("task group name"); }
	private TaskBean      createTask(TaskGroupBean taskGroup, String name)
	{
		TaskBean result = new TaskBean(taskGroup, name);
		result
				.description    ("description")
				.startEstimated (LocalDate.now())
				.startActual    (LocalDate.now())
				.finishEstimated(LocalDate.now())
				.finishActual   (LocalDate.now())
				.effortEstimated(Duration.ZERO)
				.effortActual   (Duration.ZERO);
		return result;
	}
	private List<TaskBean> createTasks(TaskGroupBean taskGroupBean, int count, String namePrefix)
	{
		List<TaskBean> result = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			result.add(createTask(taskGroupBean, namePrefix + i));
		}
		return result;
	}
}