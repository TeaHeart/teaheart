#ifdef EXAM03
#include "starter.h"
#include "oled.h"
#include "uart0.h"
#include "adc.h"

#include "string.h"

#define LED2 P0_4
#define BUZZER P0_6

uint8_t receive_buffer[16 + 1];
uint8_t length = 0;
bool is_end = false;

void uart0_rx_isr() __interrupt(URX0_VECTOR) {
    uint8_t buf = U0DBUF;
    {
        if (is_end || buf == '\r' || length >= sizeof(receive_buffer)) {
            receive_buffer[length] = '\0';
            is_end = true;
            return;
        }
        receive_buffer[length++] = buf;
    }
    URX0IF = 0;
}

int main() {
    set_32MHz();

    P0DIR |= (1 << 6) | (1 << 4);
    LED2 = BUZZER = 1;

    APCFG |= (1 << 7) | (1 << 5);

    oled_init();
    uart0_init();

    bool is_warning_enable = true;
    uint8_t inf_format[16 + 1] = "____ _.__V\r\n";
    uint8_t light_format[16 + 1] = "____ _.__V\r\n";

    oled_show_string(0, 0, "inf  :", 6);
    oled_show_string(2, 0, "light:", 6);
    oled_show_string(1, 0, inf_format, 12);
    oled_show_string(3, 0, light_format, 12);

    uart0_send_bytes("cmd: inf | light | on | off \r\n", 30);
    while (true) {
        uint16_t inf = adc_get(ADC_REF_AVDD | ADC_12_BIT | ADC_AIN5);
        uint16_t light = adc_get(ADC_REF_AVDD | ADC_12_BIT | ADC_AIN7);

        volts_format(inf_format, inf, d2a_volts(inf));
        volts_format(light_format, light, d2a_volts(light));

        oled_show_string(1, 0, inf_format, 12);
        oled_show_string(3, 0, light_format, 12);

        if (light >= 2048 && inf <= 256 && is_warning_enable) {
            BUZZER = 0;
            LED2 = 0;
        } else {
            BUZZER = 1;
            LED2 = 1;
        }

        if (is_end) {
            if (strcmp(receive_buffer, "inf") == 0) {
                uart0_send_bytes(inf_format, 12);
            } else if (strcmp(receive_buffer, "light") == 0) {
                uart0_send_bytes(light_format, 12);
            } else if (strcmp(receive_buffer, "on") == 0) {
                is_warning_enable = true;
            } else if (strcmp(receive_buffer, "off") == 0) {
                is_warning_enable = false;
            } else {
                uart0_send_bytes("error\r\n", 7);
            }
            oled_show_string(0, 8, "        ", 8);
            oled_show_string(0, 8, receive_buffer, length);
            oled_show_string(2, 8, is_warning_enable ? "on " : "off", 3);
            length = 0;
            is_end = false;
        }
        delay_ms(500);
    }
}
#endif //EXAM03
