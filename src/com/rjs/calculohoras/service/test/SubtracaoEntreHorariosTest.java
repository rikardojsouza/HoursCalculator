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

class SubtracaoEntreHorariosTest {

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

	@Test
	void testExemplo01_Atrasos() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("07:00", "11:00"));

		testeAtraso(table1, table2);

		assertEquals("11:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 1));
	}

	@Test
	void testExemplo02_Extras() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("07:00", "11:00"));

		testeExtra(table2, table1);

		assertEquals("07:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("08:00", (String) tableResultado.getModel().getValueAt(0, 1));
	}

	@Test
	void testExemplo03_01_Extra() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("13:30", "17:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("06:00", "20:00"));

		testeAtraso(table1, table2);

		assertEquals(0, tableResultado.getModel().getRowCount());

		testeExtra(table2, table1);

		assertEquals("06:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("08:00", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("13:30", (String) tableResultado.getModel().getValueAt(1, 1));
		assertEquals("17:30", (String) tableResultado.getModel().getValueAt(2, 0));
		assertEquals("20:00", (String) tableResultado.getModel().getValueAt(2, 1));

	}

	@Test
	void testExemplo03_02_Completo() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("13:30", "17:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("07:00", "12:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("14:00", "17:00"));

		testeAtraso(table1, table2);

		assertEquals("13:30", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("14:00", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("17:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("17:30", (String) tableResultado.getModel().getValueAt(1, 1));

		testeExtra(table2, table1);

		assertEquals("07:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("08:00", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("12:30", (String) tableResultado.getModel().getValueAt(1, 1));
	}

	@Test
	void testExemplo04_01() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("22:00", "05:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("21:00", "04:00"));

		testeAtraso(table1, table2);

		assertEquals("04:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("05:00", (String) tableResultado.getModel().getValueAt(0, 1));

		testeExtra(table2, table1);

		assertEquals("21:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("22:00", (String) tableResultado.getModel().getValueAt(0, 1));
	}

	@Test
	void testExemplo04_02() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("22:00", "05:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("03:00", "07:00"));

		testeAtraso(table1, table2);

		assertEquals("22:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("03:00", (String) tableResultado.getModel().getValueAt(0, 1));

		testeExtra(table2, table1);

		assertEquals("05:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("07:00", (String) tableResultado.getModel().getValueAt(0, 1));
	}

	@Test
	void testExemplo05_01UnitarioMinutosAtraso() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:00", "11:59"));

		testeAtraso(table1, table2);

		assertEquals("11:59", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 1));

		testeExtra(table2, table1);

		assertEquals(0, tableResultado.getModel().getRowCount());
	}

	@Test
	void testExemplo05_02UnitarioMinutosExtra() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:00", "12:01"));

		testeAtraso(table1, table2);

		assertEquals(0, tableResultado.getModel().getRowCount());

		testeExtra(table2, table1);

		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("12:01", (String) tableResultado.getModel().getValueAt(0, 1));

	}

	@Test
	void testExemplo05_03UnitarioExatas() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("13:30", "17:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:00", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("13:30", "17:30"));

		testeAtraso(table1, table2);

		assertEquals(0, tableResultado.getModel().getRowCount());

		testeExtra(table2, table1);

		assertEquals(0, tableResultado.getModel().getRowCount());

	}

	@Test
	void testExemplo05_04UnitarioSaidaAntesDoAlmoco() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("13:30", "17:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:00", "10:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("13:30", "17:30"));

		testeAtraso(table1, table2);

		assertEquals("10:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 1));

		testeExtra(table2, table1);

		assertEquals(0, tableResultado.getModel().getRowCount());

	}

	@Test
	void testExemplo05_05UnitarioSaidaAntesDoAlmocoExtraPraCompensar() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("13:30", "17:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:00", "10:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("13:30", "19:30"));

		testeAtraso(table1, table2);

		assertEquals("10:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 1));

		testeExtra(table2, table1);

		assertEquals("17:30", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("19:30", (String) tableResultado.getModel().getValueAt(0, 1));
	}

	@Test
	void testExemplo05_06UnitarioSaidaMinutosPicados() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("13:30", "17:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("08:05", "12:05"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("13:40", "17:40"));

		testeAtraso(table1, table2);

		assertEquals("08:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("08:05", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("13:30", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("13:40", (String) tableResultado.getModel().getValueAt(1, 1));

		testeExtra(table2, table1);

		assertEquals("12:00", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("12:05", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("17:30", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("17:40", (String) tableResultado.getModel().getValueAt(1, 1));
	}

	@Test
	void testExemplo05_06UnitarioSaidaMinutosPicados2() throws ParseException {
		horarioTrabalhoDao.inserir(new HorarioTrabalho("08:00", "12:00"));
		horarioTrabalhoDao.inserir(new HorarioTrabalho("13:30", "17:30"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("07:58", "12:00"));
		marcacaoFeitaDao.inserir(new MarcacaoFeita("13:29", "17:18"));

		testeAtraso(table1, table2);

		assertEquals("17:18", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("17:30", (String) tableResultado.getModel().getValueAt(0, 1));

		testeExtra(table2, table1);

		assertEquals("07:58", (String) tableResultado.getModel().getValueAt(0, 0));
		assertEquals("08:00", (String) tableResultado.getModel().getValueAt(0, 1));
		assertEquals("13:29", (String) tableResultado.getModel().getValueAt(1, 0));
		assertEquals("13:30", (String) tableResultado.getModel().getValueAt(1, 1));
	}

	private void testeAtraso(JTable table1, JTable table2) throws ParseException {
		tableResultado = subtracaoEntreHorarios.execute(table1, table2);
	}

	private void testeExtra(JTable table2, JTable table1) throws ParseException {
		tableResultado = subtracaoEntreHorarios.execute(table2, table1);
	}
}
