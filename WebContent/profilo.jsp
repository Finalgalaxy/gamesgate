<%@ include file="header.jsp" %>
	<section id="full">
		<h2>Il tuo profilo</h2>
		<%
			if(user != null){
				out.print("<div id=\"profile\">CF utente: "+user.getString("CF_UTENTE")+"<br/>\n"+
							"Cognome: "+user.getString("cognome")+"<br/>\n"+
							"Nome: "+user.getString("nome")+"<br/>\n"+
							"Via: "+user.getString("via")+"<br/>\n"+
							"CAP: "+user.getString("cap")+"<br/>\n"+
							"Numero civico: "+user.getString("numero_civico")+"<br/>\n"+
							"Email account: "+user.getString("EMAIL_ACCOUNT")+"<br/>\n"+
							"Saldo: "+user.getString("saldo")+" &euro;");
				out.print("</div><div id=\"scroller\">");
				
				ArrayList<Videogame> vids=(ArrayList<Videogame>)request.getAttribute("lista_videogames");
				for(int i=0;i<vids.size();i++){
					out.print("<article class=\"list\">\n"+
							"<img src=\"./images/label/"+vids.get(i).getImg()+"\"/>\n"+
							"<p class=\"nome_videogame\">"+vids.get(i).getNome()+"</p>"+
							"\t<div class=\"bottoni\">\n"+
							"\t\t<form action=\"./Articolo\" method=\"GET\">\n"+
								"\t\t\t<input type=\"hidden\" name=\"id\" value=\""+vids.get(i).getNome()+"\"/>\n"+
								"\t\t\t<input type=\"submit\" value=\"Dettagli gioco\"/>\n"+
							"\t\t</form>\n"+
							"\t\t<form action=\"./Produttore\" method=\"GET\">\n"+
								"\t\t\t<input type=\"hidden\" name=\"name\" value=\""+vids.get(i).getProduttore()+"\"/>\n"+
								"\t\t\t<input type=\"submit\" value=\"Dettagli produttore\"/>\n"+
							"\t\t</form>\n"+
						"\t</div>\n"+
						"</article>\n"+
						"<br/>\n\n");
				}
				out.print("</div>");
			}else{out.print("<h2>Non hai effettuato l'accesso...</h2>");}
		%>
	</section>
<%@ include file="footer.html" %>