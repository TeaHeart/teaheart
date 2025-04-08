#ifndef COUNTER_H
#define COUNTER_H

#include "stdbool.h"
#include "stdint.h"

void counter0_init();

void counter0_set(uint16_t value);

uint16_t counter0_get();

void counter0_set_status(bool is_run);

#endif //COUNTER_H
