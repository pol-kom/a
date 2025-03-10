package kp.util;

import jakarta.xml.ws.Service;
import kp.Constants;
import kp.w_s.WebSe;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The tools.
 */
public class Tools {

    private static final String WSDL_LOC_PREFIX = "http://localhost:8080/Study14_ejb";
    private static final List<String> IMPL_NAMES = List.of("WebSeImplA", "WebSeImplB", "WebSeImplC");

    /**
     * Private constructor to prevent instantiation.
     */
    private Tools() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets the text list.
     *
     * @return the textList
     */
    public static List<String> getTextList() {
        return IntStream.concat(
                        IntStream.rangeClosed("A".codePointAt(0), "Z".codePointAt(0)),
                        IntStream.rangeClosed("a".codePointAt(0), "z".codePointAt(0)))
                .mapToObj(num -> Character.toString((char) num)).toList();
    }

    /**
     * Creates the web services implementations.
     *
     * @return the {@link WebSe} instances list
     * @throws KpException the {@link KpException}
     */
    public static List<WebSe> createWebSeImplementations() throws KpException {

        final List<WebSe> webSeList = new ArrayList<>();
        for (String name : IMPL_NAMES) {
            webSeList.add(createWebSeImpl(name));
        }
        return webSeList;
    }

    /**
     * Creates the web service implementation.
     *
     * @param implName the implementation name
     * @return the {@link WebSe} instance
     * @throws KpException the {@link KpException}
     */
    private static WebSe createWebSeImpl(String implName) throws KpException {

        final String wsdlDocumentLocation = "%s/%s?wsdl".formatted(WSDL_LOC_PREFIX, implName);
        final String localPart = "%sService".formatted(implName);
        final Service service = createService(wsdlDocumentLocation, localPart);
        return service.getPort(WebSe.class);
    }

    /**
     * Creates the service.
     *
     * @param wsdlDocumentLocation the WSDL document location
     * @param localPart            the local part
     * @return the {@link Service}
     * @throws KpException the {@link KpException}
     */
    private static Service createService(String wsdlDocumentLocation, String localPart) throws KpException {

        final URL wsdlDocumentLocationURL;
        try {
            wsdlDocumentLocationURL = new URI(wsdlDocumentLocation).toURL();
        } catch (URISyntaxException | MalformedURLException ex) {
            throw new KpException("researchWebService(): exception[%s]".formatted(ex.getMessage()));
        }
        final QName serviceName = new QName(Constants.TARGET_NAMESPACE, localPart);
        return Service.create(wsdlDocumentLocationURL, serviceName);
    }

}
