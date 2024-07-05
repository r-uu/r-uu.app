package de.ruu.app.demo.common.datamodel;

import de.ruu.app.demo.common.datamodel.dto.CompanyDTO;
import de.ruu.app.demo.common.datamodel.dto.DepartmentDTO;
import de.ruu.app.demo.common.datamodel.jpa.CompanyEntity;
import de.ruu.app.demo.common.datamodel.jpa.DepartmentEntity;
import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

@Slf4j
@org.mapstruct.Mapper
public abstract class Mapper
{
	public static final Mapper INSTANCE = Mappers.getMapper(Mapper.class);

	private static ReferenceCycleTracking CONTEXT  = new ReferenceCycleTracking();

	public abstract CompanyEntity    map(CompanyDTO       dto   );
	public abstract CompanyDTO       map(CompanyEntity    entity);
	public abstract DepartmentEntity map(DepartmentDTO    dto   );
	public abstract DepartmentDTO    map(DepartmentEntity entity);

	@BeforeMapping public void beforeMapping(CompanyDTO source, @MappingTarget CompanyEntity target)
	{
		log.debug("before source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@BeforeMapping public void beforeMapping(CompanyEntity source, @MappingTarget CompanyDTO target)
	{
		log.debug("before source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@AfterMapping public void afterMapping(CompanyDTO source, @MappingTarget CompanyEntity target)
	{
		log.debug("after source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@AfterMapping public void afterMapping(CompanyEntity source, @MappingTarget CompanyDTO target)
	{
		log.debug("after source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@BeforeMapping public void beforeMapping(DepartmentDTO source, @MappingTarget DepartmentEntity target)
	{
		log.debug("before source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@BeforeMapping public void beforeMapping(DepartmentEntity source, @MappingTarget DepartmentDTO target)
	{
		log.debug("before source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@AfterMapping public void afterMapping(DepartmentDTO source, @MappingTarget DepartmentEntity target)
	{
		log.debug("after source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@AfterMapping public void afterMapping(DepartmentEntity source, @MappingTarget DepartmentDTO target)
	{
		log.debug("after source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@ObjectFactory
	@NonNull
	CompanyEntity lookupOrCreate(@NonNull CompanyDTO input)
	{
		CompanyEntity result = CONTEXT.get(input, CompanyEntity.class);
		if (result == null)
		{
			result = new CompanyEntity(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	CompanyDTO lookupOrCreate(@NonNull CompanyEntity input)
	{
		CompanyDTO result = CONTEXT.get(input, CompanyDTO.class);
		if (result == null)
		{
			result = new CompanyDTO(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	DepartmentEntity lookupOrCreate(@NonNull DepartmentDTO input)
	{
		DepartmentEntity result = CONTEXT.get(input, DepartmentEntity.class);
		if (result == null)
		{
				CompanyEntity company = CONTEXT.get(input.company(), CompanyEntity.class);
				if (company == null)
						company = new CompanyEntity(input.company().name());
				result = new DepartmentEntity(company, input.name());
				CONTEXT.put(input, result);
				CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	DepartmentDTO lookupOrCreate(@NonNull DepartmentEntity input)
	{
		DepartmentDTO result = CONTEXT.get(input, DepartmentDTO.class);
		if (result == null)
		{
			CompanyDTO company = CONTEXT.get(input.company(), CompanyDTO.class);
			if (company == null)
					company = new CompanyDTO(input.company().name());
			result = new DepartmentDTO(company, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}