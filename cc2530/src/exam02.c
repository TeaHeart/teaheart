#ifdef EXAM02
#include "starter.h"
#include "oled.h"
#include "uart0.h"

#define LED0 P1_0
#define LED1 P1_1

uint8_t receive_buffer[16 + 1] = "hello zigbee!\r\n";
uint8_t length = 0;
bool is_end = false;

int main() {
    set_32MHz();

    P1DIR |= (1 << 1) | (1 << 0);
    LED0 = LED1 = 1;

    oled_init();
    uart0_init();

    oled_show_string(0, 0, receive_buffer, 15);
    uart0_send_bytes(receive_buffer, 15);
    while (true) {
        if (is_end) {
            LED0 = 0;
            uart0_send_bytes(">>> ", 4);
            uart0_send_bytes(receive_buffer, length);
            uart0_send_bytes("\r\n", 2);
            oled_clear();
            oled_show_string(0, 0, receive_buffer, length);
            length = 0;
            LED0 = LED1 = 1;
            is_end = false;
        }
    }
}

void uart0_rx_isr() __interrupt(URX0_VECTOR) {
    uint8_t buf = U0DBUF;
    {
        if (is_end || buf == '\r' || length >= sizeof(receive_buffer) - 1) {
            receive_buffer[length] = '\0';
            is_end = true;
            return;
        }
        receive_buffer[length++] = buf;
        LED1 = 0;
    }
    URX0IF = 0;
}
#endif //EXAM02
