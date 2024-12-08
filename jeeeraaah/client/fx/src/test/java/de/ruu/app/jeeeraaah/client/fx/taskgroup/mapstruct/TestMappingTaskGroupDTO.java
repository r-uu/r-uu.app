package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TestMappingTaskGroupDTO
{
	@Test void dtoStandalone()
	{
		String name = "name";
		TaskGroupDTO group = createDTO(name);

		assertThat(group.name()             , is(name));
		assertThat(group.tasks().isPresent(), is(false));
	}

	@Test void dtoStandaloneMapped()
	{
		TaskGroupDTO  dto    = createDTO("name");
		TaskGroupBean mapped = dto.toBean();

		assertIs(mapped, dto);
	}

	@Test void dtoStandaloneReMapped()
	{
		TaskGroupDTO    dto    = createDTO("name");
		TaskGroupBean   mapped = dto   .toBean();
		TaskGroupDTO  reMapped = mapped.toDTO();

		assertThat(reMapped, is(dto));
	}

	private void assertIs(TaskGroupBean bean, TaskGroupDTO dto)
	{
		assertThat(bean.id               (), is(dto.id               ()));
		assertThat(bean.version          (), is(dto.version          ()));
		assertThat(bean.name             (), is(dto.name             ()));
		assertThat(bean.description      (), is(dto.description      ()));
		assertThat(bean.tasks().isPresent(), is(dto.tasks().isPresent()));

		bean.tasks().ifPresent(ts -> assertThat(ts.size(), is(dto.tasks().get().size())));
	}

	private TaskGroupDTO  createDTO  (String name)                     { return new TaskGroupDTO("name"); }
	private TaskDTO       createTask (TaskGroupDTO group, String name) { return new TaskDTO(group, name); }
	private List<TaskDTO> createTasks(TaskGroupDTO group, int count)
	{
		List<TaskDTO> result = new ArrayList<>();
		for (int i = 0; i < count; i++) result.add(createTask(group, "task " + i));
		return result;
	}
}