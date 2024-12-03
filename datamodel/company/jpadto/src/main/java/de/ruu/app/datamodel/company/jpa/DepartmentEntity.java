package de.ruu.app.datamodel.company.jpa;

import de.ruu.app.datamodel.company.Company;
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
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
//@Getter                   // generate getter methods for all fields using lombok unless configured otherwise ({@code
//                          // @Getter(AccessLevel.NONE}))
//@Accessors(fluent = true) // generate fluent accessors with lombok and java-bean-style-accessors in non-abstract classes
                          // with ide, fluent accessors will (usually / by default) be ignored by mapstruct
// can not combine @NoArgsConstructor with @NonNull fields
//@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true) // generate no args constructor for jsonb, jaxb, jpa, mapstruct, ...
@Entity
@Table
(
		schema            = "app_demo_test",
		name              = "department",
		uniqueConstraints =
		{
				@UniqueConstraint
				(
						name        = "unique_department_name_per_company",
						columnNames =
						{
								"idCompany",
								"name"
						}
				)
		}
)
public class DepartmentEntity extends AbstractMappedEntity<DepartmentDTO> implements Department
{
	/** mutable non-null */
	// no lombok-generation of setter because of additional validation in manually created method
	@NonNull
	@Column(nullable=false)
	private String name;

	/** mutable, but not nullable */
	@ManyToOne
	@JoinColumn(name = "idCompany")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NonNull
	private CompanyEntity company;

	// no args constructor for jsonb, jaxb, jpa, mapstruct, ...
	protected DepartmentEntity()
	{
		// assign default values to @NonNull fields
		name = "";
	}

	/* do not use lombok to make sure that fluent setter with its validation is called */
	public DepartmentEntity(@NonNull CompanyEntity company, @NonNull String name)
	{
		this(); // just in case something important happens in the default constructor
		name(name); // use fluent setter with its validation
		this.company = company;
		company.add(this);
	}

	/////////////////////////
	// fluent style accessors
	/////////////////////////
	@Override @NonNull public String     name() { return name; }
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

	public void beforeMapping(@NonNull DepartmentDTO input)
	{
		log.debug("before mapping starting");
		super.beforeMapping(input);
		name(input.name());
		log.debug("before mapping finished");
	}

	public void afterMapping(@NonNull DepartmentDTO input)
	{
		log.debug("after mapping starting");
		log.debug("after mapping finished");
	}

	@Override public @NonNull DepartmentDTO toTarget() { return Mapper.INSTANCE.map(this); }
}