#ifdef TASK15

#include "reg51.h"
#include "timer.h"
#include "button.h"
#include "lcd1602.h"

// 频率 20-200Hz
int16_t hz = 50;

// 占空比 0-100%
int16_t rate = 50;

// 1000 / hz = 周期(ms)
int16_t cycle = 0;

// cycle * rate / 100 = 限制
int16_t off_limit = 0;

// -1 0 1 / (反|停|正)转
int8_t dir = 0;

void update_param() {
    cycle = 1000 / hz;
    off_limit = cycle * rate / 100;
}

void pwm() {
    static uint16_t count = 0;
    if (count >= off_limit || dir == 0) {
        P3_6 = P3_7 = 0;
    } else {
        if (dir == -1) {
            P3_6 = 1;
            P3_7 = 0;
        } else if (dir == 1) {
            P3_6 = 0;
            P3_7 = 1;
        }
    }
    if (++count >= cycle) {
        count = 0;
    }
}

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    matrix_button_scan();
    pwm();
}

void lcd_show_info() {
    lcd_clear();
    lcd_write_int16_at(0, 0, 4, hz);
    lcd_write_string("Hz");
    lcd_write_int16_at(0, 8, 4, cycle);
    lcd_write_string("ms");
    lcd_write_int16_at(1, 0, 4, rate);
    lcd_write_string("%");
    lcd_write_int16_at(1, 8, 4, off_limit);
    lcd_write_string("ms");
}

int main() {
    update_param();
    timer0_init();
    lcd_init();
    lcd_show_info();
    EA = 1;
    while (true) {
        if (matrix_button_get_key() != KEY_NULL) {
            switch (matrix_button_clear_key()) {
                case KEY_0: {
                    hz = hz <= 20 ? 20 : hz - 10;
                    break;
                }
                case KEY_1: {
                    hz = hz >= 200 ? 200 : hz + 10;
                    break;
                }
                case KEY_2: {
                    rate = rate <= 0 ? 0 : rate - 10;
                    break;
                }
                case KEY_3: {
                    rate = rate >= 100 ? 100 : rate + 10;
                    break;
                }
                case KEY_4: {
                    dir = -1;
                    break;
                }
                case KEY_5: {
                    dir = 1;
                    break;
                }
                case KEY_6: {
                    dir = 0;
                    break;
                }
            }
            update_param();
            lcd_show_info();
        }
    }
}

#endif
