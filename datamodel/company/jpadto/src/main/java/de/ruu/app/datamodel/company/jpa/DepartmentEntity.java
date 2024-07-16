package de.ruu.app.datamodel.company.jpa;

import de.ruu.app.datamodel.company.Department;
import de.ruu.app.datamodel.company.dto.DepartmentDTO;
import de.ruu.app.datamodel.company.jpadto.Mapper;
import de.ruu.lib.jpa.core.mapstruct.AbstractMappedEntity;
import de.ruu.lib.util.Strings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true) // generate no args constructor for jsonb, jaxb, jpa, mapstruct, ...
@Entity
@Table(schema = "demo_test", name = "department")
public class DepartmentEntity extends AbstractMappedEntity<DepartmentDTO> implements Department
{
	/** mutable non-null */
	// no lombok-generation of setter because of additional validation in manually created method
	@NonNull
	@Setter(AccessLevel.NONE)
	@Column(unique=true, nullable=false)
	private String name;

	/** mutable, but not nullable */
	// no java-bean-style accessor here, mapstruct will ignore fields without bean-style-accessor so mapping can be
	// controlled in beforeMapping
	@ManyToOne
	@JoinColumn(name = "idDepartment")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NonNull
	@Setter
	private CompanyEntity company;

	/* do not use lombok to make sure that fluent setter with its validation is called */
	public DepartmentEntity(@NonNull CompanyEntity company, @NonNull String name)
	{
		this(); // just in case something important happens in the default constructor
		name(name); // use fluent setter with its validation
		company(company);
		company.add(this);
	}

	@Override public @NonNull Department name(@NonNull String name)
	{
		if (Strings.isEmptyOrBlank(name)) throw new IllegalArgumentException("name must not be empty nor blank");
		this.name = name;
		return this;
	}

	// java bean style accessors for those who do not work with fluent style accessors (mapstruct)
//	public @NonNull String  getName()    { return name(); }
//	public @NonNull Company getCompany() { return company(); }

	@Override public void beforeMapping(@NonNull DepartmentDTO input)
	{
		log.debug("before mapping starting");
		super.beforeMapping(input);
		name(input.name());
		log.debug("before mapping finished");
	}

	@Override public void afterMapping(@NonNull DepartmentDTO input)
	{
		log.debug("after mapping starting");
		log.debug("after mapping finished");
	}

	@Override public @NonNull DepartmentDTO toTarget() { return Mapper.INSTANCE.map(this); }
}