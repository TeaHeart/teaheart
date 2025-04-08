#include "starter.h"
#include "nixie.h"

void timer0_init() {
    // AUXR &= 0x7F;    //定时器时钟12T模式
    TMOD &= 0xF0;       //设置定时器模式
    TMOD |= 0x01;       //设置定时器模式
    TL0 = 0x66;
    TH0 = 0xFC;
    TF0 = 0;            //清除TF0标志
    TR0 = 1;            //定时器0开始计时
    ET0 = 1;
}

void int0_init() {
    EX0 = 1;
    IT0 = 1;
}

void int1_init() {
    EX1 = 1;
    IT1 = 1;
}

#ifdef TASK4_1

void timer0_isr() __interrupt(TF0_VECTOR) {
    TIM0_SetCounter(-50000); // 50ms
    static uint8_t c = 0;
    static uint8_t count100ms = 0;
    if (++c >= 10) { // 500ms
        c = 0;
        P0_0 = !P0_0;
        if (++count100ms >= 10) { // 5s
            count100ms = 0;
            P0_7 = !P0_7;
        }
    }
}

int main() {
    init();
    timer0_init();
    EA = 1;
    _74HC138_select(LED_CHANNEL);
    while (true) {}
}
#else

uint8_t second = 0;

enum {
    Running,
    Pause,
} status = Pause;

void timer0_isr() __interrupt(TF0_VECTOR) {
    TIM0_SetCounter(0xFC66); // 1ms
    nixie_scan(0);
    static uint16_t t = 0;
    if (++t >= 1000) {
        t = 0;
        if (status == Running && second <= 99) {
            second++;
        }
    }
}

void int0_isr() __interrupt(IE0_VECTOR) {
    status = status != Running ? Running : Pause;
}

void int1_isr() __interrupt(IE1_VECTOR) {
    status = Pause;
    second = 0;
}

int main() {
    init();
    int0_init();
    int1_init();
    timer0_init();
    EA = 1;
    while (true) {
        nixie_write_number_at(8, second, 2);
    }
}

#endif