package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static de.ruu.lib.util.BooleanFunctions.not;

@EqualsAndHashCode(callSuper = true)
@ToString         (callSuper = true)
public class TaskGroupDTO extends TaskGroupEntityDTO
{
	///////////////
	// constructors
	///////////////

	/**
	 * provide hand-made required args constructor to guarantee usage of hand made accessors.
	 * @param name non-empty name
	 */
	public TaskGroupDTO(@NonNull String name) { super(name); }

	///////////////////////
	// additional accessors
	///////////////////////

	public Optional<Set<TaskDTO>> taskDTOs()
	{
		if (not(super.tasks().isPresent())) return Optional.empty();

		Set<TaskDTO> result = new HashSet<>();
		super.tasks().get().forEach(t -> result.add(Map_Task_EntityDTO_DTO.INSTANCE.map(t)));
		return Optional.of(result);
	}

	////////////////////////
	// relationship handling
	////////////////////////

	// --- none ---

	///////////
	// mappings
	///////////

	public @NonNull TaskGroupBean      toBean     () { return Map_TaskGroup_DTO_Bean     .INSTANCE.map(this); }
	public @NonNull TaskGroupEntityDTO toEntityDTO() { return Map_TaskGroup_EntityDTO_DTO.INSTANCE.map(this); }

	//////////////////////
	// mapstruct callbacks
	//////////////////////

	/** {@link TaskGroupEntityDTO} -> {@link TaskGroupDTO} */
	@BeforeMapping void beforeMapping(@NonNull TaskGroupEntityDTO source)
	{
		super.beforeMapping(source); // maps id and version

		if (source.tasks().isPresent())
		{
			for (TaskEntityDTO task : source.tasks().get())
			{
				addTask(Map_Task_EntityDTO_DTO.INSTANCE.map(task));
			}
		}
		// mapping of other fields is done via mapstruct using java-beans accessors
	}

	/** {@link TaskGroupEntityDTO} -> {@link TaskGroupDTO} */
	@AfterMapping void afterMapping(@NonNull TaskGroupEntityDTO source) { }

	/** {@link TaskGroupBean} -> {@link TaskGroupDTO} */
	@BeforeMapping void beforeMapping(@NonNull TaskGroupBean source)
	{
		super.beforeMapping(source); // maps id and version

		if (source.tasks().isPresent())
		{
			for (TaskBean task : source.tasks().get())
			{
				addTask(Map_Task_Bean_DTO.INSTANCE.map(task));
			}
		}
		// mapping of other fields is done via mapstruct using java-beans accessors
	}

	/** {@link TaskGroupBean} -> {@link TaskGroupDTO} */
	@AfterMapping void afterMapping(@NonNull TaskGroupBean source) { }
}