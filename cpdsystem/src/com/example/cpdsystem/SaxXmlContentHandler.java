package com.example.cpdsystem;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxXmlContentHandler extends DefaultHandler {
	private boolean  in_mytag = false;
	
	private ParsedExampleDataSet myParsedExampleDataSet = new ParsedExampleDataSet(); 
 
	   public ParsedExampleDataSet getParsedData() { 
	         return this.myParsedExampleDataSet; 
	    } 
	    public void startDocument() throws SAXException { 
	         this.myParsedExampleDataSet = new ParsedExampleDataSet(); 
	    } 
	    public void endDocument() throws SAXException { 
	    } 

	    public void startElement(String namespaceURI, String localName, 
	              String qName, Attributes atts) throws SAXException { 
	      if (localName.equals("data")) { 
	              this.in_mytag = true; 
	         }
	          
	    } 
	    public void endElement(String namespaceURI, String localName, String qName) 
	              throws SAXException { 
	         if (localName.equals("data")) { 
	              this.in_mytag = false; 
	         }
	    } 
	     

	   public void characters(char ch[], int start, int length) { 
	         if(this.in_mytag){ 
	         myParsedExampleDataSet.setExtractedString(new String(ch, start, length)); 
	    } 
	   } 


}
