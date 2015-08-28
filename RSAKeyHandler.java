package br.com.monty.android.crypt;

import java.math.BigInteger;

import org.kobjects.base64.Base64;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class RSAKeyHandler implements ContentHandler {

	public RSAKeyHandler() {
		this.decodeBase64 = true;
	}
	
	private StringBuffer buffer = new StringBuffer();
	
	private Boolean decodeBase64;
	public void setDecodeBase64(Boolean decodeBase64) {
		this.decodeBase64 = decodeBase64;
	}
	public Boolean getDecodeBase64() {
		return decodeBase64;
	}

	private BigInteger modulus;
	public void setModulus(BigInteger modulus) {
		this.modulus = modulus;
	}
	public BigInteger getModulus() {
		return modulus;
	}
	
	private BigInteger exponent;
	public void setExponent(BigInteger exponent) {
		this.exponent = exponent;
	}
	public BigInteger getExponent() {
		return exponent;
	}
	
	public void setDocumentLocator (Locator locator) { 
	}

	public void startDocument () throws SAXException  { 
	}

	public void endDocument() throws SAXException {  
	}

	public void startPrefixMapping (String prefix, String uri) throws SAXException {  
	}

	public void endPrefixMapping (String prefix) throws SAXException  {  
	}

	public void startElement (String uri, String localName, String qName, Attributes atts) throws SAXException  {
		if (localName.equalsIgnoreCase("modulus")) {		
		} else if (localName.equalsIgnoreCase("exponent")) {	
		}
	}

	public void endElement (String uri, String localName, String qName) throws SAXException {
		if (localName.equalsIgnoreCase("modulus")) {
			this.setModulus(new BigInteger(1, Base64.decode(buffer.toString().trim())));
		} else if (localName.equalsIgnoreCase("exponent")) {
			this.setExponent(new BigInteger(1, Base64.decode(buffer.toString().trim())));
		}
		
		buffer = new StringBuffer();
	}

	public void characters (char ch[], int start, int length) throws SAXException {
		buffer.append(ch, start, length);
	}

	public void ignorableWhitespace (char ch[], int start, int length) throws SAXException {  
	}

	public void processingInstruction (String target, String data) throws SAXException {  
	}

	public void skippedEntity (String name) throws SAXException {  
	}
}
