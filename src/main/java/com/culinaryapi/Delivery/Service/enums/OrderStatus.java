package com.culinaryapi.Delivery.Service.enums;

public enum OrderStatus {
    PENDING,            // Pedido foi criado e aguarda processamento.
    CONFIRMED,          // Pedido confirmado pelo restaurante.
    PREPARING,          // Pedido está sendo preparado.
    READY_FOR_PICKUP,   // Pedido está pronto para ser retirado pelo entregador.
    OUT_FOR_DELIVERY,   // Pedido foi retirado pelo entregador e está a caminho.
    DELIVERED,          // Pedido entregue com sucesso.
    CANCELLED,          // Pedido foi cancelado pelo cliente ou restaurante.
    FAILED              // Falha no processamento do pedido (exemplo: pagamento não aprovado).
}