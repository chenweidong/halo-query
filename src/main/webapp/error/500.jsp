<%@page import="java.io.StringWriter"%>
<%@page import="java.io.PrintStream"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
Throwable ex = null;
if (request.getAttribute("exception") != null) {
	ex = (Throwable) request.getAttribute("exception");
} else if (request.getAttribute("javax.servlet.error.exception") != null) {
	ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
}
if (ex != null){
	StringWriter stringWriter = new StringWriter();
	ex.printStackTrace(new PrintWriter(stringWriter));
	out.println(stringWriter.toString());
}
%>