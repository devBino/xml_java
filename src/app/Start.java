package app;

import java.util.ArrayList;

public class Start {

	public static void main(String[] args) {

		try {
			
			StringBuilder testeXml = new StringBuilder();
			
			testeXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			testeXml.append("<Contatos>");
			testeXml.append("</Contatos>");
			
			XmlNode xml = new XmlNode(testeXml);
						
			xml.addTag("ListaContatos","Contatos",null);
			
			for(Contato c : contatosFake()) {
				
				String strDados = c.getNome()+";"+c.getTelefone()+";Informação Provisória (Refatorar!!!)";
				xml.addTag("Contato","ListaContatos",strDados);
			}
			
			System.out.println(xml.getStringDocument());
			System.out.println("__________________________________________________________");
					 
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<Contato> contatosFake(){
	
		ArrayList<Contato> contatos = new ArrayList<Contato>();
		
		contatos.add(new Contato("Fernando Bino Machado","41 99746####"));
		contatos.add(new Contato("Paulo Guedes","41 87662####"));
		contatos.add(new Contato("Seu Barriga","45 83728####"));
		contatos.add(new Contato("Drax","53 65778####"));
		contatos.add(new Contato("Warren Buffet","## #########"));
		contatos.add(new Contato("Fred Mercuri","45 333334444"));
		
		return contatos;
		
	}

}