package de.ruu.app.demo.common;

public interface Paths
{
	String DEMO    = "/demo";

	String SYSTEM  = "/system";

	String COMPANY                = "/company";
	String BY_ID                  = "/{id}";
	String BY_ID_WITH_DEPARTMENTS = BY_ID + "/optionalDepartments";
}