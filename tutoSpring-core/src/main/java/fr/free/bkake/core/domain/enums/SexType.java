package fr.free.bkake.core.domain.enums;


public enum SexType {
    M("M","MASCULIN"), F("F", "FEMININ"), I("I", "INDETERMINE") ;

    private final String code;
    private final String description;

    SexType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
