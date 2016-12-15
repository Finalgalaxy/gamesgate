<%@ include file="header.jsp" %>
	<section id="full">
		<h2>Login</h2>
		<%
		Integer result_code = (Integer)request.getAttribute("result_code");
		if(result_code == 1){
			out.print("<h2>Accesso riuscito!</h2>");
			response.setHeader("Refresh", "3; url='./';");
		}
		else if(result_code == -1)	out.print("<h2>Errore: dati di accesso non validi!</h2>");
		else out.print("<form action=\"./User\" method=\"POST\">"+
				"<input type=\"hidden\" name=\"action\" value=\"accesso\"/>"+
				"Email: <input type=\"text\" name=\"username\" value=\"\"/><br/>"+
				"Password: <input type=\"password\" name=\"password\" value=\"\"/><br/>"+
				"<input type=\"submit\" value=\"Accedi!\"/>"+
				"</form>");
		
		%>
	</section>
<%@ include file="footer.html" %>