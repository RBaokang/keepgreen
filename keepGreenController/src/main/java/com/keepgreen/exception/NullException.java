package com.keepgreen.exception;

import com.keepgreen.exception.base.BaseException;

/**
 * 空异常
 */
public class NullException extends BaseException {
    private static final long serialVersionUID = 1L;

    public NullException() {
        super("null", "null error", null);
    }
}
