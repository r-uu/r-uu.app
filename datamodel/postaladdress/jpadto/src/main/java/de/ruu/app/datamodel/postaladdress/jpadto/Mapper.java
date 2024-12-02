package de.ruu.app.datamodel.postaladdress.jpadto;

import de.ruu.app.datamodel.postaladdress.dto.PostalAddressDTO;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

@Slf4j
@org.mapstruct.Mapper
public abstract class Mapper
{
	public  static final Mapper INSTANCE = Mappers.getMapper(Mapper.class);

	private static final ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull PostalAddressEntity map(@NonNull PostalAddressDTO    dto   );
	public abstract @NonNull PostalAddressDTO    map(@NonNull PostalAddressEntity entity);

	@BeforeMapping public void beforeMapping(@NonNull PostalAddressDTO source, @NonNull @MappingTarget PostalAddressEntity target)
	{
		log.debug("before source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@BeforeMapping public void beforeMapping(@NonNull PostalAddressEntity source, @NonNull @MappingTarget PostalAddressDTO target)
	{
		log.debug("before source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@AfterMapping public void afterMapping(@NonNull PostalAddressDTO source, @NonNull @MappingTarget PostalAddressEntity target)
	{
		log.debug("after source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@AfterMapping public void afterMapping(@NonNull PostalAddressEntity source, @NonNull @MappingTarget PostalAddressDTO target)
	{
		log.debug("after source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@ObjectFactory
	@NonNull
	PostalAddressEntity lookupOrCreate(@NonNull PostalAddressDTO input)
	{
		PostalAddressEntity result = CONTEXT.get(input, PostalAddressEntity.class);
		if (result == null)
		{
			result = new PostalAddressEntity(input.street(), input.streetNumber(), input.city(), input.postalCode(), input.country());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	PostalAddressDTO lookupOrCreate(@NonNull PostalAddressEntity input)
	{
		PostalAddressDTO result = CONTEXT.get(input, PostalAddressDTO.class);
		if (result == null)
		{
			result = new PostalAddressDTO(input.street(), input.streetNumber(), input.city(), input.postalCode(), input.country());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}