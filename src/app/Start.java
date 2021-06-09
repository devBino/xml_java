package app;

public class Start {

	public static void main(String[] args) {

		try {
			
			XmlNode xml = new XmlNode("teste.xml");
						
			xml.addTag("sis:pParam","treino",null);
			xml.addTag("sis1:P1","sis:pParam","valor para tag P1");
			xml.addTag("sis1:P2","sis:pParam","valor para tag P2");
			
			System.out.println(xml.getStringDocument());
			System.out.println("__________________________________________________________");
			
			xml.removeTag("sis:pParam");
			
			System.out.println(xml.getStringDocument());
			System.out.println("__________________________________________________________");
					 
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}