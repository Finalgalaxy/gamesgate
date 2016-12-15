<%@ include file="header.jsp" %>
	<section id="full">
		<h2>Inserisci produttore</h2>
		
		<form method="post" action="./Admin" enctype="multipart/form-data">
			<input type="hidden" name="action" value="insert_produttore"/>
			Nome: <input type="text" name="nome_produttore"/><br/>
			Immagine: <input type="file" name="file" size="50" required/><br/>
			Descrizione: <textarea rows="6" cols="50" name="descrizione"></textarea><br/>
			Anni exp: <input type="text" name="anni_exp"/><br/>
			<input type="submit" value="Inserisci produttore"/>
			<input type="reset" value="Reset"/>
		</form>
	</section>
<%@ include file="footer.html" %>