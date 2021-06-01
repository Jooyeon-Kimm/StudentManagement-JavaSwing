package first;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;

class Person {
	String name, address, phone;
	String id1, id2;

	// 주소, 번호 세터
	public void setAddress(String address) {this.address = address;}
	public void setPhone(String phone) {this.phone = phone;}
}

public class Main extends JFrame {
	// 필드
	Vector<Person> v = new Vector<Person>();
	JPanel panel1, panel2, panel3, panel4;
	JPanel nameP, idP, phoneP, addressP;
	JPanel searchP, arrowsP, resultP;
	JPanel seeAllP, printP, orderP;
	JTextField nameT, idT1, idT2, phoneT, addressT, searchT1, searchT2;
	JTextArea printT, resultT;
	JButton leftB, rightB;
	JButton searchB, seeAllB;
	JButton addB, editB, deleteB, clearB, saveB, loadB, exitB;
	JLabel seeAllL, idL;

	// 생성자
	public Main() {
//		// 종료버튼 활성화
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 종료버튼 비활성화 (종료버튼이 있으므로?) (왜 종료버튼을 2개 만든 것인지?)
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("사용자 관리");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		/*** 큰 틀 : 패널 (1 (3,4), 2) ***/
		// 좌우 패널 나누고 붙이기 (1: 왼쪽, 2: 오른쪽)
		panel1 = new JPanel();
		panel2 = new JPanel();
		c.add(panel1, BorderLayout.WEST);
		c.add(panel2, BorderLayout.CENTER);

		// 상하 패널 나누고 붙이기 (3: 위쪽, 4: 아래쪽)
		panel1.setLayout(new BorderLayout());
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel1.add(panel3, BorderLayout.NORTH);
		panel1.add(panel4, BorderLayout.CENTER);

		/*** 패널 3 ***/
		// 패널 3: (개인정보, 이름, 주민번호, 전화번호, 주소) 패널 부착
		panel3.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), "개인정보"));
		nameP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		idP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		phoneP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		addressP = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JLabel nameL = new JLabel("이름:");
		nameT = new JTextField(20);
		nameP.add(nameL);
		nameP.add(nameT);

		// 주민번호 패널
		idL = new JLabel("주민번호: ");
		idT1 = new JTextField(10);
		idT2 = new JTextField(9);
		idP.add(idL);
		idP.add(idT1);
		idP.add(idT2);

		// 주민등록번호 6자로 제한
		idT1.setName("idT1");
		idT2.setName("idT2");
		idT1.addKeyListener(new TextLimitC());
		idT2.addKeyListener(new TextLimitC());

		// 전화번호 패널
		JLabel phoneL = new JLabel("전화번호:");
		phoneT = new JTextField(20);
		phoneP.add(phoneL);
		phoneP.add(phoneT);

		// 주소 패널
		JLabel addressL = new JLabel("주소:");
		addressT = new JTextField(20);
		addressP.add(addressL);
		addressP.add(addressT);

		panel3.add(nameP);
		panel3.add(idP);
		panel3.add(phoneP);
		panel3.add(addressP);
		panel3.setLayout(new GridLayout(4, 1));

		/*** 패널 4 ***/
		// 패널 4: (검색, 결과, 화살표) 패널 부착
		panel4.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), "정보검색"));
		searchP = new JPanel();
		resultP = new JPanel();
		arrowsP = new JPanel();

		panel4.setLayout(new BorderLayout());
		panel4.add(searchP, BorderLayout.NORTH);
		panel4.add(resultP, BorderLayout.CENTER);
		panel4.add(arrowsP, BorderLayout.SOUTH);

		// 패널 4 내부: 각 패널 세부 컴포턴트
		// 검색 패널
		JLabel searchL = new JLabel("검색:");
		searchT1 = new JTextField(10);
		searchT2 = new JTextField(10);
		searchB = new JButton("검색");
		searchP.add(searchL);
		searchP.add(searchT1);
		searchP.add(searchT2);
		searchP.add(searchB);
		searchP.setLayout(new FlowLayout());

		// 주민번호 글자수 제한
		searchT1.setName("idT1");
		searchT2.setName("idT2");
		searchT1.addKeyListener(new TextLimitC());
		searchT2.addKeyListener(new TextLimitC());

		// 결과 패널
		resultT = new JTextArea(10, 30);
		resultP.add(resultT);
		resultP.add(new JScrollPane(resultT));

		// 화살표 패널
		leftB = new JButton("<<");
		rightB = new JButton(">>");
		arrowsP.add(leftB);
		arrowsP.add(rightB);

		// 검색 버튼 액션 리스너 부착
		searchB.addActionListener(new SearchC());

		// 화살표 버튼 액션 리스너 부착

		/*** 패널 2 ***/
		// 패널 2: (전체보기 패널, 결과출력 패널, 명령 버튼 패널) 생성 + 부착
		panel2.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), "개인정보확인"));
		seeAllP = new JPanel();
		printP = new JPanel();
		orderP = new JPanel();

		panel2.add(seeAllP);
		panel2.add(printP);
		panel2.add(orderP);

		// 전체보기 패널
		seeAllB = new JButton("전체보기");
		seeAllP.add(seeAllB);
		seeAllL = new JLabel("<== 이것을 누르면 전체보기가 됩니다.");
		seeAllP.add(seeAllL);
		seeAllP.setLayout(new FlowLayout());
		seeAllB.addActionListener(new SeeAllC());

		// 결과출력 패널
		printT = new JTextArea(18, 50);
		printP.add(printT);
		printP.add(new JScrollPane(printT));

		// 명령 버튼 패널
		addB = new JButton("등록");
		editB = new JButton("수정");
		deleteB = new JButton("삭제");
		clearB = new JButton("Clear");
		saveB = new JButton("저장");
		loadB = new JButton("로드");
		exitB = new JButton("종료");
		orderP.setLayout(new FlowLayout());

		orderP.add(addB);
		orderP.add(editB);
		orderP.add(deleteB);
		orderP.add(clearB);
		orderP.add(saveB);
		orderP.add(loadB);
		orderP.add(exitB);

		// 버튼에 액션리스너 붙이기
		addB.addActionListener(new AddC());
		editB.addActionListener(new EditC());
		deleteB.addActionListener(new DeleteC());
		clearB.addActionListener(new ClearC());
		saveB.addActionListener(new SaveC());
		loadB.addActionListener(new LoadC());
		exitB.addActionListener(new ExitC());

		// 사이즈, 보이기, 크기 고정
		setSize(900, 500);
		setVisible(true);
		setResizable(false);

	} // 생성자 끝
	
	
	
	/******************************************************************/
	// 메인 메소드
	public static void main(String[] args) {
		new Main();
	}
	/******************************************************************/
	
	
	// 검색 버튼 클래스
	class SearchC implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Person p = new Person();
			p.id1 = searchT1.getText();
			p.id2 = searchT2.getText();

			for (int i = 0; i < v.size(); i++) {
				boolean b1 = p.id1.equals(v.get(i).id1);
				boolean b2 = p.id2.equals(v.get(i).id2);

				if (b1 && b2 == true) { // 같은 사람이 있으면 (주민등록번호가 같다)
					int index = i;
					p = v.get(index); // p 에 해당 벡터 원소를 집어넣는다.
				}
			}

			if (v.contains(p) == true) { // 찾는 정보가 있으면
				String s1, s2, s3 = "", s4, s5, s6;
				int year, month, date;
				s1 = "이름: " + p.name + "\n";
				s2 = "주민번호: " + p.id1 + "-" + p.id2 + "\n";

				if (p.phone.length() == 11) { // 개인 전화번호인 경우
					s3 += "전화번호: " + p.phone.substring(0, 3) + "-" + p.phone.substring(3, 7) + "-"+ p.phone.substring(7, 11) + "\n";
				} else if (p.phone.length() == 10) { // 집 전화번호인 경우
					s3 += "전화번호: " + p.phone.substring(0, 3) + "-" + p.phone.substring(3, 6) + "-"+ p.phone.substring(6, 10) + "\n";
				}else {
					s3 = "전화번호: " + p.phone + "\n";
				}

				s4 = "주소: " + p.address + "\n";
				year = Integer.parseInt(p.id1.substring(0, 2)); // 두 자리씩 끊기, 마지막 문자는 포함하지 X
				month = date = 0;

				// 출생 연도 조정
				if (p.id1.charAt(0) != '0') { // 00년생이 아니면
					year += 1900;	// 1900 더해준다.
				} else {
					year += 2000;
				}

				// 출생 월 조정
				if (p.id1.charAt(2) == '0') { // 10월 ~ 12월 생이 아니면 (앞자리가 0이면)
					month = Integer.parseInt(p.id1.substring(3, 4)); // 한글자만 잘라오기
				} else {
					month = Integer.parseInt(p.id1.substring(2, 4));
				}

				// 출생 날짜 조정
				if (p.id1.charAt(4) == '0') { // 십의 자리 수가 0이면
					date = Integer.parseInt(p.id1.substring(5, 6));
				} else { // 아니면
					date = Integer.parseInt(p.id1.substring(4, 6));
				}

				s5 = "생년월일: " + year + "년 " + month + "월 " + date + "일";
				s6 = s1 + s2 + s3 + s4 + s5;

				// 화면에 출력
				resultT.setText(s6);

			} else { // 찾는 정보가 없으면
				JOptionPane.showMessageDialog(null, "동일한 인적 사항을 가진 사람이 없습니다.");
			}

		}
	}

	/******************************************************************/

	// 로드 버튼 클래스
	class LoadC implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String s1, s2, s3;
			JFrame jf = new JFrame();

			// printT에 글자 출력
			s1 = "파일 읽기 완료!\n";
			s2 = "의 파일로부터 읽기를 완료하였습니다.\n";
			s3 = "총 데이터 개수 : " + v.size() + "개";
			printT.setText(s1 + s2 + s3);

			// 기다렸다가
			try {
				Thread.sleep(1000); // 1초 대기
				printT.setText(""); // 화면 지우기
				Thread.sleep(1000); // 1초 대기
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			// 1. FileDialog를 열어 불러올 파일 지정
			FileDialog dialog = new FileDialog(jf, s3, FileDialog.LOAD);
			dialog.setDirectory(".");
			dialog.setVisible(true);

			// 2. FileDialog가 비정상 종료되었을때
			if (dialog.getFile() == null)
				return;

			// 3. 불러올 파일의 경로명 저장
			String dfName = dialog.getDirectory() + dialog.getFile();

			// 4. 파일 열기, TextArea에 뿌려주기
			try {
				BufferedReader reader = new BufferedReader(new FileReader(dfName));
				printT.setText("");
				String line;
				while ((line = reader.readLine()) != null) { // 읽을 문자열이 없을때까지 반복
					printT.append(line + "\n"); // 한줄씩 TextArea에 추가
				}
				reader.close();
				setTitle(dialog.getFile());
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "열기 오류");

			}
		}
	}

	/******************************************************************/

	private static File fileOpenDlg() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("C:/test"));
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) { // 승인하면 선택한 파일을 가져오고 리턴한다.
			File f = chooser.getSelectedFile();
			return f;
		}
		return null;
	}

	/******************************************************************/
	// 저장 버튼 클래스
	class SaveC implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 파일에 적을 내용 s5
			String s1, s2, s3, s4, s5;
			s1 = "전체 인원의 개인정보 입니다.\n";
			s2 = "이름\t주민번호\t\t전화번호\t\t주소\n";
			s3 = "============================================================================\n";
			s4 = "";
			for (int i = 0; i < v.size(); i++) {
				Person p = v.get(i);
				s4 += p.name + "\t" + p.id1 + "-" + p.id2 + "\t" + p.phone + "\t\t\t" + p.address + "\n";
			}
			s5 = s1 + s2 + s3 + s4;

			// 파일 이름
			String fileName = "project01.txt";
			try {
				// 파일 객체 생성
				File f = new File(fileName);

				// false 지정 시 파일의 기존 내용에 이어서 작성하지 않음
				FileWriter fw = new FileWriter(f, false);
				fw.write(s5);
				fw.close();

				String s6 = "저장 완료!\n";
				String s7 = f.getAbsolutePath() + "의 이름으로 저장되었습니다.";
				System.out.println(s7);
				printT.setText(s6 + s7);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/******************************************************************/

// Clear 버튼 클래스
	class ClearC implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("Clear")) { // 좌측 입력 정보 지우기
				nameT.setText("");
				idT1.setText("");
				idT2.setText("");
				phoneT.setText("");
				addressT.setText("");
			}
		}
	}

	/******************************************************************/

// 등록 버튼 클래스
	class AddC implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("등록")) {
				Person p = new Person();
				p.name = nameT.getText();
				p.address = addressT.getText();
				p.phone = phoneT.getText();
				p.id1 = idT1.getText();
				p.id2 = idT2.getText();

				boolean b1, b2, b3, b4, b5, b6;
				b1 = (p.name.length() == 0); // 이름 입력 안하면
				b2 = (p.address.length() == 0); // 주소 입력 안하면
				b3 = (p.phone.length() == 0); // 전화번호 입력 안하면
				b4 = (p.id1.length() == 6); // 주민등록번호 앞자리가 6자리인지 검사
				b5 = (p.id2.length() == 7); // 주민등록번호 뒷자리가 7자리인지 검사
				b6 = ((b4 && b5) != true); // 주민등록번호 자리수가 안맞으면

				// 이미 같은 이름과 주민번호를 가진 사람이 있다면 추가하지 않음
				for (int i = 0; i < v.size(); i++) {	
					boolean b7 = p.id1.equals(v.get(i).id1);
					boolean b8 = p.id2.equals(v.get(i).id2);

					if (b7 && b8 == true) { // 같은 사람이 있으면
						JOptionPane.showMessageDialog(null, "이미 등록된 사람입니다.");
						return;
					}
				}

				// 이름을 입력하지 않으면
				if (b1) {
					JOptionPane.showMessageDialog(null, "이름을 입력해 주세요.");
				} else {

					// 주소/전화번호를 입력하지 않으면
					if (b2) {
						p.address = "미입력";
					}
					if (b3) {
						p.phone = "미입력";
					}

					// 주민등록번호 자리수 검사
					if (b6) { // 주민등록번호 자리수가 맞지 않으면
						JOptionPane.showMessageDialog(null, "주민등록번호를 잘못 입력하였습니다.");
					}
				}

				// 사람 정보를 등록한다.
				try {
					if (!b1 && !b6) { // 이름이 한 글자라도 입력되어 있고 주민등록번호 자리수가 맞다면
						v.add(p);
						String str = "저장이 잘 되었습니다.\n";
						printT.setText(str + "현재 " + v.size() + "명의 데이터가 존재합니다.");
					}
				} catch (Exception ee) {
					ee.getStackTrace();
					b.addActionListener(new ErrC());
				}
			}
		}
	}

	/******************************************************************/
// 전체보기 클래스
	class SeeAllC implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			String s1, s2, s3, s4, s5, s6;
			if (b.getText().equals("전체보기")) {
				s1 = "전체 인원의 개인정보 입니다.\n";
				s2 = "이름\t주민번호\t\t전화번호\t\t주소\n";
				s3 = "============================================================================\n";
				s4 = "";
				s5 = s3 + "총 " + v.size() + "명";
				for (int i = 0; i < v.size(); i++) {
					Person p = v.get(i);
					s4 += p.name + "\t" + p.id1 + "-" + p.id2 + "\t" + p.phone + "\t\t" + p.address + "\n";
				}
				s6 = s1 + s2 + s3 + s4 + s5;
				if (v.size() == 0) {
					printT.setText("데이터가 없습니다.");
				} else {
					printT.setText(s6);
				}

			}
		}

	}

	/******************************************************************/
// 수정 버튼 클래스
	class EditC implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new EditWindowC();
		}
	}

	/******************************************************************/
// 삭제 버튼 클래스
	class DeleteC implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new DeleteWindowC();
		}
	}

	/******************************************************************/
	@SuppressWarnings("serial")
// 새로운 삭제 창
	class DeleteWindowC extends JFrame {
		public DeleteWindowC() {
			WindowC w = new WindowC();
			Container c = w.getContentPane();
			w.setTitle("삭제");
			c.setLayout(new GridLayout(3, 1));

			JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));

			JButton deleteB = new JButton("삭제");
			JButton cancelB = new JButton("취소");

			p.add(deleteB);
			p.add(cancelB);

			// 삭제 버튼에 액션 리스너 부착
			deleteB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 입력한 이름과 주민번호를 가진 Person 객체 생성
					Person p = new Person();
					p.name = w.getNameT().getText();
					p.id1 = w.getIdT1().getText();
					p.id2 = w.getIdT2().getText();

					for (int i = 0; i < v.size(); i++) {
						boolean b1 = p.name.equals(v.get(i).name);
						boolean b2 = p.id1.equals(v.get(i).id1);
						boolean b3 = p.id2.equals(v.get(i).id2);

						if (b1 && b2 && b3 == true) { // 같은 사람이 있으면
							int index = i;
							p = v.get(index); // p 에 해당 벡터 원소를 집어넣는다.
						}
					}

					if (v.contains(p)) { // 찾는 정보가 있으면
						// 화면 출력
						String s1, s2, s3, s4, s5, s6, s7;
						s1 = "다음의 사용자를 데이터 저장소에서 삭제하였습니다.\n";
						s2 = "정보 !!!\n";
						s3 = "이름 : " + p.name + "\n";
						s4 = "주민번호 : " + p.id1 + "-" + p.id2 + "\n";
						s5 = "주소 : " + p.address + "\n";
						s6 = "현재 남은 데이터 개수 : " + (v.size() - 1);
						s7 = s1 + s2 + s3 + s4 + s5 + s6;
						printT.setText(s7);

						// 찾은 벡터의 요소를 삭제한다.
						v.remove(p);
						return;

					} else { // 찾는 정보가 없으면
						JOptionPane.showMessageDialog(null, "동일한 인적 사항을 가진 사람이 없습니다.");
					}
				}

			});

			// 취소 버튼에 액션 리스너 부착
			cancelB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					w.dispose();
				}
			});

			c.add(p);
		}
	}

	/******************************************************************/
	// 새로운 수정 창
	@SuppressWarnings("serial")
	class EditWindowC extends JFrame {
		WindowC w = new WindowC();
		Person p = new Person();

		public EditWindowC() {
			w.setTitle("수정");
			Container c = w.getContentPane();
			c.setLayout(new GridLayout(3, 1));
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

			JButton editB = new JButton("수정");
			JButton cancelB = new JButton("취소");
			panel.add(editB);
			panel.add(cancelB);
			c.add(panel);

			// 수정 버튼 액션 리스너
			editB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 입력한 이름과 주민번호를 가진 Person 객체 생성
					p.name = w.getNameT().getText();
					p.id1 = w.getIdT1().getText();
					p.id2 = w.getIdT2().getText();

					// 사람 검색하기
					for (int i = 0; i < v.size(); i++) {
						boolean b1, b2, b3;
						b1 = p.name.equals(v.get(i).name);
						b2 = p.id1.equals(v.get(i).id1);
						b3 = p.id2.equals(v.get(i).id2);

						if (b1 && b2 && b3 == true) { // 같은 사람이 있으면
							int index = i;
							p = v.get(index); // p 에 해당 벡터 원소를 집어넣는다.
							printT.setText("해당 사용자를 찾았습니다.. 수정하세요.");
						}
					}

					// 입력한 이름과 주민번호 값을 가진 인적사항이 있는지 확인
					if (v.contains(p) == true) { // 입력한 정보와 같은 사람이 있다면
						// 입력한 정보를 라벨로 만들기
						Container c = w.getContentPane();
						CardLayout card = new CardLayout(); // 카드 레이아웃 생성
						c.setLayout(card);

						JPanel newPanel = new JPanel();
						JPanel panel1 = new JPanel(new GridLayout(1, 1));
						JPanel panel2 = new JPanel(new GridLayout(1, 1));
						JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
						JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
						JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

						JLabel nameT = new JLabel("                이름:   " + p.name);
						JLabel idNumT = new JLabel("        주민번호:   " + p.id1 + "-" + p.id2);
						JLabel phoneT = new JLabel("전화번호: ");
						JTextField phoneTF = new JTextField(20);
						JLabel addressT = new JLabel("주소: ");
						JTextField addressTF = new JTextField(20);
						JButton editB = new JButton("수정");
						JButton cancelB = new JButton("취소");

						panel1.add(nameT);
						panel2.add(idNumT);
						panel3.add(phoneT);
						panel3.add(phoneTF);
						panel4.add(addressT);
						panel4.add(addressTF);
						panel5.add(editB);
						panel5.add(cancelB);

						newPanel.setLayout(new GridLayout(5, 1));
						newPanel.add(panel1);
						newPanel.add(panel2);
						newPanel.add(panel3);
						newPanel.add(panel4);
						newPanel.add(panel5);

						// 수정 버튼에 액션리스너 부착
						editB.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								p.phone = phoneTF.getText();
								p.address = addressTF.getText();
								JOptionPane.showMessageDialog(null, "수정되었습니다.");
								w.dispose();
							}
						});

						// 취소버튼에 액션리스너 부착. 창 닫기
						cancelB.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								w.dispose();
							}
						});

						c.removeAll(); // 이전 컴포넌트 모두 삭제
						c.add(newPanel);
						w.setSize(310, 210);
						w.setResizable(false);
						card.previous(c); // 클릭하면 다음 카드 레이아웃으로

					} else { // 찾는 사람이 없다면 경고창
						JOptionPane.showMessageDialog(null, "동일한 인적 사항을 가진 사람이 없습니다.");
					}
				}
			});

			// 취소버튼 액션 리스너
			cancelB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 작은 창 닫기
					// System.exit() 을 사용하면 큰 창이 종료된다. (전부 종료)
					// System.exit(0) : 정상 종료, System.exit(1) : 비정상 종료
					w.dispose(); // 이걸 몰라서 헤맸다.
				}
			});

		}
	}

	/******************************************************************/
	@SuppressWarnings("serial")
	// 새로운 창
	class WindowC extends JFrame {
		JLabel nameL = new JLabel("이름:");
		JLabel idL = new JLabel("주민번호:");
		JTextField nameT = new JTextField(20);
		JTextField idT1 = new JTextField(8);
		JTextField idT2 = new JTextField(9);

		// 이름/주민번호 게터
		public JTextField getNameT() {
			return nameT;
		}

		public JTextField getIdT1() {
			return idT1;
		}

		public JTextField getIdT2() {
			return idT2;
		}

		// 생성자
		public WindowC() {
			// 주의, 여기서 setDefaultCloseOperation() 정의를 하지 말아야 한다
			// 정의하게 되면 새 창을 닫으면 모든 창과 프로그램이 동시에 꺼진다
			// 출처: https://arintvsecond.tistory.com/14

			JPanel editP = new JPanel();
			setContentPane(editP);
			JPanel nameP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JPanel idP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			editP.setLayout(new GridLayout(2, 1));

			editP.add(nameP);
			editP.add(idP);

			editP.setLayout(new GridLayout(2, 1));
			nameP.add(nameL);
			nameP.add(nameT);
			idP.add(idL);
			idP.add(idT1);
			idP.add(idT2);

			// 주민번호 글자수 제한
			idT1.setName("idT1");
			idT2.setName("idT2");
			idT1.addKeyListener(new TextLimitC());
			idT2.addKeyListener(new TextLimitC());

			setSize(260, 180);
			setLocation(300, 300);
			setResizable(true);
			setVisible(true);
		}

	}

	/******************************************************************/

// 에러 처리
	class ErrC implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, e.getActionCommand() + "하지 못했습니다!");
		}
	}

	/******************************************************************/
// 종료 버튼 클래스
	class ExitC implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("종료")) {
				System.exit(0);
			}
		}
	}

	/******************************************************************/

// 주민등록번호 글자수 제한 클래스
	class TextLimitC extends KeyAdapter {
		public void keyTyped(KeyEvent ke) {
			JTextField src = (JTextField) ke.getSource();
			if (src.getName().equals("idT1")) {
				if (src.getText().length() >= 6) {
					ke.consume(); // 글자수 초과하면 지우기
				}
			}

			else if (src.getName().equals("idT2")) {
				if (src.getText().length() >= 7) {
					ke.consume(); // 글자수 초과하면 지우기
				}
			}

			else {
				System.out.println("잘못누르셨습니다.");
			}
		}
	}

}
// public Class 끝
