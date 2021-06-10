/**
 * @author Fernando Bino Machado
 * @see https://github.com/devBino
 */
package app;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.XMLConstants;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.Writer;
import java.io.StringWriter;
import java.io.StringReader;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlNode {

	private StringBuilder conteudoXml;
	private String nomeArquivo;
	private Document document;
	
	/**
	 * Constroi novo XmlNode usando arquivo
	 * @param pNomeArquivo String - Deverá ser nome.extensão, Exemplo: caminho/meuArquivo.xml
	 */
	public XmlNode(String pNomeArquivo) {
		
		nomeArquivo = pNomeArquivo;
		
		try {
			setDocument(pNomeArquivo);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Constroi novo XmlNode usando documento já existente
	 * @param pDocument Document - Um objeto do tipo Document que já tenha sido processado
	 */
	public XmlNode(Document pDocument) {
		
		document = pDocument;
		setDocument(pDocument);
		
	}
	
	/**
	 * Constroi novo XmlNode usando StringBuilder 
	 * @param pXml StringBuilder - Um objeto do tipo StringBuilder com conteúdo do xml
	 */
	public XmlNode(StringBuilder pXml) {
		
		conteudoXml = pXml;
		setDocument(pXml);
		
	}
	
	
	
	
	
	
	

	/**
	 * Seta document usando arquivo
	 * @param pNomeArquivo String - Nome do arquivo xml contendo xml para setar em Document document 
	 */
	public void setDocument(String pNomeArquivo) 
		throws SAXException, IOException, ParserConfigurationException {
		
		File xmlFile = new File(pNomeArquivo);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		factory.setValidating(false);
		
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		document = dBuilder.parse(new FileInputStream(xmlFile));
								
	}
	
	/**
	 * Seta document usando Document pDocument já existente
	 * @param pDocument Document - Deve ser objeto já processado e que será usado para setar Document document
	 */
	public void setDocument(Document pDocument) {
		document = pDocument;
	}
	
	
	/**
	 * Convertendo string do StringBuilder para Document, seta o document
	 * @param pXml StringBuilder com dados do xml
	 */
	public void setDocument(StringBuilder pXml) {
	
		try {
			StringReader reader = new StringReader(pXml.toString());
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			document = factory.newDocumentBuilder().parse(new InputSource(reader));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Document getDocument() {
		return document;
	}

	/**
	 * @param pTagFilha String - A nova Tag que será adicionada no Documento Xml
	 * @param pTagPai String - A Tag pai de pTagFilha, ou seja, um nível acima da Tag filha
	 * @param pConteudo String - Caso queira iniciar a nova Tag com algum texto, passar uma String,
	 *	se não, apenas passar null.
	 */
	public void addTag(String pTagFilha, String pTagPai, String pConteudo) {
		
		NodeList nodeList = document.getElementsByTagName(pTagPai);
		Node nodeTagPai = nodeList.item(0);
		
		if( nodeTagPai.getNodeType() == Node.ELEMENT_NODE ) {
			
			Element e = (Element) nodeTagPai;
			Node n = document.createElement(pTagFilha);
			
			if( pConteudo != null ) {
				n.setTextContent(pConteudo);
			}
			
			e.appendChild(n);
			
		}
				
	}
	
	/**
	 * Remove a tag informada do document xml
	 * @param prTag String - A Tag a ser removida do documento xml
	 */
	public void removeTag(String pTag) {
		
		Element e = (Element) document.getElementsByTagName(pTag).item(0);
		e.getParentNode().removeChild(e);
		
		document.normalize();
		
	}

	/**
	 * Imprime tags dentro de uma determinada tag
	 * @param prTag String - A Tag que se deseja imprimir seu conteúdo
	 */
	public void printTag(String pTag) {
		
		NodeList nodeList = document.getElementsByTagName(pTag);
				
		for( int i=0; i<nodeList.getLength(); i++ ) {
			
			Node nodePai = nodeList.item(i);
			
			System.out.println("> Nó Pai: " + nodePai);
			
			NodeList nodesFilhos = nodePai.getChildNodes();
			
			for( int j=0; j<nodesFilhos.getLength(); j++ ) {
				
				Node nodeFilho = nodesFilhos.item(j);
				
				System.out.println("\t> Nó Filho: " + nodeFilho);
				
				if( nodeFilho.getNodeType() == Node.ELEMENT_NODE ) {
					Element elem = (Element) nodeFilho;					
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
        tf.transform(new DOMSource(document), new StreamResult(out));
        
        return out.toString();
	}
	
}