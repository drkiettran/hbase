package com.drkiettran.hbase.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;

import com.drkiettran.hbase.HbaseConnection;
import com.drkiettran.hbase.model.User;

public class UserRepo {
	private static Logger logger = Logger.getLogger("hbase.UserRepo");
	private TableName userTable = TableName.valueOf("user");
	private String cf = "info";
	private Admin admin;
	private TableDescriptorBuilder tdb;
	private HbaseConnection connection;

	public UserRepo(HbaseConnection connection) {
		this.connection = connection;
		admin = connection.getAdmin();
	}

	public boolean tableExist() throws IOException {
		return admin.tableExists(userTable);
	}

	public void deleteTable() throws IOException {
		admin.disableTable(userTable);
		admin.deleteTable(userTable);
	}

	public void createTable() throws IOException {
		tdb = TableDescriptorBuilder.newBuilder(userTable);
		tdb.setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(cf.getBytes()).build());

		admin.createTable(tdb.build());
		logger.info(String.format("Created %s successfully!", cf));
	}

	public void createUser(String rowKey, String name, String email, String password) throws IOException {
		Table table = connection.getTable(userTable);
		Put put = new Put(Bytes.toBytes(rowKey));
		put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("name"), Bytes.toBytes(name));
		put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("email"), Bytes.toBytes(email));
		put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("password"), Bytes.toBytes(password));
		table.put(put);
	}

	public void delete(String rowKey) throws IOException {
		Delete delete = new Delete(Bytes.toBytes(rowKey));
		delete.addFamily(Bytes.toBytes(cf));
		Table table;
		table = connection.getTable(userTable);
		table.delete(delete);
	}

	public User getUser(String rowKey) throws IOException {
		Get get = new Get(Bytes.toBytes(rowKey));
		get.addFamily(Bytes.toBytes(cf));
		User user = new User();
		user.setKey(rowKey);
		Table table;
		table = connection.getTable(userTable);
		Result result = table.get(get);
		user.setName(new String(result.getValue(cf.getBytes(), Bytes.toBytes("name"))));
		user.setEmail(new String(result.getValue(cf.getBytes(), Bytes.toBytes("email"))));
		user.setPassword(new String(result.getValue(cf.getBytes(), Bytes.toBytes("password"))));

		return user;
	}

	public void update(User user) throws IOException {
		Table table = connection.getTable(userTable);
		Put put = new Put(Bytes.toBytes(user.getKey()));
		put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("name"), Bytes.toBytes(user.getName()));
		put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("email"), Bytes.toBytes(user.getEmail()));
		put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("password"), Bytes.toBytes(user.getPassword()));
		table.put(put);
	}

	public List<User> scan() throws IOException {
		List<User> users = new ArrayList<User>();

		Scan scan = new Scan();
		Table table;
		table = connection.getTable(userTable);
		ResultScanner resultScanner = table.getScanner(scan);
		resultScanner.forEach(result -> {
			User user = new User();
			user.setKey(new String(result.getRow()));
			user.setName(new String(result.getValue(cf.getBytes(), Bytes.toBytes("name"))));
			user.setEmail(new String(result.getValue(cf.getBytes(), Bytes.toBytes("email"))));
			user.setPassword(new String(result.getValue(cf.getBytes(), Bytes.toBytes("password"))));
			users.add(user);

		});
		return users;
	}
}
