package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.lib.jpa.core.AbstractEntity;
import de.ruu.lib.jpa.core.Entity;
import de.ruu.lib.mapstruct.BiMappedSource;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
public abstract class AbstractMappedBean<D extends AbstractMappedDTO<?>>
		extends AbstractEntity<D>
		implements BiMappedSource<D>
{
	/**
	 * necessary for copy constructors
	 * @param entity
	 */
	protected AbstractMappedBean(@NonNull Entity<Long> entity) { super(entity); }

	@Override public void beforeMapping(@NonNull D input) { mapIdAndVersion(input); }
}