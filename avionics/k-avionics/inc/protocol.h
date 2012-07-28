
#ifndef PROTOCOL
#define PROTOCOL

#include "stm32f4xx.h"

#define MOTOR_1     (uint8_t) 0x01
#define MOTOR_2     (uint8_t) 0x02
#define MOTOR_3     (uint8_t) 0x03
#define MOTOR_4     (uint8_t) 0x04
#define ANGEL_X     (uint8_t) 0x05
#define ANGEL_Y     (uint8_t) 0x06
#define ANGEL_Z     (uint8_t) 0x07
#define ANGEL_R     (uint8_t) 0x08
#define MESSAGE     (uint8_t) 0x09

#define MAX_MESS_LEN        255
#define MAX_FRAMES_LEN      MAX_MESS_LEN / 3

#define TYPE_BYTE   (uint8_t) 0x01
#define TYPE_INT    (uint8_t) 0x02
//#define TYPE_STR    (uint8_t) 0x03

typedef struct Frame {
    uint8_t cmd;
    uint8_t type;
    uint32_t data;
} _Frame;

typedef struct Protocol {
    uint32_t len;
    uint32_t num;
    struct Frame frames[MAX_FRAMES_LEN];
} _Protocol;

struct Protocol parseProtocol(uint8_t *buf);
struct Frame parseFrame(uint8_t *buf);
uint32_t parseInt(uint8_t *mess);

#endif //PROTOCOL
