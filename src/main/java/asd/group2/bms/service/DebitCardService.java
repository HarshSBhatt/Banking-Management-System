package asd.group2.bms.service;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.cards.debit.DebitCard;
import asd.group2.bms.model.cards.debit.DebitCardStatus;
import asd.group2.bms.repository.DebitCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Service
public class DebitCardService {
    @Autowired
    DebitCardRepository debitCardRepository;

    /**
     * @param account: Account of user whose debit card is being created
     * @description: This will return the debit card details
     */
    public DebitCard createDebitCard(Account account) {
        Random random = new Random();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int currentYear = localDate.getYear();
        int month = localDate.getMonthValue();

        Month expiryMonth = Month.of(month);
        Year expiryYear = Year.of(currentYear + 4);

        String pin = String.format("%04d", random.nextInt(10000));
        String cvv = String.format("%06d", random.nextInt(1000000));

        DebitCard debitCard = new DebitCard(account, pin, 50000, DebitCardStatus.ACTIVE, expiryYear, expiryMonth, cvv);
        return debitCardRepository.save(debitCard);
    }
}
