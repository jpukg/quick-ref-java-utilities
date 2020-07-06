package org.bytebrain.xml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;

import javax.xml.bind.JAXBException;

import org.bytebrain.entity.Employee;
import org.bytebrain.entity.Wages;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestEmployeeXmlOutput {

	private static final Logger LOG = LoggerFactory.getLogger(TestEmployeeXmlOutput.class);

	public static void main(String[] args) throws JAXBException, DataFormatException {

		EasyRandomParameters parameters = new EasyRandomParameters();
		parameters.stringLengthRange(5, 10);
		parameters.collectionSizeRange(2, 10);
		parameters.excludeField(FieldPredicates.named("benefit").and(FieldPredicates.inClass(Wages.class)));
		parameters.excludeField(FieldPredicates.named("bonus").and(FieldPredicates.inClass(Wages.class)));

		EasyRandom generator = new EasyRandom(parameters);
		Employee employee = generator.nextObject(Employee.class);

		byte[] convertObjectToByteArray = XmlUtils.convertObjectToByteArray(employee);

		LOG.info("Compressed XML [\n{}\n]", new String(unzip(convertObjectToByteArray)));
		
		LOG.info("Compressed XML length [{}]", convertObjectToByteArray.length);
		LOG.info("Un Compressed XML length [{}]", unzip(convertObjectToByteArray).length);
	}

	private static byte[] unzip(final byte[] content) {
		try (final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
				final ByteArrayOutputStream out = new ByteArrayOutputStream();
				final GZIPInputStream zipStream = new GZIPInputStream(byteArrayInputStream)) {
			final byte[] buffer = new byte[1024];
			int length;
			while ((length = zipStream.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			return out.toByteArray();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String unzip1(final byte[] compressed) {
		if ((compressed == null) || (compressed.length == 0)) {
			throw new IllegalArgumentException("Cannot unzip null or empty bytes");
		}
		if (!isZipped(compressed)) {
			return new String(compressed);
		}

		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressed)) {
			try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
				try (InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream,
						StandardCharsets.UTF_8)) {
					try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
						StringBuilder output = new StringBuilder();
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							output.append(line);
						}
						return output.toString();
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to unzip content", e);
		}
	}

	public static boolean isZipped(final byte[] compressed) {
		return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
				&& (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
	}

}
