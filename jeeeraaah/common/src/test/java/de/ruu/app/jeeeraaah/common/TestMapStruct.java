package de.ruu.app.jeeeraaah.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
class TestMapStruct
{
	@Test void test()
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
				.predecessors(List.of(dto))
				.successors(List.of(dto))
				.parent(dto)
				.children(List.of(dto))
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
}
