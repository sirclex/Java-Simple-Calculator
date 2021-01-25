/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import view.CalculatorView;

/**
 * Contain method for controlling user interface
 *
 * @author pc
 */
public class CalculatorController implements ActionListener {

    private CalculatorView calculator;
    private DecimalFormat decimalFormat;

    private char operator;
    private boolean ready;
    private BigDecimal result;
    private BigDecimal mResult;

    /**
     * Constructor
     *
     * @param view Object of <Code>javax.swing.JFrame</Code>
     */
    public CalculatorController(CalculatorView calculator) {
        this.calculator = calculator;
        this.decimalFormat = new DecimalFormat("#.##########");
        this.operator = 0;
        this.ready = true;
        this.result = BigDecimal.ZERO;
        this.mResult = BigDecimal.ZERO;
        addButtonEvent();
    }

    /**
     * Add action listener for number buttons
     */
    public void addButtonEvent() {
        calculator.getBtnNum0().addActionListener(this);
        calculator.getBtnNum1().addActionListener(this);
        calculator.getBtnNum2().addActionListener(this);
        calculator.getBtnNum3().addActionListener(this);
        calculator.getBtnNum4().addActionListener(this);
        calculator.getBtnNum5().addActionListener(this);
        calculator.getBtnNum6().addActionListener(this);
        calculator.getBtnNum7().addActionListener(this);
        calculator.getBtnNum8().addActionListener(this);
        calculator.getBtnNum9().addActionListener(this);
    }

    /**
     * Event of number buttons
     *
     * @param e Object of <Code>java.awt.event.ActionEvent</Code>
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (ready) {
            result = getCurrentNumber();
            setText(command);
            ready = false;
        } else {
            String txt;
            if (getCurrentText().equals("Error")) {
                txt = command;
            } else {
                txt = getCurrentText() + command;
            }
            BigDecimal number = getNumber(txt);
            setText(number + "");
        }

    }

    /**
     * Perform calculate (Equal button)
     */
    public void perform() {
        calculate();
        operator = 0;
        ready = true;
    }

    /**
     * Set operator for controller
     *
     * @param operate Flag number of operator (1, 2, 3, 4) <Code>char</Code>
     */
    public void setOperator(char operate) {
        if (ready) {
            operator = operate;
            return;
        }
        calculate();
        operator = operate;
    }

    /**
     * Calculate function with given operator
     */
    public void calculate() {
        switch (operator) {
            case '+': {
                result = result.add(getCurrentNumber());
                break;
            }

            case '-': {
                result = result.subtract(getCurrentNumber());
                break;
            }

            case '*': {
                result = result.multiply(getCurrentNumber());
                break;
            }

            case '/': {
                if (getCurrentNumber().doubleValue() == 0) {
                    setText("Error");
                    return;
                } else {
                    result = result.divide(getCurrentNumber());
                }
                break;
            }

            default: {
                result = getCurrentNumber();
                break;
            }
        }
        setText(result + "");
        ready = true;
    }

    /**
     * Get current text on screen
     *
     * @return the object of <Code>String</Code>
     */
    public String getCurrentText() {
        return calculator.getTxtDisplay().getText();
    }

    /**
     * Set text on screen follow DecimalFormat of a double
     *
     * @param number Number need printing to screen.<Code>double</Code>
     */
    public void setText(double number) {
        calculator.getTxtDisplay().setText(decimalFormat.format(number));
    }

    /**
     * Set text on screen to given string
     *
     * @param text <Code>String</Code> need printing
     */
    public void setText(String text) {
        calculator.getTxtDisplay().setText(text);
    }

    /**
     * Get current string on screen and make it a BigDecimal number
     *
     * @return the object of <Code>BigDecimal</Code>
     */
    public BigDecimal getCurrentNumber() {
        try {
            return new BigDecimal(getCurrentText());
        } catch (NumberFormatException e) {
            return result;
        }
    }

    /**
     * Get random string and make it a BigDecimal number
     *
     * @param string <Code>String</Code> need converting
     * @return the object of <Code>BigDecimal</Code>
     */
    public BigDecimal getNumber(String string) {
        try {
            return new BigDecimal(string);
        } catch (NumberFormatException e) {
            return result;
        }
    }

    /**
     * Check if number on screen have dot or not
     *
     * @return <Code>boolean</Code> datatype
     */
    public boolean haveDot() {
        return getCurrentText().contains(".");
    }

    /**
     * Percentile number on screen
     */
    public void percentile() {
        setText(getCurrentNumber().doubleValue() / 100);
        ready = true;
    }

    /**
     * Invert number on screen
     */
    public void invert() {
        if (getCurrentNumber().doubleValue() == 0) {
            setText("Error");
            result = new BigDecimal("0");
        } else {
            setText(1 / getCurrentNumber().doubleValue());
        }
        ready = true;
    }

    /**
     * Get the SquareRoot of number on screen
     */
    public void sqrt() {
        if (getCurrentNumber().doubleValue() < 0) {
            setText("Error");
            return;
        }
        setText(Math.sqrt(getCurrentNumber().doubleValue()));
        ready = true;
    }

    /**
     * Negate number on screen
     */
    public void negate() {
        setText(getCurrentNumber().negate() + "");
    }

    /**
     * Add dot for number on screen
     */
    public void addDot() {
        if (!haveDot()) {
            setText(getCurrentText() + ".");
        }
        if (ready == true) {
            setText("0.");
        }
        ready = false;
    }

    /**
     * Clear the calculator
     */
    public void clear() {
        setText("0");
        mClear();
        result = BigDecimal.ZERO;
        operator = 0;
    }

    /**
     * Add number on screen to memory and store memory
     */
    public void mAdd() {
        mResult = mResult.add(getCurrentNumber());
        ready = true;
    }

    /**
     * Subtract memory with number on screen and store in memory
     */
    public void mSub() {
        mResult = mResult.subtract(getCurrentNumber());
        ready = true;
    }

    /**
     * Show the number in memory to the screen
     */
    public void mRes() {
        setText(this.mResult + "");
        ready = true;
    }

    /**
     * Clear the memory
     */
    public void mClear() {
        mResult = BigDecimal.ZERO;
        ready = true;
    }

    /**
     * Get the user interface
     *
     * @return the object of <Code>view.CalculatorView</Code>
     */
    public CalculatorView getView() {
        return calculator;
    }

    /**
     * Set the user interface
     *
     * @param view Object of <Code>view.CalculatorView</Code>
     */
    public void setView(CalculatorView calculator) {
        this.calculator = calculator;
    }

    /**
     * Get the decimal format
     *
     * @return the object of <Code>DecimalFormat</Code>
     */
    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    /**
     * Set the decimal format
     *
     * @param decimalFormat Object of <Code>DecimalFormat</Code>
     */
    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    /**
     * Check if calculator is ready to get new number or not
     *
     * @return <Code>boolean</Code> datatype
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Set ready variables
     *
     * @param ready <Code>boolean</Code> datatype
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * Get the result of calculating
     *
     * @return the object of <Code>BigDecimal</Code>
     */
    public BigDecimal getResult() {
        return result;
    }

    /**
     * Set result to given number
     *
     * @param result Object of <Code>BigDecimal</Code>
     */
    public void setResult(BigDecimal result) {
        this.result = result;
    }

    /**
     * Get the result in memory
     *
     * @return Object of <Code>BigDecimal</Code>
     */
    public BigDecimal getmResult() {
        return mResult;
    }

    /**
     * Set result of memory to given number
     *
     * @param mResult Object of <Code>BigDecimal</Code>
     */
    public void setmResult(BigDecimal mResult) {
        this.mResult = mResult;
    }

    /**
     * Get the current operator
     * @return <Code>char</Code> datatype
     */
    public char getOperator() {
        return operator;
    }
    
    

}
