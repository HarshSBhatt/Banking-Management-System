package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.repository.DebitCardRepository;
import asd.group2.bms.repositoryImpl.DebitCardRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class DebitCardServiceImplTest {

    @Autowired
    private DebitCardServiceImpl debitCardServiceImpl;

    @MockBean
    private DebitCardRepository debitCardRepository;

    @Test
    void createDebitCard() {

    }

    @Test
    void getDebitCardByNumber() {
    }

    @Test
    void setDebitCardLimit() {
    }

    @Test
    void setDebitCardPinTest() {
        long debitCardNumber;
        debitCardNumber = 97030;
        DebitCard debitCard = new DebitCard();
        when(debitCardRepository.findById(debitCardNumber)).thenReturn(java.util.Optional.of(debitCard));
        String pin ="4808";
        assertEquals(4808,debitCardServiceImpl.setDebitCardPin(debitCardNumber,pin));
    }
}