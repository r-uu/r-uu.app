package de.ruu.app.datamodel.company.dto;

import de.ruu.app.datamodel.company.Company;
import de.ruu.app.datamodel.company.Department;
import de.ruu.app.datamodel.company.jpa.DepartmentEntity;
import de.ruu.app.datamodel.company.jpadto.Mapper;
import de.ruu.lib.jpa.core.mapstruct.AbstractMappedDTO;
import de.ruu.lib.util.Strings;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public class DepartmentDTO extends AbstractMappedDTO<DepartmentEntity> implements Department
{
	/** mutable non-null */
	@NonNull private String name;

	/** mutable non-null */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NonNull
	@JsonbTransient
	private CompanyDTO company;

	@JsonbCreator
	public DepartmentDTO(
			@JsonbProperty("id")      Long   id,
			@JsonbProperty("version") Short  version,
			@JsonbProperty("name")    String name)
	{
		super(id, version);
		this.name = name;
	}

	// no args constructor for jpa, mapstruct, ...
	protected DepartmentDTO()
	{
		// assign default values to @NonNull fields
		name = "";
	}

	/**
	 * constructor to be used by consumers, do not use lombok to make sure that fluent setter with its validation is
	 * called
	 */
	public DepartmentDTO(@NonNull CompanyDTO company, @NonNull String name)
	{
		this(); // just in case something important happens in the default constructor
		this.company = company;
		name(name); // use fluent setter with its validation
		company.add(this);
	}

	/////////////////////////
	// fluent style accessors
	/////////////////////////
	@Override @NonNull public String     name() {      return name; }
	@Override @NonNull public Department name(@NonNull String name)
	{
		if (Strings.isEmptyOrBlank(name)) throw new IllegalArgumentException("name must not be empty nor blank");
		this.name = name;
		return this;
	}

	@Override @NonNull public Company company() { return company; }

	//////////////////////////////////////////////////////////////////////////////////////////////
	// java bean style accessors for those who do not work with fluent style accessors (mapstruct)
	//////////////////////////////////////////////////////////////////////////////////////////////
	@Override @NonNull public String     getName()                     { return name();     }
	@Override @NonNull public Department setName(@NonNull String name) { return name(name); }

	@Override @NonNull public Company getCompany() { return company(); }

	@Override public void beforeMapping(@NonNull DepartmentEntity input)
	{
		log.debug("before mapping starting");
		super.beforeMapping(input);
		name(input.name());
		log.debug("before mapping finished");
	}

	@Override public void afterMapping(@NonNull DepartmentEntity input)
	{
		log.debug("before mapping starting");
		log.debug("before mapping finished");
	}

	@Override public @NonNull DepartmentEntity toSource() { return Mapper.INSTANCE.map(this); }

	/** package visible method to be called from JsonbCreator in {@link CompanyDTO} */
	@NonNull Department company(@NonNull CompanyDTO company)
	{
		this.company = company;
		return this;
	}
}