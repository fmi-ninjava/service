package ro.unibuc.hello.exception;

public class DuplicateEntryException extends RuntimeException {
    private static final String propertyRequiredTemplate = "Entry of type %s already exists..";

    public DuplicateEntryException(Object obj) {
            super(String.format(propertyRequiredTemplate,obj.getClass().getSimpleName()));
        }
}
