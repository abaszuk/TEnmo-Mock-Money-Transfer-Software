package com.techelevator.tenmo.dao;
package com.techelevator.tenmo.model;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    BigDecimal send();

    BigDecimal request();

    BigDecimal approve();

    List<Transfer> pending();

    List<Transfer> log();

    Transfer getTransfer();

    List<Transfer> rejected();


}
