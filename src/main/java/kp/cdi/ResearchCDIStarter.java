package kp.cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.inject.WeldInstance;

/**
 * The starter for CDI research.
 * <p>
 * Weld 6 / CDI 4.
 * </p>
 * <p>
 * Weld is executed in Java SE environment: EJB beans are not supported.
 * </p>
 */
public class ResearchCDIStarter {

    /**
     * Boots the <B>Weld SE</B> container.
     */
    public void startIt() {

        try (WeldContainer container = new Weld().initialize()) {
            final WeldInstance<ResearchCDIBean> weldInstance = container.select(ResearchCDIBean.class);
            final ResearchCDIBean researchCDIBean = weldInstance.get();
            /*
             * run in container
             */
            researchCDIBean.process();
        }
    }
}