package de.ruu.app.datamodel.company.dto;

import de.ruu.app.datamodel.company.Company;
import de.ruu.app.datamodel.company.Department;
import de.ruu.app.datamodel.company.jpa.DepartmentEntity;
import de.ruu.app.datamodel.company.jpadto.Mapper;
import de.ruu.lib.jpa.core.mapstruct.AbstractMappedDTO;
import de.ruu.lib.util.Strings;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
@Getter                   // generate getter methods for all fields using lombok unless configured otherwise ({@code
                          // @Getter(AccessLevel.NONE}))
@Accessors(fluent = true) // generate fluent accessors with lombok and java-bean-style-accessors in non-abstract classes
                          // with ide, fluent accessors will (usually / by default) be ignored by mapstruct
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true) // generate no args constructor for jsonb, jaxb, mapstruct, ...
public class DepartmentDTO extends AbstractMappedDTO<DepartmentEntity> implements Department
{
	/** mutable non-null */
	// no lombok-generation of setter because of additional validation in manually created method
	@NonNull
	@Setter(AccessLevel.NONE)
	private String name;

	/** mutable non-null */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NonNull @Setter
	private CompanyDTO company;

	/* do not use lombok to make sure that fluent setter with its validation is called */
	public DepartmentDTO(@NonNull CompanyDTO company, @NonNull String name)

	{
		this(); // just in case something important happens in the default constructor
		name(name); // use fluent setter with its validation
		company(company);
	}

	@Override public @NonNull Department name(@NonNull String name)
	{
		if (Strings.isEmptyOrBlank(name)) throw new IllegalArgumentException("name must not be empty nor blank");
		this.name = name;
		return this;
	}

	@Override
	@NonNull
	public Company company() { return company; }

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

	// java bean style accessors for those who do not work with fluent style accessors (mapstruct)
	public @NonNull String  getName   () { return name();    }
//	public @NonNull Company getCompany() { return company(); }

	@Override public @NonNull DepartmentEntity toSource() { return Mapper.INSTANCE.map(this); }
}