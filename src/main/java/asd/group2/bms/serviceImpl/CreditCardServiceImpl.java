package asd.group2.bms.serviceImpl;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.payload.response.CreditCardListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.repository.ICreditCardRepository;
import asd.group2.bms.service.ICreditCardService;
import asd.group2.bms.service.ICustomEmail;
import asd.group2.bms.util.CardDetails;
import asd.group2.bms.util.Helper;
import asd.group2.bms.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class CreditCardServiceImpl implements ICreditCardService {

  @Autowired
  ICreditCardRepository creditCardRepository;

  @Autowired
  ICustomEmail customEmail;

  @Autowired
  Helper helper;

  /**
   * @param creditCardStatus: Credit Card Status (PENDING, APPROVED, REJECTED)
   * @param page:             Page Number
   * @param size:             Size of the response data
   * @description: This will return all the credit cards having status resignStatus
   */
  @Override
  public PagedResponse<CreditCardListResponse> getCreditCardListByStatus(CreditCardStatus creditCardStatus, int page, int size) {
    // Making list in ascending order
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
    Page<CreditCard> cards = creditCardRepository.findByCreditCardStatusEquals(creditCardStatus, pageable);

    if (cards.getNumberOfElements() == 0) {
      return new PagedResponse<>(Collections.emptyList(), cards.getNumber(),
          cards.getSize(), cards.getTotalElements(), cards.getTotalPages(), cards.isLast());
    }

    List<CreditCardListResponse> creditCardListResponses = cards.map(ModelMapper::mapCreditCardToCreditCardListResponse).getContent();

    return new PagedResponse<>(creditCardListResponses, cards.getNumber(),
        cards.getSize(), cards.getTotalElements(), cards.getTotalPages(), cards.isLast());
  }

  /**
   * @param creditCardNumber: credit card number
   * @return credit card based on credit card number
   */
  @Override
  public CreditCard getCreditCardByCreditCardNumber(Long creditCardNumber) {
    return creditCardRepository.findById(creditCardNumber).orElseThrow(() -> new ResourceNotFoundException("Credit Card Number", "creditCardNumber", creditCardNumber));
  }

  /**
   * @param creditCardNumber: credit card number
   * @param creditCardStatus: Status of the credit card (APPROVED, REJECTED, PENDING)
   * @return the updated status of the credit card having credit card number - creditCardNumber
   */
  @Override
  public Boolean setCreditCardRequestStatus(Long creditCardNumber,
                                            CreditCardStatus creditCardStatus
  ) throws MessagingException,
      UnsupportedEncodingException {
    CreditCard creditCard = getCreditCardByCreditCardNumber(creditCardNumber);
    String email = creditCard.getAccount().getUser().getEmail();
    String firstName = creditCard.getAccount().getUser().getFirstName();
    Integer transactionLimit = creditCard.getTransactionLimit();
    creditCard.setCreditCardStatus(creditCardStatus);
    boolean response = creditCardRepository.update(creditCard);
    if (response) {
      customEmail.sendCreditCardApprovalMail(email, firstName,
          transactionLimit);

    }
    return response;
  }


  /**
   * @param account: account for which credit card would be created
   * @return This will return the debit card details
   */
  @Override
  public CreditCard createCreditCard(Account account,
                                     Integer requestedTransactionLimit) {
    CardDetails cardDetails = helper.generateCardDetails();

    String creditCardNumber = cardDetails.getCardNumber();
    String expiryMonth = cardDetails.getExpiryMonth();
    String expiryYear = cardDetails.getExpiryYear();
    String pin = cardDetails.getPin();
    String cvv = cardDetails.getCvv();

    CreditCard creditCard = new CreditCard(Long.parseLong(creditCardNumber),
        account, pin, requestedTransactionLimit, CreditCardStatus.PENDING, expiryYear, expiryMonth,
        cvv, false);

    return creditCardRepository.save(creditCard);
  }

}
