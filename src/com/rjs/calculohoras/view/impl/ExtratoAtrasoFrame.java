package com.rjs.calculohoras.view.impl;

import java.text.ParseException;

import javax.swing.JTable;

import com.rjs.calculohoras.dao.HorarioDao;
import com.rjs.calculohoras.view.abs.AbstractExtratoFrame;

public class ExtratoAtrasoFrame extends AbstractExtratoFrame {

	private static final long serialVersionUID = 1L;

	public ExtratoAtrasoFrame(HorarioDao<?> horarioDao, JTable table) throws ParseException {
		super(horarioDao, table);
		setTitle("Extrato horas em atraso");
	}

}
