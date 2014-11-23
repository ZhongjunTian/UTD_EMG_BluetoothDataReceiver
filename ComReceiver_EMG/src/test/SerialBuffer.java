 /*
      *   SerialBuffer
SerialBuffer�Ǳ������������Ĵ��ڻ������������������û�������д�����ݺʹӸû������ж�ȡ��������Ҫ�ĺ�����public synchronized String GetMsg(int Length) 
�������Ӵ���(������)�ж�ȡָ�����ȵ�һ���ַ���������Lengthָ���������ַ����ĳ��ȡ� 
public synchronized void PutChar(int c) 
�����������ڻ�������д��һ���ַ�������c ����Ҫд����ַ��� 
����������д�����ݻ����Ǵӻ�������ȡ���ݵ�ʱ�򣬱��뱣֤���ݵ�ͬ�������GetMsg��PutChar������������Ϊsynchronized���ھ���ʵ���в�ȡ��ʩʵ�ֵ����ݵ�ͬ����

      */
package test;

public class SerialBuffer {
	 public String Content = "";
     private String CurrentMsg, TempContent;
     private boolean available = false;
     private int LengthNeeded = 1;
         /**
          *
          * This function returns a string with a certain length from the incomin
          * messages.
          *
          * @param Length The length of the string to be returned.
          *
          */
    
     public synchronized String GetMsg(int Length)
     {
         LengthNeeded = Length;
         notifyAll();
 
         if (LengthNeeded > Content.length())
         {
             available = false;
             while (available == false)
             {
                 try
                 {
                     wait();
                 } catch (InterruptedException e) { }
             }
         }
        
         CurrentMsg = Content.substring(0, LengthNeeded);
         TempContent = Content.substring(LengthNeeded);
         Content = TempContent;
         LengthNeeded = 1;
         notifyAll();
         return CurrentMsg;
     }
         /**
          *
          * This function stores a character captured from the serial port to the
          * buffer area.
          *
          * @param t The char value of the character to be stored.
          *
          */
     public synchronized void PutChar(int c)
     {
         Character d = new Character((char) c);
         Content = Content.concat(d.toString());
         if (LengthNeeded < Content.length())
         {
             available = true;
         }
         notifyAll();
     }
}