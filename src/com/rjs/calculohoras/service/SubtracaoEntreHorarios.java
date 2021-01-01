package com.rjs.calculohoras.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JTable;

import com.rjs.calculohoras.model.EntradaSaida;
import com.rjs.calculohoras.model.HorarioResultado;
import com.rjs.calculohoras.model.tablemodel.impl.HorarioResultadoTableModel;

public class SubtracaoEntreHorarios {

	public JTable execute(JTable tabela1, JTable tabela2) throws ParseException {
		EntradaSaida entradaSaida = new EntradaSaida();

		HorarioResultadoTableModel horarioResultadoTableModel = new HorarioResultadoTableModel(
				new ArrayList<HorarioResultado>());

		for (Integer indiceLinhaTabela1 = 0; indiceLinhaTabela1 < tabela1.getModel()
				.getRowCount(); indiceLinhaTabela1++) {

			entradaSaida.setEntrada1(getEntrada(tabela1, indiceLinhaTabela1));
			entradaSaida.setSaida1(getSaida(tabela1, indiceLinhaTabela1));

			for (Integer indiceLinhaTabela2 = 0; indiceLinhaTabela2 < tabela2.getModel()
					.getRowCount(); indiceLinhaTabela2++) {

				entradaSaida.setEntrada2(getEntrada(tabela2, indiceLinhaTabela2));
				entradaSaida.setSaida2(getSaida(tabela2, indiceLinhaTabela2));

				normalizaHoraNorturna(entradaSaida);

				if (estaEntre(entradaSaida.getEntrada2(), entradaSaida.getEntrada1(), entradaSaida.getSaida1())) {
					registraEntrada(tabela1, entradaSaida, horarioResultadoTableModel);
				}

				if (estaEntre(entradaSaida.getSaida2(), entradaSaida.getEntrada1(), entradaSaida.getSaida1())) {
					registraSaida(tabela1, tabela2, entradaSaida, horarioResultadoTableModel, indiceLinhaTabela2);
				}
			}

		}

		JTable resultado = new JTable();
		resultado.setModel(horarioResultadoTableModel);
		return resultado;
	}

	private Long getSaida(JTable tabela, Integer linha) throws ParseException {
		return converteHora((String) tabela.getModel().getValueAt(linha, 1));
	}

	private Long getEntrada(JTable tabela, Integer linha) throws ParseException {
		return converteHora((String) tabela.getModel().getValueAt(linha, 0));
	}

	private void registraSaida(JTable tabela1, JTable tabela2, EntradaSaida entradaSaida,
			HorarioResultadoTableModel horarioResultadoTableModel, Integer IndiceLinhaAtual) throws ParseException {
		if (existeSoUmLancamento(tabela1)) {
			Long entradaE = null;
			try {
				if (tabela2.getModel().getRowCount() > 1 && tabela2.getModel().getRowCount() > (IndiceLinhaAtual + 1)) {
					entradaE = converteHora((String) tabela2.getModel().getValueAt(IndiceLinhaAtual + 1, 0));
				}
			} catch (ParseException e) {
				entradaE = null;
			}
			horarioResultadoTableModel.add(new HorarioResultado(entradaSaida.getSaida2Old(),
					entradaE == null ? entradaSaida.getSaida1Old() : entradaE));
		} else {
			horarioResultadoTableModel
					.add(new HorarioResultado(entradaSaida.getSaida2Old(), entradaSaida.getSaida1Old()));
		}
	}

	private void registraEntrada(JTable tabela1, EntradaSaida entradaSaida,
			HorarioResultadoTableModel horarioResultadoTableModel) throws ParseException {
		if (existeSoUmLancamento(tabela1)) {

			if (!lancamentoJaFoiEfetuado(entradaSaida.getEntrada2(), horarioResultadoTableModel)) {

				Long ultimaEntradaUtilizada = retornaUltimaEntradaUtilizada(horarioResultadoTableModel);

				horarioResultadoTableModel.add(new HorarioResultado(
						ultimaEntradaUtilizada == null ? entradaSaida.getEntrada1Old() : ultimaEntradaUtilizada,
						entradaSaida.getEntrada2Old()));
			}

		} else {
			horarioResultadoTableModel
					.add(new HorarioResultado(entradaSaida.getEntrada1Old(), entradaSaida.getEntrada2Old()));
		}
	}

	private boolean existeSoUmLancamento(JTable tabela) {
		return tabela.getModel().getRowCount() == 1;
	}

	private Long retornaUltimaEntradaUtilizada(HorarioResultadoTableModel horarioResultadoTableModel) {
		Long ultimaEntradaUtilizada = null;
		try {
			if (horarioResultadoTableModel.getRowCount() > 0) {
				ultimaEntradaUtilizada = converteHora((String) horarioResultadoTableModel
						.getValueAt(horarioResultadoTableModel.getRowCount() - 1, 0));
			}
		} catch (ParseException e) {
			ultimaEntradaUtilizada = null;
		}
		return ultimaEntradaUtilizada;
	}

	private boolean lancamentoJaFoiEfetuado(Long valor, HorarioResultadoTableModel horarioResultadoTableModel)
			throws ParseException {
		for (Integer k = 0; k < horarioResultadoTableModel.getRowCount(); k++) {
			if (valor.compareTo(converteHora((String) horarioResultadoTableModel.getValueAt(k, 1))) == 0) {
				return true;
			}
		}
		return false;
	}

	private void normalizaHoraNorturna(EntradaSaida entradaSaida) {

		boolean temViradaDeDia = entradaSaida.getEntrada1() > entradaSaida.getSaida1()
				|| entradaSaida.getEntrada2() > entradaSaida.getSaida2();

		entradaSaida.setEntrada1Old(entradaSaida.getEntrada1());
		entradaSaida.setSaida1Old(entradaSaida.getSaida1());
		entradaSaida.setEntrada2Old(entradaSaida.getEntrada2());
		entradaSaida.setSaida2Old(entradaSaida.getSaida2());

		if (!temViradaDeDia) {
			return;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(entradaSaida.getEntrada1());
		cal.add(Calendar.HOUR, -8);
		Long minimo = cal.getTimeInMillis();

		if (entradaSaida.getEntrada1() > entradaSaida.getEntrada2()
				&& ((entradaSaida.getEntrada1() - entradaSaida.getEntrada2()) > minimo)) {
			entradaSaida.setEntrada2(ajustaHoraNoturna(entradaSaida.getEntrada2()));
		} else if (entradaSaida.getEntrada2() > entradaSaida.getEntrada1()
				&& ((entradaSaida.getEntrada2() - entradaSaida.getEntrada1()) > cal.getTimeInMillis())) {
			entradaSaida.setEntrada1(ajustaHoraNoturna(entradaSaida.getEntrada1()));
		}

		if (entradaSaida.getEntrada1() > entradaSaida.getSaida1()) {
			entradaSaida.setSaida1(ajustaHoraNoturna(entradaSaida.getSaida1()));
		}

		if (entradaSaida.getEntrada2() > entradaSaida.getSaida2()) {
			entradaSaida.setSaida2(ajustaHoraNoturna(entradaSaida.getSaida2()));
		}
	}

	private Long ajustaHoraNoturna(Long valor) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(valor);
		cal.add(Calendar.HOUR, 24);
		return cal.getTimeInMillis();
	}

	private boolean estaEntre(Long horaValidada, Long horaEntrada, Long horaSaida) {
		return horaValidada.compareTo(horaEntrada) > 0 && horaValidada.compareTo(horaSaida) < 0;
	}

	private Long converteHora(String valor) throws ParseException {
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
		return formatador.parse(valor).getTime();
	}

}
