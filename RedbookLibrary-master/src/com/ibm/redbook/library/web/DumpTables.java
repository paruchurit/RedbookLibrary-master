package com.ibm.redbook.library.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.redbook.library.dao.impl.JdbcUtils;
import com.ibm.redbook.library.exceptions.BookException;

/**
 * Servlet implementation class DumpTables
 */
public class DumpTables extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DumpTables() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("<HTML><TITLE>Database Dump</TITLE>");
		try {
			JdbcUtils.dumpTables(out);
		} catch (BookException e) {
			// TODO Auto-generated catch block
			out.println("Exception caught");
			out.println(e.toString());
			e.printStackTrace();
		}
		out.println("</HTML>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
