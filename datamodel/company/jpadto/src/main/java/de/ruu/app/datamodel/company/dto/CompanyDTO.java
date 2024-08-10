package de.ruu.app.datamodel.company.dto;

import de.ruu.app.datamodel.company.Company;
import de.ruu.app.datamodel.company.Department;
import de.ruu.app.datamodel.company.jpa.CompanyEntity;
import de.ruu.app.datamodel.company.jpadto.Mapper;
import de.ruu.lib.jpa.core.mapstruct.AbstractMappedDTO;
import de.ruu.lib.util.Strings;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public class CompanyDTO extends AbstractMappedDTO<CompanyEntity> implements Company
{
	/** mutable non-null */
	@NonNull private String name;

	/**
	 * prevent direct access to nullable modifiable set from outside
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@EqualsAndHashCode.Exclude
//	@JsonbTypeAdapter(DepartmentsAdapter.class)
	private Set<DepartmentDTO> departments;

	/** constructor to be used by consumers */
	public CompanyDTO(@NonNull String name) { this.name = name; }

	@JsonbCreator
	public CompanyDTO(
			@JsonbProperty("id")          Long               id,
			@JsonbProperty("version")     Short              version,
			@JsonbProperty("name")        String             name,
			@JsonbProperty("departments") Set<DepartmentDTO> departments)
	{
		super(id, version);
		this.name        = name;
		this.departments = departments;

		if (departments != null) departments.forEach(d -> d.company(this));
	}

	// no args constructor for jpa, mapstruct, ..., assign default values to @NonNull fields
	protected CompanyDTO() { name = ""; }

	/////////////////////////
	// fluent style accessors
	/////////////////////////
	@Override @NonNull public String  name() { return name; }
	@Override @NonNull public Company name(@NonNull String name)
	{
		if (Strings.isEmptyOrBlank(name)) throw new IllegalArgumentException("name must not be empty nor blank");
		this.name = name;
		return this;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// java bean style accessors for those who do not work with fluent style accessors (mapstruct)
	//////////////////////////////////////////////////////////////////////////////////////////////
	@Override public @NonNull String  getName()                     { return name();     }
	@Override public @NonNull Company setName(@NonNull String name) { return name(name); }

	@Override public void beforeMapping(@NonNull CompanyEntity input)
	{
		log.debug("before mapping starting");
		super.beforeMapping(input);
		if (input.optionalDepartments().isPresent())
				input.optionalDepartments().get().forEach(d -> add(d.toTarget()));
		name(input.name());
		log.debug("before mapping finished");
	}

	@Override public void afterMapping(@NonNull CompanyEntity input)
	{
		log.debug("after mapping starting");
		log.debug("after mapping finished");
	}

	@Override public @NonNull CompanyEntity toSource() { return Mapper.INSTANCE.map(this); }

	/** return optional unmodifiable */
	@Override public Optional<Set<Department>> departments()
	{
		// return unmodifiable set of interface type instead of set of DTO2 types
		Optional<Set<DepartmentDTO>> optionalDepartments = optionalDepartments();
		if (optionalDepartments.isPresent()) return Optional.of(new HashSet<>(optionalDepartments.get()));
		return Optional.empty();
	}

	/** return optional unmodifiable */
	public Optional<Set<DepartmentDTO>> optionalDepartments()
	{
		if (isNull(departments)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(departments));
	}

	public boolean add(@NonNull DepartmentDTO department)
	{
		if (department.company() == this)
		{
			if (departmentsContains(department)) return true;
			return nonNullDepartments().add(department);
		}
		else
		{
			// following check should never return true
			if (departmentsContains(department))
					log.error("department with {} is already contained in {}", department.company(), this);

			return nonNullDepartments().add(department);
		}
	}

	public boolean remove(@NonNull DepartmentDTO dto)
	{
		if (isNull(departments)) return false;
		return departments.remove(dto);
	}

	private Set<DepartmentDTO> nonNullDepartments()
	{
		if (isNull(departments)) departments = new HashSet<>();
		return departments;
	}

	private boolean departmentsContains(DepartmentDTO dto)
	{
		if (isNull(departments)) return false;
		return departments.contains(dto);
	}
}