<%@ include file="header.jsp" %>
	<section id="full">
		<h2>Lista produttori:</h2>
			<div id="scroller_x">
			<%
			//rs=statement.executeQuery("SELECT * FROM PRODUTTORE ORDER BY NOME_PRODUTTORE ASC;");//
			ArrayList<Produttore> prod= (ArrayList<Produttore>)request.getAttribute("lista_produttori");
			/*while(rs.next())*/ 
			
			if(prod!=null){
				for(int i=0;i<prod.size();i++)
				{
				/*n_rows_search++;*/
					out.print("<article class=\"banner\">\n"+
									"<a href=\"./Produttore?name="+prod.get(i).getNome()+"\"><img src=\"./images/banner/"+prod.get(i).getImg()+"\"></a>"+
									"<span><h2>"+prod.get(i).getNome()+"</h2><br/><br/>"+
								prod.get(i).getDescrizione()+
								"</span>"+
								"</article>"+
										"\n"+
								"\n\n");
				}
			/*if(n_rows_search==0) out.print*/if(prod.size() == 0){
					out.print("<h2>Nessun produttore trovato.</h2>");
			}
		}	
			%>
			</div>
	</section>
<%@ include file="footer.html" %>