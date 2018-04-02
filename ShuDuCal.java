package shuducal;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ShuDuCal extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static ShuDuCal s;
	private JMenuBar mb=new JMenuBar();
	private JMenu mm=new JMenu("文件");
	private JMenuItem mi1=new JMenuItem("导入");
	private JMenuItem mi2=new JMenuItem("关闭");
	private JTextField txt[][]=new JTextField[9][9];
	private JLabel line[]=new JLabel[4];
	private JLabel l=new JLabel();
	private JButton Calculate = new JButton();
	private JButton clear = new JButton();
	private void txtInit(){
		l.setText("数独计算器");
        l.setBounds(200,10,450,30);
        this.getContentPane().add(l);
		int dx=40,dy=40;
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				txt[i][j]=new JTextField();
				txt[i][j].setBounds(65+i*dx, 50+j*dy, 30, 30);
				this.getContentPane().add(txt[i][j]);
			}
		}
	}
	private void lineInit(){
		for(int i=0;i<4;i++) {
			line[i]=new JLabel();
			line[i].setForeground(Color.BLACK);
		}
		String hh="———————————————————————————";
		line[0].setText(hh);
		line[0].setBounds(64, 163, 400, 5);
		line[3].setText(hh);
		line[3].setBounds(64, 283, 400, 5);
		String ll = "<html><body>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|</body></html>";
		line[1].setText(ll);
		line[1].setBounds(177, 22, 10, 400);
		line[2].setText(ll);
		line[2].setBounds(298, 22, 10, 400);
		for(int i=0;i<4;i++)
			this.getContentPane().add(line[i]);
	}
	void clear(){
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				txt[i][j].setText("");
			}
		}
	}
	private void ButtonInit(){
		Calculate.setText("计算");
		Calculate.setBounds(150, 450, 80, 40);
		Calculate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int pro[][]=new int[10][10];int flg=0;
				for(int i=0;i<9;i++) {
					for(int j=0;j<9;j++) {
						String s=txt[i][j].getText();
						if(s.equals("")) pro[i][j]=0;
						else if(s.length()==1 && s.matches("^[1-9]+$")) pro[i][j]=Integer.parseInt(s);
						else {
							//弹出警告
							JOptionPane.showMessageDialog(null, "输入有误，只能输入1~9的数字", "警告", JOptionPane.ERROR_MESSAGE); 
							flg=1;
							System.out.println("输入有误");
							clear();
							break;
						}
					}
				}
				if(flg==1) return;
				new Cal(pro);
				if(Cal.ans!=null)
				for(int i=0;i<9;i++) {
					for(int j=0;j<9;j++) {
						txt[i][j].setText(""+Cal.ans[i][j]);
					}
				}
			}
		});
		this.getContentPane().add(Calculate);
		clear.setText("清空");
		clear.setBounds(250, 450, 80, 40);
		clear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clear();
			}
			
		});
		this.getContentPane().add(clear);
	}
	private void menuInit(){
		mm.add(mi1);
        mm.add(mi2);
        mi2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
        });
        mb.add(mm);
        this.setJMenuBar(mb);
	}
	public ShuDuCal(){
		this.setTitle("数独计算器");
		this.setLayout(null);
        menuInit();
        txtInit();
        lineInit();
        ButtonInit();
        this.setBounds(330,250,500,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String args[]){
		s=new ShuDuCal();
	}
}

class Cal{
	static int c[][]=new int[10][10];
	static int pan[][]=new int[10][10];
	static int h[][]=new int[10][10],l[][]=new int[10][10];
	static int ans[][];
	static int size=0;
	static xy sp[]=new xy[81];
	static void delete(int x,int y,int val){
		int x0=x/3,y0=y/3;//System.out.println(x0+" "+y0+" "+(x0*3+y0)+" "+val);
		c[x0*3+y0][val]++;
		h[x][val]++;
		l[y][val]++;
	}
	static boolean Delete(int x,int y,int val){
		int x0=x/3,y0=y/3;//System.out.println(x0+" "+y0+" "+(x0*3+y0)+" "+val);
		if(++c[x0*3+y0][val]>1) return false;
		if(++h[x][val]>1) 	return false;
		if(++l[y][val]>1)  return false;
		return true;
	}
	static void undelete(int x,int y,int val){
		int x0=x/3,y0=y/3;
		c[x0*3+y0][val]=0;
		h[x][val]=0;l[y][val]=0;
	}
	static void print(int a[][]){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	static boolean init(){
		for(int i=0;i<81;i++) sp[i]=new xy();
		for(int i=0;i<10;i++) for(int j=0;j<10;j++){
				c[i][j]=h[i][j]=l[i][j]=0;
		}
		size=0;
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(pan[i][j]!=0) {
					if(!Delete(i,j,pan[i][j])){
						System.out.println("输入有误！！！");
						JOptionPane.showMessageDialog(null, "输入不合法！！", "警告", JOptionPane.ERROR_MESSAGE); 
						ShuDuCal.s.clear();
						return false;
					}
				}
				else{
					sp[size].x=i;
					sp[size].y=j;
					size++; 
				}
			}
		}
		return true;
	}
	static boolean dfs(int t){
		if(t==size){
			ans=pan;return true;
		}
		int x=sp[t].x,y=sp[t].y;int nx=x/3,ny=y/3;
		for(int i=1;i<=9;i++) if(c[nx*3+ny][i]==0 && h[x][i]==0 && l[y][i]==0){
			pan[x][y]=i;delete(x,y,i);
			if(dfs(t+1)) return true;
			pan[x][y]=0;undelete(x,y,i);
		}
		return false;
	}
	static void solve(){
		if(!dfs(0)) System.out.println("!!!");
	}
	public Cal(int[][] pro){
		pan=null;
		pan=pro;
		if(init()) solve();
	}
}

class xy{
	int x,y;
	xy(){}
}

