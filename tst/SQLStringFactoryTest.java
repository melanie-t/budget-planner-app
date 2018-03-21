//
//import static org.junit.Assert.*;
//import org.junit.Test;
//
//public class SQLStringFactoryTest {
//	SQLStringFactory testFactory = SQLStringFactory.getInstance();
//
//	@Test
//	public void testAddColumn() {
//		String tableName = "TESTTABLE";
//		String columnName = "testcolumn";
//		String columnType = "TestColumnType";
//		String expected =
//				"ALTER TABLE " + tableName
//				+ "\n ADD COLUMN " + columnName + " " + columnType;
//
//		assertEquals(expected, testFactory.addColumn(tableName, columnName, columnType));
//	}
//
//	@Test
//	public void testAddEntry() {
//		String tableName = "TESTTABLE";
//		String value = "123";
//		String expected = "INSERT INTO " + tableName + " VALUES (\"" + value + "\");";
//
//
//		assertEquals(expected, testFactory.addEntry(tableName, value));
//	}
//
//	/* @TODO JORDAN - I know.... TDD
//	@Test
//	public void testUpdateEntry() {
//		String tableName = "TESTTABLE";
//		String expected = "SELECT * from " + tableName;
//
//		assertEquals(expected, testFactory.showAll(tableName));
//	}
//	*/
//
//	@Test
//	public void testCreateTable() {
//		String tableName = "TESTTABLE";
//		String primaryKeyName = "testkey";
//		String primaryKeyType = "TestKeyType";
//		String expected =
//				"CREATE TABLE IF NOT EXISTS " + tableName
//				+ "(" + primaryKeyName + " " + primaryKeyType + " PRIMARY KEY);";
//
//
//		assertEquals(expected, testFactory.createTable(tableName, primaryKeyName, primaryKeyType));
//	}
//
//	@Test
//	public void testDeleteTable() {
//		String tableName = "TESTTABLE";
//		String expected = "DROP TABLE IF EXISTS " + tableName;
//
//		assertEquals(expected, testFactory.deleteTable(tableName));
//	}
//
//	@Test
//	public void testShowAll() {
//		String tableName = "TESTTABLE";
//		String expected = "SELECT * from " + tableName;
//
//		assertEquals(expected, testFactory.showAll(tableName));
//	}
//}