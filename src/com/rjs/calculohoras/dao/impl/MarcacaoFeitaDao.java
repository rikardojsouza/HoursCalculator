package com.rjs.calculohoras.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.rjs.calculohoras.dao.HorarioDao;
import com.rjs.calculohoras.model.MarcacaoFeita;
import com.rjs.calculohoras.model.tablemodel.HorarioTableModel;
import com.rjs.calculohoras.model.tablemodel.impl.MarcacaoFeitaTableModel;

public class MarcacaoFeitaDao implements HorarioDao<MarcacaoFeita> {
	private List<MarcacaoFeita> horarioList;
	private MarcacaoFeitaTableModel horarioModel;

	public MarcacaoFeitaDao() {
		super();
		horarioList = this.getHorarioList();
		horarioModel = new MarcacaoFeitaTableModel(horarioList);
		
//		this.inserirDadosExemplo();
	}

	@Override
	public MarcacaoFeita getById(Integer id) {
		return horarioList.get(id);
	}

	public void setMarcacaoFeitaTableModel(MarcacaoFeitaTableModel horarioTrabalhoTableModel) {
		this.horarioModel = horarioTrabalhoTableModel;
	}

	@Override
	public void inserir(MarcacaoFeita horario) {
		horarioList.add(horario);
		horarioModel = new MarcacaoFeitaTableModel(horarioList);
	}

	@Override
	public void remover(Integer index) {
		MarcacaoFeita horarioTrabalho = horarioList.get(index);
		horarioList.remove(horarioTrabalho);
		horarioModel = new MarcacaoFeitaTableModel(horarioList);
	}

	@Override
	public List<MarcacaoFeita> getHorarioList() {
		return new ArrayList<MarcacaoFeita>();
	}

	@Override
	public HorarioTableModel getHorarioTableModel() {
		return horarioModel;
	}

	@Override
	public void inserir(String entrada, String saida) {
		horarioList.add(new MarcacaoFeita(entrada, saida));
		horarioModel = new MarcacaoFeitaTableModel(horarioList);
	}

}
