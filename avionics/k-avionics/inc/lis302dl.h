
#ifndef __LIS302DL__H__
#define __LIS302DL__H__

#include "stm32f4xx.h"

#define LIS302DL_FLAG_TIMEOUT 0x1000

void LIS302DL_Init(void);
void LIS302DL_ReadACC(int32_t* out);
void LIS302DL_ReadACCY(int8_t* out);
void LIS302DL_ReadACCX(int8_t* out);
void LIS302DL_getSatus(int8_t *out);

#endif //__LIS302DL__H__
