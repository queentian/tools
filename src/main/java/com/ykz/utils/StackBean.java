package com.ykz.utils;

import java.util.EmptyStackException;
import java.util.LinkedList;

public class StackBean {
    private LinkedList<Object> li=new LinkedList<Object>();

    //1构造方法
    public StackBean(){

    }

    //2出栈
    public Object pop(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return li.removeFirst();
    }

    //3进栈
    public void push(Object obj){ //注意o不要0的区别，不要写成0了
        li.addFirst(obj);
    }

    //4清空
    public void clear() {
        li.clear();
    }
    //5判断是否为空
    public boolean isEmpty(){
        return li.isEmpty();
    }

    //6 将对象转换成字符串
    public String toString(){
        return li.toString();
    }

    //7返回栈口元素
    public Object peek(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return li.peekFirst();   //注意，这里与队列的区别

    }

    public static void main(String[] args) {
        StackBean stack=new StackBean();
        //进栈
        stack.push("a");
        stack.push("b");

        stack.push("c");
        System.out.println(stack.peek());
        //出栈
        System.out.println(stack.pop());  //输出 c

        //返回栈口元素
        System.out.println(stack.peek());  //输出  b


    }
}