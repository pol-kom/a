package kp.trans_b_m_t.controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import kp.trans_b_m_t.service.CapsuleAdministrator;

import java.io.Serial;
import java.io.Serializable;

/**
 * CDI managed bean for the <b>bean-managed</b> transactions research.
 */
@Named
@SessionScoped
public class TransBmtManagedBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * The capsule administrator.
     */
    private final CapsuleAdministrator capsuleAdministrator;

    /**
     * Parameterized constructor.
     *
     * @param capsuleAdministrator the capsule administrator
     */
    @Inject
    public TransBmtManagedBean(CapsuleAdministrator capsuleAdministrator) {
        this.capsuleAdministrator = capsuleAdministrator;
    }

    /**
     * Creates the capsule.
     *
     * @return the result
     */
    public String create() {
        capsuleAdministrator.create();
        return "";
    }

    /**
     * Reads the capsule.
     *
     * @return the result
     */
    public String read() {
        capsuleAdministrator.read();
        return "";
    }

    /**
     * Deletes the capsule.
     *
     * @return the result
     */
    public String delete() {
        capsuleAdministrator.delete();
        return "";
    }

    /**
     * Commits the transaction.
     *
     * @return the result
     */
    public String commit() {
        capsuleAdministrator.commit();
        return "";
    }

    /**
     * Rollbacks the transaction.
     *
     * @return the result
     */
    public String rollback() {
        capsuleAdministrator.rollback();
        return "";
    }
}