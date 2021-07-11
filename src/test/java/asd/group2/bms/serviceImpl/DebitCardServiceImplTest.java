package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.repositoryImpl.DebitCardRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DebitCardServiceImplTest {

    @Mock
    DebitCardRepositoryImpl debitCardRepository;

    @InjectMocks
    DebitCardServiceImpl debitCardService;

    @Test
    void createDebitCard() {

    }

    @Test
    void getDebitCardByNumberTest() {
        Long debitCardNumber = 6L;

        DebitCard debitCard = new DebitCard();
        debitCard.setDebitCardNumber(debitCardNumber);

        when(debitCardRepository.findById(debitCardNumber)).thenReturn(Optional.of(debitCard));

        DebitCard fetchDebitCard = debitCardService.getDebitCardByNumber(debitCardNumber);

        assertEquals(debitCardNumber, fetchDebitCard.getDebitCardNumber(), "Wrong debitCard is returned");
    }

    @Test
    void setDebitCardLimitSuccess() {
        Long debitCardNumber = 6L;
        Integer transactionLimit = 6000;

        DebitCard debitCard = new DebitCard();

        debitCard.setTransactionLimit(transactionLimit);
        debitCard.setDebitCardNumber(debitCardNumber);

        when(debitCardRepository.findById(debitCardNumber)).thenReturn(Optional.ofNullable(debitCard));

        DebitCard fetchDebitCard = debitCardService.getDebitCardByNumber(debitCardNumber);

        when(debitCardRepository.update(fetchDebitCard)).thenReturn(true);

        Boolean updatePin = debitCardService.setDebitCardLimit(debitCardNumber, transactionLimit);

        assertTrue(updatePin, "Transaction Limit not updated");
    }

    @Test
    void setDebitCardLimitFailure() {
        Long debitCardNumber = 6L;
        Integer transactionLimit = 52000;

        DebitCard debitCard = new DebitCard();

        debitCard.setTransactionLimit(transactionLimit);
        debitCard.setDebitCardNumber(debitCardNumber);

        when(debitCardRepository.findById(debitCardNumber)).thenReturn(Optional.ofNullable(debitCard));

        DebitCard fetchDebitCard = debitCardService.getDebitCardByNumber(debitCardNumber);

        when(debitCardRepository.update(fetchDebitCard)).thenReturn(false);

        Boolean updatePin = debitCardService.setDebitCardLimit(debitCardNumber, transactionLimit);

        assertFalse(updatePin, "Transaction Limit updated");
    }

    @Test
    void setDebitCardPinSuccess() {
        Long debitCardNumber = 6L;
        String pin = "9999";

        DebitCard debitCard = new DebitCard();

        debitCard.setPin(pin);
        debitCard.setDebitCardNumber(debitCardNumber);

        when(debitCardRepository.findById(debitCardNumber)).thenReturn(Optional.ofNullable(debitCard));

        DebitCard fetchDebitCard = debitCardService.getDebitCardByNumber(debitCardNumber);

        when(debitCardRepository.update(fetchDebitCard)).thenReturn(true);

        Boolean updatePin = debitCardService.setDebitCardPin(debitCardNumber, pin);

        assertTrue(updatePin, "Pin not updated");

    }

    @Test
    void setDebitCardPinFailure() {
        Long debitCardNumber = 6L;
        String pin = "9999";

        DebitCard debitCard = new DebitCard();

        debitCard.setPin(pin);
        debitCard.setDebitCardNumber(debitCardNumber);

        when(debitCardRepository.findById(debitCardNumber)).thenReturn(Optional.ofNullable(debitCard));

        DebitCard fetchDebitCard = debitCardService.getDebitCardByNumber(debitCardNumber);

        when(debitCardRepository.update(fetchDebitCard)).thenReturn(false);

        Boolean updatePin = debitCardService.setDebitCardPin(debitCardNumber, pin);

        assertFalse(updatePin, "Pin updated");

    }
}