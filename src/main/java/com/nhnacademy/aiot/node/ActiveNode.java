package com.nhnacademy.aiot.node;

import lombok.extern.slf4j.Slf4j;

/**
 * 노드들이 스레드로 돌아가는걸 관리해주는 추상클래스
 */
@Slf4j
public abstract class ActiveNode implements Runnable {

    protected ActiveNode() {

    }

    /**
     * 노드를 실행합니다
     */
    protected void start() {

        new Thread(this).start();

    }

    protected void preProcess() {
        log.info("노드가 생성되었습니다");
    }

    /**
     * 하위 클래스에서, 노드들의 로직을 구현할 부분입니다.
     */
    protected abstract void process();

    protected void postProcess() {
        log.info("노드가 종료되었습니다");
    }

    @Override
    public void run() {
        preProcess();
        while (Thread.currentThread().isAlive()) {
            process();
        }
        postProcess();

    }

}
