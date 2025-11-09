package com.taxiapp.taxi_api.model;

public enum RideStatus {
    REQUESTED,        // 0: A corrida foi solicitada, aguardando motorista
    ACCEPTED,         // 1: Um motorista aceitou a corrida
    IN_PROGRESS,      // 2: O motorista pegou o passageiro
    COMPLETED,        // 3: A corrida foi finalizada
    CANCELLED         // 4: A corrida foi cancelada
}