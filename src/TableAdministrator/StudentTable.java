package TableAdministrator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ButtonFrame.StudentAdd;
import ButtonFrame.StudentSearch;
import Control.StudentsControl;
import Model.StudentsModel;
import Operation.DormOperation;
import Operation.RegisterOperation;
import Operation.StudentsOperation;
import Operation.TableFitColumn;

public class StudentTable {
	public Object data[][] = null;

	Object columnNames[] = { "����", "�Ա�", "��������", "��ַ", "��ϵ��ʽ", "ѧ��", "ѧԺ", "רҵ", "�༶", "�����", "����" };
	public static JPanel jp = new JPanel();
	public static JTable jt = new JTable();
	public static DefaultTableModel model;

	JButton Add_Button;
	JButton Update_Button;
	JButton Delete_Button;
	JButton Search_Button;

	public StudentTable() {
		Add_Button = new JButton("����");
		Update_Button = new JButton("ˢ��");
		Delete_Button = new JButton("ɾ��");
		Search_Button = new JButton("��ѯ");

		JScrollPane js = new JScrollPane();
		jt = new JTable(model);
		jt = new JTable(querystudent());
		jt.setVisible(true);
		TableFitColumn.fitTableColumns(jt);// ����Ӧ�п�
		// jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.getTableHeader().setReorderingAllowed(false);
		jt.getTableHeader().setResizingAllowed(false);
		jt.setRowHeight(25);
		jt.setEnabled(true);
		js.setViewportView(jt);
		jp.add(js);
		Add_Button.setVisible(true);
		Add_Button.addActionListener(AddButton);
		jp.add(Add_Button);

		Update_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jt.setModel(querystudent());
				TableFitColumn.fitTableColumns(jt);// ����Ӧ�п�
				
			}
		});
		jp.add(Update_Button);

		Delete_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Delete_Button) {

					String[] options = new String[] { "��", "��" };
					// ����ǵĻ�
					int row = jt.getSelectedRow();
					if (row == -1) {
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ�����У�", "��ʾ��", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					int n = JOptionPane.showOptionDialog(null, "ȷ��ɾ��������", "��ʾ", JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					if (n == JOptionPane.YES_OPTION) {
						if (row != -1) {
							StudentsOperation student = new StudentsOperation();
							DormOperation dorm = new DormOperation();
							RegisterOperation registerOperation = new RegisterOperation();
							try {
								student.UpdateStatusStudents((int) jt.getValueAt(jt.getSelectedRow(), 5));
								dorm.UpDateRemoveOneDorm(jt.getValueAt(jt.getSelectedRow(), 9));
								registerOperation.Delete((int) jt.getValueAt(jt.getSelectedRow(), 5));
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							model.removeRow(row);
						}

					}

				}
			}
		});
		jp.add(Delete_Button);

		Search_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Search_Button) {
					new StudentSearch();
				}

			}
		});
		jp.add(Search_Button);

		jp.setVisible(false);
		jp.setBounds(220, 50, 720, 465);
		jt.setPreferredScrollableViewportSize(new Dimension(700, 400));
	}

	public JPanel getpanel() {
		return jp;
	}

	public Object getjtable() {
		return jt.getValueAt(jt.getSelectedRow(), 5);
	}

	public DefaultTableModel querystudent() {
		StudentsControl studentcontrol = new StudentsControl();
		List<StudentsModel> result;
		try {
			result = studentcontrol.all();
			data = new Object[result.size()][11];
			int j = 0;
			for (int i = 0; i < result.size(); i++) {
				data[i][j] = result.get(i).getName();
				j++;
				data[i][j] = result.get(i).getSex();
				j++;
				data[i][j] = StudentsOperation.getfromunix((String) result.get(i).getBirthday());
				;
				j++;
				data[i][j] = result.get(i).getAddress();
				j++;
				data[i][j] = result.get(i).getContact();
				j++;
				data[i][j] = result.get(i).getStudent_id();
				j++;
				data[i][j] = result.get(i).getCollege();
				j++;
				data[i][j] = result.get(i).getMajor();
				j++;
				data[i][j] = result.get(i).getClasses();
				j++;
				data[i][j] = result.get(i).getDorm_id();
				j++;
				data[i][j] = result.get(i).getBed_id();
				j = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model = new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	ActionListener AddButton = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			new StudentAdd();

		}
	};

}