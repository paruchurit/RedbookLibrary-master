package com.ibm.redbook.library.dao.impl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.redbook.library.dao.DataSourceManager;
import com.ibm.redbook.library.exceptions.BookException;
import com.ibm.redbook.library.exceptions.DataSourceException;

/**
 * A collection of methods that can be used statically across JDBC calls to perform common tasks
 */
public class JdbcUtils {
	
	private static Logger log = Logger.getLogger(JdbcUtils.class.getName());
	
	/**
	 * Closes the connection to the database and the prepared statement. This should be used at the end of all database work. Note this will not throw on any SQLExceptions that occur
	 * @param con The connection to the database to close, if not null
	 * @param ps The prepared statement to close, if not null
	 */
	public static void closeConnectionAndPS(Connection con, Statement ps){
		if (con != null){
			try {
				con.close();
			} catch (SQLException ignoreMe) {
			}
		}
		closeStatement(ps);
	}

	public static void closeStatement(Statement ps) {
		if (ps != null){
			try {
				ps.close();
			} catch (SQLException ignoreMe) {
			}
		}
	}
	
	public static void dumpTables(PrintWriter out) throws BookException {
		log.log(Level.INFO, "TEST data dump");
		Connection con = null;
		Statement bookQuery = null;
		Statement borrowQuery = null;
		Statement membersQuery = null;
		Statement styleQuery=null;
		
		try {
			try {
				con = DataSourceManager.LIBRARY.getDs().getConnection();
			} catch (SQLException | DataSourceException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				String msg = "Failed to get a connection from the book DataSource. Got an SQLException: "
						+ e.getMessage();
				throw new BookException(msg, e);
			}

			try {
				bookQuery = con.createStatement();
				String bookSelect = "select * from book";
				ResultSet bookRs = bookQuery.executeQuery(bookSelect);
				out.println("Books");
				out.print("<table>");
				out.println("<tr><td>ID</td><td>NAME</td><td>DESCRIPTION</td><td>QUANTITY</td></tr>");
				while (bookRs.next()) {
					out.print("<tr>");
					out.print("<td>");
					out.print(bookRs.getString("id"));
					out.print("</td>");
					out.print("<td>");
					out.print(bookRs.getString("name"));
					out.print("</td>");
					out.print("<td>");
					out.print(bookRs.getString("description"));
					out.print("</td");
					out.print("<td>");
					out.print(bookRs.getInt("quantity"));
					out.print("</td>");
					out.print("</tr>");
				}
				out.println("</table>");
				bookRs.close();

			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				String msg = "Failed to query date from the book table. Got an SQLException: "
						+ e.getMessage();
				throw new BookException(msg, e);
			}
			
			try {
				borrowQuery = con.createStatement();
				String borrowSelect = "select * from borrowed_list";
				ResultSet borrowRs = borrowQuery.executeQuery(borrowSelect);
				out.println("Borrowed List");
				out.println("<table>");
				out.print("<tr><td>MEMBER</td><td>BOOK</td></tr>");
				while (borrowRs.next()) {
					out.print("<tr>");
					out.print("<td>");
					out.print(borrowRs.getString("member_id"));
					out.print("</td>");
					out.print("<td>");
					out.print(borrowRs.getString("book_id"));
					out.print("</td>");
					out.print("</tr>");
				}
				out.println("</table>");
				borrowRs.close();
				
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				String msg = "Failed to query data from the borrowed_list table. Got an SQLException: "
						+ e.getMessage();
				throw new BookException(msg, e);
			}
				
				try {
					membersQuery = con.createStatement();
					String memberSelect = "select * from member";
					ResultSet memberRs = membersQuery.executeQuery(memberSelect);
					out.println("Members");
					out.print("<table>");
					out.print("<tr><td>ID</td><td>PASSWORD</td></tr>");
					while (memberRs.next()) {
						out.print("<tr>");
						out.print("<td>");
						out.print(memberRs.getString("id"));
						out.print("</td>");
						out.print("<td>");
						out.print(memberRs.getString("password"));
						out.print("</td>");
						out.print("</tr>");
					}
					out.println("</table>");
					memberRs.close();
				
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				String msg = "Failed to query data from the members table. Got an SQLException: "
						+ e.getMessage();
				throw new BookException(msg, e);
			}
			
			try {
					styleQuery = con.createStatement();
					String memberSelect = "select * from style";
					ResultSet styleRs = membersQuery.executeQuery(memberSelect);
					out.println("Style");
					out.print("<table>");
					out.print("<tr><td>member_id</td><td>fgcolor</td><td>bgcolor</td></tr>");
					while (styleRs.next()) {
						out.print("<tr>");
						out.print("<td>");
						out.print(styleRs.getString("member_id"));
						out.print("</td>");
						out.print("<td>");
						out.print(styleRs.getString("fgcolor"));
						out.print("</td>");
						out.print("<td>");
						out.print(styleRs.getString("bgcolor"));
						out.print("</td>");
						out.print("</tr>");
					}
					out.println("</table>");
					styleRs.close();
				
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				String msg = "Failed to query data from the members table. Got an SQLException: "
						+ e.getMessage();
				throw new BookException(msg, e);
			}

				
			
		} finally {
			JdbcUtils.closeConnectionAndPS(con, bookQuery);
			JdbcUtils.closeStatement(borrowQuery);
			JdbcUtils.closeStatement(membersQuery);
			JdbcUtils.closeStatement(styleQuery);
		}

	}

}
