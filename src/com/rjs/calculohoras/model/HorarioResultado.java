package com.rjs.calculohoras.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.rjs.calculohoras.model.abs.AbstractHorario;

public class HorarioResultado extends AbstractHorario {
	private Long entradaL;
	private Long saidaL;
	
	public HorarioResultado() {
		super();
	}

	public HorarioResultado(String entrada, String saida) throws ParseException {
		super(entrada, saida);
		entradaL = retornaHora(entrada);
		saidaL = retornaHora(saida);
	}
	
	public HorarioResultado(Long entradaL, Long saidaL) throws ParseException {
		super(retornaHora(entradaL), retornaHora(saidaL));
	}

	private Long retornaHora(String valor) throws ParseException {
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
		return formatador.parse(valor).getTime();
	}

	private static String retornaHora(Long valor) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(valor);
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
		return formatador.format(valor);
	}

	public Long getEntradaL() {
		return entradaL;
	}

	public void setEntradaL(Long entradaL) {
		this.entradaL = entradaL;
	}

	public Long getSaidaL() {
		return saidaL;
	}

	public void setSaidaL(Long saidaL) {
		this.saidaL = saidaL;
	}	

}
