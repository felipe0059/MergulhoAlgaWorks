package com.algaworks.algalog.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algalog.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algalog.domain.exception.NegocioException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid (
			MethodArgumentNotValidException ex,	
			HttpHeaders headers, 
			HttpStatus status, 
			WebRequest request
	) {
		List<ApiProblem.Campo> campos = new ArrayList<>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new ApiProblem.Campo(nome, mensagem));
		}
		
		ApiProblem apiProblem = new ApiProblem();
		apiProblem.setStatus(status.value());
		apiProblem.setDataHora(OffsetDateTime.now());
		apiProblem.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente!");
		apiProblem.setCampos(campos);
		
		return handleExceptionInternal(ex, apiProblem, headers, status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocioException (NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		ApiProblem apiProblem = new ApiProblem();
		apiProblem.setStatus(status.value());
		apiProblem.setDataHora(OffsetDateTime.now());
		apiProblem.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, apiProblem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleNegocioException (EntidadeNaoEncontradaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		ApiProblem apiProblem = new ApiProblem();
		apiProblem.setStatus(status.value());
		apiProblem.setDataHora(OffsetDateTime.now());
		apiProblem.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, apiProblem, new HttpHeaders(), status, request);
	}
	
	
}
