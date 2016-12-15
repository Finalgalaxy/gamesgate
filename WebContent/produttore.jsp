<%@ include file="header.jsp" %>
	<section id="full">
		<%
			Produttore produttore = (Produttore)request.getAttribute("produttore");
			ArrayList<Videogame> vids = (ArrayList<Videogame>)request.getAttribute("lista_videogames");
			if(produttore != null){
				out.print("<h1>"+produttore.getNome()+"</h1>\n"+
								"<article class=\"single_producer\">\n"+
									"<img src=\"./images/banner/"+produttore.getImg()+"\" alt=\""+produttore.getNome()+"\">"+
									"<ul>\n"+
										"<li>Nome del produttore: "+produttore.getNome()+"</li>\n"+
										"<li>Anni d'esperienza: "+produttore.getAnniExp()+"</li>\n"+
									"</ul>\n"+
								"</article>\n");
				out.print("<div id=\"scroller\">");
				for(int i=0;i<vids.size();i++){
					out.print("<article class=\"list\">\n"+
								"<img src=\"./images/label/"+vids.get(i).getImg()+"\"/>\n"+
								"<p class=\"nome_videogame\">"+vids.get(i).getNome()+"</p>"+
								"<p class=\"prezzo\"><span class=\"hide_for_phone\">Prezzo: </span>"+vids.get(i).getPrezzo()+" &euro;</p>"+
								"<div class=\"bottone\">"+
									"<form action=\"./Articolo\" method=\"GET\">"+
										"<input type=\"hidden\" name=\"id\" value=\""+vids.get(i).getNome()+"\"/>"+
										"<input type=\"submit\" value=\"Dettagli gioco\"/>"+
									"</form>"+
								"</div>\n"+
							"</article>\n"+
							"<br/>\n\n");
				}
				out.print("</div>");
			}else{
				out.print("<h2>Nessun risultato trovato.</h2>");
			}
		%>
	</section>
<%@ include file="footer.html" %>