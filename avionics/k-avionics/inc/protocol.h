
#ifndef __PROTOCOL_H
#define __PROTOCOL_H

#include "stm32f4xx.h"

#define PROTOCOL_MOTOR_1     (uint8_t) 0x01
#define PROTOCOL_MOTOR_2     (uint8_t) 0x02
#define PROTOCOL_MOTOR_3     (uint8_t) 0x03
#define PROTOCOL_MOTOR_4     (uint8_t) 0x04
#define PROTOCOL_ANGEL_X     (uint8_t) 0x05
#define PROTOCOL_ANGEL_Y     (uint8_t) 0x06
#define PROTOCOL_ANGEL_Z     (uint8_t) 0x07
#define PROTOCOL_ANGEL_R     (uint8_t) 0x08
#define PROTOCOL_MESSAGE     (uint8_t) 0x09

#define PROTOCOL_MAX_LEN         32
#define PROTOCOL_MAX_FRAMES      4

#define PROTOCOL_TYPE_BYTE   (uint8_t) 0x01
#define PROTOCOL_TYPE_INT    (uint8_t) 0x02
//#define TYPE_STR    (uint8_t) 0x03

typedef struct _PROTOCOL_Frame {
    uint8_t cmd;
    uint8_t type;
    uint8_t bData;
    uint32_t iData;
} PROTOCOL_Frame;

typedef struct _PROTOCOL_Protocol {
    uint32_t framesLen;
    uint32_t num;
    PROTOCOL_Frame frames[PROTOCOL_MAX_FRAMES];
} PROTOCOL_Protocol;

void PROTOCOL_parseProtocol(uint8_t *buf, PROTOCOL_Protocol *p);
uint32_t PROTOCOL_toByteArray(PROTOCOL_Protocol *p, uint8_t *buf);

#endif //__PROTOCOL_H
