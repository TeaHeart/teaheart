#ifndef ADC_H
#define ADC_H

#include "starter.h"

#define ADC_REF_1_25_V      0x00     // Internal 1.25V reference
#define ADC_REF_P0_7        0x40     // External reference on AIN7 pin
#define ADC_REF_AVDD        0x80     // AVDD_SOC pin
#define ADC_REF_P0_6_P0_7   0xC0     // External reference on AIN6-AIN7 differential input

#define ADC_7_BIT           0x00     //  64 decimation rate
#define ADC_9_BIT           0x10     // 128 decimation rate
#define ADC_10_BIT          0x20     // 256 decimation rate
#define ADC_12_BIT          0x30     // 512 decimation rate

#define ADC_AIN0            0x00     // single ended P0_0
#define ADC_AIN1            0x01     // single ended P0_1
#define ADC_AIN2            0x02     // single ended P0_2
#define ADC_AIN3            0x03     // single ended P0_3
#define ADC_AIN4            0x04     // single ended P0_4
#define ADC_AIN5            0x05     // single ended P0_5
#define ADC_AIN6            0x06     // single ended P0_6
#define ADC_AIN7            0x07     // single ended P0_7
#define ADC_GND             0x0C     // Ground
#define ADC_TEMP_SENS       0x0E     // on-chip temperature sensor
#define ADC_VDD_3           0x0F     // (vdd/3)

#define MAX_VOLTS 3.3f
#define MAX_RATIO ((1 << 12) - 1) // 4095

uint16_t adc_get(uint8_t opt) {
    ADCCON3 = opt;
    while (ADCIF == 0);
    ADCIF = 0;
    int16_t x = ((ADCH << 8) | ADCL) >> 3; // 补码 01111111 11111000
    return (x < 0 || x > MAX_RATIO) ? 0 : x;
}

float d2a_volts(uint16_t x) {
    return x * (MAX_VOLTS / MAX_RATIO);
}

void volts_format(char *buffer, uint16_t ad, float vo) { // "____ _.__V\n"
    buffer[0] = ad / 1000 % 10 + '0';
    buffer[1] = ad / 100 % 10 + '0';
    buffer[2] = ad / 10 % 10 + '0';
    buffer[3] = ad / 1 % 10 + '0';
    buffer[4] = ' ';

    uint16_t x = vo * 100;
    buffer[5] = x / 100 % 10 + '0';
    buffer[6] = '.';
    buffer[7] = x / 10 % 10 + '0';
    buffer[8] = x / 1 % 10 + '0';
    buffer[9] = 'V';
    buffer[10] = '\r';
    buffer[11] = '\n';
    buffer[12] = '\0';
}

#endif //ADC_H
