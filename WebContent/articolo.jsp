<%@ include file="header.jsp" %>
	<section id="full">
		<%
			Videogame vid = (Videogame)request.getAttribute("videogame");
			
			if(vid != null){
				out.print("<h1>"+vid.getNome()+"</h1>\n"+
							"<article class=\"single_videogame\">\n"+
								"\t<img src=\"./images/cover/"+vid.getImg()+"\"/>\n"+
								"\t<div>\n"+
									"\t\t<div>\n"
										+"Scheda tecnica:\n"+
										"\t\t<ul>\n"+
											"\t\t\t<li>Nome del produttore: "+vid.getProduttore()+"</li>\n"+
											"\t\t\t<li>Numero d'uscita: "+vid.getNumeroUscita()+"</li>\n"+
											"\t\t\t<li>Anno d'uscita: "+vid.getAnnoUscita()+"</li>\n"+
											"\t\t\t<li>Prezzo: "+vid.getPrezzo()+" &euro;</li>\n"+
										"\t\t</ul>\n"+
								"\t</div>\n"+
							"<div>");
				if(user != null){
					boolean trovato = (Boolean)request.getAttribute("trovato");
					if(trovato){
						out.print("<form action=\"./Achievements\" method=\"GET\">"+
								"<input type=\"hidden\" name=\"id\" value=\""+vid.getNome()+"\">\n"+
								"<input type=\"submit\" value=\"Visualizza achievements\"/>\n"+
								"\n");
						out.print("</form>");
					}else{
						out.print("<form action=\"./Acquista\" method=\"GET\">\n"+
								"<input type=\"hidden\" name=\"id\" value=\""+vid.getNome()+"\">\n"+
								"<input type=\"submit\" value=\"Acquista il gioco\"/>\n"
								+"</form>");
					}			
				}else{
					out.print("<h5>Accedi al sito per poter acquistare il titolo.</h5>");
				}
				out.print("</div></article>");
			}
				
		%>
	</section>
<%@ include file="footer.html" %>