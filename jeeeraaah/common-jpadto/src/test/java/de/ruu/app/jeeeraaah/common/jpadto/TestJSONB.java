package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.jsonb.JsonbConfigurator;
import jakarta.json.bind.Jsonb;

import java.util.HashSet;
import java.util.Set;

public class TestJSONB
{
	private final static int NUMBER_OF_TASK_GROUPS = 3;
	private final static int NUMBER_OF_TASKS_PER_TASK_GROUP = 3;

	private Jsonb getContext()
	{
		return new JsonbConfigurator().getContext();
	}

	private Set<TaskGroupEntityDTO> createTestData()
	{
		Set<TaskGroupEntityDTO> result = new HashSet<>();
		for (int i = 0; i < NUMBER_OF_TASK_GROUPS; i++)
		{
			TaskGroupEntityDTO taskGroup = new TaskGroupEntityDTO("taskgroup." + i);
			for (int j = 0; j < NUMBER_OF_TASKS_PER_TASK_GROUP; j++)
			{
				taskGroup.addTask(new TaskEntityDTO(taskGroup, "task." + 1 + "." + j));
			}
			result.add(taskGroup);
		}
		return result;
	}
}