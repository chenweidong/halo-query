<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>query</title>
<style type="text/css">
table, th, td
  {
  border: 1px solid blue;
  }
</style>
<script type="text/javascript">
function commitQuery(para){
	document.getElementById("myForm").action="<%=request.getContextPath() %>/query/"  + para;
	document.getElementById("myForm").submit();
}
</script>
</head>
<body>
<form id="myForm" action="<%=request.getContextPath() %>/query/again"  method="post">
<textarea name="sql" rows="5" cols="120">
	${sql }
</textarea>
<br/>
<input type="button" value="gl" onclick="commitQuery('gl')"/>
<input type="button" value="tz" onclick="commitQuery('tz')"/>
<input type="button" value="jk" onclick="commitQuery('jk')"/>
<input type="button" value="test" onclick="commitQuery('mysql')"/>
<table>
	<tr>
		<td></td>
		<c:forEach items="${titles }" var="title">
			<th>${title }</th>
		</c:forEach>
	</tr>
	<c:choose>
		<c:when  test="${fn:length(list) > 0 }">
			<c:forEach items="${list }" var="item" varStatus="var">
			<tr>
				<td>${var.index + 1 }</td>
				<c:forEach items="${item }" var="map">
					<td>${map.value }</td>
				</c:forEach>
			</tr>
		</c:forEach>
		</c:when>
		<c:when  test="${fn:length(list) == 0 }">
			<tr>
				<c:choose>
					<c:when test="${message == '' or message == null }">
						<td>无记录</td>
					</c:when>
					<c:otherwise>
						<td>${message }</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:when>
	</c:choose>
</table>
</form>
</body>
</html>