package com.rjs.calculohoras.view.impl;

import java.text.ParseException;

import com.rjs.calculohoras.dao.HorarioDao;
import com.rjs.calculohoras.dao.impl.HorarioTrabalhoDao;
import com.rjs.calculohoras.view.abs.AbstractLancamentoFrame;

public class HorarioTrabalhoFrame extends AbstractLancamentoFrame {

	private static final long serialVersionUID = 1L;
	private HorarioTrabalhoDao horarioTrabalhoDao;

	public HorarioTrabalhoFrame(HorarioDao<?> horarioDao) throws ParseException {
		super(horarioDao);
		setTitle("Lançamento de horas a trabalhar");
		horarioTrabalhoDao = new HorarioTrabalhoDao();
	}

	public HorarioTrabalhoDao getHorarioTrabalhoDao() {
		return horarioTrabalhoDao;
	}
}
