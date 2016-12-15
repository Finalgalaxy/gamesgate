<%@ include file="header.jsp" %>
	<section id="full">
		<h2>Carica soldi</h2>
		<%
			int result_code = (Integer)request.getAttribute("result_code");
			if(result_code == 1){
				out.print("<h2>Saldo aggiornato!</h2>");
				response.setHeader("Refresh", "2; url='./';");
			}
			else if(result_code == -1)	out.print("<h2>Non è stato possibile aggiornare il saldo...</h2>");
			else if(result_code == -2)  out.print("<h2>Valore negativo non consentito.</h2>");
			else out.print("<form action=\"\" method=\"GET\">\n"+
								"Inserisci ammontare: <input type=\"text\" name=\"amount\" value=\"\">"+
								"<input type=\"submit\" value=\"Carica!\">\n"+
								"</form>");
		%>
	</section>
<%@ include file="footer.html" %>