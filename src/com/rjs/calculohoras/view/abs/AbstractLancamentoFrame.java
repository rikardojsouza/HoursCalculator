package com.rjs.calculohoras.view.abs;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import com.rjs.calculohoras.dao.HorarioDao;

public abstract class AbstractLancamentoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HorarioDao<?> horarioDao;
	private JTable table;
	private JFormattedTextField txtEntrada;
	private JFormattedTextField txtSaida;
	private JButton botaoInserir;

	public AbstractLancamentoFrame(HorarioDao<?> horarioDao) throws ParseException {
		this.horarioDao = horarioDao;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setSize(400, 550);

		addPanel();

		addLabel("Entrada", new Point(60, 12));
		txtEntrada = addFieldText(new Point(110, 10));

		addLabel("Saída", new Point(200, 12));
		txtSaida = addFieldText(new Point(240, 10));

		botaoInserir = addButton("Adicionar", actionListenerAdicionar(), new Point(60, 40));
		addButton("Remover", actionListenerRemover(), new Point(200, 40));

		getRootPane().setDefaultButton(botaoInserir);

		addTable(new Point(10, 60));
		setVisible(true);
	}

	private void addPanel() {
		contentPane = new JPanel();
		contentPane.setSize(785, 300);
		contentPane.setLocation(0, 0);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private JFormattedTextField addFieldText(Point point) throws ParseException {
		JFormattedTextField formatedTextField = new JFormattedTextField(new MaskFormatter("##:##"));
		formatedTextField.setSize(40, 23);
		formatedTextField.setLocation(point);
		formatedTextField.setFocusLostBehavior(JFormattedTextField.COMMIT);
		contentPane.add(formatedTextField, BorderLayout.AFTER_LAST_LINE);
		return formatedTextField;
	}

	private void addLabel(String text, Point point) {
		JLabel label = new JLabel();
		label.setText(text);
		label.setSize(200, 15);
		label.setLocation(point);
		contentPane.add(label, BorderLayout.AFTER_LAST_LINE);
	}

	private void addTable(Point point) {
		table = new JTable((TableModel) horarioDao.getHorarioTableModel());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setSize(400, 600);
		scrollPane.setLocation(point);
		scrollPane.repaint();
		scrollPane.revalidate();
		contentPane.add(scrollPane, BorderLayout.AFTER_LAST_LINE);
	}

	private JButton addButton(String text, ActionListener actionListener, Point point) {
		JButton button = new JButton();
		button.setText(text);
		button.setSize(100, 23);
		button.setLocation(point);
		button.addActionListener(actionListener);

		contentPane.add(button, BorderLayout.AFTER_LAST_LINE);
		return button;
	}

	private ActionListener actionListenerAdicionar() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					validarDadosObrigatorios();
				} catch (Exception e1) {
					return;
				}
				inserirDados();
				limparCampos();
				ajustarFoco();
			}
		};
	}

	private ActionListener actionListenerRemover() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					removerDados();
					ajustarFoco();
				}
			}
		};
	}

	public JTable getJTable() {
		return table;
	}

	private void limparCampos() {
		txtEntrada.setText("");
		txtSaida.setText("");

	}

	private void ajustarFoco() {
		txtEntrada.requestFocusInWindow();
	}

	private void validarDadosObrigatorios() throws Exception {
		if (!txtEntrada.isEditValid()) {
			String mensagem = "Entrada não preenchida! Confirme por favor.";
			JOptionPane.showMessageDialog(this, mensagem);
			throw new Exception(mensagem);
		}

		if (!txtSaida.isEditValid()) {
			String mensagem = "Saída não preenchida! Confirme por favor.";
			JOptionPane.showMessageDialog(this, mensagem);
			throw new Exception(mensagem);
		}

	}

	private void inserirDados() {
		horarioDao.inserir(txtEntrada.getText(), txtSaida.getText());
		table.setModel((TableModel) horarioDao.getHorarioTableModel());
	}

	private void removerDados() {
		horarioDao.remover(table.getSelectedRow());
		table.setModel((TableModel) horarioDao.getHorarioTableModel());
	}
}
