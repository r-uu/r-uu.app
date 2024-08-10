package de.ruu.app.demo.common;

import de.ruu.app.datamodel.company.dto.CompanyDTO;
import de.ruu.app.datamodel.company.dto.DepartmentDTO;
import lombok.NonNull;

/**
 * JAX-RS clients use instances of this class as transport vehicle for all data required to create {@link
 * de.ruu.app.datamodel.company.Department} instances on the server side. Because json can not serialise back references
 * from {@link DepartmentDTO} to {@link de.ruu.app.datamodel.company.dto.CompanyDTO} (see {@link
 * jakarta.json.bind.annotation.JsonbTransient} annotation on {@code DepartmentDTO#company} field) the company id is
 * stored separately in {@link #companyId}.
 */
public class DepartmentCreateDataDTO
{
	@NonNull private Long          companyId;
	@NonNull private DepartmentDTO department;

	public DepartmentCreateDataDTO(@NonNull Long companyId, @NonNull DepartmentDTO department)
	{
		this.companyId  = companyId;
		this.department = department;
	}

	// no args constructor for jsonb, jaxb, jpa, mapstruct, ..., assign default values to @NonNull fields
	protected DepartmentCreateDataDTO()
	{
		companyId  = Long.MIN_VALUE;
		department = new DepartmentDTO(new CompanyDTO("synthetic"), "synthetic");
	}

	public @NonNull Long          getCompanyId () { return companyId;  }
	public @NonNull DepartmentDTO getDepartment() { return department; }

	@NonNull public DepartmentCreateDataDTO setCompanyId(@NonNull Long companyId)
	{
		this.companyId = companyId;
		return this;
	}

	@NonNull public DepartmentCreateDataDTO setDepartment(@NonNull DepartmentDTO department)
	{
		this.department = department;
		return this;
	}
}