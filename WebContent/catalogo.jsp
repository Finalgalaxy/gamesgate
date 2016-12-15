<%@ include file="header.jsp" %>
	<section id="full">
		<%
		ArrayList<Videogame> vids=(ArrayList<Videogame>)request.getAttribute("lista_videogames");
		String str_extra_query = (String)request.getAttribute("q");
		if(str_extra_query != null && str_extra_query != ""){
			out.print("<h1>Risultati della ricerca:</h1>\n");
		}else{
			out.print("<h1>Lista dei giochi:</h1>\n");
		}
		%>
		<script type="text/javascript">
			var pagina=1;
			var elements=-1;
			function mod_img(){
				var scroller_div = document.getElementById("scroller");
				elements = scroller_div.getElementsByTagName("article");
				document.getElementById("page_value").innerHTML = "Pagina: "+pagina;
				for(var i=0; i<elements.length; i++){
					if(i >= ((pagina-1)*8) && i <= (pagina*8 -1))
						elements[i].setAttribute("style", "display:;");
					else
						elements[i].setAttribute("style", "display:none;margin:0;padding:0;");
				}
				//testo.innerHTML = "LOL"
			}
			
			function previouspage(){
				// Math.ceil(pagina/8) returns number of pages
				if(pagina > 1){
					pagina--;
					mod_img();
				}
			}
			function nextpage(){
				if(pagina <= Math.ceil(pagina/8)){
					pagina++;
					mod_img();
				}
			}
		</script>
		<input type="button" value="Indietro" onclick="previouspage()"/>
		<input type="button" value="Avanti" onclick="nextpage()"/>
		<span id="page_value">Pagina: 1</span>
		<%
			if(user != null)
				if(user.getBoolean("is_admin") == true){
					out.print("<form style=\"display:inline;\" method=\"get\" action=\"./Page\">"+
									"<input type=\"hidden\" name=\"name\" value=\"inserisci_videogame.jsp\"/>"+
									"<input type=\"submit\" value=\"Inserisci videogioco\">"+
								"</form>"+
								"<form style=\"display:inline;\" method=\"get\" action=\"./Page\">"+
									"<input type=\"hidden\" name=\"name\" value=\"inserisci_produttore.jsp\"/>"+
									"<input type=\"submit\" value=\"Inserisci produttore\">"+
								"</form>"
								);
				}
		%>
		<br/>
		<div id="scroller">
			<%
			if(vids != null){
				for(int i=0;i<vids.size();i++){
					out.print("<article class=\"list\">\n");
					if(user != null)
						if(user.getBoolean("is_admin") == true){
							out.print("\t<p class=\"admin_action\">\n"+
										"\t\t<form action=\"./Admin\" method=\"POST\">\n"+
											"\t\t\t<input type=\"hidden\" name=\"action\" value=\"delete_videogame\"/>\n"+
											"\t\t\t<input type=\"hidden\" name=\"id\" value=\""+vids.get(i).getNome()+"\"/>\n"+
											"\t\t\t<input class=\"admin_button\" type=\"submit\" value=\"x\"/>\n"+
										"\t\t</form>\n"+
									"\t</p>\n");
						}
					out.print(		"\t<img src=\"./images/label/"+vids.get(i).getImg()+"\"/>\n"+
									"\t<p class=\"nome_videogame\">"+vids.get(i).getNome()+"</p>\n"+
									"\t<p class=\"prezzo\"><span class=\"hide_for_phone\">Prezzo: </span>"+vids.get(i).getPrezzo()+" &euro;</p>\n"+
									"\t<p class=\"produttore\">Produttore: "+vids.get(i).getProduttore()+"</p>\n"+
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
								"\n\n");
				}
				if(vids.size() == 0) out.print("<h2>Nessun risultato trovato.</h2>");
			}
			%>
		</div>
		<script type="text/javascript">
			mod_img();//caricamento di default
		</script>
	</section>
<%@ include file="footer.html" %>