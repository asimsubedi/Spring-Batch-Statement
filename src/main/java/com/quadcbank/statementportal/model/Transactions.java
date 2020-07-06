package com.quadcbank.statementportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AsimSubedi
 *
 */
@Data
@NoArgsConstructor
public class Transactions implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
    private Date date;
    private String merchant;
    private BigDecimal amount;
    private String location;
    private Status status;

}
