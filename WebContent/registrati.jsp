<%@ include file="header.jsp" %>
	<section id="full">
		<h2>Registrazione</h2>
		<%
			if(user != null){
				out.print("<h4><b><u>NOTA BENE</u></b>: lei ha effettuato l'accesso al sito con il seguente CF UTENTe: "+user.getString("CF_UTENTE")+"; essendo già nostro utente, il nuovo account eventualmente richiesto verrà associato a tale codice. <b>Nel caso si voglia iscrivere come un nuovo UTENTe, la preghiamo di scollegarsi prima da questo account!</b></h4>");
			}else{
				out.print("<h4><b><u>NOTA BENE</u></b>: lei NON ha effettuato l'accesso al sito; se lei è un nuovo UTENTe ignori semplicemente questo messaggio... ma se lei è già un nostro utente, effettui prima l'accesso per collegare correttamente il suo nuovo account con il vostro CF UTENTe!</h4>");
			}
		%>
		
		<form action="./User" method="POST">
			<%
				if(user == null){
			%>
				<h3>Informazioni relative al utente...</h3><br/>
				<input type="hidden" name="action" value="registrazione"/>
				CF utente: <input type="text" name="CF_utente" style="width:100px;" value=""/><br/>
				Nome: <input type="text" name="nome" style="width:100px;" value=""/><br/>
				Cognome:  <input type="text" name="cognome" style="width:100px;" value=""/><br/>
				Via:  <input type="text" name="via" style="width:100px;" value=""/><br/>
				CAP:  <input type="text" name="cap" style="width:100px;" value=""/><br/>
				N° civico:  <input type="text" name="n_civico" style="width:100px;" value=""/><br/>
			<%
				}else{
					out.print("<input type=\"hidden\" name=\"CF_utente\" value=\""+user.getString("CF_UTENTE")+"\"/>");
				}
			%>
			<h3>Informazioni relative all'account...</h3><br/>
			Email:  <input type="text" name="username" style="width:100px;" value=""/><br/>
			Password:  <input type="password" name="password" style="width:100px;" value=""/><br/>
			<input type="submit" value="Iscriviti!"/>
		</form>
	</section>
<%@ include file="footer.html" %>