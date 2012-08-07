/*
 * periph_init.h
 *
 *  Created on: 07.08.2012
 *      Author: kvv
 */

#ifndef PERIPH_INIT_H_
#define PERIPH_INIT_H_

#include "stm32f4xx.h"

#define LEDS GPIO_Pin_12 | GPIO_Pin_13 | GPIO_Pin_14 | GPIO_Pin_15;
#define PWM_PERIOD 1000

void initSpi(void);
void initLeds(void);
void initTimer(void);
void initPWM(void);
void PERIPH_Init_SysTick(void);

void TimingDelay_Decrement(void);
void Delay(__IO uint32_t nTime);

#endif /* PERIPH_INIT_H_ */
