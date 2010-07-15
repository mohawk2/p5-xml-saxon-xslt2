
import net.sf.saxon.s9api.*;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

public class Transformer
{
	public XsltExecutable xslt;
	public Processor proc;

	public Transformer (String stylesheet)
		throws SaxonApiException
	{
		proc = new Processor(false);
		XsltCompiler comp = proc.newXsltCompiler();
		xslt = comp.compile(new StreamSource(new StringReader(stylesheet)));
	}
	
	public XmlDestination run (String doc)
		throws SaxonApiException
	{
		XdmNode source = proc.newDocumentBuilder().build(
			new StreamSource(new StringReader(doc))
			);
		
		XdmDestination out = new XmlDestination();

		XsltTransformer trans = xslt.load();
		trans.setInitialContextNode(source);
		trans.setDestination(out);
		trans.transform();
		
		return out;
	}
}

