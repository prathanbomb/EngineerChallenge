# EngineerChallenge

app กว. ลับสมองเป็นแอพที่ช่วยผู้ใช้ในการทำข้อสอบในส่วนของ  
ใบประกอบวิชาชีพวิศวกรรม (กว.) โดยจะมี 2 โหมด เป็นโหมดเกม  
กับโหมดทำข้อสอบจริง
 
  โหมดเกมจะมีเวลาจำกัดที่ 2 นาทีโดยจะสุ่มคำถามทั้งหมดจากข้อสอบจริงมา  
ถ้าตอบถูกติดต่อกัน 3, 6, 9, ครั้งจะได้ดาวตามระดับที่ถูก 3 ครั้งได้ 1 ดาว  
6 ครั้งได้ 2 ดาว 9 ครั้งได้ 3 ดาว เป็นต้น(มีผลกับเหรียญที่ใช้ในการซื้อตัวช่วย)  
ในโหมดนี้สามารถที่จะตัวช่วยได้ สามารถดูประวัติคำถามที่เล่นไปแล้ว  
พร้อมเฉลยภายหลังได้

  โหมดข้อสอบจะเลือกวิชาได้อย่างเดียว โดยเวลาและจำนวนข้อสอบจะถูกกำหนดไว้  
เท่ากับเวลาในการทำข้อสอบจริง ในส่วนของขั้นตอนการทำข้อสอบนั้น  
มีแสดงเวลาถอยหลังและสามารถตอบได้อย่างเดียว เมื่อทำเสร็จจะมาถึงหน้าสรุปว่าทำถูก  
ไปกี่ข้อสามารถดูเฉลยได้และแสดงกราฟบันทึกความก้าวหน้าของผู้เล่นไว้ได้  

ในส่วนของ code หลัก ๆ จะมีอยู่ 4 ส่วนคือ  
#### activity  
    เป็นฟังก์ชั่นการทำงานต่าง ๆ ภายในแอพพลิเคชั่น  
#### data  
    เป็นส่วนที่ช่วยในการจัดการกับข้อมูลในส่วนต่าง ๆ  
#### fragment  
    นำมาทำงานพร้อมกันกับ activity ที่มีการแสดงผลแบบแยกกัน  
#### view  
    จัดการการแสดงผลต่าง ๆ ui/ux  

## รูป flow chart ของการทำงานโปรแกรม
![alt text](https://imgur.com/kp5h9Hc.png)



# EngineerChallenge
engineer challenge app help user prepare about engineer certificate  
in this app got 2 mode  
-play  
-test  

Play mode got time to play 2 minutes and there is save point when  
correct anwser 3, 6, 9 in a row.Each stage will give you star and bonus point  
to buy assistant for helping you to play in this mode.You can review play history  
and answer in summary page

Test mode you can only select subject and they got fix time and amount as real test.  
you can only answer in this mode and see result.You can review correct answer after finished.

code got 4 section  
#### activity  
    handle about function that implement in this application  
#### data  
    handle about data in this application  
#### fragment  
    handle about fragment that got 2 activity in same layout  
#### view  
    handle about ui/ux
    
## Flow chart
![alt text](https://imgur.com/kp5h9Hc.png)
    
