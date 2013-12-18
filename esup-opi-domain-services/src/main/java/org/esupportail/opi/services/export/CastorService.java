package org.esupportail.opi.services.export;

import static fj.P.p;
import static fj.Unit.unit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.management.RuntimeErrorException;

import org.esupportail.opi.utils.exceptions.ExportException;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.XMLContext;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.InputSource;

import fj.Effect;
import fj.F;
import fj.P2;
import fj.Unit;

public class CastorService implements ISerializationService {

	final Marshaller marshaller;
	
	final String xslXmlPath;

    private void writeToFile(String fileName, F<Writer, Unit> f) {
        try(BufferedWriter w = Files.newBufferedWriter(Paths.get(fileName), Charset.forName("UTF-8"))) {
            f.f(w);
            w.flush();
        } catch (IOException e) {
            throw new ExportException(e);
        }
    }
	
	public CastorService(final String xslXmlPath, final String castorMapping) {
		this.xslXmlPath = xslXmlPath; 
		final Mapping mp = new Mapping();
		try {
			mp.loadMapping(new InputSource(new ClassPathResource(castorMapping).getInputStream()));
			final XMLContext ctx = new XMLContext();
			ctx.addMapping(mp);
			marshaller = ctx.createMarshaller();
			marshaller.setProperty("org.exolab.castor.indent", "false");
			marshaller.setProperty("org.exolab.castor.xml.proxyInterfaces",
					"net.sf.cglib.proxy.Factory, org.hibernate.proxy.HibernateProxy");
		} catch (Exception e) {
			throw new Error(e);
		} 
	}

	/**
	 * Generate the xml in the file.
	 */
	public void objectToFileXml(final Object object, final String fileName ) {
		writeToFile(xslXmlPath + fileName, new F<Writer, Unit>() {
			public Unit f(Writer w) {
				try {
					marshaller.setWriter(w);
					marshaller.marshal(object);
				} catch (Exception e) {
					throw new ExportException("Problem while marshalling using castor : ", e);					
				}
				return unit();
			}});
	}
	
	
	public String getXslXmlPath() {
		return xslXmlPath;
	}
}
