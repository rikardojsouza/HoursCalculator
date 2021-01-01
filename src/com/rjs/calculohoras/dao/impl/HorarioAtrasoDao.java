package com.rjs.calculohoras.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.rjs.calculohoras.dao.HorarioDao;
import com.rjs.calculohoras.model.HorarioAtraso;
import com.rjs.calculohoras.model.tablemodel.HorarioTableModel;
import com.rjs.calculohoras.model.tablemodel.impl.HorarioAtrasoTableModel;

public class HorarioAtrasoDao implements HorarioDao<HorarioAtraso> {
	private List<HorarioAtraso> horarioList;
	private HorarioAtrasoTableModel horarioModel;

	public HorarioAtrasoDao() {
		super();
		horarioList = this.getHorarioList();
		horarioModel = new HorarioAtrasoTableModel(horarioList);
	}

	@Override
	public HorarioAtraso getById(Integer id) {
		return horarioList.get(id);
	}

	public void setHorarioAtrasoTableModel(HorarioAtrasoTableModel horarioTrabalhoTableModel) {
		this.horarioModel = horarioTrabalhoTableModel;
	}

	@Override
	public void inserir(HorarioAtraso horario) {
		horarioList.add(horario);
		horarioModel = new HorarioAtrasoTableModel(horarioList);
	}

	@Override
	public void remover(Integer index) {
		HorarioAtraso horarioTrabalho = horarioList.get(index);
		horarioList.remove(horarioTrabalho);
		horarioModel = new HorarioAtrasoTableModel(horarioList);
	}

	@Override
	public List<HorarioAtraso> getHorarioList() {
		return new ArrayList<HorarioAtraso>();
	}

	@Override
	public HorarioTableModel getHorarioTableModel() {
		return horarioModel;
	}

	@Override
	public void inserir(String entrada, String saida) {
		horarioList.add(new HorarioAtraso(entrada, saida));
		horarioModel = new HorarioAtrasoTableModel(horarioList);
	}

}
