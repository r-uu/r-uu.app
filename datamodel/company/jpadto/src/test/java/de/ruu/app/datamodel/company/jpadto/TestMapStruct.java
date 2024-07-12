package de.ruu.app.datamodel.company.jpadto;

import de.ruu.app.datamodel.company.dto.CompanyDTO;
import de.ruu.app.datamodel.company.dto.DepartmentDTO;
import de.ruu.app.datamodel.company.jpa.CompanyEntity;
import de.ruu.app.datamodel.company.jpa.DepartmentEntity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

class TestMapStruct
{
	@Test
	void mapValidCompanyDTO()
	{
		String     name = "name";
		CompanyDTO dto  = new CompanyDTO(name);

		CompanyEntity entity = Mapper.INSTANCE.map(dto);

		assertThat(entity       , Matchers.is(Matchers.not(Matchers.nullValue())));
		assertThat(entity.name(), Matchers.is(name));
	}

	@Test void mapValidCompanyEntity()
	{
		String        name = "name";
		CompanyEntity entity  = new CompanyEntity(name);

		CompanyDTO dto = Mapper.INSTANCE.map(entity);

		assertThat(dto       , Matchers.is(Matchers.not(Matchers.nullValue())));
		assertThat(dto.name(), Matchers.is(name));
	}

	@Test void mapValidDepartmentDTO()
	{
		String        name       = "name";
		CompanyDTO    companyDTO = new CompanyDTO(name);
		DepartmentDTO dto        = new DepartmentDTO(companyDTO, name);

		DepartmentEntity departmentEntity = Mapper.INSTANCE.map(dto);

		assertThat(departmentEntity       , Matchers.is(Matchers.not(Matchers.nullValue())));
		assertThat(departmentEntity.name(), Matchers.is(name));

		assertThat(departmentEntity.company().name(), Matchers.is(name));
	}

	@Test void mapValidDepartmentEntity()
	{
		String           name          = "name";
		CompanyEntity    companyEntity = new CompanyEntity(name);
		DepartmentEntity entity        = new DepartmentEntity(companyEntity, name);

		DepartmentDTO dto = Mapper.INSTANCE.map(entity);

		assertThat(dto       , Matchers.is(Matchers.not(Matchers.nullValue())));
		assertThat(dto.name(), Matchers.is(name));

		assertThat(dto.company().name(), Matchers.is(name));
	}
}