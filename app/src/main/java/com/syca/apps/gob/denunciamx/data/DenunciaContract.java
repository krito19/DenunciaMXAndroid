package com.syca.apps.gob.denunciamx.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.util.ArrayMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JARP on 10/14/14.
 */
public class DenunciaContract {

    public static final String CONTENT_AUTHORITY="com.syca.apps.gob.denunciamx";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_USER = "user";
    public static final String PATH_DENUNCIA_INFO = "denunciainfo";
    public static final String PATH_DENUNCIA_EVIDENCIA = "evidencias";
    public static final String PATH_DENUNCIA_HECHOS = "hechos";
    public static final String PATH_DENUNCIA_HISTORIA = "historia";
    public static final String PATH_DEPENDENCIA = "dependencia";

    // Format used for storing dates in the database.  ALso used for converting those strings
    // back into date objects for comparison/processing.
    public static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * Converts Date class to a string representation, used for easy comparison and database lookup.
     * @param date The input date
     * @return a DB-friendly representation of the date, using the format defined in DATE_FORMAT.
     */
    public static String getDbDateString(Date date){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * Converts a dateText to a long Unix time representation
     * @param dateText the input date string
     * @return the Date object
     */
    public static Date getDateFromDb(String dateText) {
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dbDateFormat.parse(dateText);
        } catch ( ParseException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public static class UserEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME="user";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_TOKEN="token";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_EMAIL = "email";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUserAndTokenUri(String token) {
            return CONTENT_URI.buildUpon().appendPath(token).appendPath("token").build();
        }

        public static String getTokenFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildUserAndEmailUri(String mail) {
            return CONTENT_URI.buildUpon().appendPath(mail).appendPath("mail").build();
        }

        public static String getMailFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }


    }

    public static class DenunciaInfoEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DENUNCIA_INFO).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_INFO;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_INFO;

        public static final String TABLE_NAME="denuncia";
        public static final String COLUMN_TITULO="titulo";
        public static final String COLUMN_ID_INTERNO="id_interno";
        public static final String COLUMN_ID_SPF="id_spf";
        public static final String COLUMN_TOKEN="token";
        public static final String COLUMN_ANONIMA = "anonima";
        public static final String COLUMN_ID_DEPENDENCIA = "dependencia";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_COORD_LAT = "lat";
        public static final String COLUMN_COORD_LONG = "long";
        public static final String COLUMN_FECHA_CREACION = "fecha_creacion";
        public static final String COLUMN_FECHA_ACTUALIZACION = "fecha_actualizacion";
        public static final String COLUMN_ESTATUS = "estatus";

        public static Uri buildDenunciaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDenunciaAndIdInternoUri(String idInterno) {
            return CONTENT_URI.buildUpon().appendPath(idInterno).appendPath("idInterno").build();
        }

        public static String getIdInternoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildDenunciaAndEstadoUri(String idEstado) {
            return CONTENT_URI.buildUpon().appendPath(idEstado).appendPath("estado").build();
        }

        public static String getIdEstadoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildDenunciaAndFechaActualizacionUri(String fechaActualizacion) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_FECHA_ACTUALIZACION,fechaActualizacion).build();
        }

        public static String getFechaActualizacionFromUri(Uri uri)
        {
            return uri.getQueryParameter(COLUMN_FECHA_ACTUALIZACION);
        }

        public static Uri buildDenunciaAndFechaCreacionUri(String fechaCreacion) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_FECHA_CREACION,fechaCreacion).build();
        }

        public static String getFechaCreacionFromUri(Uri uri)
        {
            return uri.getQueryParameter(COLUMN_FECHA_CREACION);
        }


        public static Uri buildDenunciaAndIdSFPUri(String idSFP) {
            return CONTENT_URI.buildUpon().appendPath(idSFP).appendPath("sfp").build();
        }

        public static String getIdSPFFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }



    }

    public static class DenunciaEvidenciaEntry implements BaseColumns
    {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DENUNCIA_EVIDENCIA).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_EVIDENCIA;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_EVIDENCIA;

        public static final String TABLE_NAME="evidencia";
        public static final String COLUMN_TYPE="type";
        public static final String COLUMN_ID_INTERNO="id_interno";
        public static final String COLUMN_ID_ESTATUS_S3="s3_status";
        public static final String COLUMN_FULL_PATH = "path";
        public static final String COLUMN_URI = "uri";
        public static final String COLUMN_PATH_S3 = "s3_path";

        public static Uri buildEvidenciaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDenunciaWithIdInternoUri(String idInterno)
        {
            return CONTENT_URI.buildUpon().appendPath(idInterno).appendPath("idInterno").build();
        }

        public static String getIdInternoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }


        public static Uri buildDenunciaWithEstadoS3Uri(String idEstado)
        {
            return CONTENT_URI.buildUpon().appendPath(idEstado).appendPath("idEstado").build();
        }

        public static String getIdEstadoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

    }

    public static class DenunciaHechosEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DENUNCIA_HECHOS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_HECHOS;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_HECHOS;

        public static final String TABLE_NAME="hechos";
        public static final String COLUMN_ID_INTERNO="id_interno";
        public static final String COLUMN_ID_PREGUNTA="id_pregunta";
        public static final String COLUMN_RESPUESTA= "respuesta";

        public static Uri buildHechosUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildHechoWithIdInternoUri(String idInterno)
        {
            return CONTENT_URI.buildUpon().appendPath(idInterno).appendPath("idInterno").build();
        }

        public static String getIdInternoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }
    }

    public static class DenunciaHistoriaEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DENUNCIA_HISTORIA).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_HISTORIA;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_HISTORIA;

        public static final String TABLE_NAME="historia";
        public static final String COLUMN_ID_INTERNO="id_interno";
        public static final String COLUMN_FECHA="fecha";
        public static final String COLUMN_ID_ESTATUS="estatus";
        public static final String COLUMN_MENSAJE= "mensaje";

        public static Uri buildHistoriaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDenunciaWithIdInternoUri(String idInterno)
        {
            return CONTENT_URI.buildUpon().appendPath(idInterno).build();
        }

        public static String getIdInternoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

    }

    public static class DependenciaEntry implements BaseColumns
    {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEPENDENCIA).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DEPENDENCIA;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DEPENDENCIA;

        public static final String TABLE_NAME="dependencia";
        public static final String COLUMN_ID_DEPENDENCIA="id_dependencia";
        public static final String COLUMN_DEPENDENCIA= "depedencia";

        public static Uri buildDependenciaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDependenciaWithIdDependenciaUri(String idDependencia)
        {
            return CONTENT_URI.buildUpon().appendPath(idDependencia).build();
        }

        public static String getIdDependenciaFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

        public static ArrayMap<Integer,String> getDependencias()
        {
            ArrayMap<Integer, String> mMapDepenencias = new ArrayMap<Integer, String>();
            int i=1;
            mMapDepenencias.put(i++,"ADMINISTRACIÓN FEDERAL DE SERVICIOS EDUCATIVOS EN EL DISTRITO FEDERAL");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE ALTAMIRA, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE COATZACOALCOS, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE DOS BOCAS, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE ENSENADA, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE GUAYMAS, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE LÁZARO CÁRDENAS, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE MANZANILLO, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE MAZATLÁN, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE PROGRESO, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE PUERTO MADERO, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE PUERTO VALLARTA, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE SALINA CRUZ, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE TAMPICO, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE TOPOLOBAMPO, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE TUXPAN, S.A. DE C.V.");
            mMapDepenencias.put(i++,"ADMINISTRACIÓN PORTUARIA INTEGRAL DE VERACRUZ, S.A. DE C.V.");
            mMapDepenencias.put(i++,"AEROPUERTOS Y SERVICIOS AUXILIARES");
            mMapDepenencias.put(i++,"AGENCIA ESPACIAL MEXICANA");
            mMapDepenencias.put(i++,"AGROASEMEX, S.A.");
            mMapDepenencias.put(i++,"APOYOS Y SERVICIOS A LA COMERCIALIZACIÓN AGROPECUARIA");
            mMapDepenencias.put(i++,"BANCO DEL AHORRO NACIONAL Y SERVICIOS FINANCIEROS, S.N.C.");
            mMapDepenencias.put(i++,"BANCO NACIONAL DE COMERCIO EXTERIOR, S.N.C.");
            mMapDepenencias.put(i++,"BANCO NACIONAL DE OBRAS Y SERVICIOS PÚBLICOS, S.N.C.");
            mMapDepenencias.put(i++,"BANCO NACIONAL DEL EJÉRCITO, FUERZA AÉREA Y ARMADA, S.N.C.");
            mMapDepenencias.put(i++,"CAMINOS Y PUENTES FEDERALES DE INGRESOS Y SERVICIOS CONEXOS");
            mMapDepenencias.put(i++,"CASA DE MONEDA DE MÉXICO");
            mMapDepenencias.put(i++,"CENTRO DE ENSEÑANZA TÉCNICA INDUSTRIAL");
            mMapDepenencias.put(i++,"CENTRO DE INGENIERÍA Y DESARROLLO INDUSTRIAL");
            mMapDepenencias.put(i++,"CENTRO DE INNOVACIÓN APLICADA EN TENOLOGÍAS COMPETITIVAS, A.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN CIENTÍFICA DE YUCATÁN, A.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN CIENTÍFICA Y DE EDUCACIÓN SUPERIOR DE ENSENADA, B.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN EN ALIMENTACION Y DESARROLLO, A.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN EN GEOGRAFÍA Y GEOMÁTICA ING. JORGE L. TAMAYO, A.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN EN MATEMÁTICAS, A.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN EN MATERIALES AVANZADOS, S.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN EN QUÍMICA APLICADA");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN Y ASISTENCIA EN TECNOLOGÍA Y DISEÑO DEL ESTADO DE JALISCO, A.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN Y DE ESTUDIOS AVANZADOS DEL IPN");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN Y DESARROLLO TECNOLÓGICO EN ELECTROQUÍMICA, S.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN Y DOCENCIA ECONÓMICAS, A.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIÓN Y SEGURIDAD NACIONAL");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIONES BIOLÓGICAS DEL NOROESTE, S.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIONES EN ÓPTICA, A.C.");
            mMapDepenencias.put(i++,"CENTRO DE INVESTIGACIONES Y ESTUDIOS SUPERIORES EN ANTROPOLOGÍA SOCIAL");
            mMapDepenencias.put(i++,"CENTRO DE TECNOLOGÍA AVANZADA A.C.");
            mMapDepenencias.put(i++,"CENTRO NACIONAL DE METROLOGÍA");
            mMapDepenencias.put(i++,"CENTRO REGIONAL DE ALTA ESPECIALIDAD DE CHIAPAS");
            mMapDepenencias.put(i++,"CENTROS DE INTEGRACIÓN JUVENIL, A.C.");
            mMapDepenencias.put(i++,"COLEGIO DE BACHILLERES");
            mMapDepenencias.put(i++,"COLEGIO DE POSTGRADUADOS");
            mMapDepenencias.put(i++,"COLEGIO NACIONAL DE EDUCACIÓN PROFESIONAL TÉCNICA");
            mMapDepenencias.put(i++,"COMISIÓN DE OPERACIÓN Y FOMENTO DE ACTIVIDADES ACADÉMICAS DEL IPN");
            mMapDepenencias.put(i++,"COMISIÓN FEDERAL DE ELECTRICIDAD ");
            mMapDepenencias.put(i++,"COMISIÓN FEDERAL PARA LA PROTECCIÓN CONTRA RIESGOS SANITARIOS");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL BANCARIA Y DE VALORES");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DE ACUACULTURA Y PESCA");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DE ARBITRAJE MÉDICO");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DE CULTURA FÍSICA Y DEPORTE");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DE LAS ZONAS ÁRIDAS");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DE LIBROS DE TEXTO GRATUITOS");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DE LOS SALARIOS MÍNIMOS");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DE PROTECCIÓN SOCIAL EN SALUD");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DE SEGURIDAD NUCLEAR Y SALVAGUARDIAS");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DE SEGUROS Y FIANZAS");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DE VIVIENDA");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DEL AGUA");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL DEL SISTEMA DEL AHORRO PARA EL RETIRO");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL FORESTAL");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL PARA EL DESARROLLO DE LOS PUEBLOS INDÍGENAS");
            mMapDepenencias.put(i++,"COMISIÓN NACIONAL PARA LA PROTECCIÓN Y DEFENSA DE LOS USUARIOS DE SERVICIOS FINANCIEROS");
            mMapDepenencias.put(i++,"COMISIÓN PARA LA REGULARIZACIÓN DE LA TENENCIA DE LA TIERRA");
            mMapDepenencias.put(i++,"COMPAÑÍA MEXICANA DE EXPLORACIONES, S.A. DE C.V.");
            mMapDepenencias.put(i++,"COMPAÑÍA OPERADORA DEL CENTRO CULTURAL Y TURÍSTICO DE TIJUANA S.A. DE C.V.");
            mMapDepenencias.put(i++,"CONSEJERÍA JURÍDICA DEL EJECUTIVO FEDERAL");
            mMapDepenencias.put(i++,"CONSEJO DE  PROMOCIÓN TURÍSTICA DE MÉXICO S.A. DE C.V.");
            mMapDepenencias.put(i++,"CONSEJO NACIONAL DE CIENCIA Y TECNOLOGÍA");
            mMapDepenencias.put(i++,"CONSEJO NACIONAL DE EVALUACIÓN DE LA POLÍTICA DE DESARROLLO SOCIAL");
            mMapDepenencias.put(i++,"CONSEJO NACIONAL DE FOMENTO EDUCATIVO");
            mMapDepenencias.put(i++,"CONSEJO NACIONAL PARA LA CULTURA Y LAS ARTES");
            mMapDepenencias.put(i++,"CONSEJO NACIONAL PARA PREVENIR LA DISCRIMINACIÓN");
            mMapDepenencias.put(i++,"COORDINACIÓN NACIONAL DE PROSPERA PROGRAMA DE INCLUSIÓN SOCIAL");
            mMapDepenencias.put(i++,"CORPORACIÓN MEXICANA DE INVESTIGACIÓN EN MATERIALES, S.A. DE C.V.");
            mMapDepenencias.put(i++,"DICONSA, S.A. DE C.V.");
            mMapDepenencias.put(i++,"EDUCAL, S.A. DE C.V.");
            mMapDepenencias.put(i++,"EL COLEGIO DE LA FRONTERA NORTE, A.C.");
            mMapDepenencias.put(i++,"EL COLEGIO DE LA FRONTERA SUR");
            mMapDepenencias.put(i++,"EL COLEGIO DE MICHOACÁN, A.C.");
            mMapDepenencias.put(i++,"EL COLEGIO DE SAN LUIS, A.C.");
            mMapDepenencias.put(i++,"ESTUDIOS CHURUBUSCO AZTECA, S.A.");
            mMapDepenencias.put(i++,"EXPORTADORA DE SAL, S.A. DE C.V.");
            mMapDepenencias.put(i++,"FERROCARRIL DEL ISTMO DE TEHUANTEPEC, S.A. DE C.V.");
            mMapDepenencias.put(i++,"FIDEICOMISO DE FOMENTO MINERO");
            mMapDepenencias.put(i++,"FIDEICOMISO DE FORMACIÓN Y CAPACITACIÓN PARA PERSONAL DE LA MARINA MERCANTE NACIONAL");
            mMapDepenencias.put(i++,"FIDEICOMISO DE LOS SISTEMAS NORMALIZADO DE COMPETENCIA LABORAL Y DE CERTIFICACIÓN DE COMPETENCIA LABORAL");
            mMapDepenencias.put(i++,"FIDEICOMISO DE RIESGO COMPARTIDO");
            mMapDepenencias.put(i++,"FIDEICOMISO FONDO NACIONAL DE FOMENTO EJIDAL");
            mMapDepenencias.put(i++,"FIDEICOMISO FONDO NACIONAL DE HABITACIONES POPULARES");
            mMapDepenencias.put(i++,"FIDEICOMISO PÚBLICO PROMÉXICO");
            mMapDepenencias.put(i++,"FINANCIERA RURAL");
            mMapDepenencias.put(i++,"FONDO DE CAPITALIZACIÓN E INVERSIÓN DEL SECTOR RURAL");
            mMapDepenencias.put(i++,"FONDO DE CULTURA ECONÓMICA");
            mMapDepenencias.put(i++,"FONDO DE GARANTÍA Y FOMENTO PARA LA AGRICULTURA, GANADERÍA Y AVICULTURA");
            mMapDepenencias.put(i++,"FONDO DE INFORMACIÓN Y DOCUMENTACIÓN PARA LA INDUSTRIA");
            mMapDepenencias.put(i++,"FONDO DE LA VIVIENDA DEL ISSSTE");
            mMapDepenencias.put(i++,"FONDO NACIONAL DE FOMENTO AL TURISMO");
            mMapDepenencias.put(i++,"FONDO NACIONAL PARA EL FOMENTO DE LAS ARTESANÍAS");
            mMapDepenencias.put(i++,"HOSPITAL GENERAL DE MÉXICO");
            mMapDepenencias.put(i++,"HOSPITAL GENERAL DR. MANUEL GEA GONZÁLEZ");
            mMapDepenencias.put(i++,"HOSPITAL INFANTIL DE MÉXICO FEDERICO GÓMEZ");
            mMapDepenencias.put(i++,"HOSPITAL JUÁREZ DE MÉXICO");
            mMapDepenencias.put(i++,"HOSPITAL REGIONAL DE ALTA ESPECIALIDAD DE IXTAPALUCA");
            mMapDepenencias.put(i++,"HOSPITAL REGIONAL DE ALTA ESPECIALIDAD DE CIUDAD VICTORIA");
            mMapDepenencias.put(i++,"HOSPITAL REGIONAL DE ALTA ESPECIALIDAD DE LA PENÍNSULA DE YUCATÁN");
            mMapDepenencias.put(i++,"HOSPITAL REGIONAL DE ALTA ESPECIALIDAD DE OAXACA");
            mMapDepenencias.put(i++,"HOSPITAL REGIONAL DE ALTA ESPECIALIDAD DEL BAJÍO");
            mMapDepenencias.put(i++,"IMPRESORA Y ENCUADERNADORA PROGRESO S.A. DE C.V.");
            mMapDepenencias.put(i++,"INSTALACIONES INMOBILIARIAS PARA INDUSTRIAS S.A DE C.V.  Y SU FILIAL I.I.I. SERVICIOS, S.A DE C.V.");
            mMapDepenencias.put(i++,"INSTITUTO DE ECOLOGÍA, A.C.");
            mMapDepenencias.put(i++,"INSTITUTO DE INVESTIGACIONES DR. JOSÉ MA. LUIS MORA");
            mMapDepenencias.put(i++,"INSTITUTO DE INVESTIGACIONES ELÉCTRICAS");
            mMapDepenencias.put(i++,"INSTITUTO DE SEGURIDAD SOCIAL PARA LAS FUERZAS ARMADAS MEXICANAS");
            mMapDepenencias.put(i++,"INSTITUTO DE SEGURIDAD Y SERVICIOS SOCIALES DE LOS TRABAJADORES DEL ESTADO");
            mMapDepenencias.put(i++,"INSTITUTO DEL FONDO NACIONAL PARA EL CONSUMO DE LOS TRABAJADORES");
            mMapDepenencias.put(i++,"INSTITUTO MEXICANO DE CINEMATOGRAFÍA");
            mMapDepenencias.put(i++,"INSTITUTO MEXICANO DE LA JUVENTUD");
            mMapDepenencias.put(i++,"INSTITUTO MEXICANO DE LA PROPIEDAD INDUSTRIAL");
            mMapDepenencias.put(i++,"INSTITUTO MEXICANO DE LA RADIO");
            mMapDepenencias.put(i++,"INSTITUTO MEXICANO DE TECNOLOGÍA DEL AGUA");
            mMapDepenencias.put(i++,"INSTITUTO MEXICANO DEL PETRÓLEO");
            mMapDepenencias.put(i++,"INSTITUTO MEXICANO DEL SEGURO SOCIAL");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE ANTROPOLOGÍA E HISTORIA");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE ASTROFÍSICA, ÓPTICA Y ELECTRÓNICA");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE BELLAS ARTES Y LITERATURA");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE CANCEROLOGÍA");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE CARDIOLOGÍA IGNACIO CHÁVEZ");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE CIENCIAS MÉDICAS Y NUTRICIÓN SALVADOR ZUBIRÁN");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE CIENCIAS PENALES");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE ENFERMEDADES RESPIRATORIAS ISMAEL COSÍO VILLEGAS");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE INVESTIGACIONES FORESTALES, AGRÍCOLAS Y PECUARIAS");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE INVESTIGACIONES NUCLEARES");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE LA ECONOMÍA SOCIAL ");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE LA INFRAESTRUCTURA FÍSICA Y EDUCATIVA");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE LAS MUJERES");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE LAS PERSONAS ADULTAS MAYORES");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE LENGUAS INDÍGENAS");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE MEDICINA GENÓMICA");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE MIGRACIÓN");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE NEUROLOGÍA Y NEUROCIRUGÍA MANUEL VELASCO SUÁREZ");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE PEDIATRÍA");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE PERINATOLOGÍA ISIDRO ESPINOSA DE LOS REYES");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE PSIQUIATRÍA RAMÓN DE LA FUENTE MUÑIZ");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE REHABILITACIÓN");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL DE SALUD PÚBLICA");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL PARA EL DESARROLLO DE CAPACIDADES DEL SECTOR RURAL, A.C.");
            mMapDepenencias.put(i++,"INSTITUTO NACIONAL PARA LA EDUCACIÓN DE LOS ADULTOS");
            mMapDepenencias.put(i++,"INSTITUTO PARA LA PROTECCIÓN AL AHORRO BANCARIO");
            mMapDepenencias.put(i++,"INSTITUTO POLITÉCNICO NACIONAL");
            mMapDepenencias.put(i++,"INSTITUTO POTOSINO DE INVESTIGACIÓN CIENTÍFICA Y TECNOLÓGICA, A.C.");
            mMapDepenencias.put(i++,"LABORATORIOS DE BIOLÓGICOS Y REACTIVOS DE MÉXICO, S. A. DE C. V.");
            mMapDepenencias.put(i++,"LICONSA, S.A. DE C.V.");
            mMapDepenencias.put(i++,"LOTERÍA NACIONAL PARA LA ASISTENCIA PÚBLICA");
            mMapDepenencias.put(i++,"NACIONAL FINANCIERA, S.N.C.");
            mMapDepenencias.put(i++,"NOTIMEX, AGENCIA DE NOTICIAS DEL ESTADO MEXICANO");
            mMapDepenencias.put(i++,"P.M.I. COMERCIO INTERNACIONAL, S.A. DE C.V.");
            mMapDepenencias.put(i++,"PATRONATO DE OBRAS E INSTALACIONES DEL IPN");
            mMapDepenencias.put(i++,"PEMEX GAS Y PETROQUÍMICA BÁSICA");
            mMapDepenencias.put(i++,"PEMEX-EXPLORACIÓN Y PRODUCCIÓN");
            mMapDepenencias.put(i++,"PEMEX-PETROQUÍMICA");
            mMapDepenencias.put(i++,"PEMEX-REFINACIÓN");
            mMapDepenencias.put(i++,"PETRÓLEOS MEXICANOS");
            mMapDepenencias.put(i++,"POLICÍA FEDERAL");
            mMapDepenencias.put(i++,"PRESIDENCIA DE LA REPÚBLICA");
            mMapDepenencias.put(i++,"PREVENCIÓN Y READAPTACIÓN SOCIAL");
            mMapDepenencias.put(i++,"PROCURADURÍA AGRARIA");
            mMapDepenencias.put(i++,"PROCURADURÍA FEDERAL DEL CONSUMIDOR");
            mMapDepenencias.put(i++,"PROCURADURÍA GENERAL DE LA REPÚBLICA");
            mMapDepenencias.put(i++,"PRODUCTORA NACIONAL DE BIOLÓGICOS VETERINARIOS");
            mMapDepenencias.put(i++,"PRONÓSTICOS PARA LA ASISTENCIA PÚBLICA");
            mMapDepenencias.put(i++,"REGISTRO AGRARIO NACIONAL");
            mMapDepenencias.put(i++,"SECRETARÍA DE AGRICULTURA, GANADERÍA, DESARROLLO RURAL, PESCA Y ALIMENTACIÓN");
            mMapDepenencias.put(i++,"SECRETARÍA DE COMUNICACIONES Y TRANSPORTES");
            mMapDepenencias.put(i++,"SECRETARÍA DE DESARROLLO AGRARIO, TERRITORIAL Y URBANO");
            mMapDepenencias.put(i++,"SECRETARÍA DE DESARROLLO SOCIAL");
            mMapDepenencias.put(i++,"SECRETARÍA DE ECONOMÍA");
            mMapDepenencias.put(i++,"SECRETARÍA DE EDUCACIÓN PÚBLICA");
            mMapDepenencias.put(i++,"SECRETARÍA DE ENERGÍA");
            mMapDepenencias.put(i++,"SECRETARÍA DE GOBERNACIÓN");
            mMapDepenencias.put(i++,"SECRETARÍA DE HACIENDA Y CRÉDITO PÚBLICO");
            mMapDepenencias.put(i++,"SECRETARÍA DE LA DEFENSA NACIONAL");
            mMapDepenencias.put(i++,"SECRETARÍA DE MARINA");
            mMapDepenencias.put(i++,"SECRETARÍA DE MEDIO AMBIENTE Y RECURSOS NATURALES");
            mMapDepenencias.put(i++,"SECRETARÍA DE RELACIONES EXTERIORES");
            mMapDepenencias.put(i++,"SECRETARÍA DE SALUD");
            mMapDepenencias.put(i++,"SECRETARÍA DE TURISMO");
            mMapDepenencias.put(i++,"SECRETARÍA DEL TRABAJO Y PREVISIÓN SOCIAL");
            mMapDepenencias.put(i++,"SECRETARIADO EJECUTIVO DEL SISTEMA NACIONAL DE SEGURIDAD PÚBLICA");
            mMapDepenencias.put(i++,"SERVICIO DE ADMINISTRACIÓN TRIBUTARIA");
            mMapDepenencias.put(i++,"SERVICIO DE ADMINISTRACIÓN Y ENAJENACIÓN DE BIENES");
            mMapDepenencias.put(i++,"SERVICIO DE PROTECCIÓN FEDERAL");
            mMapDepenencias.put(i++,"SERVICIO GEOLÓGICO MEXICANO");
            mMapDepenencias.put(i++,"SERVICIO NACIONAL DE SANIDAD, INOCUIDAD Y CALIDAD AGROALIMENTARIA");
            mMapDepenencias.put(i++,"SERVICIO POSTAL MEXICANO");
            mMapDepenencias.put(i++,"SERVICIOS A LA NAVEGACIÓN EN EL ESPACIO AÉREO MEXICANO");
            mMapDepenencias.put(i++,"SERVICIOS AEROPORTUARIOS DE LA CIUDAD DE MÉXICO, S.A. DE C.V.");
            mMapDepenencias.put(i++,"SISTEMA NACIONAL PARA EL DESARROLLO INTEGRAL DE LA FAMILIA");
            mMapDepenencias.put(i++,"SOCIEDAD HIPOTECARIA FEDERAL, S.N.C.");
            mMapDepenencias.put(i++,"SUPERISSSTE");
            mMapDepenencias.put(i++,"TALLERES GRÁFICOS DE MÉXICO");
            mMapDepenencias.put(i++,"TELECOMUNICACIONES DE MÉXICO");
            mMapDepenencias.put(i++,"TELEVISIÓN METROPOLITANA, S.A. DE C.V.");
            mMapDepenencias.put(i++,"UNIVERSIDAD PEDAGÓGICA NACIONAL");

            return mMapDepenencias;
        }

    }

}
