package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cheque.ChequeStatus;
import asd.group2.bms.repository.IChequeRepository;
import asd.group2.bms.service.IChequeService;
import asd.group2.bms.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;

public class IChequeServiceImpl implements IChequeService {
    @Autowired
    IChequeRepository repository;

    @Autowired
    IAccountService accountService;

    @Override
    public Boolean startCheque(Long senderAccountNumber,
        Long receiverAccountNumber, Long chequeNumber, Double chequeAmount) {

       Boolean chequeStatus = repository.update_cheque(chequeNumber);
       Boolean chequeTransactionStatus =
           repository.update_chequeTransaction(senderAccountNumber,
               receiverAccountNumber,chequeNumber,chequeAmount);
       if (chequeStatus && chequeTransactionStatus)
       {
           return true;
       }
       return false;

    }

    public void processCheque(Long chequeNumber,Long senderAccountNumber,
        Long receiverAccountNumber,Double chequeAmount)
    {
      Account senderAccount = accountService.getAccountByAccountNumber(senderAccountNumber);
      Account receiverAccount =
          accountService.getAccountByAccountNumber(receiverAccountNumber);
      Double senderBalance = senderAccount.getBalance();
      Double receiverBalance = receiverAccount.getBalance();
      ChequeStatus chequeStatus;
      if (senderBalance >= chequeAmount)
      {
        senderAccount.setBalance(senderBalance - chequeAmount);
        receiverAccount.setBalance(receiverBalance + chequeAmount);
        accountService.updateAccountBalance(senderAccount);
        accountService.updateAccountBalance(receiverAccount);
        chequeStatus = ChequeStatus.CLEARED;
      }
      else
      {
        chequeStatus = ChequeStatus.RETURN;
      }
      repository.updateChequeStatus(chequeStatus);

    }

  @Override
  public ChequeStatus chequeStatus(Long chequeNumber) {
    return repository.getChequeStatus(chequeNumber);
  }


}
