package kp.e_j_b.remote;

import jakarta.ejb.Remote;
import kp.e_j_b.Common;

/**
 * The <b>stateful</b> session bean. Extend the {@link Common} interface.
 * <p>
 * This is the {@link Remote} business interface.
 * </p>
 */
@Remote
public interface StaFu extends Common {
}