package asd.group2.bms.service;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.payload.response.CreditCardListResponse;
import asd.group2.bms.payload.response.PagedResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface ICreditCardBillService {

      Boolean payCreditCardBill(Long accountNumber);

      Long getCreditCardByAccountNumber(Long accountNumber);
}
