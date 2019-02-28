import java.util.Stack;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Queue<E> extends Stack<E>{
    public final int dump=10;
    private  Stack<E> stk;
    public Queue( ){
        this.stk = new Stack<E>();
    }

    public boolean add(E e) throws IllegalStateException, ClassCastException, 
    NullPointerException, IllegalArgumentException{  
        if (e == null) {
            throw new NullPointerException("Error: the element is null!\n");
        }

        if (super.elementCount >= 10 && !(stk.empty())) {
            throw new IllegalArgumentException("Error: there is no space to add\n");
        } else {
            if (super.elementCount < 10) {
                super.push(e);
            } else {
                while(!(super.empty()))
    			{
    				E t = super.peek();
    				super.removeElementAt(super.size()-1);
    				stk.push(t);
    			}
    			super.push(e);
            }
        }
        return true;
    }

    public boolean offer(E e) throws ClassCastException, NullPointerException, 
    IllegalArgumentException{  
        if (e == null) {
            throw new NullPointerException("Error: the element is null!\n");
        }

        if (super.elementCount >= 10 && !(stk.empty())) {
            return false;
        } else {
            if (super.elementCount < 10) {
                super.push(e);
            } else {
                while(!(super.empty()))
    			{
    				E t = super.peek();
    				super.removeElementAt(super.size()-1);
    				stk.push(t);
    			}
    			super.push(e);
            }
        }
        return true;
    }

    public E remove( ) throws NoSuchElementException {  
        if (super.empty() && stk.empty()) {
            throw new NoSuchElementException("Error: the queue is empty!\n");
        }

        if (stk.empty()) {
            while(!(super.empty()))
			{
				E t = super.peek();
				super.removeElementAt(super.size()-1);
				stk.push(t);
			}
        }
        return stk.pop();
    }

    public E poll( ) {  
        if (super.empty() && stk.empty()) {
            return null;
        }

        if (stk.empty()) {
            while(!(super.empty()))
			{
				E t = super.peek();
				super.removeElementAt(super.size()-1);
				stk.push(t);
			}
        }
        return stk.pop();
    }

    public E peek ( ) {  
        if (super.empty() && stk.empty()) {
            return null;
        }

        if (stk.empty()) {
            while(!(super.empty()))
			{
				E t = super.peek();
				super.removeElementAt(super.size()-1);
				stk.push(t);
			}
        }
        return stk.peek();
    }

    public E element( ) throws NoSuchElementException {  
        if (super.empty() && stk.empty()) {
            throw new NoSuchElementException("Error: the queue is empty!\n");
        }

        if (stk.empty()) {
            while(!(super.empty()))
			{
				E t = super.peek();
				super.removeElementAt(super.size()-1);
				stk.push(t);
			}
        }
        return stk.peek();
    }
    	/* 测试用函数，打印队列 */
	public void printQueue() throws NoSuchElementException
	{
		if(super.empty() && stk.empty())
		{
			throw new NoSuchElementException("The Queue is empty!");
		}
		int i;
		if(stk.empty())
		{
			for(i=0;i<super.size();i++)
				System.out.print(super.elementAt(i)+" ");
		}
		else
		{
			for(i=stk.size();i>0;i--)
				System.out.print(stk.elementAt(i-1)+" ");
			for(i=0;i<super.size();i++)
				System.out.print(super.elementAt(i)+" ");
		}
		System.out.print("\n");
	}
	/* 主方法 */
	public static void main(String[] args) 
	{
		Queue<Integer> q = new Queue<Integer>();
		int op;
		int e;
		Scanner in = new Scanner(System.in);
		System.out.println("-----------泛型栈模拟泛型队列-----------");
		System.out.println("1.add");
		System.out.println("2.offer");
		System.out.println("3.remove");
		System.out.println("4.poll");
		System.out.println("5.peek");
		System.out.println("6.element");
		System.out.println("7.print");
		System.out.println("0.exit");
		System.out.print("Please input your option:");
		op = in.nextInt();
		while (op != 0)
		{
			try
			{
				switch(op)
				{
					case 1:
						System.out.print("add:");
						e = in.nextInt();
						q.add(e);
						break;
					case 2:
						System.out.print("offer:");
						e = in.nextInt();
						q.offer(e);
						break;
					case 3:
						e = q.remove();
						System.out.println("remove:" + e);
						break;
					case 4:
						e = q.poll();
						System.out.println("poll:" + e);
						break;
					case 5:
						e = q.peek();
						System.out.println("peek:" + e);
						break;
					case 6:
						e = q.element();
						System.out.println("element:" + e);
						break;
					case 7:
						q.printQueue();
						break;
					default:
						System.out.println("Invalid Input!");
				}
			}
			catch(IllegalStateException ise)
			{
				System.out.println(ise.toString());
			}
			catch(ClassCastException cce)
			{
				System.out.println(cce.toString());
			}
			catch(NullPointerException npe)
			{
				System.out.println(npe.toString());
			}
			catch(IllegalArgumentException iae)
			{
				System.out.println(iae.toString());
			}
			catch(NoSuchElementException nsee)
			{
				System.out.println(nsee.toString());
			}
			finally
			{
				System.out.print("Please input your option:");
				op = in.nextInt();
			}
		}
	}
}
