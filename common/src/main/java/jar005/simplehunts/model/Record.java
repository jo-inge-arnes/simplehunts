package jar005.simplehunts.model;

import java.util.*;

public class Record {
    private Map<String, BooleanAttribute> independentAttributeMap;
    private BooleanAttribute target;

    public Record(Collection<BooleanAttribute> independentAttributes, BooleanAttribute target) {
        setIndependentAttributes(independentAttributes);
        setTarget(target);
    }

    /**
     * Returns a collection of independent attributes. Note that this collection should not be added or removed from.
     * @return Unmodifiable collection of independent attributes
     */
    public Collection<BooleanAttribute> getIndependentAttributes() {
        return Collections.unmodifiableCollection(independentAttributeMap.values());
    }

    public void setIndependentAttributes(Collection<BooleanAttribute> independentAttributes) {
        independentAttributeMap = new HashMap<>();
        for(BooleanAttribute attribute : independentAttributes)
            independentAttributeMap.put(attribute.getName(), attribute);
    }

    public BooleanAttribute getAttribute(String name) {
        return independentAttributeMap.get(name);
    }

    public BooleanAttribute getTarget() {
        return target;
    }

    public void setTarget(BooleanAttribute target) {
        this.target = target;
    }

    /**
     * Returns an unmodifiable set of attribute names
     * @return An unmodifiable set of attribte names
     */
    public Set<String> getIndependentAttributeNames() {
        return Collections.unmodifiableSet(independentAttributeMap.keySet());
    }
}
