package de.ruu.app.demo.common;

import de.ruu.app.demo.common.datamodel.Mapper;
import de.ruu.app.demo.common.datamodel.dto.CompanyDTO;
import de.ruu.app.demo.common.datamodel.dto.DepartmentDTO;
import de.ruu.app.demo.common.datamodel.jpa.CompanyEntity;
import de.ruu.app.demo.common.datamodel.jpa.DepartmentEntity;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestMapStruct
{
	@Test void mapValidCompanyDTO()
	{
		String     name = "name";
		CompanyDTO dto  = new CompanyDTO(name);

		CompanyEntity entity = Mapper.INSTANCE.map(dto);

		assertThat(entity       , is(not(nullValue())));
		assertThat(entity.name(), is(name));
	}

	@Test void mapValidCompanyEntity()
	{
		String        name = "name";
		CompanyEntity entity  = new CompanyEntity(name);

		CompanyDTO dto = Mapper.INSTANCE.map(entity);

		assertThat(dto       , is(not(nullValue())));
		assertThat(dto.name(), is(name));
	}

	@Test void mapValidDepartmentDTO()
	{
		String        name       = "name";
		CompanyDTO    companyDTO = new CompanyDTO(name);
		DepartmentDTO dto        = new DepartmentDTO(companyDTO, name);

		DepartmentEntity departmentEntity = Mapper.INSTANCE.map(dto);

		assertThat(departmentEntity       , is(not(nullValue())));
		assertThat(departmentEntity.name(), is(name));

		assertThat(departmentEntity.company().name(), is(name));
	}

	@Test void mapValidDepartmentEntity()
	{
		String           name          = "name";
		CompanyEntity    companyEntity = new CompanyEntity(name);
		DepartmentEntity entity        = new DepartmentEntity(companyEntity, name);

		DepartmentDTO dto = Mapper.INSTANCE.map(entity);

		assertThat(dto       , is(not(nullValue())));
		assertThat(dto.name(), is(name));

		assertThat(dto.company().name(), is(name));
	}
}