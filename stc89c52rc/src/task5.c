#ifdef TASK5

#include "reg51.h"
#include "motor.h"
#include "timer.h"
#include "button.h"
#include "lcd1602.h"

// 频率 20-200Hz
uint16_t hz = 50;

// 占空比 0-100%
uint16_t rate = 50;

// 1000 / hz = 间隔毫秒数
uint16_t internal = 0;

// internal * rate / 100 = 限制
uint16_t limit = 0;

void update_param() {
    internal = 1000 / hz;
    limit = internal * rate / 100;
}

#define LED0 P2_0

void pwm() { // 1ms
    static uint16_t count = 0;
    bool is_on;
    if (++count >= internal) {
        count = 0;
    }
    is_on = count < limit ? 1 : 0;
    LED0 = is_on;
    motor_set(is_on);
}

void timer0_isr() __interrupt(TF0_VECTOR) { // 1ms
    TL0 = 0x66;
    TH0 = 0xFC;
    single_button_scan();
    pwm();
}

void lcd_show_info() {
    lcd_clear();
    lcd_write_int16_at(0, 0, 4, hz);
    lcd_write_string("Hz");
    lcd_write_int16_at(0, 8, 4, internal);
    lcd_write_string("ms");
    lcd_write_int16_at(1, 0, 4, rate);
    lcd_write_string("%");
    lcd_write_int16_at(1, 8, 4, limit);
    lcd_write_string("ms");
}

int main() {
    timer0_init();
    EA = 1;
    lcd_init();
    update_param();
    lcd_show_info();
    while (true) {
        if (single_button_get_key() != KEY_NULL) {
            switch (single_button_clear_key()) {
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
            }
            update_param();
            lcd_show_info();
        }
    }
}

#endif //TASK5
