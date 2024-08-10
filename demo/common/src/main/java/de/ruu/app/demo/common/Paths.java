package de.ruu.app.demo.common;

public interface Paths
{
	/** general purpose constants */
	String BY_ID = "/{id}";

	/** constants for demo domain */
	String DEMO = "/demo";

	/** constants for datamodel domain */
	String DATAMODEL              = "/datamodel";
	String POSTAL_ADDRESS         = DATAMODEL + "/postaladdress";
	String COMPANY                = DATAMODEL + "/company";
	String DEPARTMENT             = DATAMODEL + "/department";

	/** constants for system domain */
	String SYSTEM  = "/system";

	/** special purpose constants */
	String BY_ID_WITH_DEPARTMENTS = BY_ID + "/optionalDepartments";
}