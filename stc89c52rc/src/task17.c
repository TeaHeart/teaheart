#ifdef TASK17

#include "reg51.h"
#include "delay.h"
#include "button.h"
#include "timer.h"
#include "matrix_led.h"
#include "util.h"
#include "74hc595.h"

// Hello 32 * 8
uint8_t __code ANIME[] = {
        0x7F, 0x08, 0x08, 0x08, 0x7F, 0x00, 0x0E, 0x11, 0x15, 0x0C, 0x00, 0x7F, 0x01, 0x00, 0x7F, 0x01,
        0x00, 0x3E, 0x41, 0x41, 0x41, 0x3E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
};

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    single_button_scan();
    Key key = single_button_get_key();
    if (key == KEY_0) {
        matrix_led_row_scan();
    } else if (key == KEY_1) {
        matrix_led_column_scan();
    }
}

int main() {
    timer0_init();
    EA = 1;
    while (true) {
        for (uint8_t i = 0; i < LENGTH_OF(ANIME) - 8; ++i) {
            matrix_led_set_seg(ANIME + i);
            delay_ms(40); // 1000/24=41.666667
        }
    }
}

#endif //TASK17
