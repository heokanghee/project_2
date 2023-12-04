package com.example.mhb.swing;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.example.mhb.dto.HomeBook;
import com.example.mhb.services.HomeBookService;
import com.example.mhb.services.HomeBookSwingService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

//@SpringBootApplication
@ComponentScan(basePackages = "com.example.mhb")
public class HomeBookSwingApp extends JFrame {
	
	private final HomeBookSwingService service;
	private final HomeBookService hservice;
	
	private JTable table;
	private JTextField txtserialno;
	private JTextField txtday;
	private JTextField txtsection;
	private JTextField txtaccounttitle;
	private JTextField txtremark;
	private JTextField txtrevenue;
	private JTextField txtexpense;
	private JTextField txtmid;
	private JLabel lblNewLabel;
	private JTextField txtStartDate;
	private JLabel lblNewLabel_1;
	private JTextField txtEndDate;
	private JButton btnList;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField txtTotalRevenue;
	private JLabel lblNewLabel_4;
	private JTextField txtTotalExpense;
	private JTextField[] textFields;
	private JComboBox<String> comboBox;
	private JButton btnTonggea;

	@Autowired
	public HomeBookSwingApp(HomeBookSwingService service,HomeBookService hservice) {
		this.service = service;
		this.hservice = hservice;
		createAndShowGUI();
	}
	//배사보이 
	private void createAndShowGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1284, 400);

		JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			
		txtserialno = new JTextField(10);
		txtday= new JTextField(10);
		txtsection= new JTextField(10);
		txtaccounttitle= new JTextField(10);
		txtremark= new JTextField(10);
		txtrevenue= new JTextField(10);
		txtrevenue.setBackground(new Color(128, 255, 128));
		txtexpense= new JTextField(10);
		txtexpense.setBackground(new Color(255, 128, 255));
		//----숫자를 오른쪽 정렬하여 나타내기 
		txtrevenue.setHorizontalAlignment(SwingConstants.RIGHT);
		txtexpense.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtmid= new JTextField(10);
		textFields = new JTextField[] {
				txtserialno,txtday,txtsection,txtaccounttitle,
				txtremark,txtrevenue,txtexpense,txtmid
		};
		
		inputPanel.add(new JLabel("serialno"));
		inputPanel.add(txtserialno);
		//
		inputPanel.add(new JLabel("day"));
		inputPanel.add(txtday);
		//
		inputPanel.add(new JLabel("section"));
		inputPanel.add(txtsection);
		//
		inputPanel.add(new JLabel("accounttitle"));
		inputPanel.add(txtaccounttitle);
		//
		inputPanel.add(new JLabel("remark"));
		inputPanel.add(txtremark);
		//
		inputPanel.add(new JLabel("revenue"));
		inputPanel.add(txtrevenue);
		//
		inputPanel.add(new JLabel("expense"));
		inputPanel.add(txtexpense);
		//
		inputPanel.add(new JLabel("mid"));
		inputPanel.add(txtmid);
		

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(60, 203, 251));
		JButton addButton = new JButton("Add");
		JButton updateButton = new JButton("Update");
		JButton deleteButton = new JButton("Delete");

		buttonPanel.add(addButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);

		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(inputPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		lblNewLabel = new JLabel("기간지정:");
		buttonPanel.add(lblNewLabel);
		
		txtStartDate = new JTextField();
		buttonPanel.add(txtStartDate);
		txtStartDate.setColumns(10);
		
		lblNewLabel_1 = new JLabel("~");
		buttonPanel.add(lblNewLabel_1);
		
		txtEndDate = new JTextField();
		buttonPanel.add(txtEndDate);
		txtEndDate.setColumns(10);
		
		lblNewLabel_2 = new JLabel("계정과목별");
		buttonPanel.add(lblNewLabel_2);
		
		comboBox = new JComboBox(service.getAccountTitles().toArray());
		comboBox.setMaximumRowCount(10);
		buttonPanel.add(comboBox);
		
		
		btnList = new JButton("리스트");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadMembers();
			}
		});
		buttonPanel.add(btnList);
		
		lblNewLabel_3 = new JLabel("수입계:");
		buttonPanel.add(lblNewLabel_3);
		
		txtTotalRevenue = new JTextField();
		txtTotalRevenue.setBackground(new Color(128, 255, 128));
		txtTotalExpense = new JTextField();
		txtTotalExpense.setBackground(new Color(255, 128, 255));
		txtTotalRevenue.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalExpense.setHorizontalAlignment(SwingConstants.RIGHT);

		
		buttonPanel.add(txtTotalRevenue);
		txtTotalRevenue.setColumns(10);
		
		lblNewLabel_4 = new JLabel("지출계");
		buttonPanel.add(lblNewLabel_4);
		
		
		
		buttonPanel.add(txtTotalExpense);
		txtTotalExpense.setColumns(10);
		
		btnTonggea = new JButton("계정과목별집계보고서");
		btnTonggea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String res = service.dispSumByAcc();
				JOptionPane.showMessageDialog(null,res);
			}
		});
		buttonPanel.add(btnTonggea);
		getContentPane().add(mainPanel);
		setVisible(true);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addMember();
			}
		});

		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateMember();
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteMember();
			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					// 선택한 행의 데이터를 입력 필드에 채웁니다.
					for(int i=0;i<textFields.length;i++) {
						String temp = table.getValueAt(selectedRow, i).toString();
						textFields[i].setText(temp);
					}
				}
			}
		});

	}

	private void addMember() {
		
		String serialno = txtserialno.getText();
		String day = txtday.getText();
		String section = txtsection.getText();
		String accounttitle = txtaccounttitle.getText();
		String remark = txtremark.getText();
		String revenue = txtrevenue.getText();
		String expense = txtexpense.getText();
		String mid = txtmid.getText();


		HomeBook member = new HomeBook();
		member.setSerialno(0);
		try {
			//---------------텍스트형태의 날자를 java.sql.Date로 세팅하는 법 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = day;
			java.util.Date utilDate = dateFormat.parse(dateString);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			member.setDay(sqlDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        member.setSection(section);
        member.setAccounttitle(accounttitle);
        member.setRemark(remark);
        member.setRevenue(Integer.parseInt(revenue));
        member.setExpense(Integer.parseInt(expense));
        member.setMid(mid);
        
		hservice.insert(member);
		clearInputFields();
		loadMembers();
	}
//
	private void updateMember() {
		long serialno = Long.parseLong(txtserialno.getText());
		Optional<HomeBook> foundMember = Optional.of(hservice.getBySerialno(serialno));

		if (foundMember.isPresent()) {
			HomeBook member = foundMember.get();
			member.setSerialno(Long.parseLong(txtserialno.getText()));
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = txtday.getText();
				java.util.Date utilDate = dateFormat.parse(dateString);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				member.setDay(sqlDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        member.setSection(txtsection.getText());
	        member.setAccounttitle(txtaccounttitle.getText());
	        member.setRemark(txtremark.getText());
	        member.setRevenue(Integer.parseInt(txtrevenue.getText()));
	        member.setExpense(Integer.parseInt(txtexpense.getText()));
	        member.setMid(txtmid.getText());
			
			hservice.update(member);

			JOptionPane.showMessageDialog(HomeBookSwingApp.this, "HomeBook updated successfully!");
		} else {
			JOptionPane.showMessageDialog(HomeBookSwingApp.this, "HomeBook not found!");
		}

		loadMembers();
	}
//
	private void deleteMember() {
		long sn = Long.parseLong(txtserialno.getText());
		Optional<HomeBook> foundMember = Optional.of(hservice.getBySerialno(sn));

		if (foundMember.isPresent()) {
			hservice.delete(sn);

			JOptionPane.showMessageDialog(HomeBookSwingApp.this, "Member deleted successfully!");

			clearInputFields();
		} else {
			JOptionPane.showMessageDialog(HomeBookSwingApp.this, "Member not found!");
		}

		loadMembers();
	}

	private void clearInputFields() {
		for(int i=0;i<textFields.length;i++) {
			textFields[i].setText(null);
		}
	}
//
	private void loadMembers() {
		
		// 출력할 기간
		String startDate = txtStartDate.getText();
		String endDate = txtEndDate.getText(); 
		if (startDate.isEmpty() || endDate.isEmpty()) {
	       JOptionPane.showMessageDialog(null, "기간을 지정하세요!");
	        return;
	    }
		
		
		// 출력할 계정과목 
		String x = (String)comboBox.getSelectedItem();
		List<HomeBook> members = null;
		double r=0,e=0;//차,대
		if(x.equals("전체")) {
			if (startDate !=null && endDate != null) {
				members = service.getHomeBooksByDateRange(startDate,endDate);
			} else {
				members = service.getAllMembers();
			}
			
		}else {
			if (startDate !=null && endDate != null) {
				members = service.getHomeBooksByDateRange2(x,startDate,endDate);
			} else {
				members = service.getAllMembers2(x);
			}
		}
		r= members.stream().mapToDouble(HomeBook::getRevenue).sum();
		e= members.stream().mapToDouble(HomeBook::getExpense).sum();
		
		//----------------1000만위 콤마 표시하고 오른쪽 정렬하기 위한 조치 
		Locale locale = Locale.US; // 미국 로케일
		NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
		String formattedNumber = numberFormat.format(r);
		txtTotalRevenue.setText(formattedNumber+"");
		formattedNumber = numberFormat.format(e);
		txtTotalExpense.setText(formattedNumber+"");
		//-------------------------------------------------------- 
		
		// JTable에 데이터를 표시하기 위한 모델 생성
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] { 
			"serialno", "day", "section", "accounttitle", 
			"remark","revenue","expense","mid" 
		});

		// 회원 목록을 모델에 추가
		for (HomeBook member : members) {
			model.addRow(new Object[] { 
				member.getSerialno(), 
				member.getDay(), 
				member.getSection(),
				member.getAccounttitle(), 
				member.getRemark(),
				member.getRevenue(),
				member.getExpense(),
				member.getMid()
			});
		}
		// JTable에 모델 설정
		table.setModel(model);
	}

	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ApplicationContext context = SpringApplication.run(HomeBookSwingApp.class, args);

		SwingUtilities.invokeLater(() -> {
			HomeBookSwingApp app = context.getBean(HomeBookSwingApp.class);
		});

	}
}
