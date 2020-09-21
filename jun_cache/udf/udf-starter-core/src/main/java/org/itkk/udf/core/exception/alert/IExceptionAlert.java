package org.itkk.udf.core.exception.alert;

import org.itkk.udf.core.RestResponse;

/**
 * IExceptionAlert
 */
public interface IExceptionAlert {
    /**
     * alert
     *
     * @param errorResponse errorResponse
     */
    void alert(RestResponse<String> errorResponse);
}
