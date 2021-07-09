package asd.group2.bms.service;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.payload.response.CreditCardListResponse;
import asd.group2.bms.payload.response.PagedResponse;

public interface CreditCardService {

    PagedResponse<CreditCardListResponse> getCreditCardListByStatus(CreditCardStatus creditCardStatus, int page, int size);

    CreditCard getCreditCardByCreditCardNumber(Long creditCardNumber);

    CreditCard setCreditCardRequestStatus(Long creditCardNumber, CreditCardStatus creditCardStatus);

    CreditCard createCreditCard(Account account);

}
