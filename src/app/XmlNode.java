/**
 * @author Fernando Bino Machado
 * @see https://github.com/devBino
 */
package app;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.Writer;
import java.io.StringWriter;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlNode {

	private String nomeArquivo;
	private Document doc;
	
	/**
	 * @param prNomeArquivo String - Deverá ser nome.extensão, Exemplo: caminho/meuArquivo.xml
	 * 
	 * DETALHES
	 * 		Construtor inicia XmlNode, previamente setando Document doc 
	 * 		baseando-se no prNomeArquivo que deverá conter o xml a ser setado em Document doc
	 */
	public XmlNode(String prNomeArquivo) {
		
		nomeArquivo = prNomeArquivo;
		
		try {
			setDocument(prNomeArquivo);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param prDocument Document - Um objeto do tipo Document que já tenha sido processado
	 * 
	 * DETALHES
	 * 		Caso já exista um objeto do tipo Document processado
	 * 		este deverá ser passado nesse construtor de diferente assinatura
	 s*/
	public XmlNode(Document prDocument) {
		
		doc = prDocument;
		
		setDocument(prDocument);
		
	}
	
	/**
	 * @param prNomeArquivo String - Nome do arquivo xml contendo xml para setar em Document doc
	 * 
	 * DETALHES
	 * 		Seta Document doc atraves de um arquivo.xml recebido,
	 * 		cujo qual deverá conter o xml a ser processado
	 * 
	 */
	public void setDocument(String prNomeArquivo) 
		throws SAXException, IOException, ParserConfigurationException {
		
		File xmlFile = new File(prNomeArquivo);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		factory.setValidating(false);
		
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		doc = dBuilder.parse(new FileInputStream(xmlFile));
								
	}
	
	/**
	 * @param prDocument Document - Deve ser objeto já processado e que será usado para setar Document doc
	 * 
	 * DETALHES 
	 * 		Seta Document doc atraves de document já processado recebido
	 *  	como parametro do tipo Document
	 */
	public void setDocument(Document prDocument) {
		doc = prDocument;
	}
	
	public Document getDocument() {
		return doc;
	}

	/**
	 * @param prTagFilha String - A nova Tag que será adicionada no Documento Xml
	 * @param prTagPai String - A Tag pai de prTagFilha, ou seja, um nível acima da Tag filha
	 * @param prConteudo String - Caso queira iniciar a nova Tag com algum texto, passar uma String,
	 *	se não, apenas passar null.
	 */
	public void addTag(String prTagFilha, String prTagPai, String prConteudo) {
		
		NodeList nodeList = doc.getElementsByTagName(prTagPai);
		Node nodeTagPai = nodeList.item(0);
		
		if( nodeTagPai.getNodeType() == Node.ELEMENT_NODE ) {
			
			Element e = (Element) nodeTagPai;
			Node n = doc.createElement(prTagFilha);
			
			if( prConteudo != null ) {
				n.setTextContent(prConteudo);
			}
			
			e.appendChild(n);
			
		}
				
	}
	
	/**
	 * @param prTag String - A Tag a ser removida do documento xml
	 */
	public void removeTag(String prTag) {
		
		Element e = (Element) doc.getElementsByTagName(prTag).item(0);
		e.getParentNode().removeChild(e);
		
		doc.normalize();
		
	}

	/**
	 * @param prTag String - A Tag que se deseja imprimir seu conteúdo
	 */
	public void printTag(String prTag) {
		
		NodeList nodeList = doc.getElementsByTagName(prTag);
				
		for( int i=0; i<nodeList.getLength(); i++ ) {
			
			Node nNode = nodeList.item(i);
			
			System.out.println("> Nó Pai: " + nNode);
			
			NodeList nodesFilhos = nNode.getChildNodes();
			
			for( int j=0; j<nodesFilhos.getLength(); j++ ) {
				
				Node nFilho = nodesFilhos.item(j);
				
				System.out.println("\t> Nó Filho: " + nFilho);
				
				if( nFilho.getNodeType() == Node.ELEMENT_NODE ) {
					Element elem = (Element) nFilho;					
				}
				
			}
			
		}	
		
	}
	
	/**
	 * @return String - Xml identado para visualização
	 * @throws Exception
	 */
	public String getStringDocument() throws Exception {
		
		Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(doc), new StreamResult(out));
        
        return out.toString();
	}
	
}