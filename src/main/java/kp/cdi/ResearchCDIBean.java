
    /**
     * Researches the alternative and the qualified beans.
     */
    private void alternativeAndQualifiedBeans() {

        report.clear();
        show();
        report.add(EMPTY_ROW);
        note();
        researchCDIHelper.showResults(ResearchCDIHelper.MENU_LIST.getFirst());
        if (logger.isLoggable(Level.INFO)) {
            logger.info("alternativeAndQualifiedBeans():");
        }
    }

    /**
     * Shows basic beans.
     */
    private void show() {

        /*- switch off alternative by commenting out '<alternatives>' element in the 'beans.xml' file */
        basicBean.show("ABC");
        basicBeanWithQualifier.show("KLM");
        /*- getting bean from instance with qualifier (equivalent to injected 'basicBeanWithQualifier') */
        final BasicBean basicBeanSelected = basicBeanInstance.select(new ScriptQualifier()).get();
        basicBeanSelected.show("XYZ");
    }

    /**
     * Takes notes about foreseeable items.
     */
    private void note() {

        report.add(List.of(CLASS_NAME, "note", "foreseeable[%s](String)".formatted(foreseeableF)));
        report.add(List.of(CLASS_NAME, "note", "foreseeable[%s](String)".formatted(foreseeableN)));
        report.add(List.of(CLASS_NAME, "note", "foreseeable[%s](Instance<String>)".formatted(foreseeableInstance.get())));
    }

    /**
     * Researches the decorated beans.
     */
    private void decoratedBeans() {

        /*- switch off by commenting out <decorators> element in the beans.xml file */
        report.clear();
        plainBean.show("ABC");
        researchCDIHelper.showResults(ResearchCDIHelper.MENU_LIST.get(1));
        if (logger.isLoggable(Level.INFO)) {
            logger.info("decoratedBeans():");
        }
    }

    /**
     * Researches the intercepted beans.
     */
    private void interceptedBeans() {

        report.clear();
        IntStream.rangeClosed(1, 10).sequential().forEach(_ -> {
            elapsedBean.pausedMilli();
            elapsedBean.pausedMicro();
            elapsedBean.pausedNano();
            elapsedBean.notPaused();
            report.add(EMPTY_ROW);
        });
        researchCDIHelper.showResults(ResearchCDIHelper.MENU_LIST.get(2));
        if (logger.isLoggable(Level.INFO)) {
            logger.info("interceptedBeans():");
        }
    }

    /**
     * Researches firing the events.
     */
    private void fireEvents() {

        report.clear();

        final Payload payload = new Payload("ABC");
        payloadEvent.fire(payload);
        report.add(EMPTY_ROW);

        final List<Payload> payloadList = new ArrayList<>();
        payloadList.add(new Payload("KLM"));
        payloadListEvent.fire(payloadList);
        report.add(EMPTY_ROW);

        final List<String> textList = new ArrayList<>();
        textList.add("XYZ");
        textListEvent.fire(textList);

        researchCDIHelper.showResults(ResearchCDIHelper.MENU_LIST.get(3));
        if (logger.isLoggable(Level.INFO)) {
            logger.info("fireEvents():");
        }
    }
}