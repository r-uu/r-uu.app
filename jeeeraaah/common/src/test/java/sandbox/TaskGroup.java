package sandbox;

import sandbox.Task.TaskAbstract;
import sandbox.Task.TaskEntity;
import sandbox.Task.TaskEntityDTO;

import java.util.Set;

public interface TaskGroup<T extends Task<? extends TaskGroup<T>, T>>
{
	Set<T> tasks();

	abstract class TaskGroupAbstract implements TaskGroup<TaskAbstract>
	{
		@Override public Set<TaskAbstract> tasks() { return Set.of(); }
	}

	interface TaskGroupEntity<T extends TaskEntity<? extends TaskGroupEntity<T>, T>>
			extends TaskGroup<T>, Entity<Long> { }

	class TaskGroupEntityDTO implements TaskGroupEntity<TaskEntityDTO>, TaskGroup<TaskEntityDTO>
	{
		@Override public Long id() { return 0L; }
		@Override public Set<TaskEntityDTO> tasks() { return Set.of(); }
	}
}