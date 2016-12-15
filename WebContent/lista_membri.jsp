<%@ include file="header.jsp" %>
	<section id="full">
		<h2>Lista membri:</h2>
			<div id="scroller">
			<%
			/*rs=statement.executeQuery("SELECT * FROM UTENTE ORDER BY cognome ASC;");*/
			
			ArrayList<Utente> utenti= (ArrayList<Utente>)request.getAttribute("lista_utenti");
			
			for(int i=0;i<utenti.size();i++){
				out.print("<article class=\"list\">\n");
				if(user != null)
					if(user.getBoolean("is_admin") == true){
						out.print("\t<p class=\"admin_action\">\n"+
								"\t\t<form action=\"./Admin\" method=\"POST\">\n"+
									"\t\t\t<input type=\"hidden\" name=\"action\" value=\"delete_user\"/>\n"+
									"\t\t\t<input type=\"hidden\" name=\"CF_utente\" value=\""+utenti.get(i).getCF_utente()+"\"/>\n"+
									"\t\t\t<input class=\"admin_button\" type=\"submit\" value=\"x\"");
						if(user.getString("CF_utente").equals(utenti.get(i).getCF_utente()))
							out.print(" disabled");
						out.print("/>\n"+
								"\t\t</form>\n"+
							"\t</p>\n");
					}
				out.print(		"<p>"+utenti.get(i).getCognome()+" "+utenti.get(i).getNome()+"</p>"+
							"</article>\n"+
							"<br/>\n\n");
			}
			if(utenti.size()==0) out.print("<h2>Nessun utente trovato.</h2>");
			%>
			
		</div>
	</section>
<%@ include file="footer.html" %>