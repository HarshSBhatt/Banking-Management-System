package asd.group2.bms.repositoryImpl;

import asd.group2.bms.repository.IChequeRepository;

public class ChequeRepositoryImp implements IChequeRepository {

  @Override
  public Boolean update_cheque(Long senderAccountNumber, Long receiverAccountNumber, Long chequeNumber, Double chequeAmount) {


    return true;
  }

}
