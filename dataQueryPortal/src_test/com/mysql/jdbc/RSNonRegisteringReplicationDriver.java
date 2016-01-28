package com.mysql.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class RSNonRegisteringReplicationDriver extends NonRegisteringDriver {
	public RSNonRegisteringReplicationDriver() throws SQLException {
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		return connectReplicationConnection(url, info);
	}

	@Override
	protected Connection connectReplicationConnection(String url, Properties info) throws SQLException {
		Properties parsedProps = parseURL(url, info);

		if (parsedProps == null) {
			return null;
		}

		Properties masterProps = (Properties) parsedProps.clone();
		Properties slavesProps = (Properties) parsedProps.clone();

		slavesProps.setProperty("com.mysql.jdbc.ReplicationConnection.isSlave", "true");

		int numHosts = Integer.parseInt(parsedProps.getProperty("NUM_HOSTS"));

		if (numHosts < 2) {
			throw SQLError.createSQLException(
					"Must specify at least one slave host to connect to for master/slave replication load-balancing functionality",
					"01S00", null);
		}

		for (int i = 1; i < numHosts; i++) {
			int index = i + 1;

			masterProps.remove("HOST." + index);
			masterProps.remove("PORT." + index);

			slavesProps.setProperty("HOST." + i, parsedProps.getProperty("HOST." + index));
			slavesProps.setProperty("PORT." + i, parsedProps.getProperty("PORT." + index));
		}

		masterProps.setProperty("NUM_HOSTS", "1");
		slavesProps.remove("HOST." + numHosts);
		slavesProps.remove("PORT." + numHosts);
		slavesProps.setProperty("NUM_HOSTS", String.valueOf(numHosts - 1));
		slavesProps.setProperty("HOST", slavesProps.getProperty("HOST.1"));
		slavesProps.setProperty("PORT", slavesProps.getProperty("PORT.1"));

		return new RSReplicationConnection(masterProps, slavesProps);
	}
}
