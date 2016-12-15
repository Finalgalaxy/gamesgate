<%@ page
		language="java"
		contentType="text/html; charset=UTF-8"
   		pageEncoding="UTF-8"
   		
%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="models.*" %>
<%
ResultSet user = (ResultSet)request.getAttribute("user");
/*Class.forName("com.mysql.jdbc.Driver");
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/negoziovid?user=root&password=unisatesting");
Statement statement = connection.createStatement();
ResultSet rs=null;
PreparedStatement preparedStatement=null;
ResultSet user=null;
if(session.getAttribute("user") != null){
	user=statement.executeQuery("SELECT * FROM UTENTE NATURAL JOIN ACCOUNT WHERE EMAIL_ACCOUNT = '"+session.getAttribute("user")+"'");
	user.next();
}*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html">
	<meta charset="UTF-8">
	<link type="text/css" rel="stylesheet" href="./css/style.css">
	<link type="text/css" rel="stylesheet" href="./css/article.css">
	<link type="text/css" rel="stylesheet" href="./css/slider.css">
	<link type="text/css" rel="stylesheet" href="./css/fix_phones.css">
	<link href="./images/favicon.ico" rel="icon" type="image/x-icon"/>
	<title>-Games Gate-</title>
</head>
<body>
	<script type="text/javascript">
	function executeAjax(str_key){
		var xhr = new XMLHttpRequest();
		xhr.open("GET", "./RicercaDinamicaServlet?str_key="+str_key, true);
		xhr.setRequestHeader("connection", "close");
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 1){
				//document.getElementById("status").innerHTML = "Server connection";
			}else if(xhr.readyState == 2){
				//document.getElementById("status").innerHTML = "Send request";
			}else if(xhr.readyState == 3){
				//document.getElementById("status").innerHTML = "Receive response";
			}else if(xhr.readyState == 4){
				
				//document.getElementById("status").innerHTML = "Request finished and response is ready";
				
				if(xhr.status == 200 || xhr.status == 304){
					var xml_document = xhr.responseXML;

					
					var videogames = xml_document.getElementsByTagName("videogame");
					
					document.getElementById("search").innerHTML = "";
					for(var i=0;i<videogames.length;i++){
						
						document.getElementById("search").innerHTML += "<article class=\"search_list\">"+
							"<a href=\"./Articolo?id="+videogames[i].getElementsByTagName("nome")[0].innerHTML+"\">"+
								"<div>"+
									"<img src=\"./images/label/"+videogames[i].getElementsByTagName("img")[0].innerHTML+"\"/>"+
									videogames[i].getElementsByTagName("nome")[0].innerHTML+
								"</div>"+
							"</a>"+
						"</article>";
						//document.getElementById("search").innerHTML += videogames[i].getElementsByTagName("nome")[0].innerHTML + " | ";
						//document.getElementById("search").innerHTML += videogames[i].getElementsByTagName("prezzo")[0].innerHTML + " euro<br/>";
					}
					//document.getElementById("elenco_vid").innerHTML = 
				}else{
					//document.getElementById("status").innerHTML = "Response error "+ req.status + " " + req.statusText;
				}
			}
		}
		xhr.send(null);
	}
	</script>
	<nav id="only_for_phones">Versione mobile.</nav>
	<header>
		
		<nav id="barra_accesso">
			<ul>
				<%
					if(user != null){
						out.print("<li><a href=\"./Profilo\">Benvenuto, "+user.getString("nome")+"!</a></li>\n"+
									"<li>|</li>\n"+
									"<li><span class=\"hide_for_phone\">email corrente: </span>"+user.getString("EMAIL_ACCOUNT")+"</li>\n"+
									"<li>|</li>\n"+
									"<li><a href=\"./CaricaSoldi\">saldo: "+user.getString("saldo")+" &euro;</a></li>\n"+
									"<li>|</li>\n"+
									"<li><a href=\"./User?action=exit\">esci</a></li>\n");
					}else{
						out.print("<li><a href=\"./User?mode=login\">accedi</a></li>\n"+
							"<li>|</li>\n"+
							"<li><a href=\"./User?mode=register\">registrati</a></li>\n");
					}
				%>
			</ul>
		</nav>
		<div class="logo">
			<a href="./"><span>G</span>ames <span>G</span>ate</a>
		</div>
		<nav id="barra_menu">
			<ul>
				<li><a href="./">Negozio</a></li>
				<li><a href="./Page?name=informazioni.jsp">Informazioni</a></li>
				<li><a href="./Page?name=assistenza.jsp">Assistenza</a></li>
			</ul>
		</nav>
	</header>

	<nav id="barra_menu_principale">
		<form action="./Catalogo" method="GET">
			<ul>
				<li><a href="./Articolo?mode=random">Random moment</a></li>
				<li><a href="./Catalogo">Catalogo</a></li>
				<li><a href="./ListaProduttori">Lista produttori</a></li>
				<li><a href="./ListaMembri">Lista membri</a></li>
				<li><a href="./News">Novità</a></li>
				<li><input type="text" name="q" autocomplete="off" onkeyup="executeAjax(document.getElementsByName('q')[0].value)"/>
					<div id="search"></div>
				</li>
				<li><input type="submit" value="&gt;&gt;"/></li>
			</ul>
		</form>
	</nav>
	
	
	<div id="content">
		
	