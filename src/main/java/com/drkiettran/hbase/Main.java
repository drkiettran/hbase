package com.drkiettran.hbase;

import com.drkiettran.hbase.model.InfoFamily;
import com.drkiettran.hbase.repository.UsersTable;

public class Main {
	public static void main(String... args) {
		UsersTable familyTable = new UsersTable();
		if (familyTable.tableExist()) {
			System.out.println("Table exists! Remove it");
			familyTable.deleteTable();
		}
		familyTable.createTable();
		familyTable.create("GrandpaD", "Mark Twain", "samuel@clemens.org", "abcdef1234");
		familyTable.create("HMS_Surprise", "Patrick O'Brien", "aubrey@sea.com", "bcdefg1234");
		familyTable.create("Sir Doyle", "Fyodor Dostoyevasky", "fyodor@brothers.net", "cdefgh1234");
		familyTable.create("Mark Twain", "Arthur Conan Doyle", "art@TheQueenMen.com", "defghi1234");

		print(familyTable.get("GrandpaD"));
		print(familyTable.get("HMS_Surprise"));
		print(familyTable.get("Sir Doyle"));
		print(familyTable.get("Mark Twain"));
	}

	private static void print(InfoFamily infoFamily) {
		System.out.println(String.format("rowKey=%s, name=%s, email=%s, password=%s", infoFamily.getKey(),
				infoFamily.getName(), infoFamily.getEmail(), infoFamily.getPassword()));
	}
}
