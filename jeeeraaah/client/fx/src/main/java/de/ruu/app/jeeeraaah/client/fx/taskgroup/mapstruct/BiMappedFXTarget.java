package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import lombok.NonNull;

/**
 * Interface for types that can be mapped to {@code S}.
 *
 * @param <S> {@link BiMappedFXSource} type
 *
 * @author r-uu
 */
public interface BiMappedFXTarget<S extends BiMappedFXSource<?>>
{
	/**
	 * map values from {@code input} into private / protected fields of {@code this} {@link BiMappedFXTarget}
	 * @param input
	 */
	void beforeMapping(@NonNull S input);
	void afterMapping (@NonNull S input);

	@NonNull S toFXSource();
}