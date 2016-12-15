<%@ include file="header.jsp" %>
	<section id="full">
		<h2>Inserisci videogioco</h2>
		
		<form method="post" action="./Admin" enctype="multipart/form-data">
			<input type="hidden" name="action" value="insert_videogame"/>
			Nome: <input type="text" name="nome_videogame"/><br/>
			Immagine: <input type="file" name="file" size="50" multiple required/><br/>
			N° uscita: <input type="text" name="n_uscita" /><br/>
			Prezzo: <input type="text" name="prezzo"/><br/>
			Nome del produttore: <input type="text" name="nome_produttore"/><br/>
			Anno d'uscita: <input type="text" name="anno_uscita" /><br/>
			<input type="submit" value="Inserisci videogioco"/>
			<input type="reset" value="Reset"/>
		</form>
	</section>
<%@ include file="footer.html" %>