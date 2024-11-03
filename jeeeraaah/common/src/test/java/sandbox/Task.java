package sandbox;

import sandbox.TaskGroup.TaskGroupAbstract;
import sandbox.TaskGroup.TaskGroupEntity;
import sandbox.TaskGroup.TaskGroupEntityDTO;

public interface Task<TG extends TaskGroup<T>, T extends Task<TG, T>>
{
	T  parent();
	TG group ();

	abstract class TaskAbstract implements Task<TaskGroupAbstract, TaskAbstract>
	{
		@Override public TaskAbstract      parent() { return null; }
		@Override public TaskGroupAbstract group () { return null; }
	}

	interface TaskEntity<TG extends TaskGroupEntity<    T>,
	                     T  extends TaskEntity     <TG, T>>
			extends Task<TaskGroup<T>, T>, Entity<Long> { }

	class TaskEntityDTO implements TaskEntity<TaskGroupEntityDTO, TaskEntityDTO>
	{
		@Override public Long id() { return 0L; }
		@Override public TaskEntityDTO parent() { return null; }
		@Override public TaskGroupEntityDTO group() { return null; }
	}
}