package javacenter.hibernate.example;

public class Main {
    public static void main(String[] args) {
        Box<Number> b = new Box<>();
        
        f1(b);
        
        Number i = new Integer(1);
        Double d = (Double) i;
    }
    
    public static void f1(Box<? extends Number> b) {
        b.f2();
    }
}
