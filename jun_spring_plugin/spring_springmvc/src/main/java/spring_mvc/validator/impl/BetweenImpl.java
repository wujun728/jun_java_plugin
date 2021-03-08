package spring_mvc.validator.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import spring_mvc.validator.Inf.Between;

/**
 * 自定义校验
 * @author Wujun
 *
 */
public class BetweenImpl implements ConstraintValidator<Between, Date> {
	private Date startDate;
	private Date endDate;
	private DateFormat dateFormat;
	@Override
	public void initialize(Between bt) {
		// TODO Auto-generated method stub
		System.out.println("---");
		try {
			this.dateFormat=new SimpleDateFormat(bt.format());
			this.startDate=dateFormat.parse(bt.startDate());
			this.endDate=dateFormat.parse(bt.endDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean isValid(Date value, ConstraintValidatorContext cvc) {
		// TODO Auto-generated method stub
		System.out.println("isValid");
		return (value.getTime() >= startDate.getTime() && value.getTime() <= endDate.getTime());
	}

}
