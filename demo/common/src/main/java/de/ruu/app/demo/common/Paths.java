package de.ruu.app.demo.common;

public interface Paths
{
	/** general purpose constants */
	String BY_ID = "/{id}";

	/** constants for demo domain */
	String DEMO = "/demo";

	/** constants for company domain */
	String COMPANY                = "/company";
	String BY_ID_WITH_DEPARTMENTS = BY_ID + "/optionalDepartments";

	/** constants for datamodel domain */
	String DATAMODEL      = "/datamodel";
	String POSTAL_ADDRESS = DATAMODEL + "/postaladdress";

	/** constants for system domain */
	String SYSTEM  = "/system";
}