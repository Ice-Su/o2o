package com.imooc.exceptions;

public class ProductOperationException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5747171315755163127L;

	public ProductOperationException(String msg) {
		super(msg);
	}
}
