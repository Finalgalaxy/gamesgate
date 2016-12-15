<%@ include file="header.jsp" %>
	<section id="full">
		<%
			if(user != null){
				String game_id = (String)request.getAttribute("id");
				if(game_id != null && game_id != ""){
					out.print("<h2>Achievements conseguiti in "+game_id+"</h2>\n"+
								"<div id=\"scroller\">");
					ArrayList<Achievement> achievements = (ArrayList<Achievement>)request.getAttribute("lista_achievements");
					for(int i=0;i<achievements.size();i++){
						out.print("<article class=\"list\">\n"+
									"<p style=\"width:50%;\">"+achievements.get(i).getDesc()+"</p>"+
									"<p style=\"width:30%;\">"+achievements.get(i).getTipo()+"</p>"+
								"</article>\n"+
								"<br/>\n\n");
					}
					if(achievements.size()==0) out.print("<h2>Nessun achievement conseguito.</h2>");
					out.print("</div>");
				}else{
					out.print("<h2>Non puoi visualizzare gli achievements di un gioco che non esiste...</h2>");
				}
			}else{
				out.print("<h2>Non hai eseguito l'accesso.</h2>");
			}
			
				
		%>
	</section>
<%@ include file="footer.html" %>