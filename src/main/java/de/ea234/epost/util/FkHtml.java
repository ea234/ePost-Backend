package de.ea234.epost.util;

import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Properties;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class FkHtml {

  /*
   * https://owasp.org/www-project-java-html-sanitizer/
   * https://www.baeldung.com/java-sanitize-html-prevent-xss-attacks  
   */
  private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and( Sanitizers.LINKS );

  public static String sanitize( String htmlContent )
  {
    return POLICY.sanitize( htmlContent );
  }

  public static void removeSessionAttributes( HttpSession pSession )
  {
    try
    {
      Enumeration session_attribut_namen = pSession.getAttributeNames();

      int zaehler = 0;

      while ( session_attribut_namen.hasMoreElements() )
      {
        String attribut_name = (String) session_attribut_namen.nextElement();

        zaehler ++;

        //wl( zaehler + "|remove session-Attribut \"" + attribut_name + "\"" );
        try
        {
          pSession.removeAttribute( attribut_name );
        }
        catch (Exception err_inst)
        {
          //wl( "Fehler: \"" + attribut_name + "\": " + err_inst.getMessage() );
        }
      }
    }
    catch (Exception err_inst)
    {
      //wl( "Fehler: \"removeSessionAttributes\": ", err_inst );
    }
  }

  /**
   * @param pProperties die Instanz mit den Keys und Values
   * @return einen Json-String
   */
  public static String getStringJson( Properties pProperties )
  {
    /*
     * Pruefung: Parameter "pProperties" gleich "null" ? 
     * 
     * Wurden keine Properties uebergeben, wird ein Leerstring zurueckgegeben.
     */
    if ( pProperties == null )
    {
      return "";
    }

    /*
     * Pruefung: Propertie-Instanz leer ?
     * 
     * Liegen in der Propertie-Instanz keine Daten vor, wird ein Leerstring zurueckgegeben.
     */
    if ( pProperties.isEmpty() )
    {
      return "";
    }

    /*
     * Aus der Propertie-Instanz werden die Keys als Enumeration gelesen.
     */
    Enumeration enumeration_keys = pProperties.keys();

    String akt_property_key = null;
    String akt_property_wert = null;

    String str_json_ergebnis = "";

    String str_comma = "";

    /*
     * Es wird eine While-Schleife ueber alle Elemente der Enumeration-Keys gestartet.
     */
    while ( enumeration_keys.hasMoreElements() )
    {
      akt_property_key = (String) enumeration_keys.nextElement();

      try
      {
        /*
         * Lesen des Property-Wertes. 
         * Sollte der Key nicht vorhanden sein, wird als Vorgabe ein Leerstring gesetzt.
         */
        akt_property_wert = pProperties.getProperty( akt_property_key, "" );
      }
      catch (Throwable err_inst)
      {
        /*
         * Bei einem Fehler wird der Property-Wert zu einem Leerstring
         */
        akt_property_wert = "";
      }

      /*
       * Aufbau Json
       * 
       * Der Json-Key ist der Property-Key
       * Der Wert ist der aktuelle Property-Wert, welcher noch maskiert wird.
       */
      str_json_ergebnis += str_comma + "\"" + akt_property_key + "\":" + getStringJson( akt_property_wert ) + "";

      str_comma = ",";

    }

    /*
     * Nach der While-Schleife werden die benutzten Variablen auf null gestellt.
     */
    akt_property_key = null;
    akt_property_wert = null;

    enumeration_keys = null;

    /*
     * Am Ende wird der aufgebaute String zurueckgegeben.
     */
    return "{" + str_json_ergebnis + "}";
  }

  private static final char NULL_CHAR_CODE = 0x00;

  private static final char START_OF_HEADING_CHAR_CODE = 0x01;

  private static final char START_OF_TEXT_CHAR_CODE = 0x02;

  private static final char END_OF_TEXT_CHAR_CODE = 0x03;

  private static final char END_OF_TRANSMISSION_CHAR_CODE = 0x04;

  private static final char ENQUIRY_CHAR_CODE = 0x05;

  private static final char ACKNOWLEDGE_CHAR_CODE = 0x06;

  private static final char BELL_CHAR_CODE = 0x07;

  private static final char BACKSPACE_CHAR_CODE = 0x08;

  private static final char HORIZONTAL_TAB_CHAR_CODE = 0x09;

  private static final char LINE_FEED_CHAR_CODE = 0x0A;

  private static final char VERTICAL_TAB_CHAR_CODE = 0x0B;

  private static final char FORM_FEED_CHAR_CODE = 0x0C;

  private static final char CARRIAGE_RETURN_CHAR_CODE = 0x0D;

  private static final char SHIFT_OUT_CHAR_CODE = 0x0E;

  private static final char SHIFT_IN_CHAR_CODE = 0x0F;

  private static final char DATA_LINK_ESCAPE_CHAR_CODE = 0x10;

  private static final char DEVICE_CONTROL_1_CHAR_CODE = 0x11;

  private static final char DEVICE_CONTROL_2_CHAR_CODE = 0x12;

  private static final char DEVICE_CONTROL_3_CHAR_CODE = 0x13;

  private static final char DEVICE_CONTROL_4_CHAR_CODE = 0x14;

  private static final char NEGATIVE_ACKNOWLEDGE_CHAR_CODE = 0x15;

  private static final char SYNCHRONOUS_IDLE_CHAR_CODE = 0x16;

  private static final char END_OF_TRANSMISSION_BLOCK_CHAR_CODE = 0x17;

  private static final char CANCEL_CHAR_CODE = 0x18;

  private static final char END_OF_MEDIUM_CHAR_CODE = 0x19;

  private static final char SUBSTITUTE_CHAR_CODE = 0x1A;

  private static final char ESCAPE_CHAR_CODE = 0x1B;

  private static final char FILE_SEPARATOR_CHAR_CODE = 0x1C;

  private static final char GROUP_SEPARATOR_CHAR_CODE = 0x1D;

  private static final char RECORD_SEPARATOR_CHAR_CODE = 0x1E;

  private static final char UNIT_SEPARATOR_CHAR_CODE = 0x1F;

  private static final char NABLA_CHAR_CODE = 0x2207;

  private static final char MACRON_CHAR_CODE = 0x00af;

  private static final char DAGGER_CHAR_CODE = 0x2020;

  private static final char PARAGRAPH_SIGN_CHAR_CODE = 0x00b6;

  private static final char PARTIAL_DIFFERENTIAL_CHAR_CODE = 0x2202;

  private static final char NARY_PRODUCT_CHAR_CODE = 0x220f;

  private static final char NARY_SUMMATION_CHAR_CODE = 0x2211;

  private static final char MASCULINE_ORDINAL_CHAR_CODE = 0x00ba;

  private static final char LATIN_CAPITAL_LETTER_SWITH_CARON_CHAR_CODE = 0x0160;

  private static final char LATIN_CAPITAL_LETTER_YWITH_DIAERESIS_CHAR_CODE = 0x0178;

  private static final char LATIN_CAPITAL_LIGATURE_OE_CHAR_CODE = 0x0152;

  private static final char LATIN_SMALL_LETTER_FWITH_HOOK_CHAR_CODE = 0x0192;

  private static final char LATIN_SMALL_LETTER_SWITH_CARON_CHAR_CODE = 0x0161;

  private static final char LATIN_SMALL_LIGATURE_OE_CHAR_CODE = 0x0153;

  private static final char SCRIPT_CAPITAL_P_CHAR_CODE = 0x2118;

  private static final char ACUTE_ACCENT_CHAR_CODE = 0x00b4;

  private static final char IDENTICAL_TO_CHAR_CODE = 0x2261;

  private static final char SECTION_SIGN_CHAR_CODE = 0x00a7;

  private static final char CEDILLA_CHAR_CODE = 0x00b8;

  private static final char LEFT_ANGLE_QUOTE_CHAR_CODE = 0x00ab;

  private static final char LEFT_CEILING_CHAR_CODE = 0x2308;

  private static final char LEFT_DOUBLE_QUOTE_CHAR_CODE = 0x201c;

  private static final char LEFT_FLOOR_CHAR_CODE = 0x230a;

  private static final char LEFT_POINTING_ANGLE_BRACKET_CHAR_CODE = 0x2329;

  private static final char LEFT_RIGHT_ARROW_CHAR_CODE = 0x2194;

  private static final char LEFT_RIGHT_DOUBLE_ARROW_CHAR_CODE = 0x21d4;

  private static final char LEFT_SINGLE_QUOTE_CHAR_CODE = 0x2018;

  private static final char LEFT_TO_RIGHT_MARK_CHAR_CODE = 0x200e;

  private static final char LEFTWARD_ARROW_CHAR_CODE = 0x2190;

  private static final char LEFTWARDS_DOUBLE_ARROW_CHAR_CODE = 0x21d0;

  private static final char REGISTERED_TRADEMARK_CHAR_CODE = 0x00ae;

  private static final char DEGREE_SIGN_CHAR_CODE = 0x00b0;

  private static final char FEMININE_ORDINAL_CHAR_CODE = 0x00aa;

  private static final char YEN_SIGN_CHAR_CODE = 0x00a5;

  private static final char GENERAL_CURRENCY_SIGN_CHAR_CODE = 0x00a4;

  private static final char CENT_SIGN_CHAR_CODE = 0x00a2;

  private static final char PER_MILL_SIGN_CHAR_CODE = 0x2030;

  private static final char ZERO_WIDTH_JOINER_CHAR_CODE = 0x200d;

  private static final char ZERO_WIDTH_NON_JOINER_CHAR_CODE = 0x200c;

  private static final char LESS_THAN_OR_EQUAL_TO_CHAR_CODE = 0x2264;

  private static final char LESS_THAN_SIGN_CHAR_CODE = 0x003c;

  private static final char THERE_EXISTS_CHAR_CODE = 0x2203;

  private static final char THEREFORE_CHAR_CODE = 0x2234;

  private static final char THIN_SPACE_CHAR_CODE = 0x2009;

  private static final char THREE_FOURTHS_CHAR_CODE = 0x00be;

  private static final char DIAERESIS_CHAR_CODE = 0x00a8;

  private static final char MICRO_SIGN_CHAR_CODE = 0x00b5;

  private static final char MIDDLE_DOT_CHAR_CODE = 0x00b7;

  private static final char RIGHT_ANGLE_QUOTE_CHAR_CODE = 0x00bb;

  private static final char RIGHT_CEILING_CHAR_CODE = 0x2309;

  private static final char RIGHT_DOUBLE_QUOTE_CHAR_CODE = 0x201d;

  private static final char RIGHT_FLOOR_CHAR_CODE = 0x230b;

  private static final char RIGHT_POINTING_ANGLE_BRACKET_CHAR_CODE = 0x232a;

  private static final char RIGHT_SINGLE_QUOTE_CHAR_CODE = 0x2019;

  private static final char RIGHT_TO_LEFT_MARK_CHAR_CODE = 0x200f;

  private static final char RIGHTWARD_ARROW_CHAR_CODE = 0x2192;

  private static final char RIGHTWARDS_DOUBLE_ARROW_CHAR_CODE = 0x21d2;

  private static final char TILDE_OPERATOR_CHAR_CODE = 0x223c;

  private static final char SINGLE_LEFT_POINTING_ANGLE_QUOTE_CHAR_CODE = 0x2039;

  private static final char SINGLE_LOW_9_QUOTE_CHAR_CODE = 0x201a;

  private static final char SINGLE_RIGHT_POINTING_ANGLE_QUOTE_CHAR_CODE = 0x203a;

  private static final char MINUS_SIGN_CHAR_CODE = 0x2212;

  private static final char CIRCLED_PLUS_CHAR_CODE = 0x2295;

  private static final char CIRCLED_TIMES_CHAR_CODE = 0x2297;

  private static final char DIVISION_SIGN_CHAR_CODE = 0x00f7;

  private static final char BLACK_CLUB_SUIT_CHAR_CODE = 0x2663;

  private static final char BLACK_DIAMOND_SUIT_CHAR_CODE = 0x2666;

  private static final char BLACK_HEART_SUIT_CHAR_CODE = 0x2665;

  private static final char BLACK_LETTER_CAPITAL_I_CHAR_CODE = 0x2111;

  private static final char BLACK_LETTER_CAPITAL_R_CHAR_CODE = 0x211c;

  private static final char BLACK_SPADE_SUIT_CHAR_CODE = 0x2660;

  private static final char SLASH_CHAR_CODE = 0x2044;

  private static final char ALEF_SYMBOL_CHAR_CODE = 0x2135;

  private static final char ELEMENT_OF_CHAR_CODE = 0x2208;

  private static final char ELLIPSES_CHAR_CODE = 0x2026;

  private static final char ALMOST_EQUAL_TO_CHAR_CODE = 0x2248;

  private static final char PLUS_OR_MINUS_CHAR_CODE = 0x00b1;

  private static final char EM_DASH_CHAR_CODE = 0x2014;

  private static final char EM_SPACE_CHAR_CODE = 0x2003;

  private static final char SMALL_TILDE_CHAR_CODE = 0x02dc;

  private static final char AMPERSAND_CHAR_CODE = 0x0026;

  private static final char EMPTY_SET_CHAR_CODE = 0x2205;

  private static final char EN_DASH_CHAR_CODE = 0x2013;

  private static final char EN_SPACE_CHAR_CODE = 0x2002;

  private static final char ONE_FOURTH_CHAR_CODE = 0x00bc;

  private static final char ONE_HALF_CHAR_CODE = 0x00bd;

  private static final char INFINITY_CHAR_CODE = 0x221e;

  private static final char ANGLE_CHAR_CODE = 0x2220;

  private static final char UNION_CHAR_CODE = 0x222a;

  private static final char INTEGRAL_CHAR_CODE = 0x222b;

  private static final char INTERSECTION_CHAR_CODE = 0x2229;

  private static final char INVERTED_EXCLAMATION_CHAR_CODE = 0x00a1;

  private static final char INVERTED_QUESTION_MARK_CHAR_CODE = 0x00bf;

  private static final char MODIFIER_LETTER_CIRCUMFLEX_ACCENT_CHAR_CODE = 0x02c6;

  private static final char SOFT_HYPHEN_CHAR_CODE = 0x00ad;

  private static final char LOGICAL_AND_CHAR_CODE = 0x2227;

  private static final char LOGICAL_OR_CHAR_CODE = 0x2228;

  private static final char NONBREAKING_SPACE_CHAR_CODE = 0x00a0;

  private static final char CONTAINS_AS_MEMBER_CHAR_CODE = 0x220b;

  private static final char COPYRIGHT_CHAR_CODE = 0x00a9;

  private static final char FOR_ALL_CHAR_CODE = 0x2200;

  private static final char NOT_AN_ELEMENT_OF_CHAR_CODE = 0x2209;

  private static final char NOT_ASUBSET_OF_CHAR_CODE = 0x2284;

  private static final char NOT_EQUAL_TO_CHAR_CODE = 0x2260;

  private static final char DOT_OPERATOR_CHAR_CODE = 0x22c5;

  private static final char NOT_SIGN_CHAR_CODE = 0x00ac;

  private static final char DOUBLE_DAGGER_CHAR_CODE = 0x2021;

  private static final char DOUBLE_LOW_9_QUOTE_CHAR_CODE = 0x201e;

  private static final char DOUBLE_PRIME_CHAR_CODE = 0x2033;

  private static final char DOUBLE_QUOTATION_MARK_CHAR_CODE = 0x0022;

  private static final char POUND_STERLING_CHAR_CODE = 0x00a3;

  private static final char LOWERCASE_AACUTE_ACCENT_CHAR_CODE = 0x00e1;

  private static final char LOWERCASE_ACIRCUMFLEX_ACCENT_CHAR_CODE = 0x00e2;

  private static final char LOWERCASE_AE_CHAR_CODE = 0x00e6;

  private static final char LOWERCASE_AGRAVE_ACCENT_CHAR_CODE = 0x00e0;

  private static final char LOWERCASE_ARING_CHAR_CODE = 0x00e5;

  private static final char LOWERCASE_ATILDE_CHAR_CODE = 0x00e3;

  private static final char LOWERCASE_AUMLAUT_CHAR_CODE = 0x00e4;

  private static final char LOWERCASE_CCEDILLA_CHAR_CODE = 0x00e7;

  private static final char LOWERCASE_EACUTE_ACCENT_CHAR_CODE = 0x00e9;

  private static final char LOWERCASE_ECIRCUMFLEX_ACCENT_CHAR_CODE = 0x00ea;

  private static final char LOWERCASE_EGRAVE_ACCENT_CHAR_CODE = 0x00e8;

  private static final char LOWERCASE_ETH_ICELANDIC_CHAR_CODE = 0x00f0;

  private static final char LOWERCASE_EUMLAUT_CHAR_CODE = 0x00eb;

  private static final char LOWERCASE_IACUTE_ACCENT_CHAR_CODE = 0x00ed;

  private static final char LOWERCASE_ICIRCUMFLEX_ACCENT_CHAR_CODE = 0x00ee;

  private static final char LOWERCASE_IGRAVE_ACCENT_CHAR_CODE = 0x00ec;

  private static final char LOWERCASE_IUMLAUT_CHAR_CODE = 0x00ef;

  private static final char LOWERCASE_NTILDE_CHAR_CODE = 0x00f1;

  private static final char LOWERCASE_OACUTE_ACCENT_CHAR_CODE = 0x00f3;

  private static final char LOWERCASE_OCIRCUMFLEX_ACCENT_CHAR_CODE = 0x00f4;

  private static final char LOWERCASE_OGRAVE_ACCENT_CHAR_CODE = 0x00f2;

  private static final char LOWERCASE_OSLASH_CHAR_CODE = 0x00f8;

  private static final char LOWERCASE_OTILDE_CHAR_CODE = 0x00f5;

  private static final char LOWERCASE_OUMLAUT_CHAR_CODE = 0x00f6;

  private static final char LOWERCASE_SHARPS_GERMAN_CHAR_CODE = 0x00df;

  private static final char LOWERCASE_THORN_ICELANDIC_CHAR_CODE = 0x00fe;

  private static final char LOWERCASE_UACUTE_ACCENT_CHAR_CODE = 0x00fa;

  private static final char LOWERCASE_UCIRCUMFLEX_ACCENT_CHAR_CODE = 0x00fb;

  private static final char LOWERCASE_UGRAVE_ACCENT_CHAR_CODE = 0x00f9;

  private static final char LOWERCASE_UUMLAUT_CHAR_CODE = 0x00fc;

  private static final char LOWERCASE_YACUTE_ACCENT_CHAR_CODE = 0x00fd;

  private static final char LOWERCASE_YUMLAUT_CHAR_CODE = 0x00ff;

  private static final char DOWNWARD_ARROW_CHAR_CODE = 0x2193;

  private static final char DOWNWARDS_ARROW_WITH_CORNER_LEFTWARDS_CHAR_CODE = 0x21b5;

  private static final char DOWNWARDS_DOUBLE_ARROW_CHAR_CODE = 0x21d3;

  private static final char LOZENGE_CHAR_CODE = 0x25ca;

  private static final char UP_TACK_CHAR_CODE = 0x22a5;

  private static final char APOSTROPHE_CHAR_CODE = 0x0027;

  private static final char UPPERCASE_AACUTE_ACCENT_CHAR_CODE = 0x00c1;

  private static final char UPPERCASE_ACIRCUMFLEX_ACCENT_CHAR_CODE = 0x00c2;

  private static final char UPPERCASE_AE_CHAR_CODE = 0x00c6;

  private static final char UPPERCASE_AGRAVE_ACCENT_CHAR_CODE = 0x00c0;

  private static final char UPPERCASE_ARING_CHAR_CODE = 0x00c5;

  private static final char UPPERCASE_ATILDE_CHAR_CODE = 0x00c3;

  private static final char UPPERCASE_AUMLAUT_CHAR_CODE = 0x00c4;

  private static final char UPPERCASE_CCEDILLA_CHAR_CODE = 0x00c7;

  private static final char UPPERCASE_EACUTE_ACCENT_CHAR_CODE = 0x00c9;

  private static final char UPPERCASE_ECIRCUMFLEX_ACCENT_CHAR_CODE = 0x00ca;

  private static final char UPPERCASE_EGRAVE_ACCENT_CHAR_CODE = 0x00c8;

  private static final char UPPERCASE_ETH_ICELANDIC_CHAR_CODE = 0x00d0;

  private static final char UPPERCASE_EUMLAUT_CHAR_CODE = 0x00cb;

  private static final char UPPERCASE_IACUTE_ACCENT_CHAR_CODE = 0x00cd;

  private static final char UPPERCASE_ICIRCUMFLEX_ACCENT_CHAR_CODE = 0x00ce;

  private static final char UPPERCASE_IGRAVE_ACCENT_CHAR_CODE = 0x00cc;

  private static final char UPPERCASE_IUMLAUT_CHAR_CODE = 0x00cf;

  private static final char UPPERCASE_NTILDE_CHAR_CODE = 0x00d1;

  private static final char UPPERCASE_OACUTE_ACCENT_CHAR_CODE = 0x00d3;

  private static final char UPPERCASE_OCIRCUMFLEX_ACCENT_CHAR_CODE = 0x00d4;

  private static final char UPPERCASE_OGRAVE_ACCENT_CHAR_CODE = 0x00d2;

  private static final char UPPERCASE_OSLASH_CHAR_CODE = 0x00d8;

  private static final char UPPERCASE_OTILDE_CHAR_CODE = 0x00d5;

  private static final char UPPERCASE_OUMLAUT_CHAR_CODE = 0x00d6;

  private static final char UPPERCASE_THORN_ICELANDIC_CHAR_CODE = 0x00de;

  private static final char UPPERCASE_UACUTE_ACCENT_CHAR_CODE = 0x00da;

  private static final char UPPERCASE_UCIRCUMFLEX_ACCENT_CHAR_CODE = 0x00db;

  private static final char UPPERCASE_UGRAVE_ACCENT_CHAR_CODE = 0x00d9;

  private static final char UPPERCASE_UUMLAUT_CHAR_CODE = 0x00dc;

  private static final char UPPERCASE_YACUTE_ACCENT_CHAR_CODE = 0x00dd;

  private static final char APPROXIMATELY_EQUAL_TO_CHAR_CODE = 0x2245;

  private static final char UPWARD_ARROW_CHAR_CODE = 0x2191;

  private static final char UPWARDS_DOUBLE_ARROW_CHAR_CODE = 0x21d1;

  private static final char SQUARE_ROOT_CHAR_CODE = 0x221a;

  private static final char TRADEMARK_SIGN_CHAR_CODE = 0x2122;

  private static final char GREATER_THAN_OR_EQUAL_TO_CHAR_CODE = 0x2265;

  private static final char GREATER_THAN_SIGN_CHAR_CODE = 0x003e;

  private static final char GREEK_CAPITAL_LETTER_ALPHA_CHAR_CODE = 0x0391;

  private static final char GREEK_CAPITAL_LETTER_BETA_CHAR_CODE = 0x0392;

  private static final char GREEK_CAPITAL_LETTER_CHI_CHAR_CODE = 0x03a7;

  private static final char GREEK_CAPITAL_LETTER_DELTA_CHAR_CODE = 0x0394;

  private static final char GREEK_CAPITAL_LETTER_EPSILON_CHAR_CODE = 0x0395;

  private static final char GREEK_CAPITAL_LETTER_ETA_CHAR_CODE = 0x0397;

  private static final char GREEK_CAPITAL_LETTER_GAMMA_CHAR_CODE = 0x0393;

  private static final char GREEK_CAPITAL_LETTER_IOTA_CHAR_CODE = 0x0399;

  private static final char GREEK_CAPITAL_LETTER_KAPPA_CHAR_CODE = 0x039a;

  private static final char GREEK_CAPITAL_LETTER_LAMDA_CHAR_CODE = 0x039b;

  private static final char GREEK_CAPITAL_LETTER_MU_CHAR_CODE = 0x039c;

  private static final char GREEK_CAPITAL_LETTER_NU_CHAR_CODE = 0x039d;

  private static final char GREEK_CAPITAL_LETTER_OMEGA_CHAR_CODE = 0x03a9;

  private static final char GREEK_CAPITAL_LETTER_OMICRON_CHAR_CODE = 0x039f;

  private static final char GREEK_CAPITAL_LETTER_PHI_CHAR_CODE = 0x03a6;

  private static final char GREEK_CAPITAL_LETTER_PI_CHAR_CODE = 0x03a0;

  private static final char GREEK_CAPITAL_LETTER_PSI_CHAR_CODE = 0x03a8;

  private static final char GREEK_CAPITAL_LETTER_RHO_CHAR_CODE = 0x03a1;

  private static final char GREEK_CAPITAL_LETTER_SIGMA_CHAR_CODE = 0x03a3;

  private static final char GREEK_CAPITAL_LETTER_TAU_CHAR_CODE = 0x03a4;

  private static final char GREEK_CAPITAL_LETTER_THETA_CHAR_CODE = 0x0398;

  private static final char GREEK_CAPITAL_LETTER_UPSILON_CHAR_CODE = 0x03a5;

  private static final char GREEK_CAPITAL_LETTER_XI_CHAR_CODE = 0x039e;

  private static final char GREEK_CAPITAL_LETTER_ZETA_CHAR_CODE = 0x0396;

  private static final char GREEK_PI_SYMBOL_CHAR_CODE = 0x03d6;

  private static final char GREEK_SMALL_LETTER_ALPHA_CHAR_CODE = 0x03b1;

  private static final char GREEK_SMALL_LETTER_BETA_CHAR_CODE = 0x03b2;

  private static final char GREEK_SMALL_LETTER_CHI_CHAR_CODE = 0x03c7;

  private static final char GREEK_SMALL_LETTER_DELTA_CHAR_CODE = 0x03b4;

  private static final char GREEK_SMALL_LETTER_EPSILON_CHAR_CODE = 0x03b5;

  private static final char GREEK_SMALL_LETTER_ETA_CHAR_CODE = 0x03b7;

  private static final char GREEK_SMALL_LETTER_FINAL_SIGMA_CHAR_CODE = 0x03c2;

  private static final char GREEK_SMALL_LETTER_GAMMA_CHAR_CODE = 0x03b3;

  private static final char GREEK_SMALL_LETTER_IOTA_CHAR_CODE = 0x03b9;

  private static final char GREEK_SMALL_LETTER_KAPPA_CHAR_CODE = 0x03ba;

  private static final char GREEK_SMALL_LETTER_LAMDA_CHAR_CODE = 0x03bb;

  private static final char GREEK_SMALL_LETTER_MU_CHAR_CODE = 0x03bc;

  private static final char GREEK_SMALL_LETTER_NU_CHAR_CODE = 0x03bd;

  private static final char GREEK_SMALL_LETTER_OMEGA_CHAR_CODE = 0x03c9;

  private static final char GREEK_SMALL_LETTER_OMICRON_CHAR_CODE = 0x03bf;

  private static final char GREEK_SMALL_LETTER_PHI_CHAR_CODE = 0x03c6;

  private static final char GREEK_SMALL_LETTER_PI_CHAR_CODE = 0x03c0;

  private static final char GREEK_SMALL_LETTER_PSI_CHAR_CODE = 0x03c8;

  private static final char GREEK_SMALL_LETTER_RHO_CHAR_CODE = 0x03c1;

  private static final char GREEK_SMALL_LETTER_SIGMA_CHAR_CODE = 0x03c3;

  private static final char GREEK_SMALL_LETTER_TAU_CHAR_CODE = 0x03c4;

  private static final char GREEK_SMALL_LETTER_THETA_CHAR_CODE = 0x03b8;

  private static final char GREEK_SMALL_LETTER_UPSILON_CHAR_CODE = 0x03c5;

  private static final char GREEK_SMALL_LETTER_XI_CHAR_CODE = 0x03be;

  private static final char GREEK_SMALL_LETTER_ZETA_CHAR_CODE = 0x03b6;

  private static final char GREEK_THETA_SYMBOL_CHAR_CODE = 0x03d1;

  private static final char GREEK_UPSILON_WITH_HOOK_SYMBOL_CHAR_CODE = 0x03d2;

  private static final char PRIME_CHAR_CODE = 0x2032;

  private static final char BROKEN_BAR_CHAR_CODE = 0x00a6;

  private static final char PROPORTIONAL_TO_CHAR_CODE = 0x221d;

  private static final char ASTERISK_OPERATOR_CHAR_CODE = 0x2217;

  private static final char SUBSET_OF_CHAR_CODE = 0x2282;

  private static final char SUBSET_OF_OR_EQUAL_TO_CHAR_CODE = 0x2286;

  private static final char BULLET_CHAR_CODE = 0x2022;

  private static final char MULTIPLICATION_SIGN_CHAR_CODE = 0x00d7;

  private static final char SUPERSCRIPT_ONE_CHAR_CODE = 0x00b9;

  private static final char SUPERSCRIPT_THREE_CHAR_CODE = 0x00b3;

  private static final char SUPERSCRIPT_TWO_CHAR_CODE = 0x00b2;

  private static final char SUPERSET_OF_CHAR_CODE = 0x2283;

  private static final char SUPERSET_OF_OR_EQUAL_TO_CHAR_CODE = 0x2287;

  private static final char EURO_SIGN_CHAR_CODE = 0x20ac;

  private static final char OVERLINE_SPACING_OVERSCORE_CHAR_CODE = 0x203e;

  private static final String NABLA_QOUTE_JSON = "\\u2207";

  private static final String MACRON_QOUTE_JSON = "\\u00af";

  private static final String DAGGER_QOUTE_JSON = "\\u2020";

  private static final String PARAGRAPH_SIGN_QOUTE_JSON = "\\u00b6";

  private static final String PARTIAL_DIFFERENTIAL_QOUTE_JSON = "\\u2202";

  private static final String NARY_PRODUCT_QOUTE_JSON = "\\u220f";

  private static final String NARY_SUMMATION_QOUTE_JSON = "\\u2211";

  private static final String MASCULINE_ORDINAL_QOUTE_JSON = "\\u00ba";

  private static final String LATIN_CAPITAL_LETTER_SWITH_CARON_QOUTE_JSON = "\\u0160";

  private static final String LATIN_CAPITAL_LETTER_YWITH_DIAERESIS_QOUTE_JSON = "\\u0178";

  private static final String LATIN_CAPITAL_LIGATURE_OE_QOUTE_JSON = "\\u0152";

  private static final String LATIN_SMALL_LETTER_FWITH_HOOK_QOUTE_JSON = "\\u0192";

  private static final String LATIN_SMALL_LETTER_SWITH_CARON_QOUTE_JSON = "\\u0161";

  private static final String LATIN_SMALL_LIGATURE_OE_QOUTE_JSON = "\\u0153";

  private static final String SCRIPT_CAPITAL_P_QOUTE_JSON = "\\u2118";

  private static final String ACUTE_ACCENT_QOUTE_JSON = "\\u00b4";

  private static final String IDENTICAL_TO_QOUTE_JSON = "\\u2261";

  private static final String SECTION_SIGN_QOUTE_JSON = "\\u00a7";

  private static final String CEDILLA_QOUTE_JSON = "\\u00b8";

  private static final String LEFT_ANGLE_QUOTE_QOUTE_JSON = "\\u00ab";

  private static final String LEFT_CEILING_QOUTE_JSON = "\\u2308";

  private static final String LEFT_DOUBLE_QUOTE_QOUTE_JSON = "\\u201c";

  private static final String LEFT_FLOOR_QOUTE_JSON = "\\u230a";

  private static final String LEFT_POINTING_ANGLE_BRACKET_QOUTE_JSON = "\\u2329";

  private static final String LEFT_RIGHT_ARROW_QOUTE_JSON = "\\u2194";

  private static final String LEFT_RIGHT_DOUBLE_ARROW_QOUTE_JSON = "\\u21d4";

  private static final String LEFT_SINGLE_QUOTE_QOUTE_JSON = "\\u2018";

  private static final String LEFT_TO_RIGHT_MARK_QOUTE_JSON = "\\u200e";

  private static final String LEFTWARD_ARROW_QOUTE_JSON = "\\u2190";

  private static final String LEFTWARDS_DOUBLE_ARROW_QOUTE_JSON = "\\u21d0";

  private static final String REGISTERED_TRADEMARK_QOUTE_JSON = "\\u00ae";

  private static final String DEGREE_SIGN_QOUTE_JSON = "\\u00b0";

  private static final String FEMININE_ORDINAL_QOUTE_JSON = "\\u00aa";

  private static final String YEN_SIGN_QOUTE_JSON = "\\u00a5";

  private static final String GENERAL_CURRENCY_SIGN_QOUTE_JSON = "\\u00a4";

  private static final String CENT_SIGN_QOUTE_JSON = "\\u00a2";

  private static final String PER_MILL_SIGN_QOUTE_JSON = "\\u2030";

  private static final String ZERO_WIDTH_JOINER_QOUTE_JSON = "\\u200d";

  private static final String ZERO_WIDTH_NON_JOINER_QOUTE_JSON = "\\u200c";

  private static final String LESS_THAN_OR_EQUAL_TO_QOUTE_JSON = "\\u2264";

  private static final String LESS_THAN_SIGN_QOUTE_JSON = "\\u003c";

  private static final String THERE_EXISTS_QOUTE_JSON = "\\u2203";

  private static final String THEREFORE_QOUTE_JSON = "\\u2234";

  private static final String THIN_SPACE_QOUTE_JSON = "\\u2009";

  private static final String THREE_FOURTHS_QOUTE_JSON = "\\u00be";

  private static final String DIAERESIS_QOUTE_JSON = "\\u00a8";

  private static final String MICRO_SIGN_QOUTE_JSON = "\\u00b5";

  private static final String MIDDLE_DOT_QOUTE_JSON = "\\u00b7";

  private static final String RIGHT_ANGLE_QUOTE_QOUTE_JSON = "\\u00bb";

  private static final String RIGHT_CEILING_QOUTE_JSON = "\\u2309";

  private static final String RIGHT_DOUBLE_QUOTE_QOUTE_JSON = "\\u201d";

  private static final String RIGHT_FLOOR_QOUTE_JSON = "\\u230b";

  private static final String RIGHT_POINTING_ANGLE_BRACKET_QOUTE_JSON = "\\u232a";

  private static final String RIGHT_SINGLE_QUOTE_QOUTE_JSON = "\\u2019";

  private static final String RIGHT_TO_LEFT_MARK_QOUTE_JSON = "\\u200f";

  private static final String RIGHTWARD_ARROW_QOUTE_JSON = "\\u2192";

  private static final String RIGHTWARDS_DOUBLE_ARROW_QOUTE_JSON = "\\u21d2";

  private static final String TILDE_OPERATOR_QOUTE_JSON = "\\u223c";

  private static final String SINGLE_LEFT_POINTING_ANGLE_QUOTE_QOUTE_JSON = "\\u2039";

  private static final String SINGLE_LOW_9_QUOTE_QOUTE_JSON = "\\u201a";

  private static final String SINGLE_RIGHT_POINTING_ANGLE_QUOTE_QOUTE_JSON = "\\u203a";

  private static final String MINUS_SIGN_QOUTE_JSON = "\\u2212";

  private static final String CIRCLED_PLUS_QOUTE_JSON = "\\u2295";

  private static final String CIRCLED_TIMES_QOUTE_JSON = "\\u2297";

  private static final String DIVISION_SIGN_QOUTE_JSON = "\\u00f7";

  private static final String BLACK_CLUB_SUIT_QOUTE_JSON = "\\u2663";

  private static final String BLACK_DIAMOND_SUIT_QOUTE_JSON = "\\u2666";

  private static final String BLACK_HEART_SUIT_QOUTE_JSON = "\\u2665";

  private static final String BLACK_LETTER_CAPITAL_I_QOUTE_JSON = "\\u2111";

  private static final String BLACK_LETTER_CAPITAL_R_QOUTE_JSON = "\\u211c";

  private static final String BLACK_SPADE_SUIT_QOUTE_JSON = "\\u2660";

  private static final String SLASH_QOUTE_JSON = "\\u2044";

  private static final String ALEF_SYMBOL_QOUTE_JSON = "\\u2135";

  private static final String ELEMENT_OF_QOUTE_JSON = "\\u2208";

  private static final String ELLIPSES_QOUTE_JSON = "\\u2026";

  private static final String ALMOST_EQUAL_TO_QOUTE_JSON = "\\u2248";

  private static final String PLUS_OR_MINUS_QOUTE_JSON = "\\u00b1";

  private static final String EM_DASH_QOUTE_JSON = "\\u2014";

  private static final String EM_SPACE_QOUTE_JSON = "\\u2003";

  private static final String SMALL_TILDE_QOUTE_JSON = "\\u02dc";

  private static final String AMPERSAND_QOUTE_JSON = "\\u0026";

  private static final String EMPTY_SET_QOUTE_JSON = "\\u2205";

  private static final String EN_DASH_QOUTE_JSON = "\\u2013";

  private static final String EN_SPACE_QOUTE_JSON = "\\u2002";

  private static final String ONE_FOURTH_QOUTE_JSON = "\\u00bc";

  private static final String ONE_HALF_QOUTE_JSON = "\\u00bd";

  private static final String INFINITY_QOUTE_JSON = "\\u221e";

  private static final String ANGLE_QOUTE_JSON = "\\u2220";

  private static final String UNION_QOUTE_JSON = "\\u222a";

  private static final String INTEGRAL_QOUTE_JSON = "\\u222b";

  private static final String INTERSECTION_QOUTE_JSON = "\\u2229";

  private static final String INVERTED_EXCLAMATION_QOUTE_JSON = "\\u00a1";

  private static final String INVERTED_QUESTION_MARK_QOUTE_JSON = "\\u00bf";

  private static final String MODIFIER_LETTER_CIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u02c6";

  private static final String SOFT_HYPHEN_QOUTE_JSON = "\\u00ad";

  private static final String LOGICAL_AND_QOUTE_JSON = "\\u2227";

  private static final String LOGICAL_OR_QOUTE_JSON = "\\u2228";

  private static final String NONBREAKING_SPACE_QOUTE_JSON = "\\u00a0";

  private static final String CONTAINS_AS_MEMBER_QOUTE_JSON = "\\u220b";

  private static final String COPYRIGHT_QOUTE_JSON = "\\u00a9";

  private static final String FOR_ALL_QOUTE_JSON = "\\u2200";

  private static final String NOT_AN_ELEMENT_OF_QOUTE_JSON = "\\u2209";

  private static final String NOT_ASUBSET_OF_QOUTE_JSON = "\\u2284";

  private static final String NOT_EQUAL_TO_QOUTE_JSON = "\\u2260";

  private static final String DOT_OPERATOR_QOUTE_JSON = "\\u22c5";

  private static final String NOT_SIGN_QOUTE_JSON = "\\u00ac";

  private static final String DOUBLE_DAGGER_QOUTE_JSON = "\\u2021";

  private static final String DOUBLE_LOW_9_QUOTE_QOUTE_JSON = "\\u201e";

  private static final String DOUBLE_PRIME_QOUTE_JSON = "\\u2033";

  private static final String DOUBLE_QUOTATION_MARK_QOUTE_JSON = "\\u0022";

  private static final String POUND_STERLING_QOUTE_JSON = "\\u00a3";

  private static final String LOWERCASE_AACUTE_ACCENT_QOUTE_JSON = "\\u00e1";

  private static final String LOWERCASE_ACIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u00e2";

  private static final String LOWERCASE_AE_QOUTE_JSON = "\\u00e6";

  private static final String LOWERCASE_AGRAVE_ACCENT_QOUTE_JSON = "\\u00e0";

  private static final String LOWERCASE_ARING_QOUTE_JSON = "\\u00e5";

  private static final String LOWERCASE_ATILDE_QOUTE_JSON = "\\u00e3";

  private static final String LOWERCASE_AUMLAUT_QOUTE_JSON = "\\u00e4";

  private static final String LOWERCASE_CCEDILLA_QOUTE_JSON = "\\u00e7";

  private static final String LOWERCASE_EACUTE_ACCENT_QOUTE_JSON = "\\u00e9";

  private static final String LOWERCASE_ECIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u00ea";

  private static final String LOWERCASE_EGRAVE_ACCENT_QOUTE_JSON = "\\u00e8";

  private static final String LOWERCASE_ETH_ICELANDIC_QOUTE_JSON = "\\u00f0";

  private static final String LOWERCASE_EUMLAUT_QOUTE_JSON = "\\u00eb";

  private static final String LOWERCASE_IACUTE_ACCENT_QOUTE_JSON = "\\u00ed";

  private static final String LOWERCASE_ICIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u00ee";

  private static final String LOWERCASE_IGRAVE_ACCENT_QOUTE_JSON = "\\u00ec";

  private static final String LOWERCASE_IUMLAUT_QOUTE_JSON = "\\u00ef";

  private static final String LOWERCASE_NTILDE_QOUTE_JSON = "\\u00f1";

  private static final String LOWERCASE_OACUTE_ACCENT_QOUTE_JSON = "\\u00f3";

  private static final String LOWERCASE_OCIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u00f4";

  private static final String LOWERCASE_OGRAVE_ACCENT_QOUTE_JSON = "\\u00f2";

  private static final String LOWERCASE_OSLASH_QOUTE_JSON = "\\u00f8";

  private static final String LOWERCASE_OTILDE_QOUTE_JSON = "\\u00f5";

  private static final String LOWERCASE_OUMLAUT_QOUTE_JSON = "\\u00f6";

  private static final String LOWERCASE_SHARPS_GERMAN_QOUTE_JSON = "\\u00df";

  private static final String LOWERCASE_THORN_ICELANDIC_QOUTE_JSON = "\\u00fe";

  private static final String LOWERCASE_UACUTE_ACCENT_QOUTE_JSON = "\\u00fa";

  private static final String LOWERCASE_UCIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u00fb";

  private static final String LOWERCASE_UGRAVE_ACCENT_QOUTE_JSON = "\\u00f9";

  private static final String LOWERCASE_UUMLAUT_QOUTE_JSON = "\\u00fc";

  private static final String LOWERCASE_YACUTE_ACCENT_QOUTE_JSON = "\\u00fd";

  private static final String LOWERCASE_YUMLAUT_QOUTE_JSON = "\\u00ff";

  private static final String DOWNWARD_ARROW_QOUTE_JSON = "\\u2193";

  private static final String DOWNWARDS_ARROW_WITH_CORNER_LEFTWARDS_QOUTE_JSON = "\\u21b5";

  private static final String DOWNWARDS_DOUBLE_ARROW_QOUTE_JSON = "\\u21d3";

  private static final String LOZENGE_QOUTE_JSON = "\\u25ca";

  private static final String UP_TACK_QOUTE_JSON = "\\u22a5";

  private static final String APOSTROPHE_QOUTE_JSON = "\\u0027";

  private static final String UPPERCASE_AACUTE_ACCENT_QOUTE_JSON = "\\u00c1";

  private static final String UPPERCASE_ACIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u00c2";

  private static final String UPPERCASE_AE_QOUTE_JSON = "\\u00c6";

  private static final String UPPERCASE_AGRAVE_ACCENT_QOUTE_JSON = "\\u00c0";

  private static final String UPPERCASE_ARING_QOUTE_JSON = "\\u00c5";

  private static final String UPPERCASE_ATILDE_QOUTE_JSON = "\\u00c3";

  private static final String UPPERCASE_AUMLAUT_QOUTE_JSON = "\\u00c4";

  private static final String UPPERCASE_CCEDILLA_QOUTE_JSON = "\\u00c7";

  private static final String UPPERCASE_EACUTE_ACCENT_QOUTE_JSON = "\\u00c9";

  private static final String UPPERCASE_ECIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u00ca";

  private static final String UPPERCASE_EGRAVE_ACCENT_QOUTE_JSON = "\\u00c8";

  private static final String UPPERCASE_ETH_ICELANDIC_QOUTE_JSON = "\\u00d0";

  private static final String UPPERCASE_EUMLAUT_QOUTE_JSON = "\\u00cb";

  private static final String UPPERCASE_IACUTE_ACCENT_QOUTE_JSON = "\\u00cd";

  private static final String UPPERCASE_ICIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u00ce";

  private static final String UPPERCASE_IGRAVE_ACCENT_QOUTE_JSON = "\\u00cc";

  private static final String UPPERCASE_IUMLAUT_QOUTE_JSON = "\\u00cf";

  private static final String UPPERCASE_NTILDE_QOUTE_JSON = "\\u00d1";

  private static final String UPPERCASE_OACUTE_ACCENT_QOUTE_JSON = "\\u00d3";

  private static final String UPPERCASE_OCIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u00d4";

  private static final String UPPERCASE_OGRAVE_ACCENT_QOUTE_JSON = "\\u00d2";

  private static final String UPPERCASE_OSLASH_QOUTE_JSON = "\\u00d8";

  private static final String UPPERCASE_OTILDE_QOUTE_JSON = "\\u00d5";

  private static final String UPPERCASE_OUMLAUT_QOUTE_JSON = "\\u00d6";

  private static final String UPPERCASE_THORN_ICELANDIC_QOUTE_JSON = "\\u00de";

  private static final String UPPERCASE_UACUTE_ACCENT_QOUTE_JSON = "\\u00da";

  private static final String UPPERCASE_UCIRCUMFLEX_ACCENT_QOUTE_JSON = "\\u00db";

  private static final String UPPERCASE_UGRAVE_ACCENT_QOUTE_JSON = "\\u00d9";

  private static final String UPPERCASE_UUMLAUT_QOUTE_JSON = "\\u00dc";

  private static final String UPPERCASE_YACUTE_ACCENT_QOUTE_JSON = "\\u00dd";

  private static final String APPROXIMATELY_EQUAL_TO_QOUTE_JSON = "\\u2245";

  private static final String UPWARD_ARROW_QOUTE_JSON = "\\u2191";

  private static final String UPWARDS_DOUBLE_ARROW_QOUTE_JSON = "\\u21d1";

  private static final String SQUARE_ROOT_QOUTE_JSON = "\\u221a";

  private static final String TRADEMARK_SIGN_QOUTE_JSON = "\\u2122";

  private static final String GREATER_THAN_OR_EQUAL_TO_QOUTE_JSON = "\\u2265";

  private static final String GREATER_THAN_SIGN_QOUTE_JSON = "\\u003e";

  private static final String GREEK_CAPITAL_LETTER_ALPHA_QOUTE_JSON = "\\u0391";

  private static final String GREEK_CAPITAL_LETTER_BETA_QOUTE_JSON = "\\u0392";

  private static final String GREEK_CAPITAL_LETTER_CHI_QOUTE_JSON = "\\u03a7";

  private static final String GREEK_CAPITAL_LETTER_DELTA_QOUTE_JSON = "\\u0394";

  private static final String GREEK_CAPITAL_LETTER_EPSILON_QOUTE_JSON = "\\u0395";

  private static final String GREEK_CAPITAL_LETTER_ETA_QOUTE_JSON = "\\u0397";

  private static final String GREEK_CAPITAL_LETTER_GAMMA_QOUTE_JSON = "\\u0393";

  private static final String GREEK_CAPITAL_LETTER_IOTA_QOUTE_JSON = "\\u0399";

  private static final String GREEK_CAPITAL_LETTER_KAPPA_QOUTE_JSON = "\\u039a";

  private static final String GREEK_CAPITAL_LETTER_LAMDA_QOUTE_JSON = "\\u039b";

  private static final String GREEK_CAPITAL_LETTER_MU_QOUTE_JSON = "\\u039c";

  private static final String GREEK_CAPITAL_LETTER_NU_QOUTE_JSON = "\\u039d";

  private static final String GREEK_CAPITAL_LETTER_OMEGA_QOUTE_JSON = "\\u03a9";

  private static final String GREEK_CAPITAL_LETTER_OMICRON_QOUTE_JSON = "\\u039f";

  private static final String GREEK_CAPITAL_LETTER_PHI_QOUTE_JSON = "\\u03a6";

  private static final String GREEK_CAPITAL_LETTER_PI_QOUTE_JSON = "\\u03a0";

  private static final String GREEK_CAPITAL_LETTER_PSI_QOUTE_JSON = "\\u03a8";

  private static final String GREEK_CAPITAL_LETTER_RHO_QOUTE_JSON = "\\u03a1";

  private static final String GREEK_CAPITAL_LETTER_SIGMA_QOUTE_JSON = "\\u03a3";

  private static final String GREEK_CAPITAL_LETTER_TAU_QOUTE_JSON = "\\u03a4";

  private static final String GREEK_CAPITAL_LETTER_THETA_QOUTE_JSON = "\\u0398";

  private static final String GREEK_CAPITAL_LETTER_UPSILON_QOUTE_JSON = "\\u03a5";

  private static final String GREEK_CAPITAL_LETTER_XI_QOUTE_JSON = "\\u039e";

  private static final String GREEK_CAPITAL_LETTER_ZETA_QOUTE_JSON = "\\u0396";

  private static final String GREEK_PI_SYMBOL_QOUTE_JSON = "\\u03d6";

  private static final String GREEK_SMALL_LETTER_ALPHA_QOUTE_JSON = "\\u03b1";

  private static final String GREEK_SMALL_LETTER_BETA_QOUTE_JSON = "\\u03b2";

  private static final String GREEK_SMALL_LETTER_CHI_QOUTE_JSON = "\\u03c7";

  private static final String GREEK_SMALL_LETTER_DELTA_QOUTE_JSON = "\\u03b4";

  private static final String GREEK_SMALL_LETTER_EPSILON_QOUTE_JSON = "\\u03b5";

  private static final String GREEK_SMALL_LETTER_ETA_QOUTE_JSON = "\\u03b7";

  private static final String GREEK_SMALL_LETTER_FINAL_SIGMA_QOUTE_JSON = "\\u03c2";

  private static final String GREEK_SMALL_LETTER_GAMMA_QOUTE_JSON = "\\u03b3";

  private static final String GREEK_SMALL_LETTER_IOTA_QOUTE_JSON = "\\u03b9";

  private static final String GREEK_SMALL_LETTER_KAPPA_QOUTE_JSON = "\\u03ba";

  private static final String GREEK_SMALL_LETTER_LAMDA_QOUTE_JSON = "\\u03bb";

  private static final String GREEK_SMALL_LETTER_MU_QOUTE_JSON = "\\u03bc";

  private static final String GREEK_SMALL_LETTER_NU_QOUTE_JSON = "\\u03bd";

  private static final String GREEK_SMALL_LETTER_OMEGA_QOUTE_JSON = "\\u03c9";

  private static final String GREEK_SMALL_LETTER_OMICRON_QOUTE_JSON = "\\u03bf";

  private static final String GREEK_SMALL_LETTER_PHI_QOUTE_JSON = "\\u03c6";

  private static final String GREEK_SMALL_LETTER_PI_QOUTE_JSON = "\\u03c0";

  private static final String GREEK_SMALL_LETTER_PSI_QOUTE_JSON = "\\u03c8";

  private static final String GREEK_SMALL_LETTER_RHO_QOUTE_JSON = "\\u03c1";

  private static final String GREEK_SMALL_LETTER_SIGMA_QOUTE_JSON = "\\u03c3";

  private static final String GREEK_SMALL_LETTER_TAU_QOUTE_JSON = "\\u03c4";

  private static final String GREEK_SMALL_LETTER_THETA_QOUTE_JSON = "\\u03b8";

  private static final String GREEK_SMALL_LETTER_UPSILON_QOUTE_JSON = "\\u03c5";

  private static final String GREEK_SMALL_LETTER_XI_QOUTE_JSON = "\\u03be";

  private static final String GREEK_SMALL_LETTER_ZETA_QOUTE_JSON = "\\u03b6";

  private static final String GREEK_THETA_SYMBOL_QOUTE_JSON = "\\u03d1";

  private static final String GREEK_UPSILON_WITH_HOOK_SYMBOL_QOUTE_JSON = "\\u03d2";

  private static final String PRIME_QOUTE_JSON = "\\u2032";

  private static final String BROKEN_BAR_QOUTE_JSON = "\\u00a6";

  private static final String PROPORTIONAL_TO_QOUTE_JSON = "\\u221d";

  private static final String ASTERISK_OPERATOR_QOUTE_JSON = "\\u2217";

  private static final String SUBSET_OF_OR_EQUAL_TO_QOUTE_JSON = "\\u2286";

  private static final String SUBSET_OF_QOUTE_JSON = "\\u2282";

  private static final String BULLET_QOUTE_JSON = "\\u2022";

  private static final String MULTIPLICATION_SIGN_QOUTE_JSON = "\\u00d7";

  private static final String SUPERSCRIPT_ONE_QOUTE_JSON = "\\u00b9";

  private static final String SUPERSCRIPT_THREE_QOUTE_JSON = "\\u00b3";

  private static final String SUPERSCRIPT_TWO_QOUTE_JSON = "\\u00b2";

  private static final String SUPERSET_OF_OR_EQUAL_TO_QOUTE_JSON = "\\u2287";

  private static final String SUPERSET_OF_QOUTE_JSON = "\\u2283";

  private static final String EURO_SIGN_QOUTE_JSON = "\\u20ac";

  private static final String OVERLINE_SPACING_OVERSCORE_QOUTE_JSON = "\\u203e";

  private static String EINZUG_JSON_FORMAT = "  ";

  private static String getStringJson( String pString )
  {
    /*
     * https://stackoverflow.com/questions/1367322/what-are-all-the-escape-characters
     */

    if ( pString == null )
    {
      return "null";
    }

    StringBuffer ergebnis = new StringBuffer();

    ergebnis.append( "\"" );

    int length = pString.length();

    for ( int akt_index = 0; akt_index < length; akt_index ++ )
    {
      char akt_zeichen = pString.charAt( akt_index );

      switch ( akt_zeichen )
      {
        case '\n':
          ergebnis.append( "\\n" );
          break;

        case '\r':
          ergebnis.append( "\\r" );
          break;

        case '\t':
          /*
           * https://stackoverflow.com/questions/19799006/unable-to-parse-tab-in-json-files
           * 
           * Tabulatorzeichen muessen im Json-String escaped werden, da sonst Fehler. 
           */
          ergebnis.append( "\\t" );
          break;

        case '"':
          ergebnis.append( "\\\"" );
          break;

        case '\\':
          ergebnis.append( "\\\\" );
          break;
// FALSCH
//        case '\'' :
//          ergebnis.append( "\\\'" );
//          break;

        case NULL_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case START_OF_HEADING_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case START_OF_TEXT_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case END_OF_TEXT_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case END_OF_TRANSMISSION_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case ENQUIRY_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case ACKNOWLEDGE_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case BELL_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case BACKSPACE_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case VERTICAL_TAB_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case FORM_FEED_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case SHIFT_OUT_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case SHIFT_IN_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case DATA_LINK_ESCAPE_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case DEVICE_CONTROL_1_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case DEVICE_CONTROL_2_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case DEVICE_CONTROL_3_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case DEVICE_CONTROL_4_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case NEGATIVE_ACKNOWLEDGE_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case SYNCHRONOUS_IDLE_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case END_OF_TRANSMISSION_BLOCK_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case CANCEL_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case END_OF_MEDIUM_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case SUBSTITUTE_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case ESCAPE_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case FILE_SEPARATOR_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case GROUP_SEPARATOR_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case RECORD_SEPARATOR_CHAR_CODE:
          ergebnis.append( " " );
          break;
        case UNIT_SEPARATOR_CHAR_CODE:
          ergebnis.append( " " );
          break;

        case NABLA_CHAR_CODE:
          ergebnis.append( NABLA_QOUTE_JSON );
          break;

        case MACRON_CHAR_CODE:
          ergebnis.append( MACRON_QOUTE_JSON );
          break;

        case DAGGER_CHAR_CODE:
          ergebnis.append( DAGGER_QOUTE_JSON );
          break;

        case PARAGRAPH_SIGN_CHAR_CODE:
          ergebnis.append( PARAGRAPH_SIGN_QOUTE_JSON );
          break;

        case PARTIAL_DIFFERENTIAL_CHAR_CODE:
          ergebnis.append( PARTIAL_DIFFERENTIAL_QOUTE_JSON );
          break;

        case NARY_PRODUCT_CHAR_CODE:
          ergebnis.append( NARY_PRODUCT_QOUTE_JSON );
          break;

        case NARY_SUMMATION_CHAR_CODE:
          ergebnis.append( NARY_SUMMATION_QOUTE_JSON );
          break;

        case MASCULINE_ORDINAL_CHAR_CODE:
          ergebnis.append( MASCULINE_ORDINAL_QOUTE_JSON );
          break;

        case LATIN_CAPITAL_LETTER_SWITH_CARON_CHAR_CODE:
          ergebnis.append( LATIN_CAPITAL_LETTER_SWITH_CARON_QOUTE_JSON );
          break;

        case LATIN_CAPITAL_LETTER_YWITH_DIAERESIS_CHAR_CODE:
          ergebnis.append( LATIN_CAPITAL_LETTER_YWITH_DIAERESIS_QOUTE_JSON );
          break;

        case LATIN_CAPITAL_LIGATURE_OE_CHAR_CODE:
          ergebnis.append( LATIN_CAPITAL_LIGATURE_OE_QOUTE_JSON );
          break;

        case LATIN_SMALL_LETTER_FWITH_HOOK_CHAR_CODE:
          ergebnis.append( LATIN_SMALL_LETTER_FWITH_HOOK_QOUTE_JSON );
          break;

        case LATIN_SMALL_LETTER_SWITH_CARON_CHAR_CODE:
          ergebnis.append( LATIN_SMALL_LETTER_SWITH_CARON_QOUTE_JSON );
          break;

        case LATIN_SMALL_LIGATURE_OE_CHAR_CODE:
          ergebnis.append( LATIN_SMALL_LIGATURE_OE_QOUTE_JSON );
          break;

        case SCRIPT_CAPITAL_P_CHAR_CODE:
          ergebnis.append( SCRIPT_CAPITAL_P_QOUTE_JSON );
          break;

        case ACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( ACUTE_ACCENT_QOUTE_JSON );
          break;

        case IDENTICAL_TO_CHAR_CODE:
          ergebnis.append( IDENTICAL_TO_QOUTE_JSON );
          break;

        case SECTION_SIGN_CHAR_CODE:
          ergebnis.append( SECTION_SIGN_QOUTE_JSON );
          break;

        case CEDILLA_CHAR_CODE:
          ergebnis.append( CEDILLA_QOUTE_JSON );
          break;

        case LEFT_ANGLE_QUOTE_CHAR_CODE:
          ergebnis.append( LEFT_ANGLE_QUOTE_QOUTE_JSON );
          break;

        case LEFT_CEILING_CHAR_CODE:
          ergebnis.append( LEFT_CEILING_QOUTE_JSON );
          break;

        case LEFT_DOUBLE_QUOTE_CHAR_CODE:
          ergebnis.append( LEFT_DOUBLE_QUOTE_QOUTE_JSON );
          break;

        case LEFT_FLOOR_CHAR_CODE:
          ergebnis.append( LEFT_FLOOR_QOUTE_JSON );
          break;

        case LEFT_POINTING_ANGLE_BRACKET_CHAR_CODE:
          ergebnis.append( LEFT_POINTING_ANGLE_BRACKET_QOUTE_JSON );
          break;

        case LEFT_RIGHT_ARROW_CHAR_CODE:
          ergebnis.append( LEFT_RIGHT_ARROW_QOUTE_JSON );
          break;

        case LEFT_RIGHT_DOUBLE_ARROW_CHAR_CODE:
          ergebnis.append( LEFT_RIGHT_DOUBLE_ARROW_QOUTE_JSON );
          break;

        case LEFT_SINGLE_QUOTE_CHAR_CODE:
          ergebnis.append( LEFT_SINGLE_QUOTE_QOUTE_JSON );
          break;

        case LEFT_TO_RIGHT_MARK_CHAR_CODE:
          ergebnis.append( LEFT_TO_RIGHT_MARK_QOUTE_JSON );
          break;

        case LEFTWARD_ARROW_CHAR_CODE:
          ergebnis.append( LEFTWARD_ARROW_QOUTE_JSON );
          break;

        case LEFTWARDS_DOUBLE_ARROW_CHAR_CODE:
          ergebnis.append( LEFTWARDS_DOUBLE_ARROW_QOUTE_JSON );
          break;

        case REGISTERED_TRADEMARK_CHAR_CODE:
          ergebnis.append( REGISTERED_TRADEMARK_QOUTE_JSON );
          break;

        case DEGREE_SIGN_CHAR_CODE:
          ergebnis.append( DEGREE_SIGN_QOUTE_JSON );
          break;

        case FEMININE_ORDINAL_CHAR_CODE:
          ergebnis.append( FEMININE_ORDINAL_QOUTE_JSON );
          break;

        case YEN_SIGN_CHAR_CODE:
          ergebnis.append( YEN_SIGN_QOUTE_JSON );
          break;

        case GENERAL_CURRENCY_SIGN_CHAR_CODE:
          ergebnis.append( GENERAL_CURRENCY_SIGN_QOUTE_JSON );
          break;

        case CENT_SIGN_CHAR_CODE:
          ergebnis.append( CENT_SIGN_QOUTE_JSON );
          break;

        case PER_MILL_SIGN_CHAR_CODE:
          ergebnis.append( PER_MILL_SIGN_QOUTE_JSON );
          break;

        case ZERO_WIDTH_JOINER_CHAR_CODE:
          ergebnis.append( ZERO_WIDTH_JOINER_QOUTE_JSON );
          break;

        case ZERO_WIDTH_NON_JOINER_CHAR_CODE:
          ergebnis.append( ZERO_WIDTH_NON_JOINER_QOUTE_JSON );
          break;

        case LESS_THAN_OR_EQUAL_TO_CHAR_CODE:
          ergebnis.append( LESS_THAN_OR_EQUAL_TO_QOUTE_JSON );
          break;

        case LESS_THAN_SIGN_CHAR_CODE:
          ergebnis.append( LESS_THAN_SIGN_QOUTE_JSON );
          break;

        case THERE_EXISTS_CHAR_CODE:
          ergebnis.append( THERE_EXISTS_QOUTE_JSON );
          break;

        case THEREFORE_CHAR_CODE:
          ergebnis.append( THEREFORE_QOUTE_JSON );
          break;

        case THIN_SPACE_CHAR_CODE:
          ergebnis.append( THIN_SPACE_QOUTE_JSON );
          break;

        case THREE_FOURTHS_CHAR_CODE:
          ergebnis.append( THREE_FOURTHS_QOUTE_JSON );
          break;

        case DIAERESIS_CHAR_CODE:
          ergebnis.append( DIAERESIS_QOUTE_JSON );
          break;

        case MICRO_SIGN_CHAR_CODE:
          ergebnis.append( MICRO_SIGN_QOUTE_JSON );
          break;

        case MIDDLE_DOT_CHAR_CODE:
          ergebnis.append( MIDDLE_DOT_QOUTE_JSON );
          break;

        case RIGHT_ANGLE_QUOTE_CHAR_CODE:
          ergebnis.append( RIGHT_ANGLE_QUOTE_QOUTE_JSON );
          break;

        case RIGHT_CEILING_CHAR_CODE:
          ergebnis.append( RIGHT_CEILING_QOUTE_JSON );
          break;

        case RIGHT_DOUBLE_QUOTE_CHAR_CODE:
          ergebnis.append( RIGHT_DOUBLE_QUOTE_QOUTE_JSON );
          break;

        case RIGHT_FLOOR_CHAR_CODE:
          ergebnis.append( RIGHT_FLOOR_QOUTE_JSON );
          break;

        case RIGHT_POINTING_ANGLE_BRACKET_CHAR_CODE:
          ergebnis.append( RIGHT_POINTING_ANGLE_BRACKET_QOUTE_JSON );
          break;

        case RIGHT_SINGLE_QUOTE_CHAR_CODE:
          ergebnis.append( RIGHT_SINGLE_QUOTE_QOUTE_JSON );
          break;

        case RIGHT_TO_LEFT_MARK_CHAR_CODE:
          ergebnis.append( RIGHT_TO_LEFT_MARK_QOUTE_JSON );
          break;

        case RIGHTWARD_ARROW_CHAR_CODE:
          ergebnis.append( RIGHTWARD_ARROW_QOUTE_JSON );
          break;

        case RIGHTWARDS_DOUBLE_ARROW_CHAR_CODE:
          ergebnis.append( RIGHTWARDS_DOUBLE_ARROW_QOUTE_JSON );
          break;

        case TILDE_OPERATOR_CHAR_CODE:
          ergebnis.append( TILDE_OPERATOR_QOUTE_JSON );
          break;

        case SINGLE_LEFT_POINTING_ANGLE_QUOTE_CHAR_CODE:
          ergebnis.append( SINGLE_LEFT_POINTING_ANGLE_QUOTE_QOUTE_JSON );
          break;

        case SINGLE_LOW_9_QUOTE_CHAR_CODE:
          ergebnis.append( SINGLE_LOW_9_QUOTE_QOUTE_JSON );
          break;

        case SINGLE_RIGHT_POINTING_ANGLE_QUOTE_CHAR_CODE:
          ergebnis.append( SINGLE_RIGHT_POINTING_ANGLE_QUOTE_QOUTE_JSON );
          break;

        case MINUS_SIGN_CHAR_CODE:
          ergebnis.append( MINUS_SIGN_QOUTE_JSON );
          break;

        case CIRCLED_PLUS_CHAR_CODE:
          ergebnis.append( CIRCLED_PLUS_QOUTE_JSON );
          break;

        case CIRCLED_TIMES_CHAR_CODE:
          ergebnis.append( CIRCLED_TIMES_QOUTE_JSON );
          break;

        case DIVISION_SIGN_CHAR_CODE:
          ergebnis.append( DIVISION_SIGN_QOUTE_JSON );
          break;

        case BLACK_CLUB_SUIT_CHAR_CODE:
          ergebnis.append( BLACK_CLUB_SUIT_QOUTE_JSON );
          break;

        case BLACK_DIAMOND_SUIT_CHAR_CODE:
          ergebnis.append( BLACK_DIAMOND_SUIT_QOUTE_JSON );
          break;

        case BLACK_HEART_SUIT_CHAR_CODE:
          ergebnis.append( BLACK_HEART_SUIT_QOUTE_JSON );
          break;

        case BLACK_LETTER_CAPITAL_I_CHAR_CODE:
          ergebnis.append( BLACK_LETTER_CAPITAL_I_QOUTE_JSON );
          break;

        case BLACK_LETTER_CAPITAL_R_CHAR_CODE:
          ergebnis.append( BLACK_LETTER_CAPITAL_R_QOUTE_JSON );
          break;

        case BLACK_SPADE_SUIT_CHAR_CODE:
          ergebnis.append( BLACK_SPADE_SUIT_QOUTE_JSON );
          break;

        case SLASH_CHAR_CODE:
          ergebnis.append( SLASH_QOUTE_JSON );
          break;

        case ALEF_SYMBOL_CHAR_CODE:
          ergebnis.append( ALEF_SYMBOL_QOUTE_JSON );
          break;

        case ELEMENT_OF_CHAR_CODE:
          ergebnis.append( ELEMENT_OF_QOUTE_JSON );
          break;

        case ELLIPSES_CHAR_CODE:
          ergebnis.append( ELLIPSES_QOUTE_JSON );
          break;

        case ALMOST_EQUAL_TO_CHAR_CODE:
          ergebnis.append( ALMOST_EQUAL_TO_QOUTE_JSON );
          break;

        case PLUS_OR_MINUS_CHAR_CODE:
          ergebnis.append( PLUS_OR_MINUS_QOUTE_JSON );
          break;

        case EM_DASH_CHAR_CODE:
          ergebnis.append( EM_DASH_QOUTE_JSON );
          break;

        case EM_SPACE_CHAR_CODE:
          ergebnis.append( EM_SPACE_QOUTE_JSON );
          break;

        case SMALL_TILDE_CHAR_CODE:
          ergebnis.append( SMALL_TILDE_QOUTE_JSON );
          break;

        case AMPERSAND_CHAR_CODE:
          ergebnis.append( AMPERSAND_QOUTE_JSON );
          break;

        case EMPTY_SET_CHAR_CODE:
          ergebnis.append( EMPTY_SET_QOUTE_JSON );
          break;

        case EN_DASH_CHAR_CODE:
          ergebnis.append( EN_DASH_QOUTE_JSON );
          break;

        case EN_SPACE_CHAR_CODE:
          ergebnis.append( EN_SPACE_QOUTE_JSON );
          break;

        case ONE_FOURTH_CHAR_CODE:
          ergebnis.append( ONE_FOURTH_QOUTE_JSON );
          break;

        case ONE_HALF_CHAR_CODE:
          ergebnis.append( ONE_HALF_QOUTE_JSON );
          break;

        case INFINITY_CHAR_CODE:
          ergebnis.append( INFINITY_QOUTE_JSON );
          break;

        case ANGLE_CHAR_CODE:
          ergebnis.append( ANGLE_QOUTE_JSON );
          break;

        case UNION_CHAR_CODE:
          ergebnis.append( UNION_QOUTE_JSON );
          break;

        case INTEGRAL_CHAR_CODE:
          ergebnis.append( INTEGRAL_QOUTE_JSON );
          break;

        case INTERSECTION_CHAR_CODE:
          ergebnis.append( INTERSECTION_QOUTE_JSON );
          break;

        case INVERTED_EXCLAMATION_CHAR_CODE:
          ergebnis.append( INVERTED_EXCLAMATION_QOUTE_JSON );
          break;

        case INVERTED_QUESTION_MARK_CHAR_CODE:
          ergebnis.append( INVERTED_QUESTION_MARK_QOUTE_JSON );
          break;

        case MODIFIER_LETTER_CIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( MODIFIER_LETTER_CIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case SOFT_HYPHEN_CHAR_CODE:
          ergebnis.append( SOFT_HYPHEN_QOUTE_JSON );
          break;

        case LOGICAL_AND_CHAR_CODE:
          ergebnis.append( LOGICAL_AND_QOUTE_JSON );
          break;

        case LOGICAL_OR_CHAR_CODE:
          ergebnis.append( LOGICAL_OR_QOUTE_JSON );
          break;

        case NONBREAKING_SPACE_CHAR_CODE:
          ergebnis.append( NONBREAKING_SPACE_QOUTE_JSON );
          break;

        case CONTAINS_AS_MEMBER_CHAR_CODE:
          ergebnis.append( CONTAINS_AS_MEMBER_QOUTE_JSON );
          break;

        case COPYRIGHT_CHAR_CODE:
          ergebnis.append( COPYRIGHT_QOUTE_JSON );
          break;

        case FOR_ALL_CHAR_CODE:
          ergebnis.append( FOR_ALL_QOUTE_JSON );
          break;

        case NOT_AN_ELEMENT_OF_CHAR_CODE:
          ergebnis.append( NOT_AN_ELEMENT_OF_QOUTE_JSON );
          break;

        case NOT_ASUBSET_OF_CHAR_CODE:
          ergebnis.append( NOT_ASUBSET_OF_QOUTE_JSON );
          break;

        case NOT_EQUAL_TO_CHAR_CODE:
          ergebnis.append( NOT_EQUAL_TO_QOUTE_JSON );
          break;

        case DOT_OPERATOR_CHAR_CODE:
          ergebnis.append( DOT_OPERATOR_QOUTE_JSON );
          break;

        case NOT_SIGN_CHAR_CODE:
          ergebnis.append( NOT_SIGN_QOUTE_JSON );
          break;

        case DOUBLE_DAGGER_CHAR_CODE:
          ergebnis.append( DOUBLE_DAGGER_QOUTE_JSON );
          break;

        case DOUBLE_LOW_9_QUOTE_CHAR_CODE:
          ergebnis.append( DOUBLE_LOW_9_QUOTE_QOUTE_JSON );
          break;

        case DOUBLE_PRIME_CHAR_CODE:
          ergebnis.append( DOUBLE_PRIME_QOUTE_JSON );
          break;

        case POUND_STERLING_CHAR_CODE:
          ergebnis.append( POUND_STERLING_QOUTE_JSON );
          break;

        case LOWERCASE_AACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_AACUTE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_ACIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_ACIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_AE_CHAR_CODE:
          ergebnis.append( LOWERCASE_AE_QOUTE_JSON );
          break;

        case LOWERCASE_AGRAVE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_AGRAVE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_ARING_CHAR_CODE:
          ergebnis.append( LOWERCASE_ARING_QOUTE_JSON );
          break;

        case LOWERCASE_ATILDE_CHAR_CODE:
          ergebnis.append( LOWERCASE_ATILDE_QOUTE_JSON );
          break;

        case LOWERCASE_AUMLAUT_CHAR_CODE:
          ergebnis.append( LOWERCASE_AUMLAUT_QOUTE_JSON );
          break;

        case LOWERCASE_CCEDILLA_CHAR_CODE:
          ergebnis.append( LOWERCASE_CCEDILLA_QOUTE_JSON );
          break;

        case LOWERCASE_EACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_EACUTE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_ECIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_ECIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_EGRAVE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_EGRAVE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_ETH_ICELANDIC_CHAR_CODE:
          ergebnis.append( LOWERCASE_ETH_ICELANDIC_QOUTE_JSON );
          break;

        case LOWERCASE_EUMLAUT_CHAR_CODE:
          ergebnis.append( LOWERCASE_EUMLAUT_QOUTE_JSON );
          break;

        case LOWERCASE_IACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_IACUTE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_ICIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_ICIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_IGRAVE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_IGRAVE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_IUMLAUT_CHAR_CODE:
          ergebnis.append( LOWERCASE_IUMLAUT_QOUTE_JSON );
          break;

        case LOWERCASE_NTILDE_CHAR_CODE:
          ergebnis.append( LOWERCASE_NTILDE_QOUTE_JSON );
          break;

        case LOWERCASE_OACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_OACUTE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_OCIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_OCIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_OGRAVE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_OGRAVE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_OSLASH_CHAR_CODE:
          ergebnis.append( LOWERCASE_OSLASH_QOUTE_JSON );
          break;

        case LOWERCASE_OTILDE_CHAR_CODE:
          ergebnis.append( LOWERCASE_OTILDE_QOUTE_JSON );
          break;

        case LOWERCASE_OUMLAUT_CHAR_CODE:
          ergebnis.append( LOWERCASE_OUMLAUT_QOUTE_JSON );
          break;

        case LOWERCASE_SHARPS_GERMAN_CHAR_CODE:
          ergebnis.append( LOWERCASE_SHARPS_GERMAN_QOUTE_JSON );
          break;

        case LOWERCASE_THORN_ICELANDIC_CHAR_CODE:
          ergebnis.append( LOWERCASE_THORN_ICELANDIC_QOUTE_JSON );
          break;

        case LOWERCASE_UACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_UACUTE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_UCIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_UCIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_UGRAVE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_UGRAVE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_UUMLAUT_CHAR_CODE:
          ergebnis.append( LOWERCASE_UUMLAUT_QOUTE_JSON );
          break;

        case LOWERCASE_YACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( LOWERCASE_YACUTE_ACCENT_QOUTE_JSON );
          break;

        case LOWERCASE_YUMLAUT_CHAR_CODE:
          ergebnis.append( LOWERCASE_YUMLAUT_QOUTE_JSON );
          break;

        case DOWNWARD_ARROW_CHAR_CODE:
          ergebnis.append( DOWNWARD_ARROW_QOUTE_JSON );
          break;

        case DOWNWARDS_ARROW_WITH_CORNER_LEFTWARDS_CHAR_CODE:
          ergebnis.append( DOWNWARDS_ARROW_WITH_CORNER_LEFTWARDS_QOUTE_JSON );
          break;

        case DOWNWARDS_DOUBLE_ARROW_CHAR_CODE:
          ergebnis.append( DOWNWARDS_DOUBLE_ARROW_QOUTE_JSON );
          break;

        case LOZENGE_CHAR_CODE:
          ergebnis.append( LOZENGE_QOUTE_JSON );
          break;

        case UP_TACK_CHAR_CODE:
          ergebnis.append( UP_TACK_QOUTE_JSON );
          break;
        case UPPERCASE_AACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_AACUTE_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_ACIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_ACIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_AE_CHAR_CODE:
          ergebnis.append( UPPERCASE_AE_QOUTE_JSON );
          break;

        case UPPERCASE_AGRAVE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_AGRAVE_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_ARING_CHAR_CODE:
          ergebnis.append( UPPERCASE_ARING_QOUTE_JSON );
          break;

        case UPPERCASE_ATILDE_CHAR_CODE:
          ergebnis.append( UPPERCASE_ATILDE_QOUTE_JSON );
          break;

        case UPPERCASE_AUMLAUT_CHAR_CODE:
          ergebnis.append( UPPERCASE_AUMLAUT_QOUTE_JSON );
          break;

        case UPPERCASE_CCEDILLA_CHAR_CODE:
          ergebnis.append( UPPERCASE_CCEDILLA_QOUTE_JSON );
          break;

        case UPPERCASE_EACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_EACUTE_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_ECIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_ECIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_EGRAVE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_EGRAVE_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_ETH_ICELANDIC_CHAR_CODE:
          ergebnis.append( UPPERCASE_ETH_ICELANDIC_QOUTE_JSON );
          break;

        case UPPERCASE_EUMLAUT_CHAR_CODE:
          ergebnis.append( UPPERCASE_EUMLAUT_QOUTE_JSON );
          break;

        case UPPERCASE_IACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_IACUTE_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_ICIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_ICIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_IGRAVE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_IGRAVE_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_IUMLAUT_CHAR_CODE:
          ergebnis.append( UPPERCASE_IUMLAUT_QOUTE_JSON );
          break;

        case UPPERCASE_NTILDE_CHAR_CODE:
          ergebnis.append( UPPERCASE_NTILDE_QOUTE_JSON );
          break;

        case UPPERCASE_OACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_OACUTE_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_OCIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_OCIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_OGRAVE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_OGRAVE_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_OSLASH_CHAR_CODE:
          ergebnis.append( UPPERCASE_OSLASH_QOUTE_JSON );
          break;

        case UPPERCASE_OTILDE_CHAR_CODE:
          ergebnis.append( UPPERCASE_OTILDE_QOUTE_JSON );
          break;

        case UPPERCASE_OUMLAUT_CHAR_CODE:
          ergebnis.append( UPPERCASE_OUMLAUT_QOUTE_JSON );
          break;

        case UPPERCASE_THORN_ICELANDIC_CHAR_CODE:
          ergebnis.append( UPPERCASE_THORN_ICELANDIC_QOUTE_JSON );
          break;

        case UPPERCASE_UACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_UACUTE_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_UCIRCUMFLEX_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_UCIRCUMFLEX_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_UGRAVE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_UGRAVE_ACCENT_QOUTE_JSON );
          break;

        case UPPERCASE_UUMLAUT_CHAR_CODE:
          ergebnis.append( UPPERCASE_UUMLAUT_QOUTE_JSON );
          break;

        case UPPERCASE_YACUTE_ACCENT_CHAR_CODE:
          ergebnis.append( UPPERCASE_YACUTE_ACCENT_QOUTE_JSON );
          break;

        case APPROXIMATELY_EQUAL_TO_CHAR_CODE:
          ergebnis.append( APPROXIMATELY_EQUAL_TO_QOUTE_JSON );
          break;

        case UPWARD_ARROW_CHAR_CODE:
          ergebnis.append( UPWARD_ARROW_QOUTE_JSON );
          break;

        case UPWARDS_DOUBLE_ARROW_CHAR_CODE:
          ergebnis.append( UPWARDS_DOUBLE_ARROW_QOUTE_JSON );
          break;

        case SQUARE_ROOT_CHAR_CODE:
          ergebnis.append( SQUARE_ROOT_QOUTE_JSON );
          break;

        case TRADEMARK_SIGN_CHAR_CODE:
          ergebnis.append( TRADEMARK_SIGN_QOUTE_JSON );
          break;

        case GREATER_THAN_OR_EQUAL_TO_CHAR_CODE:
          ergebnis.append( GREATER_THAN_OR_EQUAL_TO_QOUTE_JSON );
          break;

        case GREATER_THAN_SIGN_CHAR_CODE:
          ergebnis.append( GREATER_THAN_SIGN_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_ALPHA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_ALPHA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_BETA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_BETA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_CHI_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_CHI_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_DELTA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_DELTA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_EPSILON_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_EPSILON_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_ETA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_ETA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_GAMMA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_GAMMA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_IOTA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_IOTA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_KAPPA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_KAPPA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_LAMDA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_LAMDA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_MU_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_MU_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_NU_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_NU_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_OMEGA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_OMEGA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_OMICRON_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_OMICRON_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_PHI_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_PHI_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_PI_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_PI_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_PSI_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_PSI_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_RHO_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_RHO_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_SIGMA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_SIGMA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_TAU_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_TAU_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_THETA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_THETA_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_UPSILON_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_UPSILON_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_XI_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_XI_QOUTE_JSON );
          break;

        case GREEK_CAPITAL_LETTER_ZETA_CHAR_CODE:
          ergebnis.append( GREEK_CAPITAL_LETTER_ZETA_QOUTE_JSON );
          break;

        case GREEK_PI_SYMBOL_CHAR_CODE:
          ergebnis.append( GREEK_PI_SYMBOL_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_ALPHA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_ALPHA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_BETA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_BETA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_CHI_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_CHI_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_DELTA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_DELTA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_EPSILON_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_EPSILON_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_ETA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_ETA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_FINAL_SIGMA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_FINAL_SIGMA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_GAMMA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_GAMMA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_IOTA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_IOTA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_KAPPA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_KAPPA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_LAMDA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_LAMDA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_MU_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_MU_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_NU_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_NU_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_OMEGA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_OMEGA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_OMICRON_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_OMICRON_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_PHI_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_PHI_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_PI_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_PI_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_PSI_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_PSI_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_RHO_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_RHO_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_SIGMA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_SIGMA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_TAU_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_TAU_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_THETA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_THETA_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_UPSILON_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_UPSILON_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_XI_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_XI_QOUTE_JSON );
          break;

        case GREEK_SMALL_LETTER_ZETA_CHAR_CODE:
          ergebnis.append( GREEK_SMALL_LETTER_ZETA_QOUTE_JSON );
          break;

        case GREEK_THETA_SYMBOL_CHAR_CODE:
          ergebnis.append( GREEK_THETA_SYMBOL_QOUTE_JSON );
          break;

        case GREEK_UPSILON_WITH_HOOK_SYMBOL_CHAR_CODE:
          ergebnis.append( GREEK_UPSILON_WITH_HOOK_SYMBOL_QOUTE_JSON );
          break;

        case PRIME_CHAR_CODE:
          ergebnis.append( PRIME_QOUTE_JSON );
          break;

        case BROKEN_BAR_CHAR_CODE:
          ergebnis.append( BROKEN_BAR_QOUTE_JSON );
          break;

        case PROPORTIONAL_TO_CHAR_CODE:
          ergebnis.append( PROPORTIONAL_TO_QOUTE_JSON );
          break;

        case ASTERISK_OPERATOR_CHAR_CODE:
          ergebnis.append( ASTERISK_OPERATOR_QOUTE_JSON );
          break;

        case SUBSET_OF_OR_EQUAL_TO_CHAR_CODE:
          ergebnis.append( SUBSET_OF_OR_EQUAL_TO_QOUTE_JSON );
          break;

        case SUBSET_OF_CHAR_CODE:
          ergebnis.append( SUBSET_OF_QOUTE_JSON );
          break;

        case BULLET_CHAR_CODE:
          ergebnis.append( BULLET_QOUTE_JSON );
          break;

        case MULTIPLICATION_SIGN_CHAR_CODE:
          ergebnis.append( MULTIPLICATION_SIGN_QOUTE_JSON );
          break;

        case SUPERSCRIPT_ONE_CHAR_CODE:
          ergebnis.append( SUPERSCRIPT_ONE_QOUTE_JSON );
          break;

        case SUPERSCRIPT_THREE_CHAR_CODE:
          ergebnis.append( SUPERSCRIPT_THREE_QOUTE_JSON );
          break;

        case SUPERSCRIPT_TWO_CHAR_CODE:
          ergebnis.append( SUPERSCRIPT_TWO_QOUTE_JSON );
          break;

        case SUPERSET_OF_OR_EQUAL_TO_CHAR_CODE:
          ergebnis.append( SUPERSET_OF_OR_EQUAL_TO_QOUTE_JSON );
          break;

        case SUPERSET_OF_CHAR_CODE:
          ergebnis.append( SUPERSET_OF_QOUTE_JSON );
          break;

        case EURO_SIGN_CHAR_CODE:
          ergebnis.append( EURO_SIGN_QOUTE_JSON );
          break;

        case OVERLINE_SPACING_OVERSCORE_CHAR_CODE:
          ergebnis.append( OVERLINE_SPACING_OVERSCORE_QOUTE_JSON );
          break;

        default:
          ergebnis.append( akt_zeichen );
      }
    }

    ergebnis.append( "\"" );

    return ergebnis.toString();
  }

}
