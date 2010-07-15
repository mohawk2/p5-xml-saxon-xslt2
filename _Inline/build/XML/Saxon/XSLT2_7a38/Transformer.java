
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
	private XsltExecutable xslt;
	private Processor proc;
	private HashMap<String, XdmAtomicValue> params;

	public Transformer (String stylesheet)
		throws SaxonApiException
	{
		proc = new Processor(false);
		XsltCompiler comp = proc.newXsltCompiler();
		xslt = comp.compile(new StreamSource(new StringReader(stylesheet)));
		params = new HashMap<String, XdmAtomicValue>();
	}
	
	public void paramClear ()
	{
		params.clear();
	}

	public void paramAddString (String key, String value)
	{
		params.put(key, new XdmAtomicValue(value));
	}

	public void paramAddLong (String key, long value)
	{
		params.put(key, new XdmAtomicValue(value));
	}

	public void paramAddDecimal (String key, decimal value)
	{
		params.put(key, new XdmAtomicValue(value));
	}

	public void paramAddFloat (String key, float value)
	{
		params.put(key, new XdmAtomicValue(value));
	}

	public void paramAddDouble (String key, double value)
	{
		params.put(key, new XdmAtomicValue(value));
	}

	public void paramAddBoolean (String key, bool value)
	{
		params.put(key, new XdmAtomicValue(value));
	}

	public void paramAddURI (String key, URI value)
	{
		params.put(key, new XdmAtomicValue(value));
	}

	public void paramAddQName (String key, String value)
	{
		params.put(key, new XdmAtomicValue(new QName(value.toString())));
	}

	public void paramRemove (String key)
	{
		params.remove(key);
	}

	public XdmAtomicValue paramGet (String key)
	{
		return params.get(key);
	}

	public String transform (String doc, String method)
		throws SaxonApiException
	{
		XdmNode source = proc.newDocumentBuilder().build(
			new StreamSource(new StringReader(doc))
			);

		XsltTransformer trans = xslt.load();
		trans.setInitialContextNode(source);

		Serializer out = new Serializer();
		out.setOutputProperty(Serializer.Property.METHOD, method);
		StringWriter sw = new StringWriter();
		out.setOutputWriter(sw);
		trans.setDestination(out);
		
		Iterator i = params.keySet().iterator();
		while (i.hasNext())
		{
			Object k = i.next();
			Object v = params.get(k);
			
			trans.setParameter(new QName(k.toString()), v);
		}

		trans.transform();
		
		return sw.toString();
	}
}

