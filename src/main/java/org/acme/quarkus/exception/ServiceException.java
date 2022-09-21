package org.acme.quarkus.exception;

public class ServiceException extends RuntimeException {

    /**
	 *
	 */
	private static final long serialVersionUID = -6504863357422087365L;

	public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String format, Object... objects) {
        super(String.format(format, objects));
    }

}