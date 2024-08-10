package de.ruu.app.datamodel.company.dto;

import de.ruu.lib.jsonb.AbstractSetAdapter;
import de.ruu.lib.util.json.Sanitiser;
import jakarta.json.JsonValue;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class DepartmentsAdapter extends AbstractSetAdapter<DepartmentDTO>
{
	private final static Type DEPARTMENTS_TYPE = new HashSet<DepartmentDTO>() { }.getClass().getGenericSuperclass();

	@Override protected Type getType() { return DEPARTMENTS_TYPE; }

	@Override public JsonValue adaptToJson(Set<DepartmentDTO> param) throws Exception
	{
		try
		{
			return super.adaptToJson(param);
		}
		catch (Exception e)
		{
			log.error("failure adapting to json", e);
			throw e;
		}
	}

	@Override public Set<DepartmentDTO> adaptFromJson(JsonValue jsonValue) throws Exception
	{
		try
		{
			return super.adaptFromJson(jsonValue);
		}
		catch (Exception e)
		{
			log.error("failure adapting from json, jsonValue (sanitised)\n" + Sanitiser.sanitise(jsonValue.toString()), e);
			throw e;
		}
	}
}