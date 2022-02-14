package com.algaworks.algalog.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algalog.domain.enumeration.StatusEntregaEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EntregaDTO {
	
	private Long id;
	private String nomeCliente;
	private DestinatarioDTO destinatario;
	private BigDecimal taxa;
	private StatusEntregaEnum status;
	private OffsetDateTime dataPedido;
	private OffsetDateTime dataFinalizacao;
}
