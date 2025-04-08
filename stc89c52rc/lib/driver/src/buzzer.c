#include "buzzer.h"
#include "reg51.h"
#include "delay.h"

#define BUZZER P2_5

void buzzer_pulse(uint16_t duration) {
    while (duration-- != 0) {
        BUZZER = !BUZZER;
        delay_ms(1);
    }
}

void buzzer_toggle() {
    BUZZER = !BUZZER;
}

void buzzer_set(bool b) {
    BUZZER = b;
}
