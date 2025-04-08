#ifndef ONE_WARE_H
#define ONE_WARE_H

#include "stdint.h"
#include "stdbool.h"

bool one_wire_start();

void one_wire_send_bit(bool b);

bool one_wire_receive_bit();

void one_wire_send_byte(uint8_t value);

uint8_t one_wire_receive_byte();

#endif //ONE_WARE_H
