#include "led.h"
#include "reg51.h"

#define LED P2

uint8_t led_get() {
    return LED;
}

void led_set(uint8_t value) {
    LED = value;
}
