package com.solanodouglas.wl.service.exceptions;

public class ModelNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ModelNotFoundException(Object id) {
		super("Model não encontrado. Id " + id);
	}
}
