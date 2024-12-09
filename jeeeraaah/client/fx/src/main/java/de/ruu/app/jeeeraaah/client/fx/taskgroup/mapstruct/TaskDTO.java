package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ToString         (callSuper = true)
public class TaskDTO extends TaskEntityDTO
{
	///////////////
	// constructors
	///////////////

	/**
	 * provide hand-made required args constructor to guarantee usage of hand made accessors.
	 * @param name non-empty name
	 */
	public TaskDTO(@NonNull TaskGroupDTO taskGroup, @NonNull String name) { super(taskGroup, name); }

	///////////////////////
	// additional accessors
	///////////////////////

	public Optional<TaskDTO> parentDTO()
	{
		if (super.parent().isPresent())
				return Optional.of(Map_Task_EntityDTO_DTO.INSTANCE.map(super.parent().get()));
		return Optional.empty();
	}

	@Override public @NonNull TaskGroupDTO taskGroup() { return Map_TaskGroup_EntityDTO_DTO.INSTANCE.map(super.taskGroup()); }

	public @NonNull TaskBean      toBean     () { return Map_Task_DTO_Bean.INSTANCE.map(this); }
	public @NonNull TaskEntityDTO toEntityDTO() { return Map_Task_EntityDTO_DTO.INSTANCE.map(this); }

	////////////////////////
	// relationship handling
	////////////////////////

	// --- none ---

	//////////////////////
	// mapstruct callbacks
	//////////////////////

	/** {@link TaskEntityDTO} -> {@link TaskDTO} */
	public void beforeMapping(@NonNull TaskEntityDTO input)
	{
		super.beforeMapping(input); // maps id and version

		input.parent      ().ifPresent(                 this::lookupOrMapParent);
		input.children    ().ifPresent(ts -> ts.forEach(this::lookupOrMapChild));
		input.predecessors().ifPresent(ts -> ts.forEach(this::lookupOrMapPredecessor));
		input.successors  ().ifPresent(ts -> ts.forEach(this::lookupOrMapSuccessor));
		// mapping of other fields is done via mapstruct using java-beans accessors
	}

	/** {@link TaskEntityDTO} -> {@link TaskDTO} */
	public void afterMapping(@NonNull TaskEntityDTO input) { }

	/** {@link TaskBean} -> {@link TaskDTO} */
	public void beforeMapping(@NonNull TaskBean input)
	{
		super.beforeMapping(input); // maps id and version

		input.parent      ().ifPresent(                 this::lookupOrMapParent);
		input.children    ().ifPresent(ts -> ts.forEach(this::lookupOrMapChild));
		input.predecessors().ifPresent(ts -> ts.forEach(this::lookupOrMapPredecessor));
		input.successors  ().ifPresent(ts -> ts.forEach(this::lookupOrMapSuccessor));
		// mapping of other fields is done via mapstruct using java-beans accessors
	}

	/** {@link TaskBean} -> {@link TaskDTO} */
	public void afterMapping(@NonNull TaskBean input) { }

	////////////////////////////////////
	// lookup or map DTOs for EntityDTOs
	////////////////////////////////////

	private void lookupOrMapParent(@NonNull TaskEntityDTO parent)
	{
		Optional<TaskDTO> optionalParent = ObjectDictionaryAndFactory.INSTANCE.lookupTaskDTO(parent);
		optionalParent.ifPresentOrElse
				(
						(p) -> parent(p),
						( ) -> parent(Map_Task_EntityDTO_DTO.INSTANCE.map(parent))
				);
	}

	private void lookupOrMapChild(@NonNull TaskEntityDTO child)
	{
		Optional<TaskDTO> optionalChild = ObjectDictionaryAndFactory.INSTANCE.lookupTaskDTO(child);
		optionalChild.ifPresentOrElse
				(
						(c) -> addChild(c),
						( ) -> addChild(Map_Task_EntityDTO_DTO.INSTANCE.map(child))
				);
	}

	private void lookupOrMapPredecessor(@NonNull TaskEntityDTO predecessor)
	{
		Optional<TaskDTO> optionalPredecessor = ObjectDictionaryAndFactory.INSTANCE.lookupTaskDTO(predecessor);
		optionalPredecessor.ifPresentOrElse
				(
						(p) -> addPredecessor(p),
						( ) -> addPredecessor(Map_Task_EntityDTO_DTO.INSTANCE.map(predecessor))
				);
	}

	private void lookupOrMapSuccessor(@NonNull TaskEntityDTO successor)
	{
		Optional<TaskDTO> optionalPredecessor = ObjectDictionaryAndFactory.INSTANCE.lookupTaskDTO(successor);
		optionalPredecessor.ifPresentOrElse
				(
						(s) -> addSuccessor(s),
						( ) -> addSuccessor(Map_Task_EntityDTO_DTO.INSTANCE.map(successor))
				);
	}

	///////////////////////////////
	// lookup or map DTOs for beans
	///////////////////////////////

	private void lookupOrMapParent(@NonNull TaskBean parent)
	{
		Optional<TaskDTO> optionalParent = ObjectDictionaryAndFactory.INSTANCE.lookupTaskDTO(parent);
		optionalParent.ifPresentOrElse
				(
						(p) -> parent(p),
						( ) -> parent(Map_Task_Bean_DTO.INSTANCE.map(parent))
				);
	}

	private void lookupOrMapChild(@NonNull TaskBean child)
	{
		Optional<TaskDTO> optionalChild = ObjectDictionaryAndFactory.INSTANCE.lookupTaskDTO(child);
		optionalChild.ifPresentOrElse
				(
						(c) -> addChild(c),
						( ) -> addChild(Map_Task_Bean_DTO.INSTANCE.map(child))
				);
	}

	private void lookupOrMapPredecessor(@NonNull TaskBean predecessor)
	{
		Optional<TaskDTO> optionalPredecessor = ObjectDictionaryAndFactory.INSTANCE.lookupTaskDTO(predecessor);
		optionalPredecessor.ifPresentOrElse
				(
						(p) -> addPredecessor(p),
						( ) -> addPredecessor(Map_Task_Bean_DTO.INSTANCE.map(predecessor))
				);
	}

	private void lookupOrMapSuccessor(@NonNull TaskBean successor)
	{
		Optional<TaskDTO> optionalPredecessor = ObjectDictionaryAndFactory.INSTANCE.lookupTaskDTO(successor);
		optionalPredecessor.ifPresentOrElse
				(
						(s) -> addSuccessor(s),
						( ) -> addSuccessor(Map_Task_Bean_DTO.INSTANCE.map(successor))
				);
	}
}