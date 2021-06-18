package designpatterns.创建型模式.建造者模式;

public class Test {

    public static void main(String[] args) {
        //接收建造对象，建造一个具体的对象
        PersonDirector pd = new PersonDirector();
        
        //将建造对象的实例 ManBuilder ，传递给建造方法.建造出实体对象。
        Person person = pd.constructPerson(new ManBuilder());
        
        System.out.println(person.getBody());
        System.out.println(person.getFoot());
        System.out.println(person.getHead());
    }
}