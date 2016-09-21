package lv.ctco.enums;

public enum Browser {

    FIREFOX("firefox"),
    IE("ie"),
    CHROME("chrome");

    private String name;

    Browser(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

}
