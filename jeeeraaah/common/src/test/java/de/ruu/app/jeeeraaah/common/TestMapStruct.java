package de.ruu.app.jeeeraaah.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
class TestMapStruct
{
	@Test void testStandaloneDTO()
	{
		TaskDTO dto = new TaskDTO("name");

		dto
				.description("description")
				.startEstimated(LocalDate.now())
				.finishEstimated(LocalDate.now())
				.effortEstimated(Duration.ZERO)
				.startActual(LocalDate.now())
				.finishActual(LocalDate.now())
				.effortActual(Duration.ZERO)
		;

		log.debug("dto\n{}", dto);

		TaskEntity entity = dto.toSource();

		log.debug("entity\n{}", entity);

		assertThat(dto.name           (), is(entity.name           ()));
		assertThat(dto.description    (), is(entity.description    ()));
		assertThat(dto.startEstimated (), is(entity.startEstimated ()));
		assertThat(dto.finishEstimated(), is(entity.finishEstimated()));
		assertThat(dto.effortEstimated(), is(entity.effortEstimated()));
		assertThat(dto.startActual    (), is(entity.startActual    ()));
		assertThat(dto.finishActual   (), is(entity.finishActual   ()));
		assertThat(dto.effortActual   (), is(entity.effortActual   ()));
	}

	@Test void testDTOWithNonSelfParent()
	{
		TaskDTO dto = new TaskDTO("child");

		String parentName = "non self parent";

		dto.parent(new TaskDTO(parentName));

		TaskEntity entity = dto.toSource();

		assertThat(entity.parent().isPresent(), is(true));
		assertThat(entity.parent().get().name(), is(parentName));
		assertThat(entity.parent().get().children().isPresent(), is(true));
		assertThat(entity.parent().get().children().get().contains(entity), is(true));
	}

	@Test void testDTOWithSelfParent()
	{
		TaskDTO dto = new TaskDTO("parent and child");

		dto.parent(dto);

		TaskEntity entity = dto.toSource();

		assertThat(entity.parent().isPresent(), is(true));
		assertThat(entity.parent(), is(entity));
	}
}