package first;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;


class Member{
	public String name, age;
}

public class Add extends JFrame {
	public Add() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		JTextField nameT = new JTextField(20);
		JTextField ageT = new JTextField(20);
		
		JLabel nameL = new JLabel("Name: ");
		JLabel ageL = new JLabel("Age: ");
		
		JTextArea box = new JTextArea(10,10);



		JButton b = new JButton("Button");

		Vector v = new Vector();
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				if(b.getText().equals("Button")) {
					Member m = new Member();
					m.name = nameT.getText();
					m.age = ageT.getText();
					v.add(m);
					for(int i=0; i<v.size(); i++) {
						Member vv = (Member)v.get(i);
						String str = i+1 +"\tName: " + vv.name + "\tAge: " + vv.age +"\n";
						box.setText(str);
						System.out.printf("%-2d\t%-7s%-10s\t%-7s%-10s\n", i , "Name: " , vv.name , "\t\tAge: " , vv.age);
					}
					System.out.println("___________________________________________________________");
				}
				
			}
			
		});


	
		p1.add(nameL);
		p1.add(nameT);
		p1.add(ageL);
		p1.add(ageT);
		p1.setLayout(new GridLayout(2,2));
		
		p2.add(b);
		
		p3.add(box);
		c.setLayout(new BorderLayout());
		c.add(p1, "North");
		c.add(p2, "Center");
		c.add(p3, "South");

		
		
		setVisible(true);
		setSize(310,330);
	}
	
	
	public static void main(String[] args) {
		new Add();
	}

}
