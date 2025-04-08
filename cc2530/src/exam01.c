#ifdef EXAM01
#include "starter.h"

#define LED0 P1_0
#define LED1 P1_1
#define LED2 P0_4

int main() {
    set_32MHz();
    P1DIR |= (1 << 1) | (1 << 0);
    P0DIR |= (1 << 4);
    while (true) {
        LED0 = !LED0;
        delay_ms(200);
        LED1 = !LED1;
        delay_ms(200);
        LED2 = !LED2;
        delay_ms(200);
    }
}
#endif //EXAM01
