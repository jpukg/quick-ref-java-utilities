package org.bytebrain.xml;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPOutputStream;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class XmlUtils.
 */
public final class XmlUtils {

	/** The Constant LOGGER. */
	private static final Logger LOG = LoggerFactory.getLogger(XmlUtils.class);

	private static final String UTF_8 = "UTF-8";

	/** The contexts. */
	@SuppressWarnings("rawtypes")
	private static Map<Class, JAXBContext> contexts = new ConcurrentHashMap<>();

	/**
	 * Instantiates a new xml utils.
	 */
	private XmlUtils() {
		// hide
	}

	/**
	 * Gets the context.
	 *
	 * @param clazz the clazz
	 * @return the context
	 * @throws JAXBException the JAXB exception
	 */
	@SuppressWarnings("rawtypes")
	private static synchronized JAXBContext getContext(Class clazz) throws JAXBException {
		// Attention: cache only JAXBContext
		JAXBContext context = contexts.get(clazz);
		if (context == null) {
			context = JAXBContext.newInstance(clazz);
			contexts.put(clazz, context);
		}
		return context;
	}

	/**
	 * This method is used to convert xml into an object.
	 *
	 * @param <T>       the generic type
	 * @param is        the inputSteam
	 * @param jaxbClass the jaxb class
	 * @return the return object type
	 */
	public static <T> T unmarshall(InputStream is, Class<T> jaxbClass) {
		return JAXB.unmarshal(new InputStreamReader(is, StandardCharsets.UTF_8), jaxbClass);
	}

	/**
	 * Clear cache.
	 */
	public static void clearCache() {
		contexts.clear();
	}

	/**
	 * Marshal object to string.
	 *
	 * @param <T>          the generic type
	 * @param object       the object
	 * @param namespaceURI the namespace URI
	 * @return the string
	 * @throws JAXBException the JAXB exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> String marshalToString(T object, String namespaceURI) throws JAXBException {
		LOG.info("marshalToString()- start");
		String rootElementName;
		JAXBContext context = getContext(object.getClass());
		javax.xml.bind.Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
		marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
		StringWriter sw = new StringWriter();
		rootElementName = object.getClass().getSimpleName();
		marshaller.marshal(new JAXBElement(new QName(namespaceURI, rootElementName), object.getClass(), object), sw);
		LOG.info("marshalToString()- end");
		return sw.toString();
	}

	/**
	 * This method is used to convert an JAXB object to XML String.
	 *
	 * @param <T>    the generic type
	 * @param object the object
	 * @return the string
	 */
	public static <T> String convertObjectToXML(T object) {
		LOG.info("convertObjectToXML()- start");
		StringWriter stringWriter = new StringWriter();
		try {
			JAXBContext context = getContext(object.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, UTF_8);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
			marshaller.marshal(object, stringWriter);
		} catch (Exception e) {
			LOG.error("Error occoured in convertObjectToXML ", e);
		}
		LOG.info("convertObjectToXML()- end");
		return stringWriter.toString();
	}

	/**
	 * This method is used to convert an JAXB object to XML String.
	 *
	 * @param <T>    the generic type
	 * @param object the object
	 * @return the byte[]
	 */
	public static <T> byte[] convertObjectToByteArray(T object) {
		LOG.info("convertObjectToByteArray()- start");
		ByteArrayOutputStream xmlStream = new ByteArrayOutputStream(1024 * 8);
		try {
			GZIPOutputStream gzip = new GZIPOutputStream(xmlStream);
			JAXBContext context = getContext(object.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, UTF_8);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(object, gzip);
			gzip.close();
		} catch (Exception e) {
			LOG.error("Error occoured in convertObjectToByteArray ", e);
		}
		LOG.info("convertObjectToByteArray()- end");
		return xmlStream.toByteArray();
	}
}