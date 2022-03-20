package ro.unibuc.hello.exception;

public class PropertyRequiredException extends RuntimeException {
    private static final String propertyRequiredTemplate = "Property %s.%s is required.";

    public PropertyRequiredException(String propertyName, Object obj) {
            super(String.format(propertyRequiredTemplate,obj.getClass().getSimpleName(), propertyName));
        }
}
