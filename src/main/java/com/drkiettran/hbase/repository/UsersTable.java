package com.drkiettran.hbase.repository;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersTable {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersTable.class);
	private TableName usersTable = TableName.valueOf("users");
	private String cf = "info";
	private Admin admin;
	private Connection connection;
	private TableDescriptorBuilder tdb;
	private Configuration config;

	public UsersTable() {
		config = HBaseConfiguration.create();

		String path = this.getClass().getClassLoader().getResource("hbase-site.xml").getPath();
		config.addResource(new Path(path));

		try {
			connection = ConnectionFactory.createConnection(config);
			admin = connection.getAdmin();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean tableExist() {
		try {
			return admin.tableExists(usersTable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void deleteTable() {
		try {
			admin.disableTable(usersTable);
			admin.deleteTable(usersTable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createTable() {
		tdb = TableDescriptorBuilder.newBuilder(usersTable);
		tdb.setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(cf.getBytes()).build());

		try {
			admin.createTable(tdb.build());
			System.out.println(String.format("Created %s successfully!", cf));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void create(String rowKey, String name, String email, String password) {
		try {
			Table table = connection.getTable(usersTable);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("name"), Bytes.toBytes(name));
			put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("email"), Bytes.toBytes(email));
			put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("password"), Bytes.toBytes(password));
			table.put(put);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public InfoFamily get(String rowKey) {
		Get get = new Get(Bytes.toBytes(rowKey));
		get.addFamily(Bytes.toBytes(cf));
		InfoFamily info = new InfoFamily();
		info.setKey(rowKey);
		Table table;
		try {
			table = connection.getTable(usersTable);
			Result result = table.get(get);
			info.setName(new String(result.getValue(cf.getBytes(), Bytes.toBytes("name"))));
			info.setEmail(new String(result.getValue(cf.getBytes(), Bytes.toBytes("email"))));
			info.setPassword(new String(result.getValue(cf.getBytes(), Bytes.toBytes("password"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
}
