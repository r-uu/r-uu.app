package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import lombok.NonNull;

/**
 * Interface for types that can be mapped to {@code T}.
 *
 * @param <T> {@link BiMappedFXTarget} type
 *
 * @author r-uu
 */
public interface BiMappedFXSource<T extends BiMappedFXTarget<?>>
{
	/**
	 * map values from {@code input} into private / protected fields of {@code this} {@link BiMappedFXSource}
	 * @param input
	 */
	void beforeMapping(@NonNull T input);
	void afterMapping (@NonNull T input);

	@NonNull T toFXTarget();
}