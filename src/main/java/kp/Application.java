
    private static final List<String> MENU_LIST = List.of("Validation", "CDI", "RESTful Web Services", "Exit");

    /**
     * The primary entry point for launching the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        Locale.setDefault(Locale.ENGLISH);
        initLogging();

        final ResearchValidation researchValidation = new ResearchValidation();
        final ResearchCDIStarter researchCDIStarter = new ResearchCDIStarter();
        final ResearchRestful researchRestful = new ResearchRestful();

        boolean loopFlag = true;
        while (loopFlag) {
            int chosenOption = JOptionPane.showOptionDialog(null, null, "Study12",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    MENU_LIST.toArray(), MENU_LIST.getLast());
            switch (chosenOption) {
                case 0 -> researchValidation.process();
                case 1 -> researchCDIStarter.startIt();
                case 2 -> researchRestful.process();
                default -> loopFlag = false; // exit application
            }
        }
        System.exit(0);
    }

    /**
     * Initializes logging.
     */
    private static void initLogging() {

        Logger parentLogger = logger.getParent();
        Handler[] handlers = parentLogger.getHandlers();
        while (handlers.length == 0) {
            final Optional<Logger> loggerOpt = Optional.ofNullable(parentLogger.getParent());
            if (loggerOpt.isEmpty()) {
                logger.severe("Logging problem: handlers not found!");
                return;
            }
            parentLogger = loggerOpt.get();
            handlers = parentLogger.getHandlers();
        }
        Arrays.stream(handlers).forEach(handler -> handler.setFormatter(new LoggingFormatter()));
    }
}
