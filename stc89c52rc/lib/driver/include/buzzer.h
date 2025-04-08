#ifndef BUZZER_H
#define BUZZER_H

#include "stdint.h"
#include "stdbool.h"

void buzzer_pulse(uint16_t duration);

void buzzer_toggle();

void buzzer_set(bool b);

#endif //BUZZER_H
