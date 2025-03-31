package com.example.locktest.java_language;

class SharedResource {
    private int count = 0;

    public void increment() {
        count++;
        System.out.println(Thread.currentThread().getName() + " - count: " + count);
    }
}

public class SynchronizedExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // 멀티스레드를 사용할 때 해당 스레드에서 실행할 작업을 설정하기 위해서 Runnable 인터페이스 구현
        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    resource.increment();
                }
            }
        };

        // 두 개의 스레드가 같은 공유 자원에 접근
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        t1.start();
        t2.start();
    }
}
