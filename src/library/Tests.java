package library;

import library.entities.*;
import library.entities.helpers.*;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    @Test
    public void testOverdueFineCalculation(){
        //arrange
        IPatron patron=new Patron("john","doe","doe@mail.com",1224345445,1);
        IBookHelper bookHelper=new BookHelper();
        IPatronHelper patronHelper=new PatronHelper();
        ILoanHelper loanHelper=new LoanHelper();
        Library lib=new Library(bookHelper,patronHelper,loanHelper);
        Book book=new Book("author","title","1234",3);
        CalendarFileHelper calendar=new CalendarFileHelper();
        ILoan loan=new Loan(book,patron,calendar.loadCalendar().getDueDate(1), ILoan.LoanState.OVER_DUE,1);

        double expected=1.0;
        double actual;
        //act
        actual=lib.calculateOverDueFine(loan);

        assertNotEquals(expected,actual);
    }
    @Test
    public void incorrectAmountLevied(){
        //arrange
        IPatron patron=new Patron("john","doe","doe@mail.com",1224345445,1);
        IBookHelper bookHelper=new BookHelper();
        IPatronHelper patronHelper=new PatronHelper();
        ILoanHelper loanHelper=new LoanHelper();
        Library lib=new Library(bookHelper,patronHelper,loanHelper);
        Book book=new Book("author","title","1234",3);
        CalendarFileHelper calendar=new CalendarFileHelper();
        ILoan loan=new Loan(book,patron,calendar.loadCalendar().getDueDate(-4), ILoan.LoanState.OVER_DUE,1);

        double expected=4.0;
        double actual;
        //act
        actual=lib.calculateOverDueFine(loan);
        System.out.println(actual);
        assertEquals(expected,actual);
    }

    @Test
    public void incorrectLoanState(){
        IPatron patron=new Patron("john","doe","doe@mail.com",1224345445,1);
        IBookHelper bookHelper=new BookHelper();
        IPatronHelper patronHelper=new PatronHelper();
        ILoanHelper loanHelper=new LoanHelper();
        Book book=new Book("author","title","1234",3);
        CalendarFileHelper calendar=new CalendarFileHelper();
        ILoan loan=new Loan(book,patron,calendar.loadCalendar().getDueDate(2), ILoan.LoanState.CURRENT,1);

        loan.updateOverDueStatus(calendar.loadCalendar().getDate());

        assertEquals (loan.isOverDue(),false);
    }

    @Test
    public void incorrectPatronStatus(){
        IPatron patron=new Patron("john","doe","doe@mail.com",1224345445,1);


        patron.payFine(1);

        assertNotEquals (patron.getFinesPayable(),1);
    }

    @Test
    public void negativeFineAmount(){
        IPatron patron=new Patron("john","doe","doe@mail.com",1224345445,1);
        IBookHelper bookHelper=new BookHelper();
        IPatronHelper patronHelper=new PatronHelper();
        ILoanHelper loanHelper=new LoanHelper();
        Book book=new Book("author","title","1234",3);
        CalendarFileHelper calendar=new CalendarFileHelper();
        ILoan loan=new Loan(book,patron,calendar.loadCalendar().getDueDate(2), ILoan.LoanState.CURRENT,1);

        loan.updateOverDueStatus(calendar.loadCalendar().getDate());

        assertNotEquals (loan.getPatron().getFinesPayable(),1);
    }
}
