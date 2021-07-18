package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.model.cards.debit.DebitCardStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.repository.IDebitCardRepository;
import asd.group2.bms.util.CardDetails;
import asd.group2.bms.util.Helper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DebitCardServiceImplTest {

  @Mock
  IDebitCardRepository debitCardRepository;

  @Mock
  Helper helper;

  @InjectMocks
  DebitCardServiceImpl debitCardService;

  @Test
  void createDebitCardTest() {
    Account account = new Account();
    account.setAccountNumber(1L);
    DebitCard debitcard = new DebitCard();
    debitcard.setDebitCardNumber(6L);
    CardDetails cardDetails = new CardDetails();
    cardDetails.setCardNumber("123456789");
    cardDetails.setExpiryMonth("12");
    cardDetails.setExpiryYear("2022");
    cardDetails.setPin("1234");
    cardDetails.setCvv("123");

    when(helper.generateCardDetails()).thenReturn(cardDetails);
    when(debitCardRepository.save(any())).thenReturn(debitcard);

    DebitCard debitCard = debitCardService.createDebitCard(account);
    assertEquals(6L, debitCard.getDebitCardNumber());

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
  void setDebitCardLimitSuccessTest() {
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
  void setDebitCardLimitFailureTest() {
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
  void setDebitCardPinSuccessTest() {
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
  void setDebitCardPinFailureTest() {
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

  @Test
  void setDebitCardRequestStatusSuccessTest(){
    DebitCard debitCard = new DebitCard();
    Long debitCardNumber = 6L;
    User user = new User();
    user.setEmail("abc123");
    user.setFirstName("Geetanjali");
    Account account = new Account();
    account.setUser(user);
    debitCard.setAccount(account);
    DebitCardStatus debitCardStatus = DebitCardStatus.ACTIVE;
    debitCard.setDebitCardNumber(debitCardNumber);

    when(debitCardRepository.findById(any())).thenReturn(Optional.of(debitCard));
    when(debitCardRepository.update(any())).thenReturn(true);

    Boolean updateStatus =
        debitCardService.setDebitCardRequestStatus(debitCardNumber,
        debitCardStatus);
    assertTrue(updateStatus,"Status not updated");
  }

  @Test
  void setDebitCardRequestStatusFailureTest(){
    DebitCard debitCard = new DebitCard();
    Long debitCardNumber = 6L;
    User user = new User();
    user.setEmail("abc123");
    user.setFirstName("Geetanjali");
    Account account = new Account();
    account.setUser(user);
    debitCard.setAccount(account);
    DebitCardStatus debitCardStatus = DebitCardStatus.ACTIVE;
    debitCard.setDebitCardNumber(debitCardNumber);

    when(debitCardRepository.findById(any())).thenReturn(Optional.of(debitCard));
    when(debitCardRepository.update(any())).thenReturn(false);

    Boolean updateStatus =
        debitCardService.setDebitCardRequestStatus(debitCardNumber,
            debitCardStatus);
    assertFalse(updateStatus,"Status updated");
  }

}