package kp.w_s;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.io.Serializable;

/**
 * The web service <B>WebSe</B>.
 */
@WebService
public interface WebSe extends Serializable {
    /**
     * This is a business method.
     *
     * @param arg the argument
     * @return result the result
     */
    @WebMethod
    String tryIt(String arg);
}