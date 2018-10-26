package test1;
//海尔工厂专门负责生产海尔电视，海信工厂专门负责生产海信电视，
//1.生成的电视机具备自我复制能力（浅复制）
//2.每种品牌电视机仅允许生成一台，且仅允许该电视机自我复制一份
//3.满足开闭原则
//4.使用工厂方法，单例和原型模式结合进行设计
public class Test {
	public static void main(String[] args) {
		//测试各种要求
		//实例化海尔工厂
		Factory fac1 = new HaierFctory();
		Factory fac2 = new HaierFctory();
		//用工厂生成两个海尔电视
		Product pro1 = fac1.getOB();
		Product pro2 = fac1.getOB();
		Product pro3 = fac1.getOB();
		//测试一个工厂能否生成两个电视
		System.out.println(pro1==pro2);
		//测试两个工厂能否生成两个电视
		System.out.println(pro3==pro2);
		//测试克隆功能是否成功和是否只能克隆一次
		Product pro4 = (Product) pro1.clone();
		pro4.show();
		Product pro5 = (Product) pro1.clone();
	}
}
//定义工厂接口
interface Factory{
	Product getOB();
}
//海尔工厂实现工厂接口
class HaierFctory implements Factory{	
	public Product getOB(){
		//调用海尔电视的制造方法
		return Haier.checkAndCreate();
	}
}
//海信工厂实现
class HaixinFactory implements Factory{
	public Product getOB(){
		//调用海信电视的制造方法
		return Haixin.checkAndCreate();
	}
}


//定义产品接口
abstract class Product implements Cloneable{
	//定义克隆的对象
	private Object clone;
	//用于区别不同实例的方法
	abstract public void show();
	
	//克隆方法，用synchronized修饰的方法在同一时刻只能有一个线程调用
	synchronized public Object clone(){
		if(clone==null){
			try {
				//调用父类的clone方法
				clone = super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return clone;
		}else{
			System.out.println("一台电视只能复制一次");
			return null;
		}
	}
}

class Haier extends Product{
	//定义一个静态变量
	/*静态变量的特点：
	 *		程序中的所有Haier类的 对象的 静态变量 其实都是同一个。
	 *
	 * 		如果一个类有属性，我们在创建的这个类的对象的时候会先定义它所有的属性，
	 * 普通的变量作为属性的时候，每次创建类对象时，都会重新定义属性，而静态
	 * 变量作为属性的时候，只有第一次创建类对象时才会进行定义，后面所有的对象
	 * 在使用这个属性（变量）的时候，其实是用的同一个，比如我们创建了两个类的对象，
	 * 类里面有一个整形的变量 i ，值是1，在一个对象里面对这个 i 加了一个1，那么这两个
	 * 对象里面的 i 现在都是2了
	 * 
	 * 		题目里的一个工厂只能创建一个电视的要求，可以通过利用静态变量的特点，设计
	 * 一个单例模式的代码来满足要求
	 * */
	private static Haier haier;
	
	//将构造方法私有化，使其它类不能直接创建Haier对象，只能用下面的checkAndCreate方法创造
	//private是一种修饰符，被这个修饰符修饰的方法只能在本类中调用
	/*
	 * 构造方法：
	 * 		是我们在创建对象时调用的方法，它的特点时没有返回值并且方法名跟类名相同，这就是
	 * 为什么我们new 后面的方法跟类名相同，下面用private修饰了构造方法后，我们就不能在
	 * 类外的其它地方直接用new进行初始化对象
	 * 		有时候我们不写这个方法是因为默认就有这个方法，不写也可以调用，可以说我们写了的话
	 * 其实实在覆盖这个默认存在的构造方法
	 * */
	private Haier(){}
	
	//用于创建对象的静态方法
	/*
	 * 静态方法的特点：
	 * 		可以直接使用类名进行调用
	 * 
	 * 		一般我们调用某一个类的方法的时候，我们需要先实例化这个类获得一个对象，然后通过这
	 * 个实例调用类的方法，而静态方法可以直接通过类的名字进行调用，不用先实例化对象，
	 * 格式为：类名.方法名
	 * 
	 * 		使用这种方法我们就可以在构造方法被设置为私有时，还能再其它地方获得类的实例
	 * */
	public static Product checkAndCreate(){
		/*
		 * 		这里是单例模式的关键，我们这个方法是将上面定义的静态变量返回出去，给外面调用这个方法
		 * 的地方，前面我们知道了静态变量的特性，那么如果我们每次调用这个方法的时候都调用create方法
		 * 进行创建对象，前面的静态变量每次都会进行初始化，相当于前面那个 i 在不断被赋值为1，这样的话，
		 * 我们只能保证系统中只有一个相同的Haier电视机的盒子，但是里面电视每次都是新的，所以我们要进行
		 * 判断，如果这个电视机已经被制造（不为null，没被初始化），我们才调用create方法进行调用
		 * */
		if(haier==null){
			haier = create();
			return haier;
		}else{
			return haier;
		}
	}
	
	//生成对象的方法，设置为私有化，原因跟构造方法私有化一样，加上synchronized修饰也是为了应对多线程同时调用的情况
	synchronized private static Haier create(){
		System.out.println("第一次生成");
		return new Haier();
	}
	
	public void show(){
		System.out.println("我是海尔");
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
		System.out.println("第一次生成");
		return new Haixin();
	}
	
	public void show(){
		System.out.println("我是海信");
	}
}