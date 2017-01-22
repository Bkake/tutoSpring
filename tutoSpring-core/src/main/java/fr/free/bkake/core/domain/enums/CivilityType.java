package fr.free.bkake.core.domain.enums;

public enum CivilityType {

    MME("MME","MADAME"), MLLE("MLLE", "MADEMOISELLE"), MR("MR", "MONSIEUR") ;

    private final String code;
    private final String description;

    CivilityType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getdescription() {
        return description;
    }
}
