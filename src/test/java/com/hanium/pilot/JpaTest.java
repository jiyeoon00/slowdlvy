package com.hanium.pilot;

import com.hanium.pilot.domain.model.State;
import com.hanium.pilot.domain.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@DataJpaTest
class JpaTest {

    @Autowired
    EntityManagerFactory emf;

    private List<State> stateList = Arrays.asList(State.DELETED,State.COMPLETED,State.DELETED,State.ACTIVE);

    void setTodo(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Todo todo = new Todo();
        todo.setTitle("test");
        todo.setState(State.ACTIVE);
        em.persist(todo);
        em.getTransaction().commit();
        em.close();
    }

    void setTodoState(int index){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Todo todo = em.createQuery("select t from Todo t where t.id=:id",Todo.class)
                        .setParameter("id",1l).getSingleResult();
        todo.setState(stateList.get(index));
        em.persist(todo);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    void conflictTest() throws InterruptedException {

        setTodo();
        int loopNum = 4;
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch((int) loopNum);

        for(int i=0;i<loopNum;i++){
            int finalI = i;
            service.execute(()->{
                try {
                    setTodoState(finalI);
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                    latch.countDown();
                }
            });
        }
        latch.await();
        EntityManager em = emf.createEntityManager();
        Todo todo = em.createQuery("select t from Todo t where t.id=:id",Todo.class)
                .setParameter("id",1l).getSingleResult();
        System.out.println(todo.getId()+" "+todo.getTitle()+" "+todo.getState().toString()+" "+todo.getVersion());
    }
}
