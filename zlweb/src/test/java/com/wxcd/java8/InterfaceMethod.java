package com.wxcd.java8;

/**
 * Created by wenjusun on 9/8/16.
 */
public interface InterfaceMethod {

    default public String bow(){
        return "Bow";
    }
    static public void Laugh(){
        System.out.println("Haha");
    }


}

interface Animal{
    default void talk(){
        System.out.println("An animal");
    }
}

interface Member{
    default void talk(){
        System.out.println("A member");
    }
}


class MyAbcImplementation implements InterfaceMethod{

    public void checkDefault(){
        InterfaceMethod.super.bow();
        InterfaceMethod.Laugh();
    }
}

class InterfaceStudio implements Animal,Member{

    @Override
    public void talk() {
        Animal.super.talk();
        Member.super.talk();

    }


}