<%@ include file="header.jsp" %>
	<%
		ArrayList<Genere> generi = (ArrayList<Genere>)request.getAttribute("lista_generi");
		ArrayList<Produttore> prod = (ArrayList<Produttore>)request.getAttribute("lista_produttori");
		ArrayList<Videogame> vids = (ArrayList<Videogame>)request.getAttribute("lista_videogames");
	%>
	<section id="left_s">
		<b>Genere:</b>
		<ul>
			<%
			for(int i=0;i<generi.size();i++)
				out.print("<li><a href=\"./Catalogo?genere="+generi.get(i).getNome()+"\">"+generi.get(i).getNome()+"</a></li>");
			%>
		</ul>
		<br/><br/><br/>
		<b>Produttori:</b>
		<ul>
			<%
			for(int i=0;i<prod.size();i++)
				out.print("<li><a href=\"./Produttore?name="+prod.get(i).getNome()+"\">"+prod.get(i).getNome()+"</a></li>");
			%>
		</ul>
		
	</section>
	<aside>
		<h1 id="scorrimento">Benvenuto nel sito! Ecco alcuni dei nostri prodotti:</h1>
		<script type="text/javascript">
			var margin_left=0;
			var direzione_destra=true;
			var amount=0.5;
			setInterval(
					function(){
						if(direzione_destra) margin_left+=amount;
						else				 margin_left-=amount;
						if(margin_left==400 && direzione_destra){
							direzione_destra = false;
						}else if(margin_left==0 && !direzione_destra){
							direzione_destra = true;
						}
						document.getElementById("scorrimento").setAttribute("style", "margin-left:"+margin_left+"px;");
					}
				, 10);
		</script>
		<div id="slider">
			<figure>
				<%
					for(int i=0;i<vids.size();i++)
						out.print("<a href=\"./Articolo?id="+vids.get(i).getNome()+"\"><img src=\"./images/cover/"+vids.get(i).getImg()+"\"/></a>");
				%>
			</figure>
		</div>
	</aside>
	
<%@ include file="footer.html" %>