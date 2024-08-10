package de.ruu.app.datamodel.company.dto;

import de.ruu.lib.jsonb.JsonbConfigurator;
import jakarta.json.bind.Jsonb;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
class TestJsonB
{
	private final static Type SET_OF_COMPANIES = new HashSet<CompanyDTO>() { }.getClass().getGenericSuperclass();

	private final static int NUMBER_OF_COMPANIES   = 3;
	private final static int NUMBER_OF_DEPARTMENTS = 3;

	private Jsonb context;

	@BeforeEach void beforeEach() { context = new JsonbConfigurator().getContext(); }

	@Test void test()
	{
		Set<CompanyDTO> companiesIn = createTestData();
		String jsonIn = context.toJson(companiesIn);
		log.debug("jsonIn\n{}", jsonIn);
		Set<CompanyDTO> companiesOut = context.fromJson(jsonIn, SET_OF_COMPANIES);
		String jsonOut = context.toJson(companiesOut);
		log.debug("jsonOut\n{}", jsonOut);
		assertThat(jsonOut, is(jsonIn));
	}

	private Set<CompanyDTO> createTestData()
	{
		Set<CompanyDTO> result = new HashSet<>();
		for (int i = 0; i < NUMBER_OF_COMPANIES; i++)
		{
			CompanyDTO company = new CompanyDTO((long) i, (short) i, "" + i, new HashSet<>());
			for (int j = 0; j < NUMBER_OF_DEPARTMENTS; j++)
			{
				company.add(new DepartmentDTO((long) i, (short) i, "d." + j));
			}
			result.add(company);
		}
		return result;
	}
}