package de.ruu.app.datamodel.postaladdress.jpadto;

import de.ruu.app.datamodel.postaladdress.dto.PostalAddressDTO;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Slf4j
@org.mapstruct.Mapper
public abstract class Mapper
{
	public static final Mapper INSTANCE = Mappers.getMapper(Mapper.class);

	private static ReferenceCycleTracking CONTEXT  = new ReferenceCycleTracking();

	public abstract PostalAddressEntity map(PostalAddressDTO    dto   );
	public abstract PostalAddressDTO    map(PostalAddressEntity entity);

	@BeforeMapping public void beforeMapping(PostalAddressDTO source, @MappingTarget PostalAddressEntity target)
	{
		log.debug("before source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@BeforeMapping public void beforeMapping(PostalAddressEntity source, @MappingTarget PostalAddressDTO target)
	{
		log.debug("before source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@AfterMapping public void afterMapping(PostalAddressDTO source, @MappingTarget PostalAddressEntity target)
	{
		log.debug("after source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@AfterMapping public void afterMapping(PostalAddressEntity source, @MappingTarget PostalAddressDTO target)
	{
		log.debug("after source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}
}