package com.rjs.calculohoras.view.impl;

import java.text.ParseException;

import com.rjs.calculohoras.dao.HorarioDao;
import com.rjs.calculohoras.dao.impl.MarcacaoFeitaDao;
import com.rjs.calculohoras.view.abs.AbstractLancamentoFrame;

public class MarcacaoFeitaFrame extends AbstractLancamentoFrame {

	private static final long serialVersionUID = 1L;
	private MarcacaoFeitaDao marcacaoFeitaDao;

	public MarcacaoFeitaFrame(HorarioDao<?> horarioDao) throws ParseException {
		super(horarioDao);
		setTitle("Lan�amento de marca��es feitas");
		marcacaoFeitaDao = new MarcacaoFeitaDao();
	}

	public MarcacaoFeitaDao getMarcacaoFeitaDao() {
		return marcacaoFeitaDao;
	}

}
