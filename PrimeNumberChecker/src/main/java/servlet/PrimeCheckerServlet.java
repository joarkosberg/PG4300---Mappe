package servlet;

/*
 *  Navn: Joar Kosberg - kosjoa14
 *  Filnavn: PrimeCheckerServlet.java
 *  Oppgave: Innlevering 3 - Mappe
 */

import org.apache.commons.math3.primes.Primes;
import org.apache.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PrimeCheckerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String RESULT_VIEW = "checker.jsp";
    private static final String ERROR_VIEW = "error.jsp";
    private static final Logger inputLog = Logger.getLogger("input");
    private static final Logger errorsLog = Logger.getLogger("error");


    /**
     * Takes paramters from a HTTP POST request and dispatch new view based on input received
     * @param request an HttpServletRequest object which contains a request from client
     * @param response an HttpServletResponse object which contains response from server
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String input = request.getParameter("number");
        logInput(input);
        boolean isValid = inputChecker(input);
        try {
            if(isValid) showResult(request, response, input);
            else showError(request, response, input);
        } catch (ServletException e) { errorsLog.error("Error with servlet: " + e.getStackTrace().toString());
        } catch (IOException e) { errorsLog.error("Error with IO: " + e.getStackTrace().toString());
        }
    }


    /**
     * Takes input from user and logs it
     * @param input input from user
     * @see org.apache.log4j.Logger#info(Object)
     */
    private void logInput(String input) {
        inputLog.info("Input: " + input);
    }


    /**
     * If error has happend, go to error view
     * @param request an HttpServletRequest object which contains a request from client
     * @param response an HttpServletResponse object which contains response from server
     * @param input input from user
     * @see javax.servlet.ServletRequest#setAttribute(String, Object)
     */
    private void showError(HttpServletRequest request, HttpServletResponse response, String input)
            throws ServletException, IOException {
        request.setAttribute("error", "Error");
        dispatchView(request, response, ERROR_VIEW);
    }


    /**
     * If input was correct and answer is ready go to result view
     * @param request an HttpServletRequest object which contains a request from client
     * @param response an HttpServletResponse object which contains response from server
     * @param input input from user
     * @see javax.servlet.ServletRequest#setAttribute(String, Object)
     */
    private void showResult(HttpServletRequest request, HttpServletResponse response, String input)
            throws ServletException, IOException {
        int number = Integer.parseInt(input);
        boolean isPrime = primeChecker(number);
        request.setAttribute("prime", isPrime);
        request.setAttribute("number", number);
        dispatchView(request, response, RESULT_VIEW);
    }


    /**
     * Takes input and check if its valid
     * @param input input from user
     * @return a boolean value, true or false
     * @see String#matches(String)
     */
    public boolean inputChecker(String input) {
        if (input.length() < 1) return false;
        else if (input.length() > 10) return false;
        if (input.matches("[0-9]+")) {
            if (Long.parseLong(input) > 2147483647) return false;
            else return true;
        }
        else return false;
    }


    /**
     * Takes a number and returns if number is prime or not
     * @param number that is checked
     * @return a boolean value, true or false
     * @see org.apache.commons.math3.primes.Primes#isPrime(int)
     */
    public boolean primeChecker(int number){
        return Primes.isPrime(number);
    }


    /**
     * Takes a number and returns if number is prime or not
     * @param request an HttpServletRequest object which contains a request from client
     * @param response an HttpServletResponse object which contains response from server
     * @see javax.servlet.RequestDispatcher#forward(ServletRequest, ServletResponse)
     */
    private void dispatchView(HttpServletRequest request, HttpServletResponse response, String file)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(file);
        view.forward(request, response);
    }
}
