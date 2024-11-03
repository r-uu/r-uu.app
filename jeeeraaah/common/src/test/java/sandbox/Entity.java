package sandbox;

import java.io.Serializable;

public interface Entity<I extends Serializable>
{
	I id();

	class EntityAbstract implements Entity<Long>
	{
		@Override public Long id() { return 0L; }
	}
}