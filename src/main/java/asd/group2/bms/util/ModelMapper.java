package asd.group2.bms.util;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.term_deposit.TermDepositDetail;
import asd.group2.bms.model.term_deposit.TermDepositStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.*;

public class ModelMapper {

  /**
   * @param leaveRequest
   * @return leaveListResponse
   * @description returns list of leave requests
   */
  public static LeaveListResponse mapLeavesToLeaveListResponse(LeaveRequest leaveRequest) {
    LeaveListResponse leaveListResponse = new LeaveListResponse();
    leaveListResponse.setLeaveId(leaveRequest.getLeaveId());
    leaveListResponse.setFromDate(leaveRequest.getFromDate());
    leaveListResponse.setToDate(leaveRequest.getToDate());
    leaveListResponse.setReason(leaveRequest.getReason());
    leaveListResponse.setRequestStatus(leaveRequest.getRequestStatus());

    User user = leaveRequest.getUser();
    UserMetaResponse userMetaResponse = new UserMetaResponse(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getUsername(),
        user.getEmail(),
        user.getPhone()
    );
    leaveListResponse.setUserMetaResponse(userMetaResponse);
    return leaveListResponse;
  }

  /**
   * @param resignRequest
   * @return resignListResponse
   * @description returns list of resign requests
   */
  public static ResignListResponse mapResignsToResignListResponse(ResignRequest resignRequest) {
    ResignListResponse resignListResponse = new ResignListResponse();
    resignListResponse.setResignId(resignRequest.getResignId());
    resignListResponse.setDate(resignRequest.getDate());
    resignListResponse.setReason(resignRequest.getReason());
    resignListResponse.setRequestStatus(resignRequest.getRequestStatus());

    User user = resignRequest.getUser();
    UserMetaResponse userMetaResponse = new UserMetaResponse(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getUsername(),
        user.getEmail(),
        user.getPhone()
    );
    resignListResponse.setUserMetaResponse(userMetaResponse);
    return resignListResponse;
  }


  /**
   * @param creditCard
   * @return creditCardListResponse
   * @description returns list of credit cards
   */
  public static CreditCardListResponse mapCreditCardToCreditCardListResponse(CreditCard creditCard) {
    CreditCardListResponse creditCardListResponse = new CreditCardListResponse();
    creditCardListResponse.setCreditCardNumber(creditCard.getCreditCardNumber());
    creditCardListResponse.setPin(creditCard.getPin());
    creditCardListResponse.setTransactionLimit(creditCard.getTransactionLimit());
    creditCardListResponse.setCreditCardStatus(creditCard.getCreditCardStatus());
    creditCardListResponse.setExpiryYear(creditCard.getExpiryYear());
    creditCardListResponse.setExpiryMonth(creditCard.getExpiryMonth());
    creditCardListResponse.setCvv(creditCard.getCvv());
    creditCardListResponse.setActive(creditCard.getActive());

    Account account = creditCard.getAccount();
    User user = creditCard.getAccount().getUser();
    UserMetaResponse userMetaResponse = new UserMetaResponse(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getUsername(),
        user.getEmail(),
        user.getPhone()
    );
    AccountDetailResponse accountDetailResponse = new AccountDetailResponse(
        account.getAccountNumber(),
        account.getAccountType(),
        account.getBalance(),
        account.getCreditScore(),
        account.getCreatedAt(),
        account.getUpdatedAt(),
        userMetaResponse
    );
    creditCardListResponse.setAccountDetailResponse(accountDetailResponse);

    return creditCardListResponse;
  }

}
