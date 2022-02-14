package com.algaworks.algalog.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = -1062138421589651833L;
	
	public NegocioException (String message) {
		super(message);
	}

	
}
