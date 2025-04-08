#ifndef IR_H
#define IR_H

#include "stdint.h"

void ir_init();

void ir_scan();

uint8_t ir_get_address();

uint8_t ir_get_command();

#endif
