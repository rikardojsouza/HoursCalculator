package com.rjs.calculohoras.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EntradaSaida {
	private Long entrada1;
	private Long saida1;
	private Long entrada2;
	private Long saida2;
	private Long entrada1Old;
	private Long saida1Old;
	private Long entrada2Old;
	private Long saida2Old;

	public EntradaSaida() {
		entrada1 = 0L;
		saida1 = 0L;
		entrada2 = 0L;
		saida2 = 0L;
		entrada1Old = 0L;
		saida1Old = 0L;
		entrada2Old = 0L;
		saida2Old = 0L;

	}

	public Long getEntrada1() {
		return entrada1;
	}

	public void setEntrada1(Long entrada1) {
		this.entrada1 = entrada1;
	}

	public Long getSaida1() {
		return saida1;
	}

	public void setSaida1(Long saida1) {
		this.saida1 = saida1;
	}

	public Long getEntrada2() {
		return entrada2;
	}

	public void setEntrada2(Long entrada2) {
		this.entrada2 = entrada2;
	}

	public Long getSaida2() {
		return saida2;
	}

	public void setSaida2(Long saida2) {
		this.saida2 = saida2;
	}

	public Long getEntrada1Old() {
		return entrada1Old;
	}

	public void setEntrada1Old(Long entrada1Old) {
		this.entrada1Old = entrada1Old;
	}

	public Long getSaida1Old() {
		return saida1Old;
	}

	public void setSaida1Old(Long saida1Old) {
		this.saida1Old = saida1Old;
	}

	public Long getEntrada2Old() {
		return entrada2Old;
	}

	public void setEntrada2Old(Long entrada2Old) {
		this.entrada2Old = entrada2Old;
	}

	public Long getSaida2Old() {
		return saida2Old;
	}

	public void setSaida2Old(Long saida2Old) {
		this.saida2Old = saida2Old;
	}

	@Override
	public String toString() {
		return "EntradaSaida [entrada1=" + converteHora(entrada1) + ", saida1=" + converteHora(saida1) + ", entrada2="
				+ converteHora(entrada2) + ", saida2=" + converteHora(saida2) + ", entrada1Old="
				+ converteHora(entrada1Old) + ", saida1Old=" + converteHora(saida1Old) + ", entrada2Old="
				+ converteHora(entrada2Old) + ", saida2Old=" + converteHora(saida2Old) + "]";
	}

	private static String converteHora(Long valor) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(valor);
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
		return formatador.format(valor);
	}

}
