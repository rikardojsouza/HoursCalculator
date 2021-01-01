package com.rjs.calculohoras.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.rjs.calculohoras.dao.HorarioDao;
import com.rjs.calculohoras.model.HorarioTrabalho;
import com.rjs.calculohoras.model.tablemodel.HorarioTableModel;
import com.rjs.calculohoras.model.tablemodel.impl.HorarioTrabalhoTableModel;

public class HorarioTrabalhoDao implements HorarioDao<HorarioTrabalho> {
	private List<HorarioTrabalho> horarioList;
	private HorarioTrabalhoTableModel horarioModel;

	public HorarioTrabalhoDao() {
		super();
		horarioList = this.getHorarioList();
		horarioModel = new HorarioTrabalhoTableModel(horarioList);
	}

	@Override
	public HorarioTrabalho getById(Integer id) {
		return horarioList.get(id);
	}

	public void setHorarioTrabalhoTableModel(HorarioTrabalhoTableModel horarioTrabalhoTableModel) {
		this.horarioModel = horarioTrabalhoTableModel;
	}

	@Override
	public void inserir(HorarioTrabalho horario) {
		horarioList.add(horario);
		horarioModel = new HorarioTrabalhoTableModel(horarioList);
	}

	@Override
	public void remover(Integer index) {
		HorarioTrabalho horarioTrabalho = horarioList.get(index);
		horarioList.remove(horarioTrabalho);
		horarioModel = new HorarioTrabalhoTableModel(horarioList);
	}

	@Override
	public List<HorarioTrabalho> getHorarioList() {
		return new ArrayList<HorarioTrabalho>();
	}

	@Override
	public HorarioTableModel getHorarioTableModel() {
		return horarioModel;
	}

	@Override
	public void inserir(String entrada, String saida) {
		horarioList.add(new HorarioTrabalho(entrada, saida));
		horarioModel = new HorarioTrabalhoTableModel(horarioList);
	}

}
