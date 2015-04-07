package jar005.simplehunts.model;

public class BooleanAttribute {
    private String name;
    private boolean value;

    public String getName() {
        return name;
    }

    public boolean isTrue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public BooleanAttribute(String name, boolean value) {
        this.name = name;
        this.value = value;
    }
}
