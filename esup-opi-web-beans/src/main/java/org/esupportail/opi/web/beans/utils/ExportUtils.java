/**
 * 
 */
package org.esupportail.opi.web.beans.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.context.FacesContext;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.utils.Constantes;
import org.springframework.util.StringUtils;

/**
 * @author tducreux
 *
 */
public class ExportUtils {
	
	/*
	 ******************* PROPERTIES ******************* */
	private static final Logger LOGGER = new LoggerImpl(ExportUtils.class);	
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	private ExportUtils() {
		throw new UnsupportedOperationException();
	}


	/*
	 ******************* METHODS ********************** */

	/**
	 * Generate a csv format send by http.
	 * @param map key the key column name and value is the list value.
	 * @param fileName 
	 */
	public static void csvGenerate(final Map<Integer, List<String>> map, final String fileName) {
		String s = makeCvs(map);
		PDFUtils.setDownLoadAndSend(s.getBytes(),
				FacesContext.getCurrentInstance(),
				Constantes.HTTP_TYPE_EXCEL,
				fileName);
	}
	
	/**
	 * @param zipStream
	 * @param fileNameCsv
	 * @param map
	 * @return ZipOutputStream
	 */
	public static ZipOutputStream prepareCSVinZip(final ZipOutputStream zipStream,
			final String fileNameCsv, final Map<Integer, List<String>> map) {
		try {
			String s = makeCvs(map);
			
			ZipEntry zipEntry = new ZipEntry(fileNameCsv);
			zipStream.putNextEntry(zipEntry);
			
			zipStream.write(s.getBytes());
			zipStream.closeEntry();
		} catch (IOException e) {
			LOGGER.error("probleme de preparation du zip contenant le csv = " 
								+ fileNameCsv + "exception : " + e);
		}     				    
		
		return zipStream;
	}
	
	/**
	 * Make the string in format csv.
	 * @param map
	 * @return String
	 */
	public static String makeCvs(final Map<Integer, List<String>> map) {
		StringBuffer buffer = new StringBuffer();
		for (Map.Entry<Integer, List<String>> iEntry : map.entrySet()) {
			List<String> ligne = iEntry.getValue();
			for (String s : ligne) {
				buffer.append(s + ";");
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}
	
	/**
	 * 
	 * @param s if not null else return empty 
	 * @return String
	 */
	public static String isNotNull(final String s) {
		if (StringUtils.hasText(s)) {
			return s;
		} 
		return "";
	}
	
	/**
	 * @param nbBlank
	 * @return a list of blank
	 */
	public static List<String> addBlankList(final Integer nbBlank) {
		List<String> listBlank = new ArrayList<String>();
		for (int i = 0; i < nbBlank; i++) {
			listBlank.add("");
		}
		return listBlank;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

}
