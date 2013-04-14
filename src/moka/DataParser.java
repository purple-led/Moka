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
    public void addNode(Word word){
        Word[] tmpWords = engine.data;
        engine.data = new Word[engine.data.length+1];
        for(int i=0; i<tmpWords.length; i++)
            engine.data[i] = tmpWords[i];
        engine.data[tmpWords.length] = word;
        
        Element elem = doc.createElement("word");
        elem.setAttribute("eng",word.english);
        elem.setAttribute("rus",word.russian);
        elem.setAttribute("r2e",String.valueOf(word.value_r2e));
        elem.setAttribute("e2r",String.valueOf(word.value_e2r));        
        
        if (word.isActive) elem.setAttribute("act","true");
        else elem.setAttribute("act","false");
        
        Element rootelement = doc.getDocumentElement();
        rootelement.appendChild(elem);
        
        XMLWrite();
    }
    
    private int findNode(Word word){
        int n = -1;
        for(int i = 0; i < engine.data.length; i++){
            if(engine.data[i].equals(word)) {n = i; break;}
        }
        return n;
    }
    
    private Node findDocNodeByWord(Word word){
        Node node = null;

        NodeList nodeList = doc.getElementsByTagName("word");
        for (int i = 0; i < nodeList.getLength(); i++){
            node = nodeList.item(i);
            
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                 Element eElement = (Element) node;
                 if ((eElement.getAttribute("eng").equals(word.english))&&
                     (eElement.getAttribute("rus").equals(word.russian))) break;             
            }
        }
        
        return node;
    }
    
    public void changeNode(Word newWord, Word oldWord){
        int iWord = findNode(oldWord);
        Node oldNode = findDocNodeByWord(oldWord);
        if(iWord == -1 || oldNode == null) return;
        
        engine.data[iWord] = newWord;
        
        Element elem = doc.createElement("word");
        elem.setAttribute("eng",newWord.english);
        elem.setAttribute("rus",newWord.russian);
        elem.setAttribute("r2e",String.valueOf(newWord.value_r2e));
        elem.setAttribute("e2r",String.valueOf(newWord.value_e2r));        
        
        if (newWord.isActive) elem.setAttribute("act","true");
        else elem.setAttribute("act","false");
        doc.getDocumentElement().replaceChild(elem, oldNode);
        
        XMLWrite();
    }
    
    public void deleteNode(Word word){
        int iWord = findNode(word);
        if(iWord == -1) return;
        
        Word[] tmpWords = engine.data;
        engine.data = new Word[engine.data.length - 1];
        
        for(int i = 0, t = 0; i < engine.data.length; i++){
            if(i == iWord) t++;
            engine.data[i] = tmpWords[i + t];
        }
        
        doc.getDocumentElement().removeChild(findDocNodeByWord(word));
        XMLWrite();
    }
    
    public void clearData(){
        engine.data = new Word[0];
        
        NodeList nodeList = doc.getElementsByTagName("word");
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                doc.getDocumentElement().removeChild(node);
            }
        }
        
        XMLWrite();
    }
    
    //ХЗ, как это работает.
    public void XMLWrite(){
        try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(file));
		transformer.transform(source, result);
        } catch (Exception e) {e.printStackTrace();}
    }
    
    public DataParser(LogicEngine engine){
        this.engine = engine;
        try {
            File fXmlFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Parsing XML Data Base file.");
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");

            NodeList nodeList = doc.getElementsByTagName("word");

            engine.data = new Word[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {

                    Node nNode = nodeList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element elem = (Element) nNode;

                            boolean isa;
                            if (elem.getAttribute("act").equals("true"))isa = true;
                            else isa = false;

                            engine.data[i] = new Word(elem.getAttribute("rus"),
                                                elem.getAttribute("eng"),
                                                Integer.parseInt(elem.getAttribute("r2e")),
                                                Integer.parseInt(elem.getAttribute("e2r")),
                                                isa);
                    }

                    System.out.println(engine.data[i].toString());
            }
        } catch (Exception e) {e.printStackTrace();}
}
        public LogicEngine engine;
        private Document doc;
        private String file = "src/moka/dictionary.xml";
}