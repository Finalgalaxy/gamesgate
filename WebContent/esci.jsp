<%@ include file="header.jsp" %>
	<section id="full">
		<h2>Login</h2>
		<%
		out.print("<h2>Sei stato scollegato!</h2>");
		response.setHeader("Refresh", "3; url='./';");
		%>
	</section>
<%@ include file="footer.html" %>