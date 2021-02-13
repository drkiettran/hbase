package com.drkiettran.hbase;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.drkiettran.hbase.model.User;
import com.drkiettran.hbase.repository.UserRepo;

/**
 * This is a simple Java program that access HBase database using Java APIs It
 * 
 * @author student
 *
 */
public class Main {
	private static Logger logger = Logger.getLogger("hbase.Main");
	private HbaseConnection connection;

	public static void main(String... args) throws IOException {
		logger.info("sample run ends.");
		Main main = new Main();
		main.runUserExample();
	}

	public Main() throws IOException {
		connection = HbaseConnection
				.getInstance(this.getClass().getClassLoader().getResource("hbase-site.xml").getPath());
	}

	/**
	 * In this example, you are able to do the following activities:
	 * 1. Create a brand new HBase table.
	 * 2. Insert entries into the table.
	 * 3. Get each entry in a table and print them on screen.
	 * 4. Get 'all' entries (via scanner).
	 * 5. Remove/delete an entry on a table.
	 * 6. Update an entry on a table.
	 * 
	 * @throws IOException
	 */
	private void runUserExample() throws IOException {

		UserRepo userRepo = new UserRepo(connection);
		if (userRepo.tableExist()) {
			logger.info("Table exists! Remove it");
			userRepo.deleteTable();
		}

		userRepo.createTable();
		userRepo.createUser("GrandpaD", "Mark Twain", "samuel@clemens.org", "abcdef1234");
		userRepo.createUser("HMS_Surprise", "Patrick O'Brien", "aubrey@sea.com", "bcdefg1234");
		userRepo.createUser("Sir Doyle", "Fyodor Dostoyevasky", "fyodor@brothers.net", "cdefgh1234");
		userRepo.createUser("Mark Twain", "Arthur Conan Doyle", "art@TheQueenMen.com", "defghi1234");

		logger.info("getting users ...");
		print(userRepo.getUser("GrandpaD"));
		print(userRepo.getUser("HMS_Surprise"));
		print(userRepo.getUser("Sir Doyle"));
		print(userRepo.getUser("Mark Twain"));

		logger.info("deleting ...");
		userRepo.delete("Sir Doyle");

		logger.info("scanning ...");
		List<User> users = userRepo.scan();
		print(users);

		logger.info("updating user ...");
		User user = userRepo.getUser("GrandpaD");
		user.setEmail("UpdatedEmail@mail.com");
		userRepo.update(user);

		logger.info("scanning ...");
		users = userRepo.scan();
		print(users);

		logger.info("sample run ends.");
	}

	private void print(List<User> users) {
		users.stream().forEach(user -> {
			print(user);
		});
	}

	private void print(User user) {
		System.out.println(String.format("\trowKey=%s, name=%s, email=%s, password=%s", user.getKey(), user.getName(),
				user.getEmail(), user.getPassword()));
	}
}
