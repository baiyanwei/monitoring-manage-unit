SELECT MT.ID,
       R.CITY_NAME,
       MT.CREATE_AT,
       MT.SCHEDULE,
       MT.OPERATION,
       MT.TARGET_IP,
       MT.TARGET_PORT,
       MT.CONTENT,
       MT.META_DATA,
       MT.RES_ID,
       MT.IS_REALTIME
  FROM (SELECT T.ID,
               T.REGION,
               T.CREATE_AT,
               T.SCHEDULE,
               T.OPERATION,
               T.TARGET_IP,
               T.TARGET_PORT,
               T.CONTENT,
               T.META_DATA,
               T.RES_ID,
               T.IS_REALTIME
          FROM MSU_TASK T
         WHERE T.IS_REALTIME <> 0) MT
  LEFT JOIN (SELECT C.CITY_NAME, C.CITY_CODE FROM SYS_CITY C) R
    ON MT.REGION = R.CITY_CODE;
