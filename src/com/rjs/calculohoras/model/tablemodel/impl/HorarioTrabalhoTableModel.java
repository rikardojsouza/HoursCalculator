package com.rjs.calculohoras.model.tablemodel.impl;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.rjs.calculohoras.model.HorarioTrabalho;
import com.rjs.calculohoras.model.tablemodel.HorarioTableModel;

public class HorarioTrabalhoTableModel extends AbstractTableModel implements HorarioTableModel {

	private static final long serialVersionUID = 1L;
	private List<HorarioTrabalho> linhas;
	private String[] columns = null;

	public HorarioTrabalhoTableModel(List<HorarioTrabalho> linhas) {
		super();
		this.linhas = linhas;
		this.getColunas();
	}

	private void getColunas() {
		columns = new String[2];
		columns[0] = "Entrada";
		columns[1] = "Saida";
	}

	public void add(HorarioTrabalho linha) {
		linhas.add(linha);
		int ultimoIndice = getRowCount() - 1;
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	@Override
	public int getRowCount() {
		return linhas.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columns[columnIndex].getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HorarioTrabalho horarioTrabalho = linhas.get(rowIndex);
		String valueObject = null;
		switch (columnIndex) {
		case 0:
			valueObject = horarioTrabalho.getEntrada();
			break;
		case 1:
			valueObject = horarioTrabalho.getSaida();
			break;
		default:
			System.err.println("Índice inválido para propriedade");
		}

		return valueObject;
	}

	public void setValueAt(HorarioTrabalho aValue, int rowIndex, int columnIndex) {
		HorarioTrabalho horarioTrabalho = linhas.get(rowIndex);

		horarioTrabalho.setEntrada(aValue.getEntrada());
		horarioTrabalho.setSaida(aValue.getSaida());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
	}

}
