package chainOfCommandAndMemento;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestChainOfCommandAndMemento {

    @Test
    public void test(){
        Photo photo = new Photo("photo01012001", 168, "PNJ"); //создаем объект фото

        Formatting formatting = new Formatting(); //создаем комманды из цепочки
        Processing processing = new Processing();
        Retouch retouch = new Retouch();
        formatting.setNext(processing);
        processing.setNext(retouch);

        formatting.handle(photo); //запускаем цепочку комманд, каждая из которых сохраняет снимок состояния до совершения работы

        assertEquals("RAW", photo.getFormat()); //формат изменился
        assertTrue(photo.isProcessing()); //обработка
        assertTrue(photo.isRetouch());    //и ретушь есть


        photo.restore(retouch.getSnapshot()); //вернули снимок фотографии, перед тем как ее ретушировали
        assertEquals("RAW", photo.getFormat()); //формат изменился
        assertTrue(photo.isProcessing());  //обработка есть
        assertFalse(photo.isRetouch());    //а вот ретуши нет

        photo.restore(formatting.getSnapshot()); //вернули снимок фотографии, перед тем как ее форматировали в самом начале обработки
        assertEquals("PNJ", photo.getFormat()); //формат не менялся
        assertFalse(photo.isProcessing()); //обработки нет
        assertFalse(photo.isRetouch());   //и ретуши нет
    }
}

