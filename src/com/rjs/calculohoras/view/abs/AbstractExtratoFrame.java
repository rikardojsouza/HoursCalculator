package com.rjs.calculohoras.view.abs;

import java.awt.BorderLayout;
import java.awt.Point;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import com.rjs.calculohoras.dao.HorarioDao;

public abstract class AbstractExtratoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HorarioDao<?> horarioDao;
	
	public AbstractExtratoFrame(HorarioDao<?> horarioDao, JTable table) throws ParseException {
		this.horarioDao = horarioDao;
		setTitle("Extrato de horas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setSize(400, 550);

		addPanel();
		addTable(table, new Point(0, 0));
		setVisible(true);
	}

	private void addPanel() {
		contentPane = new JPanel();
		contentPane.setSize(this.getWidth(), this.getHeight());
		contentPane.setLocation(0, 0);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private void addTable(JTable table, Point point) {
		if (table == null) {
           table = new JTable((TableModel) horarioDao.getHorarioTableModel());
		}

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setSize(this.getWidth(), this.getHeight());
		scrollPane.setLocation(point);
		scrollPane.repaint();
		scrollPane.revalidate();
		contentPane.add(scrollPane, BorderLayout.AFTER_LAST_LINE);
	}
}
