enum Colors {
    RED("\u001b[31m"),
    BLUE("\u001b[34m"),
    DEFAULT("\u001b[00m");

    private String colorCode;


    Colors(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getCode() {
        return colorCode;
    }
}
