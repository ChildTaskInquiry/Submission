package dao;

import static constant.Constants.*;
import static constant.RedmineConstants.*;
import static constant.SqlConstants.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.dbutils.DbUtils;

import com.PerformanceMeasurement;

import dto.RedmineDto;
import managementRedmine.OperationSql;

public class RedmineDao {

	private OperationSql oprSql = new OperationSql();

	/**
	 * @param path
	 * @param file
	 * @param encode
	 * @return
	 * @throws SQLException
	 */
	public Map<Integer, RedmineDto> getRedmineList(String file) throws SQLException {
		PerformanceMeasurement pm = new PerformanceMeasurement();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i;
		SqlDao sDao = new SqlDao();
		Map<Integer, RedmineDto> items = new TreeMap<Integer, RedmineDto>();
		Integer issue_id = null;
		Integer parent_id = null;
		String subject = null;
		String tracker = null;
		String status = null;
		String project = null;
		String license = null;
		String subsystem = null;
		String author = null;
		String assignedTo = null;
		String priority = null;
		String start_date = null;
		String due_date = null;
		Timestamp created_on = null;
		Timestamp updated_on = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASS);
			ps = conn.prepareStatement(sDao.getSQL(SQL_PATH, file, DEFAULT_SQL_ENCODE));
			rs = ps.executeQuery();

			while (rs.next()) {
				i = 1;
				issue_id = rs.getInt(i++);
				parent_id = rs.getInt(i++);
				subject = rs.getString(i++);
				tracker = rs.getString(i++);
				status = rs.getString(i++);
				project = rs.getString(i++);
				license = rs.getString(i++);
				subsystem = rs.getString(i++);
				author = rs.getString(i++);
				assignedTo = rs.getString(i++);
				priority = rs.getString(i++);
				start_date = rs.getString(i++);
				due_date = rs.getString(i++);
				created_on = rs.getTimestamp(i++);
				updated_on = rs.getTimestamp(i++);

				items.put(issue_id, new RedmineDto(issue_id, parent_id, subject, tracker, status, project, license,
						subsystem, author, assignedTo, priority, start_date, due_date, created_on, updated_on));
			}

		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
			throw new SQLException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn, ps, rs);
			pm.getPerformance("[DEBUG] load completed of " + file + " ： ");
		}
		return items;
	}

	public Map<String, Integer> getStringMap(String file) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		SqlDao sDao = new SqlDao();

		Map<String, Integer> items = new TreeMap<String, Integer>();
		String key = null;
		Integer value = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASS);
			ps = conn.prepareStatement(sDao.getSQL(SQL_PATH, file, DEFAULT_SQL_ENCODE));
			rs = ps.executeQuery();

			while (rs.next()) {
				key = rs.getString(1);
				value = rs.getInt(2);

				items.put(key, value);
			}

		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
			throw new SQLException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn, ps, rs);
		}
		return items;
	}

	public Map<Integer, String> getCustomField(String file) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		SqlDao sDao = new SqlDao();

		Map<Integer, String> items = new TreeMap<Integer, String>();
		Integer key = null;
		String value = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASS);
			ps = conn.prepareStatement(sDao.getSQL(SQL_PATH, file, DEFAULT_SQL_ENCODE));
			rs = ps.executeQuery();

			while (rs.next()) {
				key = rs.getInt(1);
				value = rs.getString(2);

				items.put(key, value);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);

		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());

		} finally {
			DbUtils.closeQuietly(conn, ps, rs);
		}
		return items;
	}

	// Keyに複数のものがある可能性のあるものをValueを配列化する。
	public Map<Integer, List<String>> getArrayCustomField(String path, String file, String code, String split)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		SqlDao sDao = new SqlDao();

		Map<Integer, List<String>> items = new TreeMap<Integer, List<String>>();
		List<RedmineDto> values = new ArrayList<RedmineDto>();
		Integer key = null;
		String value = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASS);
			ps = conn.prepareStatement(sDao.getSQL(path, file, code));
			rs = ps.executeQuery();

			while (rs.next()) {
				key = rs.getInt(1);
				value = rs.getString(2);
				values.add(new RedmineDto(key, value));
			}

			items = oprSql.groupBy(values, split);

		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
			throw new SQLException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn, ps, rs);
		}
		return items;
	}

	public Map<Integer, Integer> getIdMap(String file) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		SqlDao sDao = new SqlDao();

		Map<Integer, Integer> items = new TreeMap<Integer, Integer>();
		Integer key = null;
		Integer value = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASS);
			ps = conn.prepareStatement(sDao.getSQL(SQL_PATH, file, DEFAULT_SQL_ENCODE));
			rs = ps.executeQuery();

			while (rs.next()) {
				key = rs.getInt(1);
				value = rs.getInt(2);

				items.put(key, value);
			}

		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
			throw new SQLException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn, ps, rs);
		}
		return items;
	}

}
