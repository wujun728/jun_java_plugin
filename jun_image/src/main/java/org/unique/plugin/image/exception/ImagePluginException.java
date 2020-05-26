package org.unique.plugin.image.exception;

/**
 * image exception
 * @author rex
 *
 */
public class ImagePluginException extends RuntimeException{

    private static final long serialVersionUID = -102448018298923692L;

    public ImagePluginException() {
        super();
    }

    public ImagePluginException(String message) {
        super(message);
    }

    public ImagePluginException(Throwable cause) {
        super(cause);
    }

    public ImagePluginException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
