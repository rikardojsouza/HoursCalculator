package com.rjs.calculohoras.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.rjs.calculohoras.dao.HorarioDao;
import com.rjs.calculohoras.model.HorarioExtra;
import com.rjs.calculohoras.model.tablemodel.HorarioTableModel;
import com.rjs.calculohoras.model.tablemodel.impl.HorarioExtraTableModel;

public class HorarioExtraDao implements HorarioDao<HorarioExtra> {
	private List<HorarioExtra> horarioList;
	private HorarioExtraTableModel horarioModel;

	public HorarioExtraDao() {
		super();
		horarioList = this.getHorarioList();
		horarioModel = new HorarioExtraTableModel(horarioList);
	}

	@Override
	public HorarioExtra getById(Integer id) {
		return horarioList.get(id);
	}

	public void setHorarioExtraTableModel(HorarioExtraTableModel horarioTrabalhoTableModel) {
		this.horarioModel = horarioTrabalhoTableModel;
	}

	@Override
	public void inserir(HorarioExtra horario) {
		horarioList.add(horario);
		horarioModel = new HorarioExtraTableModel(horarioList);
	}

	@Override
	public void remover(Integer index) {
		HorarioExtra horarioTrabalho = horarioList.get(index);
		horarioList.remove(horarioTrabalho);
		horarioModel = new HorarioExtraTableModel(horarioList);
	}

	@Override
	public List<HorarioExtra> getHorarioList() {
		return new ArrayList<HorarioExtra>();
	}

	@Override
	public HorarioTableModel getHorarioTableModel() {
		return horarioModel;
	}

	@Override
	public void inserir(String entrada, String saida) {
		horarioList.add(new HorarioExtra(entrada, saida));
		horarioModel = new HorarioExtraTableModel(horarioList);
	}

}
