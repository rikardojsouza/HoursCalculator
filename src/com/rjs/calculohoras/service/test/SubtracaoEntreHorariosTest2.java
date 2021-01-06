package com.rjs.calculohoras.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rjs.calculohoras.dao.impl.HorarioTrabalhoDao;
import com.rjs.calculohoras.dao.impl.MarcacaoFeitaDao;
import com.rjs.calculohoras.model.HorarioTrabalho;
import com.rjs.calculohoras.model.MarcacaoFeita;
import com.rjs.calculohoras.service.SubtracaoEntreHorarios;

class SubtracaoEntreHorariosTest2 {

	private HorarioTrabalhoDao horarioTrabalhoDao;
	private MarcacaoFeitaDao marcacaoFeitaDao;
	private JTable table1;
	private JTable table2;
	private JTable tableResultado;
	private SubtracaoEntreHorarios subtracaoEntreHorarios;

	@BeforeEach
	public void setUp() throws Exception {
		horarioTrabalhoDao = new HorarioTrabalhoDao();
		marcacaoFeitaDao = new MarcacaoFeitaDao();
		table1 = new JTable((TableModel) horarioTrabalhoDao.getHorarioTableModel());
		table2 = new JTable((TableModel) marcacaoFeitaDao.getHorarioTableModel());
		tableResultado = null;
		subtracaoEntreHorarios = new SubtracaoEntreHorarios();
	}

	private void testeAtraso(JTable table1, JTable table2) throws ParseException {
		tableResultado = subtracaoEntreHorarios.execute(table1, table2);
	}

	private void testeExtra(JTable table2, JTable table1) throws ParseException {
		tableResultado = subtracaoEntreHorarios.execute(table2, table1);
	}

	@Test
	void testSimulacao01_01() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("06:00", "08:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("12:00", "13:00"));

		testeAtraso(table1, table2);

		assertEquals("08:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 1));

		testeExtra(table2, table1);

		assertEquals("06:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("08:00", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("13:00", (String) tableResultado.getModel().getValueAt(1, 1));
	}

	@Test
	void testSimulacao01_02() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("06:00", "07:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:15", "10:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("10:10", "11:35"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("11:50", "12:45"));

		testeAtraso(table1, table2);

		assertEquals("08:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("08:15", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("10:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("10:10", (String) tableResultado.getModel().getValueAt(1, 1));
		assertEquals("11:35", (String) tableResultado.getModel().getValueAt(2, 0));
		assertEquals("11:50", (String) tableResultado.getModel().getValueAt(2, 1));

		testeExtra(table2, table1);

		assertEquals("06:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("07:30", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("12:45", (String) tableResultado.getModel().getValueAt(1, 1));
	}

	@Test
	void testSimulacao02_01() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("14:00", "18:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:00", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("19:00", "21:00"));

		testeAtraso(table1, table2);

		assertEquals("14:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("18:00", (String) tableResultado.getModel().getValueAt(0, 1));

		testeExtra(table2, table1);

		assertEquals("19:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("21:00", (String) tableResultado.getModel().getValueAt(0, 1));
	}

	@Test
	void testSimulacao02_02() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("14:00", "18:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("06:00", "07:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("14:00", "18:00"));

		testeAtraso(table1, table2);

		assertEquals("08:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 1));

		testeExtra(table2, table1);

		assertEquals("06:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("07:00", (String) tableResultado.getModel().getValueAt(0, 1));
	}

	@Test
	void testSimulacao02_03() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("14:00", "18:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:00", "11:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("12:00", "16:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("17:00", "21:00"));

		testeAtraso(table1, table2);

		assertEquals("11:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("16:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("17:00", (String) tableResultado.getModel().getValueAt(1, 1));

		testeExtra(table2, table1);

		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("14:00", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("18:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("21:00", (String) tableResultado.getModel().getValueAt(1, 1));
	}

	@Test
	void testSimulacao02_04() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("14:00", "18:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:00", "11:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("12:00", "16:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("17:00", "21:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("22:00", "23:00"));

		testeAtraso(table1, table2);

		assertEquals("11:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("16:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("17:00", (String) tableResultado.getModel().getValueAt(1, 1));

		testeExtra(table2, table1);

		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("14:00", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("18:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("21:00", (String) tableResultado.getModel().getValueAt(1, 1));
		assertEquals("22:00", (String) tableResultado.getModel().getValueAt(2, 0));
		assertEquals("23:00", (String) tableResultado.getModel().getValueAt(2, 1));
	}

	@Test
	void testSimulacao02_05() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("14:00", "18:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:00", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("14:00", "18:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("20:00", "23:00"));

		testeAtraso(table1, table2);

		assertEquals(0, tableResultado.getModel().getRowCount());

		testeExtra(table2, table1);

		assertEquals("20:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("23:00", (String) tableResultado.getModel().getValueAt(0, 1));
	}

	@Test
	void testSimulacao02_06() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("14:00", "18:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("06:00", "06:45"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:00", "09:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("10:00", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("14:00", "16:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("17:00", "18:45"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("19:55", "21:50"));

		testeAtraso(table1, table2);

		assertEquals("09:30", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("10:00", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("16:30", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("17:00", (String) tableResultado.getModel().getValueAt(1, 1));

		testeExtra(table2, table1);

		assertEquals("06:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("07:45", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("18:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("18:45", (String) tableResultado.getModel().getValueAt(1, 1));
		assertEquals("19:55", (String) tableResultado.getModel().getValueAt(2, 0));
		assertEquals("21:50", (String) tableResultado.getModel().getValueAt(2, 1));
	}
}
