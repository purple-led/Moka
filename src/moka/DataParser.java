package moka;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author EvilZerg
 */
public class DataParser {
    public Word[] getData(){
        return words;
    }
    
    public void addNode(Word word){
        Word[] tmpWords = words;
        words = new Word[words.length+1];
        for(int i=0; i<tmpWords.length; ++i)
            words[i] = tmpWords[i];
        words[tmpWords.length] = word;
        
        Element temp = doc.createElement("word");
        temp.setAttribute("eng",word.english);
        temp.setAttribute("rus",word.russian);
        temp.setAttribute("r2e",String.valueOf(word.value_r2e));
        temp.setAttribute("e2r",String.valueOf(word.value_e2r));        
        
        if (word.isActive) temp.setAttribute("act","true");
        else temp.setAttribute("act","false");
        
        Element rootelement = doc.getDocumentElement();
        rootelement.appendChild(temp);
    }
    
    //ХЗ, как это работает.
    public void XMLWrite(){
        try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(file));
		transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public DataParser(){
        try {
            File fXmlFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Parsing XML Data Base file.");
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");

            nList = doc.getElementsByTagName("word");

            words = new Word[nList.getLength()];

            for (int i = 0; i < nList.getLength(); i++) {

                    Node nNode = nList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element eElement = (Element) nNode;

                            boolean isa;
                            if (eElement.getAttribute("act").equals("true"))isa = true;
                            else isa = false;

                            words[i] = new Word(eElement.getAttribute("rus"),
                                                eElement.getAttribute("eng"),
                                                Integer.parseInt(eElement.getAttribute("r2e")),
                                                Integer.parseInt(eElement.getAttribute("e2r")),
                                                isa);
                    }

                    System.out.println(words[i].toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}
        private Word[] words;
        private NodeList nList;
        private Document doc;
        private String file = "src/moka/dictionary.xml";
}
