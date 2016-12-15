package servletpackage;

public class FunzioniStatiche {
	public static String filter(String str){
		if(str != null){
			StringBuffer x = new StringBuffer("");
			char c;
			for(int i=0;i<str.length();i++){
				c=str.charAt(i);
				switch(c){
					case '<': x.append("&lt;"); break;
					case '>': x.append("&gt;"); break;
					case '"': x.append("&quot;"); break;
					case '&': x.append("&amp;"); break;
					default: x.append(c);
				}
			}
			return x.toString();
		}else{
			return null;
		}
	}
}
