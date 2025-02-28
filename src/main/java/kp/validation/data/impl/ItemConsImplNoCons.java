package kp.validation.data.impl;

import kp.validation.data.ItemCons;

/**
 * The implementation without any constraints.
 * <p>
 * The property-level constraint is inherited from interface.
 * </p>
 */
public class ItemConsImplNoCons implements ItemCons {

    private String val;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVal(String val) {
        this.val = val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVal() {
        return val;
    }
}