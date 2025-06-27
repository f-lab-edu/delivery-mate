package ksh.deliverymate.order.controller;

import jakarta.validation.Valid;
import ksh.deliverymate.global.dto.request.PageRequestDto;
import ksh.deliverymate.global.dto.response.PageResponseDto;
import ksh.deliverymate.order.dto.request.WaitingRiderOrderRequestDto;
import ksh.deliverymate.order.dto.response.WaitingRiderOrderResponseDto;
import ksh.deliverymate.order.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @GetMapping("/orders/waiting")
    public ResponseEntity<PageResponseDto> findOrderInfosWaitingForRider(
        @Valid WaitingRiderOrderRequestDto orderRequestDto,
        @Valid PageRequestDto pageRequestDto
    ) {

        Slice<WaitingRiderOrderResponseDto> dtoSlice = orderFacade
            .findOrderInfosWaitingForRider(orderRequestDto, pageRequestDto)
            .map(WaitingRiderOrderResponseDto::from);

        return ResponseEntity.ok(PageResponseDto.of(dtoSlice));
    }
}
