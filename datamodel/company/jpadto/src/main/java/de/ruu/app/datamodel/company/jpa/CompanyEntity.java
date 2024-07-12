package de.ruu.app.datamodel.company.jpa;

import de.ruu.app.datamodel.company.Company;
import de.ruu.app.datamodel.company.Department;
import de.ruu.app.datamodel.company.dto.CompanyDTO;
import de.ruu.app.datamodel.company.jpadto.Mapper;
import de.ruu.lib.jpa.core.mapstruct.AbstractMappedEntity;
import de.ruu.lib.util.Strings;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
@Getter                   // generate getter methods for all fields using lombok unless configured otherwise ({@code
                          // @Getter(AccessLevel.NONE}))
@Accessors(fluent = true) // generate fluent accessors with lombok and java-bean-style-accessors in non-abstract classes
                          // with ide, fluent accessors will (usually / by default) be ignored by mapstruct
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true) // generate no args constructor for jsonb, jaxb, jpa, mapstruct, ...
@Entity
@Table(schema = "test", name = "company")
public class CompanyEntity extends AbstractMappedEntity<CompanyDTO> implements Company
{
	/** mutable non-null */
	// no lombok-generation of setter because of additional validation in manually created method
	@NonNull
	@Setter(AccessLevel.NONE)
	@Column(unique=true, nullable=false)
	private String name;

	/**
	 * prevent direct access to modifiable set from outside
	 * may explicitly be {@code null}, {@code null} indicates that there was no attempt to load related objects from db
	 * (lazy)
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Getter(AccessLevel.NONE)
	@OneToMany
	(
			mappedBy = DepartmentEntity_.COMPANY,
			// do not use cascade REMOVE in to-many relations as this may result in cascading deletes that wipe out both sides
			// of the relation entirely
			cascade  = { CascadeType.PERSIST, CascadeType.MERGE }
	)
	private Set<DepartmentEntity> departments;

	/* do not use lombok to make sure that fluent setter with its validation is called */
	public CompanyEntity(@NonNull String name)
	{
		this();     // just in case something important happens in the default constructor
		name(name); // use fluent setter with its validation
	}

	@Override
	@NonNull
	public Company name(@NonNull String name)
	{
		if (Strings.isEmptyOrBlank(name)) throw new IllegalArgumentException("name must not be empty nor blank");
		this.name = name;
		return this;
	}

	// java bean style accessors for those who do not work with fluent style accessors (mapstruct)
	@NonNull
	public String getName() { return name(); }

	@Override
	public void beforeMapping(@NonNull CompanyDTO input)
	{
		log.debug("before mapping starting");
		super.beforeMapping(input);
		if (input.optionalDepartments().isPresent())
				input.optionalDepartments().get().forEach(e -> add(e.toSource()));
		name(input.name());
		log.debug("before mapping finished");
	}

	@Override
	public void afterMapping(@NonNull CompanyDTO input)
	{
		log.debug("after mapping starting");
		log.debug("after mapping finished");
	}

	@Override public @NonNull CompanyDTO toTarget() { return Mapper.INSTANCE.map(this); }

	@Override public Optional<Set<Department>> departments()
	{
		// return unmodifiable set of interface type instead of set of DTO2 types
		Optional<Set<DepartmentEntity>> optionalDepartments = optionalDepartments();
		if (optionalDepartments.isPresent()) return Optional.of(new HashSet<>(optionalDepartments.get()));
		return Optional.empty();
	}

	/** return optional unmodifiable */
	public Optional<Set<DepartmentEntity>> optionalDepartments()
	{
		if (isNull(departments)) return Optional.empty();
		return Optional.of(Collections.unmodifiableSet(departments));
	}

	public boolean add(@NonNull DepartmentEntity department)
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

			// assign this department as department of department and update employees
			department.company(this);
			return nonNullDepartments().add(department);
		}
	}

	public boolean remove(@NonNull DepartmentEntity entity)
	{
		if (isNull(departments)) return false;
		return departments.remove(entity);
	}

	private Set<DepartmentEntity> nonNullDepartments()
	{
		if (isNull(departments)) departments = new HashSet<>();
		return departments;
	}

	private boolean departmentsContains(DepartmentEntity department)
	{
		if (isNull(departments)) return false;
		return departments.contains(department);
	}
}