package com.nseaf.mycalculator;

public class Calculator {
    private String numberString = "0";
    private String detailsString = "";
    private long intNumber;
    private double realNumber;
    private boolean isIntNumber = true;
    private boolean numHasRadixPoint = false;
    private long memoryInt = 0;
    private double memoryDouble = 0.0;
    private boolean isIntMemory = true;
    private char currentOperation;
    private double operand1;

    public Calculator() {
    }

    public String getNumberString() {
        return numberString;
    }

    public String getDetailsString() {
        return detailsString;
    }

    public void processNumber(int i) {
        if (numberString.length() < 12) {  // limit of 12 digits
            if (isIntNumber) {
                intNumber = intNumber * 10 + i;
                numberString = String.valueOf(intNumber);
            } else {
                numberString += i;
                realNumber = Double.parseDouble(numberString);
            }
            detailsString = "Clicked: " + i;
        } else {
            detailsString = "The number is too long..";
        }
    }

    public void processDecimalPoint() {
        if (!numHasRadixPoint) {
            numHasRadixPoint = true;
            numberString += ".";
            isIntNumber = false;
        }
    }

    public void clearClicked() {
        if (numberString.equals("0")) {
            // Reset calculator
            numberString = "0";
            detailsString = "";
            intNumber = 0;
            realNumber = 0.0;
            isIntNumber = true;
            numHasRadixPoint = false;
            operand1 = 0.0;
        } else {
            // Clear current number
            numberString = "0";
            detailsString = "";
            intNumber = 0;
            realNumber = 0.0;
            isIntNumber = true;
            numHasRadixPoint = false;
        }
    }

    public void memPlusClicked() {
        if (isIntMemory) {
            if (isIntNumber) {
                memoryInt += intNumber;
            } else {
                isIntMemory = false;
                memoryDouble += realNumber;
            }
        } else {
            memoryDouble += realNumber;
        }
        detailsString = "Memory: " + (isIntMemory ? memoryInt : memoryDouble);
    }

    public void memMinusClicked() {
        if (isIntMemory) {
            if (isIntNumber) {
                memoryInt -= intNumber;
            } else {
                isIntMemory = false;
                memoryDouble -= realNumber;
            }
        } else {
            memoryDouble -= realNumber;
        }
        detailsString = "Memory: " + (isIntMemory ? memoryInt : memoryDouble);
    }

    public void memClearClicked() {
        memoryInt = 0;
        memoryDouble = 0.0;
        isIntMemory = true;
        detailsString = "Memory Cleared";
    }

    public void memRecallClicked() {
        if (isIntMemory) {
            numberString = String.valueOf(memoryInt);
            intNumber = memoryInt;
        } else {
            numberString = String.valueOf(memoryDouble);
            realNumber = memoryDouble;
        }
        detailsString = "Memory Recalled";
    }

    public void percentageClicked() {
        double currentNumber = isIntNumber ? intNumber : realNumber;
        double result = currentNumber / 100;
        numberString = String.valueOf(result);
        if (isIntNumber) {
            intNumber = (long) result;
            isIntNumber = false;
        } else {
            realNumber = result;
        }
        detailsString = "Percentage: " + result;
    }

    public void operationClicked(char operation) {
        operand1 = isIntNumber ? intNumber : realNumber;
        currentOperation = operation;
        clearClicked();
    }

    public void equalsClicked() {
        double operand2 = isIntNumber ? intNumber : realNumber;
        double result = 0.0;
        switch (currentOperation) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                if (operand2 != 0) {
                    result = operand1 / operand2;
                } else {
                    detailsString = "Error: Divide by zero";
                    return;
                }
                break;
            default:
                result = operand2;
                break;
        }
        numberString = String.valueOf(result);
        if (isIntNumber) {
            intNumber = (long) result;
            isIntNumber = false;
        } else {
            realNumber = result;
        }
        detailsString = "Result: " + result;
    }

}
