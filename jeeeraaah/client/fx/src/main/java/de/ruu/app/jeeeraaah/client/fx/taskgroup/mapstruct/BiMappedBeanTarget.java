package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import lombok.NonNull;

/**
 * Interface for types that can be mapped to {@code S}.
 *
 * @param <S> {@link BiMappedFXSource} type
 *
 * @author r-uu
 */
public interface BiMappedBeanTarget<S extends BiMappedBeanSource<?>>
{
	/**
	 * map values from {@code input} into private / protected fields of {@code this} {@link BiMappedBeanTarget}
	 * @param input
	 */
	void beforeMapping(@NonNull S input);
	void afterMapping (@NonNull S input);

	@NonNull S toBeanSource();
}