package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer send(BigDecimal amount, int senderId, int receiverId);

    BigDecimal request();

    BigDecimal approve();

    List<Transfer> pending();

    List<Transfer> log();

    Transfer getTransfer();

    Transfer getTransferById(int transferId);

    List<Transfer> rejected();


}
