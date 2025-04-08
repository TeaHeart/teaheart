#ifndef UART_H
#define UART_H

#include "stdint.h"

typedef enum BPS {
    BPS_11MHz_1200 = 0xD0,
    BPS_11MHz_2400 = 0xE8,
    BPS_11MHz_4800 = 0xF4,
    BPS_11MHz_9600 = 0xFA,
    BPS_11MHz_14000 = 0xFC,
    BPS_11MHz_19200 = 0xFD,
    BPS_11MHz_28800 = 0xFE,
    BPS_11MHz_57600 = 0xFF
} BPS;

void uart_init();

void uart_set_bps(BPS bps);

void uart_send_byte(uint8_t value);

void uart_send_string(uint8_t *str);

uint8_t uart_get_byte();

#endif //UART_H
