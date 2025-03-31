package com.example.locktest.java_language;

class Counter {
    private int count = 0;

    public synchronized void increment() {  // synchronized 없음
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}

public class RaceConditionExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        // 10개의 스레드가 각자 1000번씩 증가 (총 10,000번 증가 기대)
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        // 모든 스레드가 종료될 때까지 대기
        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }

        // 결과 출력 (기대값: 10000, 하지만 실제 값은 작을 가능성이 큼)
        System.out.println("Final count: " + counter.getCount());
    }
}

