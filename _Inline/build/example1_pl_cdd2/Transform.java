
import net.sf.saxon.option.dom4j.DOM4JObjectModel;
import net.sf.saxon.option.jdom.JDOMObjectModel;
import net.sf.saxon.option.xom.XOMObjectModel;
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

public class Transform {
	public void run() throws SaxonApiException {
		Processor proc = new Processor(false);
		XsltCompiler comp = proc.newXsltCompiler();
		String stylesheet =
			"<xsl:transform version='2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>" +
			"  <xsl:param name='in'/>" +
			"  <xsl:template name='main'><xsl:value-of select=\"contains($in, 'e')\"/></xsl:template>" +
			"</xsl:transform>";
		XsltExecutable exp = comp.compile(new StreamSource(new StringReader(stylesheet)));

		Serializer out = new Serializer();
		out.setOutputProperty(Serializer.Property.METHOD, "text");
		XsltTransformer t = exp.load();
		t.setInitialTemplate(new QName("main"));

		String[] fruit = {"apple", "banana", "cherry"};
		QName paramName = new QName("in");
		for (String s: fruit) {
			StringWriter sw = new StringWriter();
			out.setOutputWriter(sw);
			t.setParameter(paramName, new XdmAtomicValue(s));
			t.setDestination(out);
			t.transform();
			System.out.println(s + ": " + sw.toString());
		}
	}
}

