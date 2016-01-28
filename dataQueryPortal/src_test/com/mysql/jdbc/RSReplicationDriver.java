package com.mysql.jdbc;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RSReplicationDriver extends RSNonRegisteringReplicationDriver implements Driver {
	static {
		try {
			DriverManager.registerDriver(new RSNonRegisteringReplicationDriver());
			
		} catch (SQLException E) {
			throw new RuntimeException("Can't register driver!");
		}
	}

	public RSReplicationDriver() throws SQLException {
	}
}