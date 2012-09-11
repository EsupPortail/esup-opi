/**
 * 
 */
package org.esupportail.opi.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cleprous
 *
 */
public class StringUtils {

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The logger.
	 */
//	private static final Logger LOGGER = new LoggerImpl(StringUtils.class);	

	/** Index du 1er caractere accentué. **/
	private static final int MIN = 192;
	/** Index du dernier caractere accentué. **/
	private static final int MAX = 255;
	/** Vecteur de correspondance entre accent / sans accent. **/
	private static final List<String> MAP = initMap();

	/*
	 ******************* INIT ************************* */


	/**
	 * Private constructor.
	 */
	private StringUtils() {
		throw new UnsupportedOperationException();
	}


	/*
	 ******************* METHODS ********************** */



	/** 
	 * Initialisation du tableau de correspondance entre les caractéres accentués
	 * et leur homologues non accentués. 
	 */
	private static List<String> initMap() {
		List<String> result         = new ArrayList<String>();
		String car  = null;

		car = new String("A");
		result.add( car );            /* '\u00C0'   À   alt-0192  */ 
		result.add( car );            /* '\u00C1'   Á   alt-0193  */
		result.add( car );            /* '\u00C2'   Â   alt-0194  */
		result.add( car );            /* '\u00C3'   Ã   alt-0195  */
		result.add( car );            /* '\u00C4'   Ä   alt-0196  */
		result.add( car );            /* '\u00C5'   Å   alt-0197  */
		car = new String("AE");
		result.add( car );            /* '\u00C6'   Æ   alt-0198  */
		car = new String("C");
		result.add( car );            /* '\u00C7'   Ç   alt-0199  */
		car = new String("E");
		result.add( car );            /* '\u00C8'   È   alt-0200  */
		result.add( car );            /* '\u00C9'   É   alt-0201  */
		result.add( car );            /* '\u00CA'   Ê   alt-0202  */
		result.add( car );            /* '\u00CB'   Ë   alt-0203  */
		car = new String("I");
		result.add( car );            /* '\u00CC'   Ì   alt-0204  */
		result.add( car );            /* '\u00CD'   Í   alt-0205  */
		result.add( car );            /* '\u00CE'   Î   alt-0206  */
		result.add( car );            /* '\u00CF'   Ï   alt-0207  */
		car = new String("D");
		result.add( car );            /* '\u00D0'   Ð   alt-0208  */
		car = new String("N");
		result.add( car );            /* '\u00D1'   Ñ   alt-0209  */
		car = new String("O");
		result.add( car );            /* '\u00D2'   Ò   alt-0210  */
		result.add( car );            /* '\u00D3'   Ó   alt-0211  */
		result.add( car );            /* '\u00D4'   Ô   alt-0212  */
		result.add( car );            /* '\u00D5'   Õ   alt-0213  */
		result.add( car );            /* '\u00D6'   Ö   alt-0214  */
		car = new String("*");
		result.add( car );            /* '\u00D7'   ×   alt-0215  */
		car = new String("0");
		result.add( car );            /* '\u00D8'   Ø   alt-0216  */
		car = new String("U");
		result.add( car );            /* '\u00D9'   Ù   alt-0217  */
		result.add( car );            /* '\u00DA'   Ú   alt-0218  */
		result.add( car );            /* '\u00DB'   Û   alt-0219  */
		result.add( car );            /* '\u00DC'   Ü   alt-0220  */
		car = new String("Y");
		result.add( car );            /* '\u00DD'   Ý   alt-0221  */
		car = new String("Þ");
		result.add( car );            /* '\u00DE'   Þ   alt-0222  */
		car = new String("B");
		result.add( car );            /* '\u00DF'   ß   alt-0223  */
		car = new String("a");
		result.add( car );            /* '\u00E0'   à   alt-0224  */
		result.add( car );            /* '\u00E1'   á   alt-0225  */
		result.add( car );            /* '\u00E2'   â   alt-0226  */
		result.add( car );            /* '\u00E3'   ã   alt-0227  */
		result.add( car );            /* '\u00E4'   ä   alt-0228  */
		result.add( car );            /* '\u00E5'   å   alt-0229  */
		car = new String("ae");
		result.add( car );            /* '\u00E6'   æ   alt-0230  */
		car = new String("c");
		result.add( car );            /* '\u00E7'   ç   alt-0231  */
		car = new String("e");
		result.add( car );            /* '\u00E8'   è   alt-0232  */
		result.add( car );            /* '\u00E9'   é   alt-0233  */
		result.add( car );            /* '\u00EA'   ê   alt-0234  */
		result.add( car );            /* '\u00EB'   ë   alt-0235  */
		car = new String("i");
		result.add( car );            /* '\u00EC'   ì   alt-0236  */
		result.add( car );            /* '\u00ED'   í   alt-0237  */
		result.add( car );            /* '\u00EE'   î   alt-0238  */
		result.add( car );            /* '\u00EF'   ï   alt-0239  */
		car = new String("d");
		result.add( car );            /* '\u00F0'   ð   alt-0240  */
		car = new String("n");
		result.add( car );            /* '\u00F1'   ñ   alt-0241  */
		car = new String("o");
		result.add( car );            /* '\u00F2'   ò   alt-0242  */
		result.add( car );            /* '\u00F3'   ó   alt-0243  */
		result.add( car );            /* '\u00F4'   ô   alt-0244  */
		result.add( car );            /* '\u00F5'   õ   alt-0245  */
		result.add( car );            /* '\u00F6'   ö   alt-0246  */
		car = new String("/");
		result.add( car );            /* '\u00F7'   ÷   alt-0247  */
		car = new String("0");
		result.add( car );            /* '\u00F8'   ø   alt-0248  */
		car = new String("u");
		result.add( car );            /* '\u00F9'   ù   alt-0249  */
		result.add( car );            /* '\u00FA'   ú   alt-0250  */
		result.add( car );            /* '\u00FB'   û   alt-0251  */
		result.add( car );            /* '\u00FC'   ü   alt-0252  */
		car = new String("y");
		result.add( car );            /* '\u00FD'   ý   alt-0253  */
		car = new String("þ");
		result.add( car );            /* '\u00FE'   þ   alt-0254  */
		car = new String("y");
		result.add( car );            /* '\u00FF'   ÿ   alt-0255  */
		result.add( car );            /* '\u00FF'       alt-0255  */

		return result;
	}

	/** 
	 * Transforme une chaine pouvant contenir des accents dans une version sans accent.
	 *  @param chaine Chaine a convertir sans accent
	 *  @return Chaine dont les accents ont été supprimé
	 **/
	public static String sansAccent(final String chaine) {
		StringBuffer result  = new StringBuffer(chaine);

		for (int bcl = 0; bcl < result.length(); bcl++) {
			int carVal = chaine.charAt(bcl);
		if (carVal >= MIN && carVal <= MAX ) {
			// Remplacement
			String newVal = MAP.get(carVal - MIN);
			result.replace(bcl, bcl + 1, newVal);
		}   
		}
		return result.toString();
	}



	/*
	 ******************* ACCESSORS ******************** */

}
