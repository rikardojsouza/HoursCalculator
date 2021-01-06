package com.rjs.calculohoras.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.rjs.calculohoras.dao.impl.HorarioAtrasoDao;
import com.rjs.calculohoras.dao.impl.HorarioExtraDao;
import com.rjs.calculohoras.dao.impl.HorarioTrabalhoDao;
import com.rjs.calculohoras.dao.impl.MarcacaoFeitaDao;
import com.rjs.calculohoras.service.SubtracaoEntreHorarios;
import com.rjs.calculohoras.view.impl.ExtratoAtrasoFrame;
import com.rjs.calculohoras.view.impl.ExtratoExtraFrame;
import com.rjs.calculohoras.view.impl.HorarioTrabalhoFrame;
import com.rjs.calculohoras.view.impl.MarcacaoFeitaFrame;

public class PrincipalFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HorarioTrabalhoFrame horarioTrabalhoFrame;
	private MarcacaoFeitaFrame marcacaoFeitaFrame;
	private ExtratoAtrasoFrame horarioAtrasoFrame;
	private ExtratoExtraFrame horarioExtraFrame;

	private HorarioTrabalhoDao horarioTrabalhoDao;
	private MarcacaoFeitaDao marcacaoFeitaDao;
	private HorarioAtrasoDao horarioAtrasoDao;
	private HorarioExtraDao horarioExtraDao;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalFrame frame = new PrincipalFrame();
					UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PrincipalFrame() {
		setTitle("Sistema de cálculo de horas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		setSize(450, 180);

		addPane();
		addButton("Lançar Horário de Trabalho", actionListenerChamarHorarioTrabalho(), new Point(10, 10));
		addButton("Lançar Marcações Feitas", actionListenerChamarMarcacaoFeita(), new Point(220, 10));
		addButton("Calcular Horas de Atraso", actionListenerChamarCalculoAtraso(), new Point(10, 60));
		addButton("Calcular Horas Extras", actionListenerChamarCalculoExtra(), new Point(220, 60));
		addFoot();
	}

	private void addPane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private void addButton(String text, ActionListener actionListener, Point point) {
		JButton button = new JButton();
		button.setText(text);
		button.setSize(200, 40);
		button.setLocation(point);
		button.addActionListener(actionListener);
		contentPane.add(button, BorderLayout.AFTER_LAST_LINE);
	}

	private void addFoot() {
		JLabel foot = new JLabel("2021.a.01.0002");
		foot.setHorizontalAlignment(JLabel.RIGHT);
		contentPane.add(foot, BorderLayout.AFTER_LAST_LINE);
	}

	private void chamarFrame(JFrame formulario) {
		formulario.setAlwaysOnTop(true);
		formulario.setVisible(true);
	}

	private ActionListener actionListenerChamarHorarioTrabalho() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (horarioTrabalhoFrame == null) {
					try {
						horarioTrabalhoDao = new HorarioTrabalhoDao();
						horarioTrabalhoFrame = new HorarioTrabalhoFrame(horarioTrabalhoDao);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				chamarFrame(horarioTrabalhoFrame);
			}
		};
	}

	private ActionListener actionListenerChamarMarcacaoFeita() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (marcacaoFeitaFrame == null) {
					try {
						marcacaoFeitaDao = new MarcacaoFeitaDao();
						marcacaoFeitaFrame = new MarcacaoFeitaFrame(marcacaoFeitaDao);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				chamarFrame(marcacaoFeitaFrame);
			}
		};
	}

	private ActionListener actionListenerChamarCalculoExtra() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!existeLancamento()) {
						return;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				horarioExtraDao = new HorarioExtraDao();
				SubtracaoEntreHorarios subtracaoEntreHorarios = new SubtracaoEntreHorarios();
				JTable tableResultado = null;
				try {
					tableResultado = subtracaoEntreHorarios.execute(marcacaoFeitaFrame.getJTable(),
							horarioTrabalhoFrame.getJTable());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				if (horarioExtraFrame != null) {
					horarioExtraFrame.dispose();
				}
				try {
					horarioExtraFrame = new ExtratoExtraFrame(horarioExtraDao, tableResultado);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				chamarFrame(horarioExtraFrame);
			}
		};
	}

	private ActionListener actionListenerChamarCalculoAtraso() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!existeLancamento()) {
						return;
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				horarioAtrasoDao = new HorarioAtrasoDao();
				SubtracaoEntreHorarios subtracaoEntreHorarios = new SubtracaoEntreHorarios();
				JTable tableResultado = null;
				try {
					tableResultado = subtracaoEntreHorarios.execute(horarioTrabalhoFrame.getJTable(),
							marcacaoFeitaFrame.getJTable());
				} catch (ParseException e2) {
					e2.printStackTrace();
				}

				if (horarioAtrasoFrame != null) {
					horarioAtrasoFrame.dispose();
				}
				try {
					horarioAtrasoFrame = new ExtratoAtrasoFrame(horarioAtrasoDao, tableResultado);
				} catch (ParseException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				chamarFrame(horarioAtrasoFrame);
			}
		};
	}

	private boolean existeLancamento() throws Exception {
		if (horarioTrabalhoFrame == null || marcacaoFeitaFrame == null) {
			JOptionPane.showMessageDialog(this, "Para realizar os calculos é necessário realizar os lançamentos");
			return false;
		}
		return true;
	}

}
