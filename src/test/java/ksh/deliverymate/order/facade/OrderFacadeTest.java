package ksh.deliverymate.order.facade;

import ksh.deliverymate.global.exception.CustomException;
import ksh.deliverymate.order.entity.Order;
import ksh.deliverymate.order.entity.OrderStatus;
import ksh.deliverymate.order.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class OrderFacadeTest {



    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAllInBatch();
    }

    @DisplayName("동일 주문에 여러 라이더가 동시 배차 요청 시 첫 요청자만 성공한다")
    @Test
    void assignRider1() throws InterruptedException {
        // given
        Order order = createOrder(OrderStatus.ACCEPTED, 1L);
        orderRepository.save(order);

        int thread_num = 1000;
        int pool_size    = 32;

        ExecutorService executor = Executors.newFixedThreadPool(pool_size);
        CountDownLatch latch = new CountDownLatch(thread_num);

        AtomicLong riderIdGenerator = new AtomicLong(1L);
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failureCount = new AtomicInteger();

        // when
        for (int i = 0; i < thread_num; i++) {
            executor.submit(() -> {
                long riderId = riderIdGenerator.getAndIncrement();
                try {
                    orderFacade.assignRider(order.getId(), riderId);
                    successCount.incrementAndGet();
                } catch (CustomException e) {
                    System.out.println(e.getErrorCode().getMessageKey() + Thread.currentThread().getName());
                    failureCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();

        // then
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failureCount.get()).isEqualTo(thread_num - 1);
    }

    private Order createOrder(OrderStatus status, long storeId) {
        return Order.builder()
            .status(status)
            .totalPrice(15_000)
            .discountAmount(0)
            .usedPoint(0)
            .finalPrice(15_000)
            .userId(1L)
            .storeId(storeId)
            .build();
    }
}
