<%@ include file="header.jsp" %>
	<section id="full">
		<h2>Nuove uscite:</h2>
		
			<%
			ArrayList<Videogame> vids = (ArrayList<Videogame>)request.getAttribute("lista_videogames");
			for(int i=0;i<vids.size();i++){
				out.print("<article class=\"grid\">\n"+
								"<a href=\"./Articolo?id="+vids.get(i).getNome()+"\">"+
									"<img src=\"./images/grid/"+vids.get(i).getImg()+"\"/>\n"+
								"</a>"+
								"<p>"+vids.get(i).getNome()+"</p>"+
								"<p>Prezzo: "+vids.get(i).getPrezzo()+" &euro;</p>"+
								"<p>Produttore: "+vids.get(i).getProduttore()+"</p>"+
								"<p>"+
									"<form action=\"./Articolo\" method=\"GET\">"+
										"<input type=\"hidden\" name=\"id\" value=\""+vids.get(i).getNome()+"\"/>"+
										"<input type=\"submit\" value=\"Dettagli gioco\"/>"+
									"</form>\n"+
									"<form action=\"./Produttore\" method=\"GET\">"+
										"<input type=\"hidden\" name=\"name\" value=\""+vids.get(i).getProduttore()+"\"/>"+
										"<input type=\"submit\" value=\"Dettagli produttore\"/>"+
									"</form>\n"+
								"</p>"+
							"</article>\n"+
							"\n\n");
			}
			if(vids.size()==0) out.print("<h2>Nessun risultato trovato.</h2>");
			%>
			
	</section>
<%@ include file="footer.html" %>