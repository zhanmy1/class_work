package test1;
//��������ר�Ÿ��������������ӣ����Ź���ר�Ÿ����������ŵ��ӣ�
//1.���ɵĵ��ӻ��߱����Ҹ���������ǳ���ƣ�
//2.ÿ��Ʒ�Ƶ��ӻ�����������һ̨���ҽ�����õ��ӻ����Ҹ���һ��
//3.���㿪��ԭ��
//4.ʹ�ù���������������ԭ��ģʽ��Ͻ������
public class Test {
	public static void main(String[] args) {
		//���Ը���Ҫ��
		//ʵ������������
		Factory fac1 = new HaierFctory();
		Factory fac2 = new HaierFctory();
		//�ù�������������������
		Product pro1 = fac1.getOB();
		Product pro2 = fac1.getOB();
		Product pro3 = fac1.getOB();
		//����һ�������ܷ�������������
		System.out.println(pro1==pro2);
		//�������������ܷ�������������
		System.out.println(pro3==pro2);
		//���Կ�¡�����Ƿ�ɹ����Ƿ�ֻ�ܿ�¡һ��
		Product pro4 = (Product) pro1.clone();
		pro4.show();
		Product pro5 = (Product) pro1.clone();
	}
}
//���幤���ӿ�
interface Factory{
	Product getOB();
}
//��������ʵ�ֹ����ӿ�
class HaierFctory implements Factory{	
	public Product getOB(){
		//���ú������ӵ����췽��
		return Haier.checkAndCreate();
	}
}
//���Ź���ʵ��
class HaixinFactory implements Factory{
	public Product getOB(){
		//���ú��ŵ��ӵ����췽��
		return Haixin.checkAndCreate();
	}
}


//�����Ʒ�ӿ�
abstract class Product implements Cloneable{
	//�����¡�Ķ���
	private Object clone;
	//��������ͬʵ���ķ���
	abstract public void show();
	
	//��¡��������synchronized���εķ�����ͬһʱ��ֻ����һ���̵߳���
	synchronized public Object clone(){
		if(clone==null){
			try {
				//���ø����clone����
				clone = super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return clone;
		}else{
			System.out.println("һ̨����ֻ�ܸ���һ��");
			return null;
		}
	}
}

class Haier extends Product{
	//����һ����̬����
	/*��̬�������ص㣺
	 *		�����е�����Haier��� ����� ��̬���� ��ʵ����ͬһ����
	 *
	 * 		���һ���������ԣ������ڴ����������Ķ����ʱ����ȶ��������е����ԣ�
	 * ��ͨ�ı�����Ϊ���Ե�ʱ��ÿ�δ��������ʱ���������¶������ԣ�����̬
	 * ������Ϊ���Ե�ʱ��ֻ�е�һ�δ��������ʱ�Ż���ж��壬�������еĶ���
	 * ��ʹ��������ԣ���������ʱ����ʵ���õ�ͬһ�����������Ǵ�����������Ķ���
	 * ��������һ�����εı��� i ��ֵ��1����һ�������������� i ����һ��1����ô������
	 * ��������� i ���ڶ���2��
	 * 
	 * 		��Ŀ���һ������ֻ�ܴ���һ�����ӵ�Ҫ�󣬿���ͨ�����þ�̬�������ص㣬���
	 * һ������ģʽ�Ĵ���������Ҫ��
	 * */
	private static Haier haier;
	
	//�����췽��˽�л���ʹ�����಻��ֱ�Ӵ���Haier����ֻ���������checkAndCreate��������
	//private��һ�����η�����������η����εķ���ֻ���ڱ����е���
	/*
	 * ���췽����
	 * 		�������ڴ�������ʱ���õķ����������ص�ʱû�з���ֵ���ҷ�������������ͬ�������
	 * Ϊʲô����new ����ķ�����������ͬ��������private�����˹��췽�������ǾͲ�����
	 * ����������ط�ֱ����new���г�ʼ������
	 * 		��ʱ�����ǲ�д�����������ΪĬ�Ͼ��������������дҲ���Ե��ã�����˵����д�˵Ļ�
	 * ��ʵʵ�ڸ������Ĭ�ϴ��ڵĹ��췽��
	 * */
	private Haier(){}
	
	//���ڴ�������ľ�̬����
	/*
	 * ��̬�������ص㣺
	 * 		����ֱ��ʹ���������е���
	 * 
	 * 		һ�����ǵ���ĳһ����ķ�����ʱ��������Ҫ��ʵ�����������һ������Ȼ��ͨ����
	 * ��ʵ��������ķ���������̬��������ֱ��ͨ��������ֽ��е��ã�������ʵ��������
	 * ��ʽΪ������.������
	 * 
	 * 		ʹ�����ַ������ǾͿ����ڹ��췽��������Ϊ˽��ʱ�������������ط�������ʵ��
	 * */
	public static Product checkAndCreate(){
		/*
		 * 		�����ǵ���ģʽ�Ĺؼ���������������ǽ����涨��ľ�̬�������س�ȥ������������������
		 * �ĵط���ǰ������֪���˾�̬���������ԣ���ô�������ÿ�ε������������ʱ�򶼵���create����
		 * ���д�������ǰ��ľ�̬����ÿ�ζ�����г�ʼ�����൱��ǰ���Ǹ� i �ڲ��ϱ���ֵΪ1�������Ļ���
		 * ����ֻ�ܱ�֤ϵͳ��ֻ��һ����ͬ��Haier���ӻ��ĺ��ӣ������������ÿ�ζ����µģ���������Ҫ����
		 * �жϣ����������ӻ��Ѿ������죨��Ϊnull��û����ʼ���������ǲŵ���create�������е���
		 * */
		if(haier==null){
			haier = create();
			return haier;
		}else{
			return haier;
		}
	}
	
	//���ɶ���ķ���������Ϊ˽�л���ԭ������췽��˽�л�һ��������synchronized����Ҳ��Ϊ��Ӧ�Զ��߳�ͬʱ���õ����
	synchronized private static Haier create(){
		System.out.println("��һ������");
		return new Haier();
	}
	
	public void show(){
		System.out.println("���Ǻ���");
	}
}

class Haixin extends Product{
	private static Haixin haixin;
	
	private Haixin(){}
	
	public static Product checkAndCreate(){
		if(haixin==null){
			haixin = create();
			return haixin;
		}else{
			return haixin;
		}
	}
	
	synchronized private static Haixin create(){
		System.out.println("��һ������");
		return new Haixin();
	}
	
	public void show(){
		System.out.println("���Ǻ���");
	}
}