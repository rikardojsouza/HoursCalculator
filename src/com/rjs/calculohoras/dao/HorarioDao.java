package com.rjs.calculohoras.dao;

import java.util.List;

import com.rjs.calculohoras.model.tablemodel.HorarioTableModel;

public interface HorarioDao<T> {

	public void inserir(T horario);

	public void remover(Integer index);

	public List<T> getHorarioList();

	public T getById(Integer id);

	public HorarioTableModel getHorarioTableModel();

	public void inserir(String entrada, String saida);

}
