#ifdef TASK14

#include "reg51.h"
#include "delay.h"
#include "timer.h"
#include "nixie.h"
#include "button.h"
#include "uart.h"
#include "util.h"

uint8_t __code str[][4][4] = {
        {"1",  "2",  "3",  "4"},
        {"5",  "6",  "7",  "8"},
        {"9",  "10", "11", "12"},
        {"13", "14", "15", "16"},
};

// 选择发送的字符串
int8_t k = -1;

// 当前索引
uint8_t i = 0;

BPS UART_BPS[] = {BPS_11MHz_1200, BPS_11MHz_2400, BPS_11MHz_4800, BPS_11MHz_9600};
uint16_t BPS_VALUE[] = {1200, 2400, 4800, 9600};

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    matrix_button_scan();
    nixie_scan();
    if (matrix_button_get_key() != KEY_NULL) {
        i = 0;
        k = lowest_bit_at(matrix_button_clear_key());
        uart_set_bps(UART_BPS[k]);
        nixie_write_uint16(BPS_VALUE[k]);
    }
}

int main() {
    timer0_init();
    uart_init();
    EA = 1;
    while (true) {
        if (k >= 0) {
            uart_send_string(str[k][i]);
            if (++i == 4) {
                i = 0;
            }
            delay_ms(250);
        }
    }
}

#endif
