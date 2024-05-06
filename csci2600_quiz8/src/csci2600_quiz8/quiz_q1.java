package csci2600_quiz8;

public class quiz_q1 {
	static class A {
		A m(Object a) { System.out.println("A.m(Object)->A"); return new A(); }
		A m(A a) { System.out.println("A.m(A)->A"); return a; }
		B m(C c) { System.out.println("A.m(C)->B"); return c; }
		}
	static class B extends A {
		C m(Integer i) { System.out.println("B.m(Integer)->C"); return new D(); }
		C m(C c) { System.out.println("B.m(C)->C"); return c; }
		B m(B b) { System.out.println("B.m(B)->B"); return b; }
		}
	static class C extends B {
		D m(A a) { System.out.println("C.m(A)->D"); return (D)a; }
		D m(int i) { System.out.println("C.m(int)->D"); return new D(); }
		A m(D d) { System.out.println("C.m(D)->A"); return d; }
	}
	static class D extends C {
		D m(A a) { System.out.println("D.m(A)->D"); return new D(); }
		C m(D d) { System.out.println("D.m(D)->C"); return d; }
	}

    public static void main(String[] args) {
        A a1 = new A();
        A a2 = new B();
        A a3 = new C();
        A a4 = new D();
        B b1 = new B();
        B b2 = new C();
        B b3 = new D();
        Object b4 = new B();
        C c1 = new C();
        C c2 = new D();
        D d1 = new D();
        
     // Print statements for each method call
        System.out.println("(a) b1.m(\"RPI\"): ");
        b1.m("RPI");

        System.out.println("(b) a1.m(5): ");
        a1.m(5);

        System.out.println("(c) a3.m(d1): ");
        a3.m(d1);

        System.out.println("(d) c1.m(37): ");
        c1.m(37);

        System.out.println("(e) b2.m(42): ");
        b2.m(42);

        System.out.println("(f) a4.m(d1): ");
        a4.m(d1);

        System.out.println("(g) b3.m(b3): ");
        b3.m(b3);

        System.out.println("(h) b2.m(a2): ");
        b2.m(a2);

        System.out.println("(i) a3.m(new D()): ");
        a3.m(new D());

        System.out.println("(j) c2.m(c1): ");
        c2.m(c1);

        System.out.println("(k) a2.m(a2): ");
        a2.m(a2);

        System.out.println("(l) b4.m(b1): ");
        ((B) b4).m(b1);

        System.out.println("(m) a2 = a3.m(b2): ");
        a2 = a3.m(b2);

        System.out.println("(n) c2 = b2.m(a4): ");
        c2 = (C) b2.m(a4);

        System.out.println("(o) c2.m(b4): ");
        c2.m(b4);
        
    }
}
