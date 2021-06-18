package cn.springmvc.jpa.common.solr;

/**
 * @author Vincent.wang
 *
 */
public class SolrException extends RuntimeException {

    private static final long serialVersionUID = -3644165253296979138L;

    public SolrException() {
        super();
    }

    public SolrException(String message, Throwable cause) {
        super(message, cause);
    }

    public SolrException(String message) {
        super(message);
    }

    public SolrException(Throwable cause) {
        super(cause);
    }
}
