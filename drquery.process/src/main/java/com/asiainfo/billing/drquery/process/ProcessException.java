package com.asiainfo.billing.drquery.process;

import com.asiainfo.billing.drquery.core.DRQueryNestedCheckedException;

/**
 *
 * @author tianyi
 */
public class ProcessException extends DRQueryNestedCheckedException{
	
	private static final long serialVersionUID = 948253775072443812L;


    public ProcessException(String reason) {
        super(reason);
    }

    public ProcessException(String reason, Throwable cause) {
        super(reason, cause);
    }

}
