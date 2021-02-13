package com.drkiettran.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

public class HbaseConnection {
	private Configuration config;
	private Connection connection;
	private Admin admin;

	private HbaseConnection(String path) throws IOException {
		config = HBaseConfiguration.create();
		config.addResource(new Path(path));
		connection = ConnectionFactory.createConnection(config);
		admin = connection.getAdmin();
	}

	public Admin getAdmin() {
		return admin;
	}

	public static HbaseConnection getInstance(String path) throws IOException {
		return new HbaseConnection(path);
	}

	public Table getTable(TableName userTable) throws IOException {
		return connection.getTable(userTable);
	}

}
