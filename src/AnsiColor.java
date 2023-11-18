public enum AnsiColor {
    RESET("\033[0m"),
    RED("\033[0;31m"),
    RED_BACKGROUND("\033[0;101m"),
    CYAN("\033[0;36m"),
    CYAN_BOLD("\033[1;36m"),
    BLUE("\033[0;34m"),
    YELLOW("\033[0;33m"),
    YELLOW_BACKGROUND("\033[0;103m"),
    GREEN_BACKGROUND("\033[42m"),
    WHITE_BOLD("\033[1;37m");

    private String ansiCode;

    AnsiColor(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    @Override
    public String toString() {
        return ansiCode;
    }   
}
