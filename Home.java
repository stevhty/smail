package smail;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class Home extends JFrame implements ActionListener{
    JTabbedPane jtp = new JTabbedPane();
    JButton button_logout,button_ubahpin,button_ubahpass,searching;
    JTextField searchField;
    String emails;
    String name;
    Home(String mail,String nama){
    	emails=mail;
    	name=nama;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JPanel panel2 = new JPanel(new GridLayout(1,3,5,5));
        JPanel panel3 = new JPanel(new GridLayout(1,2,5,5));
        setTitle("Welcome, "+name);
        setSize(800,600);
        button_logout = new JButton("Logout");
        button_ubahpin = new JButton("Ubah PIN");
        button_ubahpass = new JButton("Ubah Password");
        searching = new JButton("Search");
        jtp.addTab("Inbox" , new Panel1(emails));
        jtp.addTab("OutBox" , new Panel2(emails));
        jtp.addTab("Compose" , new Panel3(emails));
        searchField = new JTextField();
        panel2.add(searching);
        panel2.add(button_ubahpass);
        panel2.add(button_ubahpin);
        panel2.add(button_logout);
        panel3.add(searchField,BorderLayout.WEST);
        panel3.add(panel2,BorderLayout.EAST);
        add(panel3);
        add(jtp);
        panel3.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        //add(panel1);
        setVisible(true);
        button_ubahpass.addActionListener(this);
        searching.addActionListener(this);
        button_ubahpin.addActionListener(this);
        button_logout.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try{
            if(arg0.getSource()==button_logout){
                new MainProgram();
                this.dispose();
            }
            else if(arg0.getSource()==button_ubahpass){
            	new Upass(emails,name);
                this.dispose();
            }
            else if(arg0.getSource()==button_ubahpin){
            	new UPIN(emails,name);
                this.dispose();
            }
            else if(arg0.getSource()==searching){
            	new Panelsearch(emails,searchField.getText());
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
	}
}

class Panel1 extends JPanel implements ActionListener{
    JButton button_reply,button_delete,read;
    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    String mail;
    String dumb1,dumb2,dumb3,dumb4,dumb5;
	public Panel1(String emails){
    	mail=emails;
		try{
			smail_func a = new smail_func();
			data = a.selectEmailInbox(mail);
		}
		catch(Exception e){
			e.printStackTrace();
		}
    	JPanel panel11 = new JPanel(new GridLayout(2,1,5,5));
    	JPanel panel111 = new JPanel(new GridLayout(1,3,5,5));
        button_reply = new JButton("Reply");
        button_delete = new JButton("Delete");
        read = new JButton("Read");
        Vector<Object> colHeads = new Vector<Object>();
        colHeads.add("Email Pengirim");
        colHeads.add("Subject");
        colHeads.add("Isi");
        colHeads.add("Status");
        colHeads.add("Waktu Kirim");
        JTable table = new JTable(data, colHeads);
        JScrollPane jsp = new JScrollPane(table);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                dumb1=((table.getValueAt(table.getSelectedRow(), 0).toString()));
                dumb2=((table.getValueAt(table.getSelectedRow(), 1).toString()));
                dumb3=((table.getValueAt(table.getSelectedRow(), 2).toString()));
                dumb4=((table.getValueAt(table.getSelectedRow(), 3).toString()));
                dumb5=((table.getValueAt(table.getSelectedRow(), 4).toString()));
            }
        });
        panel11.add(jsp);
        panel111.add(button_reply);
        panel111.add(button_delete);
        panel111.add(read);
        panel11.add(panel111);
        add(panel11);
        button_reply.addActionListener(this);
        button_delete.addActionListener(this);
        read.addActionListener(this);
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try{
			smail_func func = new smail_func();
			if(arg0.getSource()==button_reply){
				new Panel4(dumb2,dumb1,dumb3,mail);
			}
			else if(arg0.getSource()==button_delete){
				func.hapus(mail, dumb2, dumb3, dumb1);
				revalidate();
				repaint();
			}
			else if(arg0.getSource()==read){
				func.reads(mail,dumb3,dumb2,dumb1);
				new Panel5(dumb2,dumb1,dumb3,mail);
				revalidate();
				repaint();
	        }
		}
    	catch (Exception e){
    		e.printStackTrace();
    	}
	}
}

class Panel2 extends JPanel implements ActionListener{
    JButton button_delete,open;
    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    String mail;
    String dumb1,dumb2,dumb3,dumb4,dumb5;
	public Panel2(String emails){
		mail=emails;
//        JPanel table_panel = new JPanel();
//        table_panel.setLayout(new BoxLayout(table_panel,BoxLayout.LINE_AXIS));
//        JPanel button_panel = new JPanel();
//        button_panel.setLayout(new BoxLayout(button_panel,BoxLayout.LINE_AXIS));
//        button_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		try{
			smail_func a = new smail_func();
			data = a.selectEmailSent(mail);
		}
		catch(Exception e){
			e.printStackTrace();
		}
    	JPanel panel12 = new JPanel(new GridLayout(2,1,5,5));
    	JPanel panel123 = new JPanel(new GridLayout(1,2,5,5));
    	button_delete = new JButton("Delete");
    	open = new JButton("Read");
        Vector<Object> colHeads = new Vector<Object>();
        colHeads.add("Email Penerima");
        colHeads.add("Subject");
        colHeads.add("Isi");
        colHeads.add("Status");
        colHeads.add("Waktu Kirim");
        JTable table = new JTable(data, colHeads);
        JScrollPane jsp = new JScrollPane(table);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                dumb1=((table.getValueAt(table.getSelectedRow(), 0).toString()));
                dumb2=((table.getValueAt(table.getSelectedRow(), 1).toString()));
                dumb3=((table.getValueAt(table.getSelectedRow(), 2).toString()));
                dumb4=((table.getValueAt(table.getSelectedRow(), 3).toString()));
                dumb5=((table.getValueAt(table.getSelectedRow(), 4).toString()));
            }
        });
        panel12.add(jsp);
        panel123.add(button_delete);
        panel123.add(open);
        panel12.add(panel123);
        add(panel12);
//        button_panel.add(Box.createHorizontalGlue());
//        button_panel.add(button_delete);
//        button_panel.add(Box.createRigidArea(new Dimension(10,0)));
        button_delete.addActionListener(this);
        open.addActionListener(this);
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try{
			smail_func func = new smail_func();
			if(arg0.getSource()==button_delete){
				//public void hapus(String ID_Penerima,String Subjects,String isi,String ID_Pengirim) throws SQLException{
				//new Panel4();
				func.hapus(dumb1, dumb2, dumb3, mail);
				revalidate();
				repaint();
	        }
			else{
				new Panel5(dumb2,dumb1,dumb3,mail);
			}
		}
    	catch (Exception e){
    		e.printStackTrace();
    	}
	}
}

class Panel3 extends JPanel implements ActionListener{
    JButton button_ok;
	JTextField textField_to,textField_subject;
	JTextArea textArea_isi;
	Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    String mail;
    public Panel3(String emails){
    	mail=emails;
    	setSize(800,600);
    	JPanel panel13 = new JPanel(new GridLayout(3,10,5,5));
        JLabel label_to = new JLabel("To :");
        JLabel label_subject = new JLabel("Subject ");
        button_ok = new JButton("Send");
        textField_to = new JTextField();
        textField_subject = new JTextField();
        textArea_isi = new JTextArea();
        JPanel msg_panel = new JPanel(new GridLayout(2,1));
        msg_panel.add(label_to,BorderLayout.WEST);
        msg_panel.add(textField_to,BorderLayout.EAST);
        msg_panel.add(label_subject,BorderLayout.WEST);
        msg_panel.add(textField_subject,BorderLayout.EAST);
        panel13.add(msg_panel);
        panel13.add(textArea_isi);
        panel13.add(button_ok);
        add(panel13);
        button_ok.addActionListener(this);
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			smail_func func = new smail_func();
			if(arg0.getSource()==button_ok){
				func.kirimEmail(mail, textField_to.getText(), textField_subject.getText(), textArea_isi.getText());
				JOptionPane.showMessageDialog(null, "Email sent successfully");
				revalidate();
				textField_to.setText("");
				textField_subject.setText("");
				textArea_isi.setText("");
	        }
		}
    	catch (Exception e){
    		e.printStackTrace();
    	}
		// TODO Auto-generated method stub
		
	}
}
class Panel4 extends JFrame implements ActionListener{
        JButton button_ok,cancel;
    	JTextField textField_to,textField_subject;
    	JTextArea textArea_isi;
    	String mail;
        public Panel4(String subject,String to,String isi,String emails){//String emails,String subject,String to){
        	mail=emails;
        	setSize(800,600);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        	JPanel panel14 = new JPanel(new GridLayout(3,10,5,5));
        	JPanel panel141 = new JPanel(new GridLayout(1,2,5,5));
            JLabel label_to = new JLabel("To :");
            JLabel label_subject = new JLabel("Subject ");
            button_ok = new JButton("Send");
            cancel = new JButton("Cancel");
            textField_to = new JTextField();
            textField_subject = new JTextField();
            textArea_isi = new JTextArea();
            textField_to.setText(to);
            textField_subject.setText(subject);
            textArea_isi.setText(isi);
            JPanel msg_panel = new JPanel(new GridLayout(2,1,5,5));
            msg_panel.add(label_to,BorderLayout.WEST);
            msg_panel.add(textField_to,BorderLayout.EAST);
            msg_panel.add(label_subject,BorderLayout.WEST);
            msg_panel.add(textField_subject,BorderLayout.EAST);
            panel14.add(msg_panel);
            panel14.add(textArea_isi);
            panel141.add(button_ok,BorderLayout.EAST);
            panel141.add(cancel,BorderLayout.WEST);
            panel14.add(panel141);
            add(panel14);
            setVisible(true);
            cancel.addActionListener(this);
            button_ok.addActionListener(this);
        }
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try{
				smail_func func = new smail_func();
				if(arg0.getSource()==button_ok){
					func.kirimEmail(mail, textField_to.getText(), textField_subject.getText(), textArea_isi.getText());
					JOptionPane.showMessageDialog(null, "Email sent successfully");
					revalidate();
					textField_to.setText("");
					textField_subject.setText("");
					textArea_isi.setText("");
					repaint();
		        }
				else{
					dispose();
					setVisible(false);
				}
			}
	    	catch (Exception e){
	    		e.printStackTrace();
	    	}
			// TODO Auto-generated method stub
			
		}
}
class Panel5 extends JFrame implements ActionListener{
    JButton button_ok;
	JTextField textField_to,textField_subject;
	JTextArea textArea_isi;
	String mail;
    public Panel5(String subject,String to,String isi,String from){
    	mail=from;
    	setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    	JPanel panel14 = new JPanel(new GridLayout(4,10,5,5));
        JLabel label_to = new JLabel("From :");
        JLabel label_subject = new JLabel("Subject ");
        button_ok = new JButton("Close");
        textField_to = new JTextField();
        textField_subject = new JTextField();
        textArea_isi = new JTextArea();
        textField_to.setText(to);
        textField_subject.setText(subject);
        textArea_isi.setText(isi);
        JPanel msg_panel = new JPanel(new GridLayout(2,1,5,5));
        msg_panel.add(label_to,BorderLayout.WEST);
        msg_panel.add(textField_to,BorderLayout.EAST);
        msg_panel.add(label_subject,BorderLayout.WEST);
        msg_panel.add(textField_subject,BorderLayout.EAST);
        panel14.add(msg_panel);
        panel14.add(textArea_isi);
        panel14.add(button_ok);
        add(panel14);
        setVisible(true);
        button_ok.addActionListener(this);
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try{
			smail_func func = new smail_func();
			if(arg0.getSource()==button_ok){
				revalidate();
				repaint();
				dispose();
			}
		}
    	catch (Exception e){
    		e.printStackTrace();
    	}
	}
}

class Panelsearch extends JFrame implements ActionListener{
    JButton button_delete,open;
    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    String mail;
    String dumb1,dumb2,dumb3,dumb4,dumb5;
	public Panelsearch(String emails,String searchess){
		mail=emails;
//        JPanel table_panel = new JPanel();
//        table_panel.setLayout(new BoxLayout(table_panel,BoxLayout.LINE_AXIS));
//        JPanel button_panel = new JPanel();
//        button_panel.setLayout(new BoxLayout(button_panel,BoxLayout.LINE_AXIS));
//        button_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		try{
			smail_func a = new smail_func();
			data = a.searchEmail(searchess,mail);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		setSize(800,600);
        JPanel panel12 = new JPanel(new GridLayout(2,1,5,5));
    	JPanel panel123 = new JPanel(new GridLayout(1,2,5,5));
    	button_delete = new JButton("Delete");
    	open = new JButton("Read");
        Vector<Object> colHeads = new Vector<Object>();
        colHeads.add("Email Penerima");
        colHeads.add("Email Pengirim");
        colHeads.add("Subject");
        colHeads.add("Isi");
        colHeads.add("Status");
        colHeads.add("Waktu Kirim");
        JTable table = new JTable(data, colHeads);
        JScrollPane jsp = new JScrollPane(table);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                dumb1=((table.getValueAt(table.getSelectedRow(), 0).toString()));
                dumb2=((table.getValueAt(table.getSelectedRow(), 1).toString()));
                dumb3=((table.getValueAt(table.getSelectedRow(), 2).toString()));
                dumb4=((table.getValueAt(table.getSelectedRow(), 3).toString()));
                dumb5=((table.getValueAt(table.getSelectedRow(), 4).toString()));
            }
        });
        panel12.add(jsp);
        panel123.add(open);
        panel12.add(panel123);
        add(panel12);
        setVisible(true);
//        button_panel.add(Box.createHorizontalGlue());
//        button_panel.add(button_delete);
//        button_panel.add(Box.createRigidArea(new Dimension(10,0)));
        open.addActionListener(this);
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try{
			new Panel5(dumb2,dumb1,dumb3,mail);
		}
    	catch (Exception e){
    		e.printStackTrace();
    	}
	}
}

