package ksh.deliverymate.order.repository;

import ksh.deliverymate.order.entity.Order;
import ksh.deliverymate.order.entity.OrderStatus;
import ksh.deliverymate.order.repository.projection.OrderWithStore;
import ksh.deliverymate.order.vo.Position;
import ksh.deliverymate.store.entity.FoodCategory;
import ksh.deliverymate.store.entity.Store;
import ksh.deliverymate.store.entity.StoreStatus;
import ksh.deliverymate.store.repository.StoreRepository;
import ksh.deliverymate.store.vo.Address;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    GeometryFactory geometryFactory;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private StoreRepository storeRepository;

    private double centerLat = 37.5665;
    private double centerLon = 126.9780;

    private List<Store> stores;
    private List<Order> targetOrders;
    private List<Order> nonTargetOrders;

    @BeforeEach
    void setUp() {
        Store inRange = createStore("경계 안에 있는 가게", centerLat + 0.003, centerLon);
        Store onBoundary = createStore("경계에 거의 근처에 있는 가게", centerLat + 0.0045, centerLon);
        Store outRange = createStore("경계 밖에 있는 가게", centerLat + 0.01, centerLon);

        stores = List.of(inRange, onBoundary, outRange);
        storeRepository.saveAll(stores);

        targetOrders = new ArrayList<>();
        nonTargetOrders = new ArrayList<>();

        for (Store store : stores) {
            Order targetOrder = createOrder(OrderStatus.ACCEPTED, store.getId());
            orderRepository.save(targetOrder);
            targetOrders.add(targetOrder);

            Order nonTargetOrder = createOrder(OrderStatus.PAID, store.getId());
            orderRepository.save(nonTargetOrder);
            nonTargetOrders.add(nonTargetOrder);
        }
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
    }

    @DisplayName("라이더의 현재 위치를 중심으로 일정 반경 내 라이더가 배정되지 않는 주문을 조회한다")
    @Test
    void findByStatusAndWithinRadius1() {
        //given
        Position riderCoordinate = Position.of(37.5665, 126.978);
        int radius = 500;
        Pageable pageable = PageRequest.of(0, 2);

        //when
        Slice<OrderWithStore> orderStoreInfos = orderRepository.findByStatusAndWithinRadius(
            OrderStatus.ACCEPTED,
            riderCoordinate,
            radius,
            pageable
        );

        //then
        assertThat(orderStoreInfos.hasNext()).isFalse();
        assertThat(orderStoreInfos.getContent()).hasSize(2)
            .extracting("orderId", "name")
            .containsExactlyInAnyOrder(
                Tuple.tuple(targetOrders.get(0).getId(), stores.get(0).getName()),
                Tuple.tuple(targetOrders.get(1).getId(), stores.get(1).getName())
            );
    }

    private Order createOrder(OrderStatus status, long storeId) {
        return Order.builder()
            .status(status)
            .totalPrice(15000)
            .discountAmount(0)
            .usedPoint(0)
            .finalPrice(15000)
            .userId(1L)
            .storeId(storeId)
            .build();
    }

    private Store createStore(String name, double lat, double lon) {
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        point.setSRID(4326);

        Address address = Address.of("서울시", "강서구", "방화동", "금낭화로", "167");

        return Store.builder()
            .name(name)
            .description(name + " description")
            .address(address)
            .coordinate(point)
            .phone("010-1234-5678")
            .category(FoodCategory.PIZZA)
            .status(StoreStatus.OPEN)
            .ownerId(42L)
            .build();
    }
}
