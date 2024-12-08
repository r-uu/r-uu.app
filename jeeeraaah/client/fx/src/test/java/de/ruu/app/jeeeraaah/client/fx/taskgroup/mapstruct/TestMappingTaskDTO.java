package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TestMappingTaskDTO
{
	@Test void dtoStandalone()
	{
		String  name = "name";
		TaskDTO dto  = createDTO(createGroupDTO(), name);

		assertThat(dto.name     ()            , is(name ));
		assertThat(dto.parent   ().isPresent(), is(false));
		assertThat(dto.parentDTO().isPresent(), is(false));
		assertThat(dto.children ().isPresent(), is(false));
	}

	@Test void dtoStandaloneMapped()
	{
		TaskDTO  dto    = createDTO(createGroupDTO(), "name");
		TaskBean mapped = dto.toBean();

		assertThat(dto, is(mapped));
	}

	@Test void dtoStandaloneReMapped()
	{
		TaskDTO    dto    = createDTO(createGroupDTO(), "name");
		TaskBean   mapped = dto   .toBean();
		TaskDTO  reMapped = mapped.toDTO();

		assertThat(dto, is(reMapped));
	}

	private TaskGroupDTO createGroupDTO() { return new TaskGroupDTO("name"); }
	private TaskDTO      createDTO(TaskGroupDTO group, String name)
	{
		TaskDTO result = new TaskDTO(group, name );
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
	private List<TaskDTO> createDTOs(TaskGroupDTO taskGroupBean, int count, String namePrefix)
	{
		List<TaskDTO> result = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			result.add(createDTO(taskGroupBean, namePrefix + i));
		}
		return result;
	}

	private void assertIs(TaskEntityDTO taskDTO, TaskBean taskBean)
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
}