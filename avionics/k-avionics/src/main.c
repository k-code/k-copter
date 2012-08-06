/* Includes ------------------------------------------------------------------*/
#include "main.h"
#include "stm32f4_discovery.h"
#include "axis.h"
#include "protocol.h"
#include "usb_core.h"
#include "usbd_core.h"
#include "usbd_desc.h"
#include "usbd_cdc_core.h"
#include "usbd_cdc_vcp.h"
#include "usbd_usr.h"

/* Private typedef -----------------------------------------------------------*/
/* Private define ------------------------------------------------------------*/

#define TESTRESULT_ADDRESS         0x080FFFFC
#define ALLTEST_PASS               0x00000000
#define ALLTEST_FAIL               0x55555555

/* Private macro -------------------------------------------------------------*/
/* Private variables ---------------------------------------------------------*/
#ifdef USB_OTG_HS_INTERNAL_DMA_ENABLED
#if defined ( __ICCARM__ ) /*!< IAR Compiler */
#pragma data_alignment = 4
#endif
#endif /* USB_OTG_HS_INTERNAL_DMA_ENABLED */
__ALIGN_BEGIN USB_OTG_CORE_HANDLE USB_OTG_dev __ALIGN_END;

uint16_t PrescalerValue = 0;
__IO uint32_t TimingDelay;
__IO uint8_t DemoEnterCondition = 0x00;
__IO uint8_t UserButtonPressed = 0x00;

uint8_t Buffer[64];

/* Private function prototypes -----------------------------------------------*/
void getData(void) {
    PROTOCOL_Protocol p;
    PROTOCOL_parseProtocol(Data_buf, &p);
    /*for (int32_t i = 0; i < p.len; i++) {
        switch (p.frames[i].cmd) {
        case PROTOCOL_MOTOR_1:
            TIM_SetCompare1(TIM4, p.frames[i].data);
            break;
        case PROTOCOL_MOTOR_2:
            TIM_SetCompare2(TIM4, p.frames[i].data);
            break;
        case PROTOCOL_MOTOR_3:
            TIM_SetCompare3(TIM4, p.frames[i].data);
            break;
        case PROTOCOL_MOTOR_4:
            TIM_SetCompare4(TIM4, p.frames[i].data);
            break;
        }
    }*/
}

//static void Demo_Exec(void);
/* Private functions ---------------------------------------------------------*/

/**
 * @brief  Main program.
 * @param  None
 * @retval None
 */
int main(void) {
    RCC_ClocksTypeDef RCC_Clocks;
    uint8_t buf[PROTOCOL_MAX_LEN];
    //int32_t data[3];
    //struct PROTOCOL_Protocol p;
    uint32_t val = PWM_PERIOD;
    Data_get = 0;

    initLeds();
    initTimer();
    initPWM();
    initSpi();
    LIS302DL_Init();

    /* USB configuration */
    USBD_Init(&USB_OTG_dev, USB_OTG_FS_CORE_ID, &USR_desc, &USBD_CDC_cb,
            &USR_cb);

    /* Initialize LEDs and User_Button on STM32F4-Discovery --------------------*/
    STM_EVAL_PBInit(BUTTON_USER, BUTTON_MODE_EXTI);

    /* SysTick end of count event each 1ms */
    RCC_GetClocksFreq(&RCC_Clocks);
    SysTick_Config(RCC_Clocks.HCLK_Frequency / 1000);

    while (1) {
        //LIS302DL_ReadACC(data);

        val = PWM_PERIOD - val;
        TIM_SetCompare1(TIM4, val);

        //p.num = 1;
        /*p.frames[0].cmd = PROTOCOL_ANGEL_X;
         p.frames[0].type = PROTOCOL_TYPE_INT;
         p.frames[0].data = abs(data[0]);*/

        /*      p.frames[1].cmd = PROTOCOL_ANGEL_X;
         p.frames[1].type = PROTOCOL_TYPE_INT;
         p.frames[1].data = abs(data[1]);*/

        //uint32_t len = PROTOCOL_getBytes(p, 0, buf);
        uint32_t len = 10;
        for (uint8_t i = 0; i < len; i++) {
            buf[i] = i + '0';
        }

        /*buf[0] = (uint8_t)abs(PWM_PERIOD+abs(data[0]));
         buf[1] = (uint8_t)abs(PWM_PERIOD+abs(data[1]));
         buf[2] = 0;*/

        VCP_DataTx(buf, len);

        if (Data_get == 1) {
            TIM_SetCompare4(TIM4, 1000);
            getData();
            Data_get = 0;
        }
        else {
            TIM_SetCompare4(TIM4, 0);
        }

        Delay(1000);
    }
}

/**
 * @brief  Inserts a delay time.
 * @param  nTime: specifies the delay time length, in 10 ms.
 * @retval None
 */
void Delay(__IO uint32_t nTime) {
    TimingDelay = nTime;

    while (TimingDelay != 0)
        ;
}

/**
 * @brief  Decrements the TimingDelay variable.
 * @param  None
 * @retval None
 */
void TimingDelay_Decrement(void) {
    if (TimingDelay != 0x00) {
        TimingDelay--;
    }
}

void initLeds() {
    GPIO_InitTypeDef GPIO_InitStructure;

    RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOD, ENABLE);

    GPIO_InitStructure.GPIO_Pin = LEDS;
    GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF;
    GPIO_InitStructure.GPIO_Speed = GPIO_Speed_100MHz;
    GPIO_InitStructure.GPIO_OType = GPIO_OType_PP;
    GPIO_InitStructure.GPIO_PuPd = GPIO_PuPd_UP;
    GPIO_Init(GPIOD, &GPIO_InitStructure);

    GPIO_PinAFConfig(GPIOD, GPIO_PinSource12, GPIO_AF_TIM4);
    GPIO_PinAFConfig(GPIOD, GPIO_PinSource13, GPIO_AF_TIM4);
    GPIO_PinAFConfig(GPIOD, GPIO_PinSource14, GPIO_AF_TIM4);
    GPIO_PinAFConfig(GPIOD, GPIO_PinSource15, GPIO_AF_TIM4);
}

void initTimer() {
    /* TIM4 clock enable */
    RCC_APB1PeriphClockCmd(RCC_APB1Periph_TIM4, ENABLE);

    /* Compute the prescaler value */
    u32 PrescalerValue = (uint16_t) ((SystemCoreClock / 2) / 21000000) - 1;

    TIM_TimeBaseInitTypeDef TIM_TimeBaseStructure;
    /* Time base configuration */
    TIM_TimeBaseStructure.TIM_Period = PWM_PERIOD;
    TIM_TimeBaseStructure.TIM_Prescaler = PrescalerValue;
    TIM_TimeBaseStructure.TIM_ClockDivision = 0;
    TIM_TimeBaseStructure.TIM_CounterMode = TIM_CounterMode_Up;

    TIM_TimeBaseInit(TIM4, &TIM_TimeBaseStructure);
}

void initPWM() {
    TIM_OCInitTypeDef TIM_OCInitStructure;

    TIM_OCInitStructure.TIM_OCMode = TIM_OCMode_PWM1;
    TIM_OCInitStructure.TIM_OutputState = TIM_OutputState_Enable;
    TIM_OCInitStructure.TIM_Pulse = 0;
    TIM_OCInitStructure.TIM_OCPolarity = TIM_OCPolarity_High;

    /* PWM1 Mode configuration: Channel1 (GPIOD Pin 12)*/
    TIM_OC1Init(TIM4, &TIM_OCInitStructure);
    TIM_OC1PreloadConfig(TIM4, TIM_OCPreload_Enable);

    /* PWM1 Mode configuration: Channel2 (GPIOD Pin 13)*/
    TIM_OC2Init(TIM4, &TIM_OCInitStructure);
    TIM_OC2PreloadConfig(TIM4, TIM_OCPreload_Enable);

    /* PWM1 Mode configuration: Channel2 (GPIOD Pin 14)*/
    TIM_OC3Init(TIM4, &TIM_OCInitStructure);
    TIM_OC3PreloadConfig(TIM4, TIM_OCPreload_Enable);

    /* PWM1 Mode configuration: Channel4 (GPIOD Pin 15)*/
    TIM_OC4Init(TIM4, &TIM_OCInitStructure);
    TIM_OC4PreloadConfig(TIM4, TIM_OCPreload_Enable);

    TIM_Cmd(TIM4, ENABLE);
}

#ifdef  USE_FULL_ASSERT

/**
 * @brief  Reports the name of the source file and the source line number
 *   where the assert_param error has occurred.
 * @param  file: pointer to the source file name
 * @param  line: assert_param error line source number
 * @retval None
 */
void assert_failed(uint8_t* file, uint32_t line)
{
    /* User can add his own implementation to report the file name and line number,
     ex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */

    /* Infinite loop */
    while (1)
    {
    }
}
#endif

/**
 * @}
 */

/******************* (C) COPYRIGHT 2011 STMicroelectronics *****END OF FILE****/
