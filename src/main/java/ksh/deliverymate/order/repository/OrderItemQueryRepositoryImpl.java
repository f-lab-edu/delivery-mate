package ksh.deliverymate.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ksh.deliverymate.menu.entity.QMenu;
import ksh.deliverymate.order.entity.QOrderItem;
import ksh.deliverymate.order.repository.projection.OrderItemDetail;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static ksh.deliverymate.menu.entity.QMenu.*;
import static ksh.deliverymate.order.entity.QOrderItem.*;

@RequiredArgsConstructor
public class OrderItemQueryRepositoryImpl implements OrderItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderItemDetail> getDetailsByOrderIdIn(List<Long> orderIds) {

        return queryFactory
            .select(
                Projections.constructor(
                    OrderItemDetail.class,
                    orderItem.quantity,
                    menu.name
                )
            )
            .from(orderItem)
            .join(menu)
            .on(orderItem.menuId.eq(menu.id))
            .where(
                orderItem.orderId.in(orderIds)
            )
            .fetch();
    }
}
