<%@ include file="header.jsp" %>
	<section id="full">
		<%
			int result_code = (Integer)request.getAttribute("result_code");
			if(result_code == 1)		out.print("<h2>Acquisto effettuato!</h2><br/>\n");
			else if(result_code == -1)	out.print("<h2>Errore nell'acquisto.</h2><br/>\n");
			else if(result_code == -2)  out.print("<h2>Non hai abbastanza soldi! <a href=\"./CaricaSoldi\">Ricarica!</a></h2>");
			else out.print("<h2>Errore: nome del gioco non valido.</h2>");
		%>
	</section>
<%@ include file="footer.html" %>