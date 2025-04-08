
// void task3_1() {
//     init();
//     int0_init();
//     int1_init();
//     EA = 1;
//     _74HC138_select(LED_CHANNEL);
//     dir = 1;
//     while (true) {
//         static int8_t i = 0;
//         P0 = ~(1 << i);
//         if (dir > 0) {
//             i = i >= 8 - 1 ? 0 : i + dir;
//         } else if (dir < 0) {
//             i = i <= 0 ? 8 - 1 : i + dir;
//         }
//         delay_ms(200);
//     }
// }
//
// void task3_2() {
//     init();
//     int0_init();
//     int1_init();
//     EA = 1;
//     nixie_write_number_at(8, 0, 2);
//     while (true) {
//         static int8_t x = 0;
//         if (dir != 0) {
//             x -= dir;
//             dir = 0;
//             if (x == 100) {
//                 x = 0;
//             } else if (x == -1) {
//                 x = 99;
//             }
//             nixie_write_number_at(8, x, 2);
//         }
//         nixie_scan(1);
//     }
// }

// void task2_1() {
//     const char charset[] = "0123456789ABCDEF";
//     while (1) {
//         for (uint8_t i = 0; i < 8; ++i) {
//             for (uint8_t j = 0; j < 10; ++j) {
//                 nixie_show(i, NIXIE_SEG_DATA[charset[j]]);
//                 delay_ms(160);
//             }
//         }
//         for (uint8_t j = 0; j < 10; ++j) {
//             nixie_fill(NIXIE_SEG_DATA[charset[j]]);
//             for (int i = 0; i < 160; ++i) {
//                 nixie_scan(1);
//             }
//         }
//     }
// }
//
// void task2_2() {
//     uint16_t y = 2022;
//     uint16_t m = 1;
//     uint16_t c = 0;
//     nixie_write_number_at(8 - 4, y, 4);
//     nixie_write_at(8 - 4, NIXIE_SEG_DATA['-']);
//     nixie_write_number_at(8 - 1, m, 2);
//     while (true) {
//         nixie_scan(1);
//         if (++c >= 160) {
//             c = 0;
//             nixie_write_number_at(8 - 1, m, 2);
//             nixie_write_number_at(8 - 4, y, 4);
//             if (++m >= 13) {
//                 m = 1;
//                 y++;
//             }
//         }
//     }
// }

// void task1() {
//     _74HC138_select(ULN2003_CHANNEL);
//     P0 = 0x00;
//     while (true) {
//         _74HC138_select(LED_CHANNEL);
//         for (int i = 0; i < 3 * 2; ++i) {
//             P0 = ~P0;
//             delay(60000);
//             delay(60000);
//         }
//
//         for (uint8_t i = 1; i <= 8; i++) {
//             P0 = 0xff << i;
//             delay(60000);
//         }
//
//         _74HC138_select(ULN2003_CHANNEL);
//
//         P0 = 0X10;
//         delay(60000);
//         delay(60000);
//         P0 = 0x00;
//
//         _74HC138_select(LED_CHANNEL);
//
//         for (uint8_t i = 1; i <= 8; i++) {
//             P0 = ~(0xff << i);
//             delay(60000);
//         }
//
//         _74HC138_select(ULN2003_CHANNEL);
//
//         P0 = 0X40;
//         delay(60000);
//         delay(60000);
//         P0 = 0x00;
//     }
// }
