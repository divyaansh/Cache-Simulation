/*import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class Frames {
	public static void main(String args[]) {
		Frame1 first = new Frame1();
		//Frame2 second = new Frame2();
	}
}

class ButtonListener1 implements ActionListener {
	public void actionPerformed(ActionEvent ae)
	{
		JButton b= (JButton)ae.getSource();
		String str = ae.getActionCommand();
		b.setForeground(Color.red);
		if(str.equals("Submit")) {
			//
		}	
	}
}

class Frame1 extends JFrame {
	Frame1() {
		super("Algorithm Predictor");
		setSize(500,500);  
		
		JPanel fin = new JPanel(new GridLayout(0,1));
		JPanel alg = new JPanel(new GridLayout(1,0)); 
		JPanel asc = new JPanel(new GridLayout(1,0)); 
		JPanel lvl = new JPanel(new GridLayout(1,0)); 
		
		JLabel algLabel = new JLabel("Algorithm:");
		JButton alg1 = new JButton("LRU"); 
		JButton alg2 = new JButton("LFU"); 
		JButton alg3 = new JButton("RRIP"); 
		alg.add(algLabel);
		alg.add(alg1);
		alg.add(alg2);
		alg.add(alg3);
		
		JLabel ascLabel = new JLabel("Associativity:");
		JButton asc1 = new JButton("Direct"); 
		JButton asc2 = new JButton("Partially Assoc"); 
		JButton asc3 = new JButton("Fully Assoc"); 
		asc.add(ascLabel);
		asc.add(asc1);
		asc.add(asc2);
		asc.add(asc3);
		
		JLabel lvlLabel = new JLabel("No. of Levels:");
		JButton lvl1 = new JButton("1"); 
		JButton lvl2 = new JButton("2"); 
		JButton lvl3 = new JButton("3");
		lvl.add(lvlLabel);
		lvl.add(lvl1);
		lvl.add(lvl2);
		lvl.add(lvl3);
		
		JButton subm = new JButton("Submit");
		
		fin.add(alg);
		fin.add(asc);
		fin.add(lvl);
		fin.add(subm);
		
		add(fin);
		
		alg1.addActionListener(new ButtonListener());
		alg2.addActionListener(new ButtonListener());
		alg3.addActionListener(new ButtonListener());
		asc1.addActionListener(new ButtonListener());
		asc2.addActionListener(new ButtonListener());
		asc3.addActionListener(new ButtonListener());
		lvl1.addActionListener(new ButtonListener());
		lvl2.addActionListener(new ButtonListener());
		lvl3.addActionListener(new ButtonListener());
		subm.addActionListener(new ButtonListener());
		
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

class Frame2 extends JFrame {
	Frame2() {
		super("Algorithm Predictor");
		setSize(500,500);  
		
		JPanel p = new JPanel(null);
		
		JTextField entry = new JTextField();
		entry.setLocation(50,50);
		entry.setSize(50,350);
		p.add(entry);
		
		JTextField cache = new JTextField();
		cache.setLocation(150,50);
		cache.setSize(200,350);
		p.add(cache);
		
		JTextField hitmiss = new JTextField();
		hitmiss.setLocation(400,50);
		hitmiss.setSize(50,350);
		p.add(hitmiss);
		
		JButton simulate = new JButton("Simulate");
		simulate.setLocation(200,410);
		simulate.setSize(100,50);
		p.add(simulate);
		
		add(p);
		
		simulate.addActionListener(new ButtonListener());
		
		/*JTextField entry = new JTextField();
		entry.setLocation(50,50);
		entry.setSize(350,50);
		add(entry);
		
		/*JPanel fin = new JPanel(new GridLayout(0,1));
		JPanel alg = new JPanel(new GridLayout(1,0)); 
		JPanel asc = new JPanel(new GridLayout(1,0)); 
		JPanel lvl = new JPanel(new GridLayout(1,0)); 
		
		JLabel algLabel = new JLabel("Algorithm:");
		JButton alg1 = new JButton("LRU"); 
		JButton alg2 = new JButton("LFU"); 
		JButton alg3 = new JButton("RRIP"); 
		alg.add(algLabel);
		alg.add(alg1);
		alg.add(alg2);
		alg.add(alg3);
		
		JLabel ascLabel = new JLabel("Associativity:");
		JButton asc1 = new JButton("Direct"); 
		JButton asc2 = new JButton("Partially Assoc"); 
		JButton asc3 = new JButton("Fully Assoc"); 
		asc.add(ascLabel);
		asc.add(asc1);
		asc.add(asc2);
		asc.add(asc3);
		
		JLabel lvlLabel = new JLabel("No. of Levels:");
		JButton lvl1 = new JButton("1"); 
		JButton lvl2 = new JButton("2"); 
		JButton lvl3 = new JButton("3");
		lvl.add(lvlLabel);
		lvl.add(lvl1);
		lvl.add(lvl2);
		lvl.add(lvl3);
		
		JButton subm = new JButton("Submit");
		
		fin.add(alg);
		fin.add(asc);
		fin.add(lvl);
		fin.add(subm);
		
		add(fin);
		
		alg1.addActionListener(new ButtonListener());
		alg2.addActionListener(new ButtonListener());
		alg3.addActionListener(new ButtonListener());
		asc1.addActionListener(new ButtonListener());
		asc2.addActionListener(new ButtonListener());
		asc3.addActionListener(new ButtonListener());
		lvl1.addActionListener(new ButtonListener());
		lvl2.addActionListener(new ButtonListener());
		lvl3.addActionListener(new ButtonListener());
		subm.addActionListener(new ButtonListener());
		*
		
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
*/