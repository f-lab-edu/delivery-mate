package ksh.deliverymate.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ksh.deliverymate.order.entity.OrderStatus;
import ksh.deliverymate.order.repository.projection.OrderStoreInfo;
import ksh.deliverymate.order.vo.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static ksh.deliverymate.order.entity.QOrder.order;
import static ksh.deliverymate.store.entity.QStore.store;

@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<OrderStoreInfo> findByStatusAndWithinRadius(
        OrderStatus status,
        Position center,
        int radius,
        Pageable pageable
    ) {
        String centerWkt = convertIntoWkt(center);

        BooleanTemplate withinRadius = withinRadiusFilter(radius, centerWkt);

        NumberExpression distanceFromStore = distanceFromStore(centerWkt);

        List<OrderStoreInfo> result = queryFactory
            .select(Projections.constructor(
                OrderStoreInfo.class,
                order.id,
                store.name,
                store.address,
                store.phone,
                distanceFromStore.as("distance")
            ))
            .from(store)
            .join(order)
            .on(order.storeId.eq(store.id))
            .where(
                order.status.eq(OrderStatus.ACCEPTED),
                withinRadius
            )
            .orderBy(
                distanceFromStore.asc(),
                order.createdAt.asc()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .fetch();

        return createSlice(result, pageable);
    }

    private static String convertIntoWkt(Position center) {
        return String.format(
            "POINT (%f %f)",
            center.getLatitude(),
            center.getLongitude()
        );
    }

    private static BooleanTemplate withinRadiusFilter(double radius, String centerWkt) {
        return Expressions.booleanTemplate(
            "ST_Contains(ST_Buffer(ST_GeomFromText({0}, 4326), {1}), {2})",
            Expressions.constant(centerWkt),
            Expressions.constant(radius),
            store.coordinate
        );
    }

    private static NumberExpression distanceFromStore(String centerWkt) {
        return Expressions.numberTemplate(
            Double.class,
            "ST_Distance_Sphere({0}, ST_GeomFromText({1}, 4326))",
            store.coordinate,
            Expressions.constant(centerWkt)
        );
    }

    private <T> Slice<T> createSlice(List<T> item, Pageable pageable) {
        boolean hasNext = item.size() > pageable.getPageSize();
        int size = Math.min(item.size(), pageable.getPageSize());
        List<T> content = item.subList(0, size);

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
