package ksh.deliverymate.order.service;

import ksh.deliverymate.order.repository.OrderItemRepository;
import ksh.deliverymate.order.repository.projection.OrderItemDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public List<OrderItemDetail> getDetailsByOrderIdIn(List<Long> orderIds){
        return orderItemRepository.getDetailsByOrderIdIn(orderIds);
    }
}
